package scene;

import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;

public class Shape extends SceneObject {

    public Shape(Vec3 position){
        super(position);
    }

    public double intersect(Ray ray){
        return 0.0;
    }

    public RgbColor getColor(){
        return new RgbColor(0,0,0);
    }
}
