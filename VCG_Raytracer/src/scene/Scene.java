package scene;

import utils.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PraktikumCG on 27.04.2016.
 */

//class contains information of all objects in the scene
public class Scene {

    private List<Shape> shapeList = new ArrayList<Shape>();
    private List<Light> lightList = new ArrayList<Light>();
    private Camera camera;

    public Scene(){

    }

    //adds a sphere to shapeList
    public void createSphere(Vec3 position, float radius){
        this.shapeList.add(new Sphere(position, radius));
    }

    //adds a light to lightList
    public void createLight(Vec3 position, Vec3 color){
        this.lightList.add(new Light(position, color));
    }

    public void createCamera(Vec3 position,Vec3 userUp,Vec3 centerOfInterest,float viewAngle,float focalLength){
        this.camera = new Camera(position, userUp, centerOfInterest, viewAngle, focalLength);
    }

    public Camera getCamera(){
        return camera;
    }
}
