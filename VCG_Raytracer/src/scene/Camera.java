package scene;

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


    public Camera(Vec3 position, Vec3 userUp, Vec3 centerOfInterest, float viewAngle, float focalLength){
        super(position);
        this.userUp = userUp;
        this.centerOfInterest = centerOfInterest;
        this.viewAngle = viewAngle;
        this.focalLength = focalLength;
        this.height = 2f * (float)Math.tan(viewAngle/2f);
        this.width  = 4f * height / 3f;
        calculateCamCoord();
        this.centerPoint = position.add(camViewVec.multScalar(focalLength));
    }

    //calculation of cameracoordinationsystem
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

    public Vec3 calculateDestinationPoint(int x, int y){

        //normalize x and y pixel positions
        float yNorm, xNorm;
        xNorm = 2*((x + 0.5f)/800)-1;
        yNorm = 2*((y + 0.5f)/600)-1;

        Vec3 destinationPoint = centerPoint.add(widthVec.multScalar(xNorm));
        destinationPoint = destinationPoint.add(heightVec.multScalar(yNorm));
        destinationPoint.y = -destinationPoint.y;
        return destinationPoint;
    }

    public Vec3 getPosition() {
        return this.position;
    }
}
