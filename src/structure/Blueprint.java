package structure;

import javax.swing.*;
import java.awt.*;

public class Blueprint {

    public final Color color;
    public final Image icon;
    public final boolean iconChange;

    public Blueprint(Color color, ImageIcon icon){
        this.color = color;
        this.icon = icon.getImage();
        this.iconChange = true;
    }

    public Blueprint(Color color){
        this.color = color;
        this.iconChange = false;
        this.icon = null;
    }
}
