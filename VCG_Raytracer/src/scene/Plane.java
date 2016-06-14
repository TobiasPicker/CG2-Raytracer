package scene;

import raytracer.Intersection;
import raytracer.Ray;
import utils.Material;
import utils.Vec3;

public class Plane extends Shape {

    private Vec3 normal;

    //constructor
    public Plane(Vec3 position, Vec3 normal, Material material){
        super(position, normal, material, "Plane");
        this.normal = normal;
    }

    //calculation of intersection between ray and plane
    public Intersection intersect(Ray ray){

        //ray does not hit
        if(this.normal.scalar(ray.getDirection()) == 0){
            return new Intersection(false);
        }

        //ray does hit
        else {
            float q = -this.normal.scalar(this.position); //distance between coordinate origin and plane
            float t = -(this.normal.scalar(ray.getpOrigin()) + q) / this.normal.scalar(ray.getDirection()); //distance between camera and plane

            //creates an intersection object
            if (t > 10E-9) {
                return new Intersection(ray, t, this, true);
            } else {
                return new Intersection(false);
            }
        }
    }
}
