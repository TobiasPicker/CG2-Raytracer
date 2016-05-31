package scene;

import raytracer.Intersection;
import utils.Log;
import utils.Material;
import utils.Matrix4;
import utils.Vec3;
import raytracer.Ray;

public class Sphere extends Shape {

    private float radius;
    private Matrix4 inversePointMatrix;

    public Sphere(Vec3 position, float radius, Material material){
        super(position, material, "Sphere");
        this.radius = radius;
        this.pointMatrix = pointMatrix.scale(new Vec3(radius,radius,radius));
        this.inversePointMatrix = pointMatrix.invert();
       Log.print(this, ""+pointMatrix);
    }

    public  Intersection intersect(Ray ray){

        //Log.print(this, ""+inversePointMatrix);
        //Log.print(this, "ray origin vor trafo: "+ray.getpOrigin());
        //beamen///////////////////
        ray.setpOrigin(inversePointMatrix.multVec3(ray.getpOrigin(), true));
        ray.setDirection(inversePointMatrix.multVec3(ray.getDirection(), false));
        ///////////////////////////

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
            double t0 = (-b - Math.sqrt(discriminant)) / 2d;
            double t1 = (-b + Math.sqrt(discriminant)) / 2d;

            if(Math.abs(t0)<Math.abs(t1)){
                t = t0;
            }else{
                t = t1;
            }

            //Log.print(this, "T: "+t);

            if (t > 10E-9) {
                ///beamen////////////////////////
                Vec3 intersectionPoint = ray.getpOrigin().add(ray.getDirection().multScalar((float)t));
                //Log.print(this, ""+intersectionPoint);
                intersectionPoint = pointMatrix.multVec3(intersectionPoint, true);
                //Log.print(this, ""+intersectionPoint);
                //Log.print(this, ""+pointMatrix);
                //Log.print(this, ray.getDirection()+", "+ray.getpOrigin()+", "+t);
                Vec3 normal = intersectionPoint.sub(this.position);
                normal = normal.normalize();
                /////////////////////////////////
                //retransformation of ray
                ray.setpOrigin(pointMatrix.multVec3(ray.getpOrigin(), true));
                ray.setDirection(pointMatrix.multVec3(ray.getDirection(), false));

                //Log.print(this, "ray origin nach trafo: "+ray.getpOrigin());
                return new Intersection(ray,(float)t, intersectionPoint, normal, this, true);
            } else {
                return new Intersection(false);
            }
        }
    }
}

