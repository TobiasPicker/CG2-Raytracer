package raytracer;

import scene.Shape;
import utils.Vec3;

public class Intersection {

    Vec3 interSectionPoint;
    Vec3 normal;
    Ray inRay;
    Ray outRay;
    Shape shape;
    float distance;
    boolean hit;

    public Intersection(Ray inRay,float distance, Shape shape, boolean hit){
        this.distance = distance;
        this.inRay = inRay;
        this.shape = shape;
        this.hit = hit;
        calculateIntersectionPoint();
        calculateNormal();
    }

    public Intersection(boolean hit){
        this.hit = hit;
    }

    private void calculateIntersectionPoint(){
        this.interSectionPoint = inRay.getpOrigin().add(inRay.getDirection().multScalar(distance));
    }

    private void calculateNormal(){
        if(shape.objectType.equals("Sphere")){
            normal = interSectionPoint.sub(shape.getPosition());
            normal = normal.normalize();
        }
    }

    public float getDistance(){
        return distance;
    }

    public boolean isHit() {
        return hit;
    }
}
