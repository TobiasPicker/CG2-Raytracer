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
    private float distance = 1000000;
    private boolean hit;

    //constructor if ray does not hit object
    public Intersection(boolean hit){
        this.hit = hit;
    }

    //constructor if ray hits sphere
    public Intersection(Ray inRay,float distance, Vec3 interSectionPoint,Vec3 normal, Shape shape, boolean hit){
        this.inRay = inRay;
        this.distance = distance;
        this.interSectionPoint = interSectionPoint;
        this.normal = normal;
        this.shape = shape;
        this.hit = hit;
    }

    //constructor if ray hits plane
    public Intersection(Ray inRay,float distance, Shape shape, boolean hit){
        this.distance = distance;
        this.inRay = inRay;
        this.shape = shape;
        this.hit = hit;
        this.normal = shape.getNormal();
        this.interSectionPoint = inRay.getpOrigin().add(inRay.getDirection().multScalar(distance));
    }

    ///// Getter & Setter /////

    public float getDistance(){
        return distance;
    }

    public Vec3 getInterSectionPoint(){
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