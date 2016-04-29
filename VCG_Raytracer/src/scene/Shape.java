package scene;

import raytracer.Ray;
import utils.Vec3;

public class Shape extends SceneObject {

    public Shape(Vec3 position){
        super(position);
    }

    public double intersect(Ray ray){
        return 0.0;
    }

}
