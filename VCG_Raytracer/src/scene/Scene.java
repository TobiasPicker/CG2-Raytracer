package scene;

import utils.Material;
import utils.RgbColor;
import utils.Vec3;

import java.util.ArrayList;
import java.util.List;

//class contains information of all objects in the scene
public class Scene {

    public  ArrayList<Shape> shapeList;
    public  ArrayList<Light> lightList;
    private  Camera camera;
    private  AmbientLight ambientLight;

    public Scene(){
        this.shapeList = new ArrayList<Shape>();
        this.lightList = new ArrayList<Light>();
    }

    //creates a sphere and adds it to shapeList (parameters are position, radius and material)
    public void createSphere(Vec3 position, float radius, Material material){
        shapeList.add(new Sphere(position, radius, material));
    }

    //creates a plane and adds it to shapeList (parameters are position, normal and material)
    public void createPlane(Vec3 position, Vec3 normal, Material material){
        shapeList.add(new Plane(position, normal, material));
    }

    //creates a light and adds it to lightList (parameters are position, color and lightType)
    public void createLight(Vec3 position, RgbColor color, String lightType){
        if(lightType.equals("PointLight")) {
            lightList.add(new PointLight(position, color));
        }
    }

    //creates ambient light (parameter color)
    public void createAmbientLight(RgbColor color){
        this.ambientLight = new AmbientLight(color);
    }

    //creates camera (parameters are position, userUp, center of interest, view Angle and focal Length)
    public void createCamera(Vec3 position,Vec3 userUp,Vec3 centerOfInterest,float viewAngle,float focalLength){
        this.camera = new Camera(position, userUp, centerOfInterest, viewAngle, focalLength);
    }

    ///// Getter & Setter /////

    public AmbientLight getAmbientLight(){
        return ambientLight;
    }

    public Camera getCamera(){
        return camera;
    }
}
