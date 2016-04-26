package scene;

import utils.Vec3;

/**
 * Created by Administrator on 2016/4/26.
 */
public class Plane extends Shape {

    private Vec3 normal;

    public Plane(Vec3 position, Vec3 normal){
        super(position);
        this.normal = normal;
    }
}
