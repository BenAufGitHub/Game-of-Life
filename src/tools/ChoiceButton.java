package tools;

import javax.swing.JButton;


/**
 * pass a String array: by every click, the button changes text to next item + performs action for it
 * params:
 * choices: whatever gets put as an option for the button
 * listener: (func. interface: ChoiceButton.ChoiceListener)
 *      '-> when clicked, selected choice will be param for listener.perform(String)
 */
public class ChoiceButton extends JButton {

    private ChoiceListener listener;
    private String[] choices;
    private int choice;


    /**
     * @param choices represents possible states of the Program
     * @param listener receives the selected choices and can operate with them
     */
    public ChoiceButton(String[] choices, ChoiceListener listener){
        String[] empty = {"---"};
        this.choices = (choices != null) ? choices : empty;
        this.listener = listener;
        this.choice = 0;

        this.addActionListener(e -> next());
        this.setText(this.choices[0]);
    }


    public void setListener(ChoiceListener listener){
        this.listener = listener;
    }


    public String getCurrentState(){
        return choices[choice];
    }


    /**
     * gets called by click on Object, switches to next Choice -> calls listener
     */
    protected void next(){
        if(choices.length < 2)
            return;
        if((++choice)==choices.length){
            choice = 0;
        }
        setText(choices[choice]);
        if(listener != null)
            listener.perform(choices[choice]);
    }
}
