package structure;

import javax.swing.BorderFactory;
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
    public static void catchError(GUI disposable, Exception e, int errorCode, boolean exit){
        new Thread(() -> {
            informUser(e, errorCode, exit);
        }).start();

        if(disposable == null){
            throw new RuntimeException(e);
        }
        disposable.setVisible(false);
        disposable.dispose();
    }


    public static void catchError(Exception e, int errorCode, boolean exit){
        catchError(null, e, errorCode, exit);
    }


    /**
     opens a window for insightful error message
     */
    private static void informUser(Exception e, int errorCode, boolean exit) {
        JFrame frame = new ErrorFrame("An issue occurred", errorCode, exit);
        JTextArea text = new JTextArea("", 3, 20);
        JScrollPane scroll = new JScrollPane (text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        text.append(getText(e, errorCode));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setBackground(Color.DARK_GRAY);
        text.setForeground(Color.CYAN);

        scroll.setPreferredSize(new Dimension(380,380));
        scroll.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);

        frame.add(scroll);
        frame.setVisible(true);
        frame.pack();
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
        return switch (errorCode) {
            case (2) -> "Something interrupted the game process.";
            case (3) -> "The run process was suddenly interrupted.";
            case (4) -> "The gui doesn't support the input numbers.";
            case (5) -> "World is too big.";
            case (6) -> "File does not exist or is faulty.";
            default -> "An error occurred.";
        };
    }


    /**
     * In use to override processWindowEvent, which lets us exit the sys with the proper exit code if it has a parent window to remove.
     */
    static class ErrorFrame extends JFrame{
        boolean exit;
        int errorCode;

        public ErrorFrame(String s, int errorcode, boolean exit){
            super(s);
            this.errorCode = errorcode;
            this.exit = exit;
        }

        @Override
        public void processWindowEvent(WindowEvent e) {
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                dispose();
                if(exit)
                    System.exit(errorCode);
            }
        }
    }

}