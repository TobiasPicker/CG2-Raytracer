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

        Vec3 localOrigin = inversePointMatrix.multVec3(ray.getpOrigin(), true);
        Vec3 localDirection = inversePointMatrix.multVec3(ray.getDirection(), false);

        Ray tempRay = new Ray(localOrigin,localDirection, 1000000);
        //Log.print(this, ""+inversePointMatrix);
        //Log.print(this, "ray origin vor trafo: "+ray.getpOrigin());

        //calculation of intersection between ray and sphere
        float b = 2*(tempRay.getpOrigin().scalar(tempRay.getDirection()));
        float c = tempRay.getpOrigin().x*tempRay.getpOrigin().x + tempRay.getpOrigin().y*tempRay.getpOrigin().y + tempRay.getpOrigin().z*tempRay.getpOrigin().z - radius*radius;
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
                Vec3 intersectionPoint = tempRay.getpOrigin().add(tempRay.getDirection().multScalar((float)t));
                //Log.print(this, ""+intersectionPoint);
                intersectionPoint = pointMatrix.multVec3(intersectionPoint, true);
                //Log.print(this, ""+intersectionPoint);
                Log.print(this, ""+pointMatrix);
                //Log.print(this, ray.getDirection()+", "+ray.getpOrigin()+", "+t);
                Vec3 normal = intersectionPoint.sub(this.position);
                //normal = normal.normalize();
                //Log.print(this, ""+ normal);
                /////////////////////////////////
                //retransformation of ray

                //Log.print(this, "ray origin nach trafo: "+ray.getpOrigin());
                return new Intersection(ray,(float)t, intersectionPoint, normal, this, true);
            } else {
                return new Intersection(false);
            }
        }
    }
}

