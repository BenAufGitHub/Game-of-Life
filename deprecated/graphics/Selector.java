package graphics;

import components.Cell;
import components.CellTracker;

public class Selector{

    SelectActionListener listener;


    public Selector(SelectActionListener listener){
        this.listener = listener;
    }


    public void preselect(){
        select(0,1);
        select(1,0);
        select(2,1);
        select(2,3);
        select(3,2);
        select(4,2);
    }


    public void select(int x, int y){
        listener.select(x, y);
    }

}
