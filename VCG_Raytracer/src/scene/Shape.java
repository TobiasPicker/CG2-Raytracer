package scene;

import raytracer.Ray;
import utils.Material;
import utils.Vec3;

public class Shape extends SceneObject {

    public String objectType;
    private Material material;

    public Shape(Vec3 position, Material material, String objectType){
        super(position);
        this.material = material;
        this.objectType = objectType;
    }

    public double intersect(Ray ray){
        return 0.0;
    }

    public Material getMaterial(){
        return this.material;
    }
}
