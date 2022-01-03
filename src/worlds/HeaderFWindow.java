package worlds;

import structure.Printer;
import structure.Settings;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * Provides a header that is to be seen on top of the Application and can be filled with any Text
 */
public class HeaderFWindow extends FWindow implements Printer{
    private JPanel header;
    private JLabel headerText;

    public HeaderFWindow(int x, int y, Settings settings) {
        super(x, y, settings);
        this.header = new JPanel();
        this.headerText = new JLabel();
        this.headerText.setVerticalAlignment(SwingConstants.CENTER);
        this.getGridPanel().getParent().setPreferredSize(new Dimension(780, 0));

        header.setLayout(new BorderLayout());
        header.add(headerText, BorderLayout.CENTER);
        header.setPreferredSize(new Dimension(0,20));
        this.add(header, BorderLayout.NORTH);
    }


    /**
     * sets the text of the header
     * @param text
     */
    public void setHeader(String text){
        headerText.setText(text);
    }


    /**
     * returns text of the header
     * @return
     */
    public String getHeader(){
        return headerText.getText();
    }


    /**
     * this method lets you choose on which side of the header the text is standing
     * @param alignment one of the following SwingConstants: LEFT, CENTER, RIGHT
     */
    public void setHeaderTextAlignment(int alignment){
        switch(alignment){
            case SwingConstants.LEFT -> headerText.setHorizontalAlignment(SwingConstants.LEFT);
            case SwingConstants.RIGHT -> headerText.setHorizontalAlignment(SwingConstants.RIGHT);
            case SwingConstants.CENTER -> headerText.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public void setHeaderSize(int height){
        header.setPreferredSize(new Dimension(0, height));
    }

    @Override
    public void write(String text) {
        setHeader(text);
    }
}
