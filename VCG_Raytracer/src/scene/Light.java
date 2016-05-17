package scene;

import utils.RgbColor;
import utils.Vec3;

public class Light extends SceneObject{

    RgbColor color;

    public Light(Vec3 position, RgbColor color){
        super(position);
        this.color = color;
    }

    public RgbColor getColor(){
        return color;
    }
}
