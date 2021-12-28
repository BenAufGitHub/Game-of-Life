package structure;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;


public class ErrorHandler {

    /**
    disposes the process and informs user of an error
     */
    public static void catchError(GUI window, Exception e, int errorCode){
        new Thread(() -> {
            informUser(e, errorCode);
        }).start();

        if(window == null){
            throw new RuntimeException();
        }
        window.setVisible(false);
        window.dispose();
    }

    /**
    opens a window for insightful error message
     */
    private static void informUser(Exception e, int errorCode) {
        JFrame frame = new ErrorFrame("An issue occurred", errorCode);
        JTextArea text = new JTextArea("", 3, 20);
        JScrollPane scroll = new JScrollPane (text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        text.append(getText(e, errorCode));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setBackground(Color.DARK_GRAY);
        text.setForeground(Color.CYAN);

        scroll.setPreferredSize(new Dimension(380,380));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setSize(400,400);
        frame.getContentPane().setBackground(Color.BLACK);

        frame.add(scroll);
        frame.setVisible(true);
    }


    /**
    get all printed text for window
     */
    private static String getText(Exception e, int errorCode) {
        StringBuilder text = new StringBuilder();
        text.append(getErrorMessage(errorCode));
        text.append("\nYou can review the whole java error below:\n\n");

        StringWriter stackTraceWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTraceWriter));
        text.append(stackTraceWriter.toString());

        return text.toString();
    }


    /**
    custom error message for each code, done w switch-case
     */
    private static String getErrorMessage(int errorCode) {
        switch(errorCode){
            case(2):
                return "Something interrupted the game process.";
            case(3):
                return "The run process was suddenly interrupted.";
            case(4):
                return "The gui doesn't support the input numbers.";
            case(5):
                return "World is too big.";
            default:
                return "An error occurred.";
        }
    }


    /**
    in use to override processWindowEvent, which lets us exit the sys with the proper exit code
     */
    static class ErrorFrame extends JFrame{
        int errorCode;

        public ErrorFrame(String s, int errorcode){
            super(s);
            this.errorCode = errorcode;
        }

        @Override
        public void processWindowEvent(WindowEvent e) {
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                dispose();
                System.exit(errorCode);
            }
        }
    }

}
