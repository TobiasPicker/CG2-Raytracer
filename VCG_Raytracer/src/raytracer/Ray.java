package raytracer;

import scene.Camera;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Ray{
    private Vec3 pOrigin = new Vec3();
    private Vec3 direction = new Vec3();
    private float length;

    public Ray(Vec3 direction, float length){
        this.pOrigin = pOrigin;
        this.direction = direction;
        this.length = length;
    }

}
