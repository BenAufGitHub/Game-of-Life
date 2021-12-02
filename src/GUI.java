import javax.swing.*;

public abstract class GUI extends JFrame implements Output{
    private Game game;
    private Settings settings;
    private JLabel[][] grid;


    public GUI(){
        setSettings(new Settings());
    }


    @Override
    public void setGame(Game game){
        this.game = game;
    }

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public JLabel[][] getGrid(){
        return grid;
    }
}
