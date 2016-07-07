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

    //Constructor
    public Sphere(Vec3 position, float radius, Material material){
        super(position, material, "Sphere");
        this.radius = radius;
        this.pointMatrix = pointMatrix.scale(new Vec3(radius,radius,radius));
        this.inversePointMatrix = pointMatrix.invert();
    }

    // calculation of intersection between ray and sphere
    public  Intersection intersect(Ray ray){

        //calculation of beamed ray
        Vec3 localOrigin = inversePointMatrix.multVec3(ray.getpOrigin(), true);
        Vec3 localDirection = inversePointMatrix.multVec3(ray.getDirection(), false).normalize();
        Ray tempRay = new Ray(localOrigin,localDirection, 1000000);

        //calculation of discriminant
        float b = 2*(tempRay.getpOrigin().scalar(tempRay.getDirection()));
        float c = tempRay.getpOrigin().scalar(tempRay.getpOrigin()) - radius*radius;
        float discriminant = b*b - 4*c;
        //Log.print(this,"discriminant: " + discriminant);

        //ray does not hit
        if(discriminant<0){
            return new Intersection(false);
        }

        //ray does hit
        else {
            //calculation of distance between camera and sphere
            double localT;
            double localT0 = (-b - Math.sqrt(discriminant)) / 2d;
            double localT1 = (-b + Math.sqrt(discriminant)) / 2d;

            //Log.print(this, "t0: " + localT0 + "; t1: " + localT1);

            //checking for smallest distance
            if(localT0<localT1 && localT0>10E-5){
                localT = localT0;
            }else{
                localT = localT1;
            }

            if (localT > 10E-5) {

                //calculation of beamed intersection point
                Vec3 intersectionPoint = tempRay.getpOrigin().add(tempRay.getDirection().multScalar((float)localT));

                //back-transformation
                intersectionPoint = pointMatrix.multVec3(intersectionPoint, true);
                float globalT = intersectionPoint.sub(ray.getpOrigin()).length();

                //calculation of beamed normal
                Vec3 normal = intersectionPoint.sub(this.position);
                normal = normal.normalize();

                return new Intersection(ray,globalT, intersectionPoint, normal, this, true);

                } else {
                return new Intersection(false);
            }
        }
    }
}