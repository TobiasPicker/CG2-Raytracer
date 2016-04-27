package scene;

import utils.Vec3;

/**
 * Created by PraktikumCG on 27.04.2016.
 */
public class Light extends SceneObject{

    Vec3 color;

    public Light(Vec3 position, Vec3 color){
        super(position);
        this.color = color;
    }

    public Vec3 getColor(){
        return color;
    }
}
