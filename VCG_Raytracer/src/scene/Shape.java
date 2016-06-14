package scene;

import raytracer.Intersection;
import raytracer.Ray;
import utils.Log;
import utils.Material;
import utils.Matrix4;
import utils.Vec3;

public class Shape extends SceneObject {

    public String objectType;
    private Material material;
    private Vec3 normal;
    protected Matrix4 pointMatrix;
    protected Matrix4 normalMatrix;

    //constructor for spheres
    public Shape(Vec3 position, Material material, String objectType){
        super(position);
        this.pointMatrix = new Matrix4().translate(position);
        this.normalMatrix = pointMatrix.invert().transpose();
        this.material = material;
        this.objectType = objectType;
    }

    //constructor for planes
    public Shape(Vec3 position, Vec3 normal, Material material, String objectType){
        super(position);
        this.normal = normal;
        this.material = material;
        this.objectType = objectType;
    }

    // publication of intersect methode
    public Intersection intersect(Ray ray){
        return new Intersection(false);
    }

    ///// Getter & Setter /////

    public Material getMaterial(){
        return material;
    }

    public Vec3 getNormal() {
        return normal;
    }
}
