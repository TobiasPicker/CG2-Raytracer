// ************************************************************ //
//                      Hochschule Duesseldorf                  //
//                                                              //
//                     Vertiefung Computergrafik                //
// ************************************************************ //


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    1. Documentation:    Did you comment your code shortly but clearly?
    2. Structure:        Did you clean up your code and put everything into the right bucket?
    3. Performance:      Are all loops and everything inside really necessary?
    4. Theory:           Are you going the right way?

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 <<< !!RAYCING TEAM!! >>>

     Master of Documentation: Claudia Meinen
     Master of Structure: Tobias Picker
     Master of Performance: Chuxiao Jiang
     Master of Theory: Severin Strerath

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

import scene.Scene;
import ui.Window;
import raytracer.Raytracer;
import utils.*;

// Main application class. This is the routine called by the JVM to run the program.
public class Main {

    static int IMAGE_WIDTH = 800;
    static int IMAGE_HEIGHT = 600;


    // Initial method. This is where the show begins.
    public static void main(String[] args){
        long tStart = System.currentTimeMillis();

        Window renderWindow = new Window(IMAGE_WIDTH, IMAGE_HEIGHT);

        Scene scene = new Scene();

        setupScene(scene);

        draw(renderWindow, scene);

        renderWindow.setTimeToLabel(String.valueOf(stopTime(tStart)));
    }

    //builds the scene
    private static void setupScene(Scene scene){
        setupCamera(scene);
        setupLights(scene);

        scene.createSphere(new Vec3(3, 0, 0), 1f, new Phong(new RgbColor(.5f,.5f,.5f),new RgbColor(1f,0,0),new RgbColor(.5f,.7f,.25f),5));
        scene.createPlane(new Vec3(0, -5, 0), new Vec3(0, 1, 0), new Lambert(new RgbColor(1f,.3f,.4f),new RgbColor(.25f,.5f,.7f)));
        scene.createSphere(new Vec3(-2, 0, 0), 1f, new Phong(new RgbColor(.5f,.5f,.5f),new RgbColor(0,0,1f),new RgbColor(.25f,.5f,.7f),5));
    }

    //specifying the parameters of the camera and adding the camera object to scene
    private static void setupCamera(Scene scene){
        Vec3 position = new Vec3(0,0,5);
        Vec3 userUp = new Vec3(0,1,0);
        Vec3 centerOfInterest = new Vec3(0,0,0);
        float viewAngle =  3f;
        float focalLength = 30f;

        scene.createCamera(position,userUp,centerOfInterest,viewAngle,focalLength);
    }

    //specifying the parameters of the lights and adding the light objects to scene.lightList
    private static void setupLights(Scene scene){

        scene.createLight(new Vec3(8,5,5f), new RgbColor(0.75f,0.75f,0.75f), "PointLight");
        scene.createAmbientLight(new RgbColor(.1f,.1f,.1f));
    }

    //added an object camera in draw and raytraceScene so the direction of a ray could be calculated in class Raytracer
    private static void draw(Window renderWindow, Scene scene){
        raytraceScene(renderWindow, scene);
    }

    //added camera
    private static void raytraceScene(Window renderWindow, Scene scene){
        Raytracer raytracer = new Raytracer(renderWindow, scene);
        raytracer.renderScene();
    }

    private static double stopTime(long tStart){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }
}