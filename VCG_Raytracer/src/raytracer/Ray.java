package raytracer;

import utils.Log;
import utils.Vec3;

public class Ray{
    private Vec3 pOrigin = new Vec3();
    private Vec3 destinationPoint = new Vec3();
    private Vec3 direction;
    private float length;

    public Ray(Vec3 pOrigin, Vec3 destinationPoint){
        this.pOrigin = pOrigin;
        this.destinationPoint = destinationPoint;
        this.direction = calculateDirection();
    }

    public Ray(Vec3 pOrigin, Vec3 direction, float length){
        this.pOrigin = pOrigin;
        this.direction = direction;
        this.length = length;
        //Log.print(this, "direction" + direction);
    }

    public Vec3 calculateDirection(){
        direction = destinationPoint.sub(pOrigin);
        direction = direction.normalize();
        return direction;
    }

    public Vec3 getDirection() {
        return direction;
    }

    public Vec3 getpOrigin(){
        return pOrigin;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction.normalize();
    }

    public Vec3 getDestinationPoint() {
        return destinationPoint;
    }

    public void setpOrigin(Vec3 pOrigin) {
        this.pOrigin = pOrigin;
    }

    public float getLength() {
        return length;
    }
}


