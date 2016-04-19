package scene;

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
        Vec3 rayDirection = new Vec3();

        //in arbeit

        return rayDirection;
    }

}
