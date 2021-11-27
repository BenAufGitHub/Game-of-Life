package graphics;


import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorHandler {

    /*
    only publicly callable message, disposes all other processes and informs user of an error
     */
    public static void catchError(GUI window, Exception e, int errorCode){
        informUser(e, errorCode);
        window.setVisible(false);
        window.dispose();
        System.exit(errorCode);
    }

    /*
    opens a window for insightful error message
     */
    private static void informUser(Exception e, int errorCode) {
        JFrame frame = new JFrame("An issue occurred");
        JTextArea text = new JTextArea();

        text.append(getText(e, errorCode));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(200,400);
        frame.setVisible(true);
    }


    /*
    get all printed text for window
     */
    private static String getText(Exception e, int errorCode) {
        StringBuilder text = new StringBuilder();
        text.append(getErrorMessage(errorCode));
        text.append("\nYou can review the whole java error below:\n");

        StringWriter stackTraceWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTraceWriter));
        text.append(e.toString() + "\n" + stackTraceWriter.toString());
        return text.toString();
    }

    /*
    custom error message for each code, done w switch-case
     */
    private static String getErrorMessage(int errorCode) {
        switch(errorCode){
            case(2):
                return "Something interrupted the game process.";
            default:
                return "An error occurred";
        }
    }


}
