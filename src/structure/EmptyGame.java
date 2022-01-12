package structure;

/**
 * the GUI needs to be satisfied with an EmptyGame before a real game is connected to it
 */
public class EmptyGame  extends Game{
    public EmptyGame(Output op) {
        super(op);
    }

    @Override
    public void reset() {

    }

    @Override
    protected void act() {

    }
}
