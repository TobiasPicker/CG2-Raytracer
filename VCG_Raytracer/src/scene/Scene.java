package scene;

import utils.Material;
import utils.RgbColor;
import utils.Vec3;

import java.util.ArrayList;
import java.util.List;

//class contains information of all objects in the scene
public class Scene {

    public static List<Shape> shapeList = new ArrayList<Shape>();
    public static List<Light> lightList = new ArrayList<Light>();
    private static Camera camera;
    private static AmbientLight ambientLight;

    //adds a sphere to shapeList
    public static void createSphere(Vec3 position, float radius, Material material){
        shapeList.add(new Sphere(position, radius, material));
    }

    //adds a light to lightList
    public static void createLight(Vec3 position, RgbColor color, String lightType){
        if(lightType.equals("PointLight")) {
            lightList.add(new PointLight(position, color));
        }
    }

    public static void createAmbientLight(RgbColor color){
        ambientLight = new AmbientLight(color);
    }

    public static void createCamera(Vec3 position,Vec3 userUp,Vec3 centerOfInterest,float viewAngle,float focalLength){
        camera = new Camera(position, userUp, centerOfInterest, viewAngle, focalLength);
    }

    public static AmbientLight getAmbientLight(){return ambientLight;}
    public static Camera getCamera(){
        return camera;
    }
}
