package fourConnect_extension.grid;

public class GridNode<T> {
    private final int x;
    private final int y;
    private final Grid<T> grid;
    private T content;

    public GridNode(int x, int y, Grid<T> grid, T content){
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.content = content;
    }

    GridNode<T> getLeft(){
        if(!grid.isInBounds(x-1, y))
            return null;
        return grid.getNode(x-1, y);
    }

    GridNode<T> getRight(){
        if(!grid.isInBounds(x+1, y))
            return null;
        return grid.getNode(x+1, y);
    }

    GridNode<T> getUp(){
        if(!grid.isInBounds(x, y-1))
            return null;
        return grid.getNode(x, y-1);
    }

    GridNode<T> getDown(){
        if(!grid.isInBounds(x, y+1))
            return null;
        return grid.getNode(x, y+1);
    }




    // ------------------------------------ getter / setter -----------------------------------------------

    public void setContent(T content){
        this.content = content;
    }

    public Grid<T> getGrid(){
        return  grid;
    }

    public T getContent(){
        return content;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
