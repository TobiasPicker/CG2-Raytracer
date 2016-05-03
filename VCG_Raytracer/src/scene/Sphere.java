package scene;

import utils.RgbColor;
import utils.Vec3;
import raytracer.Ray;
public class Sphere extends Shape {

    private float radius;
    private RgbColor color = new RgbColor(1, 0f, 0f);

    public Sphere(Vec3 position,float radius){
        super(position);
        this.radius = radius;
    }

    public double intersect(Ray ray){

        //calculation of intersection between ray and sphere
        float b = 2*(ray.getpOrigin().scalar(ray.getDirection()));
        float c = ray.getpOrigin().x*ray.getpOrigin().x + ray.getpOrigin().y*ray.getpOrigin().y + ray.getpOrigin().z*ray.getpOrigin().z - radius*radius;
        float discriminant = b*b - 4*c;

        //ray does not hit
        if(discriminant<0){
            return 0.0f;
        }
        //ray does hit
        else {
            double t;
            double t0 = -b - Math.sqrt(discriminant) / 2;
            double t1 = -b + Math.sqrt(discriminant) / 2;

            if(Math.abs(t0)<Math.abs(t1)){
                t = t0;
            }else{
                t = t1;
            }

            if (t > 10E-9) {
                return t;
            } else {
                return 0.0;
            }
        }
    }

    public RgbColor getColor(){
        return this.color;
    }
}
