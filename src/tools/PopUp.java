package tools;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class PopUp<R> extends JDialog {

    private R result = null;

    public PopUp(JFrame parent){
        super(parent, true);
    }

    public R getResult(){
        return result;
    }

    protected void setResult(R result){
        this.result = result;
    }

    public boolean noResult(){
        return result == null;
    }
}
