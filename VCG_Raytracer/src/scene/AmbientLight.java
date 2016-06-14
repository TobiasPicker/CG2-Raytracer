package scene;

import utils.RgbColor;

public class AmbientLight{

    RgbColor color;

    public AmbientLight(RgbColor color){
        this.color = color;
    }

    ///// Getter & Setter /////

    public RgbColor getColor() {
        return color;
    }
}
