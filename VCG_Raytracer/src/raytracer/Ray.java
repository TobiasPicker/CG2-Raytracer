package raytracer;

import utils.Vec3;

public class Ray{
    private Vec3 pOrigin = new Vec3();
    private Vec3 destinationPoint = new Vec3();
    private float length;

    public Ray(Vec3 pOrigin, Vec3 destinationPoint, float length){
        this.pOrigin = pOrigin;
        this.destinationPoint = destinationPoint;
        this.length = length;
    }

    public Vec3 getDirection(){
        Vec3 direction = destinationPoint.sub(pOrigin);
        direction = new Vec3(direction.x/direction.length(),direction.y/direction.length(),direction.z/direction.length());
        return direction;
    }

    public Vec3 getpOrigin(){
        return pOrigin;
    }
}
