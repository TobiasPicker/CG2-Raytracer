package scene;

import raytracer.Intersection;
import raytracer.Ray;
import utils.Material;
import utils.Vec3;

public class Shape extends SceneObject {

    public String objectType;
    private Material material;
    private Vec3 normal;

    public Shape(Vec3 position, Material material, String objectType){
        super(position);
        this.material = material;
        this.objectType = objectType;
    }

    public Shape(Vec3 position, Vec3 normal, Material material, String objectType){
        super(position);
        this.normal = normal;
        this.material = material;
        this.objectType = objectType;
    }

    public Intersection intersect(Ray ray){
        return new Intersection(false);
    }

    public Material getMaterial(){
        return this.material;
    }

    public Vec3 getNormal() {
        return normal;
    }
}
