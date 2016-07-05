package scene;

import utils.Log;
import utils.Vec3;

public class Camera extends SceneObject {

    private Vec3 centerOfInterest = new Vec3();
    private Vec3 userUp = new Vec3();
    private float viewAngle;
    private float focalLength;
    private Vec3 camViewVec = new Vec3();
    private Vec3 camUpVec = new Vec3();
    private Vec3 camSideVec = new Vec3();
    private float height;
    private float width;
    private Vec3 widthVec;
    private Vec3 heightVec;
    private Vec3 centerPoint;

    //constructor
    public Camera(Vec3 position, Vec3 userUp, Vec3 centerOfInterest, float viewAngle, float focalLength){
        super(position);
        this.userUp = userUp;
        this.centerOfInterest = centerOfInterest;
        this.viewAngle = viewAngle;
        this.focalLength = focalLength;
        this.height = 2* focalLength* (float)Math.tan( (viewAngle * (Math.PI /360f)));
        this.width  = 4f * height / 3f;
        calculateCamCoord();
        this.centerPoint = position.add(camViewVec.multScalar(focalLength));
    }

    //calculation of camera coordinate system
    private void calculateCamCoord(){

        this.camViewVec = centerOfInterest.sub(position);
        this.camViewVec = camViewVec.normalize();

        this.camSideVec = camViewVec.cross(userUp);
        this.camSideVec = camSideVec.normalize();

        this.camUpVec = camSideVec.cross(camViewVec);
        this.camUpVec = camUpVec.normalize();

        widthVec = camSideVec.multScalar(0.5f*width);
        heightVec = camUpVec.multScalar(0.5f*height);
    }

    //calculates each destination point for ray
    public Vec3 calculateDestinationPoint(int m, int n, int supersampling){

        //normalize x and y pixel positions
        float nNorm, mNorm;
        mNorm = 2*((m + 0.5f)/(800*supersampling))-1;
        nNorm = 2*((n + 0.5f)/(600*supersampling))-1;

        Vec3 destinationPoint = centerPoint.add(widthVec.multScalar(mNorm)).add(heightVec.multScalar(nNorm));
        destinationPoint.y = -destinationPoint.y;
        return destinationPoint;
    }

    ///// Getter & Setter /////

    public Vec3 getPosition() {
        return this.position;
    }
}
