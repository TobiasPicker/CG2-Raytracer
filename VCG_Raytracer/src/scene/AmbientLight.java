package scene;

import utils.RgbColor;

/**
 * Created by PraktikumCG on 17.05.2016.
 */
public class AmbientLight{

    RgbColor color;

    public AmbientLight(RgbColor color){
        this.color = color;
    }

    public RgbColor getColor() {
        return color;
    }
}
