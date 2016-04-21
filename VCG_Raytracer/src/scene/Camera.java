package scene;

import utils.Log;
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
        this.camViewVec.normalize();
        this.camSideVec = camViewVec.cross(userUp);
        this.camSideVec.normalize();
        this.camUpVec = camSideVec.cross(camViewVec);
        this.camUpVec.normalize();
    }

    public Vec3 calculateRayDirection(int x, int y){
        Vec3 rayDirection;

        //normalize x and y pixel positions
        float yNorm, xNorm;
        xNorm = 2*((x + 0.5f)/800)-1;
        yNorm = 2*((y + 0.5f)/600)-1;

        //calculating direction of ray from cameraposition to center of pixel x,y
        rayDirection = camViewVec.multScalar(focalLength);
        Log.print(rayDirection, String.valueOf(rayDirection));
        rayDirection = rayDirection.add(camUpVec.multScalar(yNorm));
        Log.print(rayDirection, String.valueOf(camUpVec.multScalar(yNorm)));
        rayDirection = rayDirection.add(camSideVec.multScalar(xNorm));
        Log.print(rayDirection, String.valueOf(camSideVec.multScalar(xNorm)));

        rayDirection.normalize();

        //Log.print(rayDirection, String.valueOf(camSideVec.multScalar(xNorm)));

        return rayDirection;
    }

}
