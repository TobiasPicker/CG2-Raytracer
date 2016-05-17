package raytracer;

import scene.Shape;
import scene.Sphere;
import utils.Log;
import utils.Vec3;

/**
 * Created by PraktikumCG on 17.05.2016.
 */
public class Intersection {

    Vec3 interSectionPoint;
    Vec3 normal;
    Ray inRay;
    Ray outRay;
    Shape shape;
    float distance;
    boolean hit;

    public Intersection(Ray inRay,float distance, Shape shape){
        this.distance = distance;
        this.inRay = inRay;
        this.shape = shape;
        calculateIntersectionPoint();
        calculateNormal();
    }

    private void calculateIntersectionPoint(){
        this.interSectionPoint = inRay.getpOrigin().add(inRay.getDirection().multScalar(distance));
    }

    private void calculateNormal(){
        if(shape.objectType.equals("Sphere")){
            normal = interSectionPoint.sub(shape.getPosition());
            normal = normal.normalize();
            //Log.print(normal, ""+normal);
        }
    }

}
