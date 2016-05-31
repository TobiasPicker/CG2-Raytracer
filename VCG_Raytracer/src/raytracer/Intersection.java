package raytracer;

import scene.Shape;
import utils.Log;
import utils.Vec3;

public class Intersection {

    private Vec3 interSectionPoint;
    private Vec3 normal;
    private Ray inRay;
    private Ray outRay;
    private Shape shape;
    private float distance;
    private boolean hit;

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

    public Intersection(Ray inRay, Vec3 interSectionPoint,Vec3 normal, Shape shape, boolean hit){
        this.inRay = inRay;
        this.interSectionPoint = interSectionPoint;
        //Log.print(this, "IntersectionPoint erstellt");
        this.normal = normal;
        this.shape = shape;
        this.hit = hit;
    }

    private void calculateIntersectionPoint(){
        this.interSectionPoint = inRay.getpOrigin().add(inRay.getDirection().multScalar(distance));
    }

    private void calculateNormal(){
        if(shape.objectType.equals("Sphere")){
            normal = interSectionPoint.sub(shape.getPosition());
            normal = normal.normalize();
        }else if(shape.objectType.equals("Plane")){
            normal = shape.getNormal();
        }
    }

    public float getDistance(){
        return distance;
    }

    public Vec3 getInterSectionPoint(){
        //Log.print(this, "Intersectionpoint wird geholt "+interSectionPoint);
        return interSectionPoint;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public Shape getShape() {
        return shape;
    }

    public boolean isHit() {
        return hit;
    }

    public Ray getInRay() {
        return inRay;
    }
}
