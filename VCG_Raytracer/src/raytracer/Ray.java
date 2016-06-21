package raytracer;

import utils.Log;
import utils.Vec3;

public class Ray{
    private Vec3 pOrigin = new Vec3();
    private Vec3 destinationPoint = new Vec3();
    private Vec3 direction;
    private float length;

    //constructor via direction
    public Ray(Vec3 pOrigin, Vec3 direction, float length){
        this.pOrigin = pOrigin;
        this.direction = direction;
        this.length = length;
    }

    //default constructor
    public Ray(Vec3 pOrigin, Vec3 destinationPoint){
        this.pOrigin = pOrigin;
        this.destinationPoint = destinationPoint;
        this.direction = calculateDirection();
    }

    public Vec3 calculateDirection(){
        direction = destinationPoint.sub(pOrigin);
        direction = direction.normalize();
        return direction;
    }

    ///// Getter & Setter /////

    public Vec3 getDirection() {
        return direction;
    }

    public Vec3 getpOrigin(){
        return pOrigin;
    }

    public float getLength() {
        return length;
    }
}