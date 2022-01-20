package pong_extension;

import structure.Game;

public class Ball {

    private double xVector;
    private double yVector;
    private int thickness;
    private Game game;

    private double x;
    private double y;

    private enum Border {
        LEFT, RIGHT, TOP, DOWN
    }

    public Ball(int thickness, Game game){
        setThickness(thickness);
        setGame(game);
    }


    public void act(){
        if(isAtEdge()){
            for(Border dir : Border.values()){
                if(isAtEdge(dir))
                    adjustVectorAtBounce(dir);
            }
        }
        moveOneUnit();
    }


    private void moveOneUnit() {
        double length = getVectorLength(xVector, yVector);
        double xMove = xVector / length;
        double yMove = yVector / length;
        this.x += xMove;
        this.y += yMove;
    }


    public boolean isAtEdge() {
        if(game == null)
            return false;
        if(isAtEdge(Border.LEFT) || isAtEdge(Border.RIGHT) || isAtEdge(Border.DOWN) || isAtEdge(Border.TOP))
            return true;
        return false;
    }


    // --------------------------------------- background operations -------------------------------------------

    /**
     * This method calculates length of vector.
     * @return The value by which the vector needs to be divided in order to achieve a 1-unit move.
     */
    protected double getVectorLength(double xVector, double yVector){
        return Math.sqrt(Math.pow(xVector, 2) + Math.pow(yVector, 2));
    }


    private boolean isAtEdge(Border dir){
        return switch (dir){
            case TOP -> getY()-thickness <= 0;
            case DOWN -> getY()+thickness >= getGame().getOutput().gridHeight()-1;
            case LEFT -> getX()-thickness <= 0;
            case RIGHT -> getX() +thickness >= getGame().getOutput().gridWidth()-1;
        };
    }


    private void adjustVectorAtBounce(Border dir){
        double len = getVectorLength(getXVector(), getYVector());
        int xPos = (int) ((getXVector()/len) + x);
        int yPos = (int) (getYVector()/len + y);
        if(isInBounds(xPos-thickness, yPos-thickness) && isInBounds(xPos+thickness, yPos+thickness)){
            return;
        }

        switch(dir){
            case LEFT, RIGHT -> xVector = -xVector;
            case TOP, DOWN -> yVector = -yVector;
        }
    }


    private boolean isInBounds(int x, int y){
        if(x < 0 || y<0){
            return false;
        }
        return x < getGame().getOutput().gridWidth() && y < getGame().getOutput().gridHeight();
    }

    //---------------------------------------- getter / setter --------------------------------------------------

    public Game getGame(){
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void setThickness(int thickness) {
        this.thickness = (thickness >= 0) ? thickness : 0;
    }

    public int getX(){
        return (int) x;
    }

    public int getY(){
        return (int) y;
    }

    public double getXVector(){
        return xVector;
    }

    public double getYVector(){
        return yVector;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setVector(double x, double y){
        this.xVector = x;
        this.yVector = y;
    }
}
