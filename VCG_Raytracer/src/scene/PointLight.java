package scene;

import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 17.05.2016.
 */
public class PointLight extends Light {

    public PointLight(Vec3 position, RgbColor color){
        super(position, color);
    }
}
