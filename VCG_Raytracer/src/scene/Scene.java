package scene;

import utils.Material;
import utils.Vec3;

import java.util.ArrayList;
import java.util.List;

//class contains information of all objects in the scene
public class Scene {

    public static List<Shape> shapeList = new ArrayList<Shape>();
    public static List<Light> lightList = new ArrayList<Light>();
    private static Camera camera;

    //adds a sphere to shapeList
    public static void createSphere(Vec3 position, float radius, Material material){
        shapeList.add(new Sphere(position, radius, material));
    }

    //adds a light to lightList
    public static void createLight(Vec3 position, Vec3 color){
        lightList.add(new Light(position, color));
    }

    public static void createCamera(Vec3 position,Vec3 userUp,Vec3 centerOfInterest,float viewAngle,float focalLength){
        camera = new Camera(position, userUp, centerOfInterest, viewAngle, focalLength);
    }

    public static Camera getCamera(){
        return camera;
    }
}
