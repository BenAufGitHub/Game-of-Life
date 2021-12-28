package gol_extension;

import structure.Output;
import structure.Printer;

public class CountingGOL extends GameOfLife{
    private String outline = "The Game Of Life / Generation: ";
    private int count = 1;
    private Printer counter;


    /**
     * the coordinates need to be in bounds of output grid, else the Error-Handler takes over
     *
     * @param op -> output
     * @param x
     * @param y
     */
    public CountingGOL(Output op, int x, int y) {
        super(op, x, y);
        counter = null;
    }

    public CountingGOL(Output op, int x, int y, Printer countPrinter) {
        super(op, x, y);
        counter = countPrinter;
    }

    public void setCountPrinter(Printer printer){
        this.counter = printer;
    }

    @Override
    public void act(){
        super.act();
        count();
    }

    @Override
    public void reset(){
        super.reset();
        count = 0;
        count();
    }

    private void count(){
        String text = outline + (++count);
        if(counter == null)
            System.out.println(text);
        else{
            counter.write(text);
        }
    }
}
