package scene;

import raytracer.Intersection;
import utils.Material;
import utils.Vec3;
import raytracer.Ray;
public class Sphere extends Shape {

    private float radius;

    public Sphere(Vec3 position, float radius, Material material){
        super(position, material, "Sphere");
        this.radius = radius;
    }

    public  Intersection intersect(Ray ray){

        //calculation of intersection between ray and sphere
        float b = 2*(ray.getpOrigin().scalar(ray.getDirection()));
        float c = ray.getpOrigin().x*ray.getpOrigin().x + ray.getpOrigin().y*ray.getpOrigin().y + ray.getpOrigin().z*ray.getpOrigin().z - radius*radius;
        float discriminant = b*b - 4*c;

        //ray does not hit
        if(discriminant<0){
            return new Intersection(false);
        }
        //ray does hit
        else {
            double t;
            double t0 = -b - Math.sqrt(discriminant) / 2d;
            double t1 = -b + Math.sqrt(discriminant) / 2d;

            if(Math.abs(t0)<Math.abs(t1)){
                t = t0;
            }else{
                t = t1;
            }

            if (t > 10E-9) {
                return new Intersection(ray, (float)t, this, true);
            } else {
                return new Intersection(false);
            }
        }
    }
}

