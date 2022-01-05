package tools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChoiceWindow extends PopUp<String> implements MouseListener{

    private String[] choices;

    private final int maxPanelHeight = 100;
    private final int panelMargin = 10;
    private final int panelButtonDistance = 10;
    private final int textWidth = 160;
    private final int labelHeight = 25;

    private final int optionsPerSite;
    private final int panelAmount;
    private final int panelHeight;

    private PanelHolder holder;
    private JPanel arrows;
    private JPanel container;
    private JPanel current;

    public ChoiceWindow(String[] choices, JFrame parent){
        super(parent);
        this.choices = choices;
        this.container = new JPanel();

        ((FlowLayout) container.getLayout()).setHgap(0);
        ((FlowLayout) container.getLayout()).setVgap(0);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(parent);
        this.setTitle("Save File Options");

        this.optionsPerSite = calcOptionsPerSide();
        this.panelAmount = calcPanelAmount(optionsPerSite);
        this.panelHeight = calcPanelHeight(optionsPerSite, labelHeight);
        this.holder = new PanelHolder(panelAmount);

        setUpChoiceContainer();
        setUpArrows();

        this.current = holder.get();
        this.add(container, BorderLayout.NORTH);
        this.add(arrows, BorderLayout.SOUTH);
        this.pack();
    }


    /**
     * The setup will be completed by adding the container to the window.
     */
    private void setUpChoiceContainer() {
        JLabel[] options = getChoiceLabels(choices);
        customizeAllPanels();
        for(int i=0; i<options.length; i++){
            addLabel(i, options[i]);
        }
        fillLastPanel();
        container.add(holder.next());
    }


    /**
     * sets up left/right click arrow to change the page
     */
    private void setUpArrows() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JButton left = new JButton("<--");
        JButton right = new JButton("-->");

        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Dimension dim = new Dimension(40,20);

        left.setBorder(border);
        left.setFocusable(false);
        left.setPreferredSize(dim);
        left.addActionListener( e -> {
            changePanel(holder.last());
        });

        right.setBorder(border);
        right.setFocusable(false);
        right.setPreferredSize(dim);
        right.addActionListener( e -> {
            changePanel(holder.next());
        });

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        this.arrows = panel;
    }


    private void changePanel(JPanel next) {
        container.remove(current);
        container.add(next);
        this.current = next;
        this.revalidate();
        this.repaint();
        this.pack();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        String result = label.getText();
        setResult(result);
        this.dispose();
    }


    //---------------------------------- label -----------------------------------------------

    private void addLabel(int number, JLabel label){
        current = holder.get();
        current.add(label);
        if(number % optionsPerSite == optionsPerSite-1)
            current = holder.next();
    }


    /**
     * prepares Label which present the options
     */
    private JLabel[] getChoiceLabels(String[] choices){
        JLabel[] array = new JLabel[choices.length];
        for(int i=0; i< choices.length; i++){

            JLabel label = getStandardLabel();
            label.setText(choices[i]);
            label.addMouseListener(this);
            array[i] = label;

        }
        return array;
    }


    private JLabel getStandardLabel(){
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        label.setPreferredSize(new Dimension(textWidth, labelHeight));
        return label;
    }


    //-------------------------------------- Panel -------------------------------------------------

    private void fillLastPanel() {
        int toBeDisplayed = optionsPerSite;
        int unfilled = toBeDisplayed - (choices.length % toBeDisplayed);
        current = holder.get();

        for(int i=0; i<unfilled; i++){
            current.add(getStandardLabel());
        }
    }

    private void customizeAllPanels() {
        for(int i=0; i<panelAmount; i++){
            JPanel panel = holder.next();
            customizePanel(panel, optionsPerSite);
        }
    }


    private void customizePanel(JPanel panel, int capacity){
        if(panel == null)
            return;
        int m = panelMargin;
        Border border = BorderFactory.createEmptyBorder(m, m, panelButtonDistance, m);
        panel.setBorder(border);
        panel.setPreferredSize(new Dimension(textWidth, panelHeight));
        panel.setLayout(new GridLayout(capacity, 1));
    }

    //---------------------------- sizing calculations ----------------------------

    private int calcPanelHeight(int elements, int elementHeight) {
        return elementHeight * elements;
    }

    private int calcOptionsPerSide() {
        return maxPanelHeight / labelHeight;
    }

    private int calcPanelAmount(int optionsPerSite) {
        return (choices.length / optionsPerSite) + 1;
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


    //------------------------- inner class -------------------------------


    private class PanelHolder{
        JPanel[] content;
        int current = 0;


        private PanelHolder(int amount){
            content = (amount > 0) ? new JPanel[amount]: new JPanel[0];
            for(int i=0; i< amount; i++){
                JPanel label = new JPanel();
                content[i] = label;
            }
        }


        private JPanel get(){
            if(content.length == 0)
                return null;
            return content[current];
        }


        private JPanel next(){
            if(empty())
                return null;
            current = (current + 1) % content.length;
            return content[current];
        }


        private JPanel last(){
            if(empty())
                return null;
            current = (current - 1 + content.length) % content.length;
            return content[current];
        }


        private boolean empty(){
            return content.length == 0;
        }
    }
}
