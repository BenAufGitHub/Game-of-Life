import javax.swing.JFrame;

public class GUI extends JFrame {

    public final int WIDTH = 1300;
    public final int HEIGHT = 800;

    public GUI(){
        setResizable(false);
        setBounds(0,0, WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game of Life");
        setVisible(true);
    }
}
