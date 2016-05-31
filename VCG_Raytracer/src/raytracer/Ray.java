package raytracer;

import utils.Vec3;

public class Ray{
    private Vec3 pOrigin = new Vec3();
    private Vec3 destinationPoint = new Vec3();
    private Vec3 direction;
    private float length;

    public Ray(Vec3 pOrigin, Vec3 destinationPoint, float length){
        this.pOrigin = pOrigin;
        this.destinationPoint = destinationPoint;
        this.length = length;
        this.direction = getDirection();
    }

    public Vec3 getDirection(){
        direction = destinationPoint.sub(pOrigin);
        direction = direction.normalize();
        return direction;
    }

    public Vec3 getpOrigin(){
        return pOrigin;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction.normalize();
    }

    public void setpOrigin(Vec3 pOrigin) {
        this.pOrigin = pOrigin;
    }

    public float getLength() {
        return length;
    }
}


