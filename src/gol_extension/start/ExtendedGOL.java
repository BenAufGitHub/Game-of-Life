package gol_extension.start;

import gol_extension.structure.GameOfLife;
import structure.Printer;

/**
 * adding a count system to GOL, output is shown by Printer
 * adding saving and loading to GOL
 */
public class ExtendedGOL extends GameOfLife {
    private String outline = "The Game Of Life / Generation: ";
    private int count = 1;
    private Printer counter;


    /**
     * the coordinates need to be in bounds of output grid, else the Error-Handler takes over
     */
    public ExtendedGOL(WindowGOL output, int x, int y, Printer countPrinter) {
        super(output, x, y);
        counter = countPrinter;
        initSaveModule(output);
        initGameSpeed(output);

        if(counter != null)
            counter.write(outline + 1);
    }


    private void initGameSpeed(WindowGOL output) {
        //sets output-configs for slow, normal, fast and supersonic speed on Button click
        output.configureSpeedButton(800, 150, 55, 30);
    }


    private void initSaveModule(WindowGOL window) {
        LoadButton load = new LoadButton(this);
        SaveButton save = new SaveButton(this);

        load.setBounds(170, 700, 80, 25);
        load.setFocusable(false);

        save.setBounds(70, 700, 80, 25);
        save.setFocusable(false);

        window.addButton(save, false);
        window.addButton(load, false);
    }



    // ------------------------ counting ----------------------------------------


    public void setCountPrinter(Printer printer){
        this.counter = printer;
        if(counter != null)
            counter.write(outline + 1);
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
