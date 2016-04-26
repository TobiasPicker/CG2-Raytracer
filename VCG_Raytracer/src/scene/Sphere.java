package scene;

import utils.Vec3;

/**
 * Created by Administrator on 2016/4/26.
 */
public class Sphere extends Shape {

    private float radius;

    public Sphere(Vec3 position,float radius){
        super(position);
        this.radius = radius;
    }
}
