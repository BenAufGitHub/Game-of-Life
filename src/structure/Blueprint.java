package structure;

import java.awt.Color;
import java.awt.Image;

public class Blueprint {

    public final Color color;
    public final Image icon;
    public final boolean iconChange;

    public Blueprint(Color color, Image icon){
        this.color = color;
        this.icon = icon;
        this.iconChange = true;
    }

    public Blueprint(Color color){
        this.color = color;
        this.iconChange = false;
        this.icon = null;
    }
}
