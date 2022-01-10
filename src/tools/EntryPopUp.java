package tools;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EntryPopUp  extends PopUp<String>{

    private EntryVerification verifier = null;
    private JButton submit;
    private JTextField textField;
    private JLabel messageBox;
    private int minimumWidth = 150;

    public EntryPopUp(JFrame parent) {
        super(parent);

        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.submit = new JButton("submit");
        this.textField = new JTextField();
        this.messageBox = new JLabel();

        messageBox.setBorder(new EmptyBorder(4, 4, 8, 4));
        messageBox.setPreferredSize(new Dimension(minimumWidth, 30));
        messageBox.setText("Huhu \\^u^/`");

        submit.setBorder(new EmptyBorder(0, 4, 4, 4));
        submit.setFocusable(false);
        submit.addActionListener( e -> sendText());

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendText();
                }
            }

        });

        this.add(messageBox, BorderLayout.NORTH);
        this.add(textField, BorderLayout.CENTER);
        this.add(submit, BorderLayout.EAST);
        this.pack();

    }

    public void setEntryVerification(EntryVerification verifier){
        this.verifier = verifier;
    }


    public void write(String message){
        messageBox.setText(message);
        adjustMessageBoxWidth();
        pack();
    }


    private void sendText(){
        String input = textField.getText();
        if(verifier == null)
            return;
        if(!verifier.isValidEntry(input))
            return;
        setResult(textField.getText());
        this.dispose();
    }


    private void adjustMessageBoxWidth(){
        FontMetrics fm = messageBox.getFontMetrics(messageBox.getFont());
        String text = messageBox.getText();

        int width = fm.stringWidth(text) +10;
        int height = 30;

        width = Math.max(width, minimumWidth);
        messageBox.setPreferredSize(new Dimension(width, height));
    }
}
