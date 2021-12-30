package tools;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChoiceWindow extends JDialog implements MouseListener{

    private String[] choices;
    private JFrame parent;
    private String result;

    public ChoiceWindow(String[] choices, JFrame parent){
        super(parent, true);
        this.choices = choices;
        this.parent = parent;

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
        this.setLayout(new GridLayout(choices.length, 1));
        this.setTitle("Save File Options");
        this.setSize(170, 100);

        JLabel[] options = getLabels(choices);
        for(JLabel label : options){
            this.add(label);
        }

        //TODO JSCROLLPANE
    }


    /**
     * prepares Label which present the options
     */
    private JLabel[] getLabels(String[] choices){
        JLabel[] array = new JLabel[choices.length];
        for(int i=0; i< choices.length; i++){

            JLabel label = new JLabel(choices[i]);
            label.setSize(170,30);
            label.addMouseListener(this);
            array[i] = label;

        }
        return array;
    }

    public String getResult(){
        return result;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        result = label.getText();
        this.dispose();
    }


    //----------------------------- empty Methods ---------------------------------

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
