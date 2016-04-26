package scene;

import utils.Log;
import utils.Vec2;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Camera extends SceneObject {

    private Vec3 centerOfInterest = new Vec3();
    private Vec3 userUp = new Vec3();
    private float viewAngle;
    private float focalLength;
    private Vec3 camViewVec = new Vec3();
    private Vec3 camUpVec = new Vec3();
    private Vec3 camSideVec = new Vec3();

    public Camera(Vec3 position, Vec3 userUp, Vec3 centerOfInterest, float viewAngle, float focalLength){
        super(position);
        this.userUp = userUp;
        this.centerOfInterest = centerOfInterest;
        this.viewAngle = viewAngle;
        this.focalLength = focalLength;
        calculateCamCoord();
    }

    //calculation of cameracoordinationsystem
    private void calculateCamCoord(){
        this.camViewVec = centerOfInterest.sub(position);
        //normalize
        this.camViewVec = new Vec3(camViewVec.x/camViewVec.length(),camViewVec.y/camViewVec.length(),camViewVec.z/camViewVec.length());
        Log.print(camViewVec, String.valueOf(camViewVec));

        this.camSideVec = camViewVec.cross(userUp);
        //normalize
        this.camSideVec = new Vec3(camSideVec.x/camSideVec.length(),camSideVec.y/camSideVec.length(),camSideVec.z/camSideVec.length());
        Log.print(camSideVec, String.valueOf(camSideVec));

        this.camUpVec = camSideVec.cross(camViewVec);
        //normalize
        this.camUpVec = new Vec3(camUpVec.x/camUpVec.length(),camUpVec.y/camUpVec.length(),camUpVec.z/camUpVec.length());
        Log.print(camUpVec, String.valueOf(camUpVec));
    }

    public Vec3 calculateDestinationPoint(int x, int y){

        //normalize x and y pixel positions
        float yNorm, xNorm;
        xNorm = 2*((x + 0.5f)/800)-1;
        yNorm = 2*((y + 0.5f)/600)-1;

        Vec3 destinationPoint = new Vec3(camSideVec.x * xNorm,camUpVec.y * yNorm, camViewVec.z * focalLength);
        return destinationPoint;
    }

    public Vec3 getPosition() {
        return this.position;
    }
}
