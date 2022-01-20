package pong_extension;

import structure.Game;
import structure.Output;

import java.util.ArrayList;

public class Pong extends Game {

    private ArrayList<Ball> entities = new ArrayList<>();

    public Pong(Output op) {
        super(op);
    }


    @Override
    public void reset() {
        entities.clear();
    }


    @Override
    public void onClick(int x, int y){
        Ball ball = new Ball(1, this);
        ball.setPosition(x, y);
        ball.setVector(Math.random(), Math.random());
        entities.add(ball);
    }


    @Override
    protected void act() {
        for(Ball ball : entities){
            ball.act();
            getGUI().showCircle(ball.getX(), ball.getY(), 1);
        }
        getGUI().drawNewGen();
    }

    public MyWindow getGUI(){
        return (MyWindow) getOutput();
    }
}
