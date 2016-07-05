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

    //builds the scene by setting up camera, lights and objects
    private static void setupScene(Scene scene){
        setupCamera(scene);
        setupLights(scene);


        scene.createPlane(new Vec3(10, 0, 0), new Vec3(-1, 0, 0), new Lambert(new RgbColor(0f,.8f,0f),new RgbColor(0,.5f,0f))); //Right
        scene.createPlane(new Vec3(0, 10, 0), new Vec3(0, -1, 0), new Lambert(new RgbColor(.8f,.8f,.8f),new RgbColor(.5f,.5f,.5f))); //Top
        scene.createPlane(new Vec3(-10, 0, 0), new Vec3(1, 0, 0), new Lambert(new RgbColor(.8f,.0f,.0f),new RgbColor(.5f,0,0))); //Left
        scene.createPlane(new Vec3(0, -10, 0), new Vec3(0, 1, 0), new Lambert(new RgbColor(.8f,.8f,.8f),new RgbColor(.5f,.5f,.5f))); //Bottom
        scene.createPlane(new Vec3(0, 0, -10), new Vec3(0, 0, 1), new Lambert(new RgbColor(.8f,.1f,.8f),new RgbColor(.5f,.5f,.5f))); //Back
        scene.createPlane(new Vec3(0, 0, 20), new Vec3(0, 0, -1), new Lambert(new RgbColor(.8f,.8f,.8f),new RgbColor(.5f,.5f,.5f))); //Front


        /*
        scene.createPlane(new Vec3(10, 0, 0), new Vec3(-1, 0, 0), new Phong(new RgbColor(.0f,.8f,0f),new RgbColor(0,.5f,0),new RgbColor(.5f,.5f,.5f),3,.5f,0)); //Right
        scene.createPlane(new Vec3(0, 10, 0), new Vec3(0, -1, 0), new Phong(new RgbColor(.3f,.3f,.3f),new RgbColor(.5f,.5f,.5f),new RgbColor(.5f,.5f,.5f),3,.5f,0)); //Top
        scene.createPlane(new Vec3(-10, 0, 0), new Vec3(1, 0, 0), new Phong(new RgbColor(.8f,.0f,.0f),new RgbColor(.5f,0,0),new RgbColor(.5f,.5f,.5f),3,.5f,0)); //Left
        scene.createPlane(new Vec3(0, -10, 0), new Vec3(0, 1, 0), new Phong(new RgbColor(.8f,.8f,.8f),new RgbColor(.5f,.5f,.5f),new RgbColor(.5f,.5f,.5f),3,.5f,0)); //Bottom
        scene.createPlane(new Vec3(0, 0, -10), new Vec3(0, 0, 1), new Phong(new RgbColor(.1f,.8f,.8f),new RgbColor(.5f,.5f,.5f),new RgbColor(.5f,.5f,.5f),3,.5f,0)); //Back
        scene.createPlane(new Vec3(0, 0, 10), new Vec3(0, 0, -1), new Phong(new RgbColor(.8f,.8f,.8f),new RgbColor(.5f,.5f,.5f),new RgbColor(.25f,.5f,.7f),5, .5f, 0)); //Front
        */
        scene.createSphere(new Vec3(4, -6, -6), 2f, new Phong(new RgbColor(.5f,.1f,.1f),new RgbColor(1f,0,0), new RgbColor(.5f,.7f,.25f), 3, 0f, 1f)); //red
        scene.createSphere(new Vec3(-6, -6, -6), 2f, new Phong(new RgbColor(.1f,.1f,.35f),new RgbColor(0,0,1f),new RgbColor(.25f,.5f,.7f), 5, 1, 0f)); //blue
        scene.createSphere(new Vec3(5, 0, -1f), 1f, new Phong(new RgbColor(.1f,.35f,.1f),new RgbColor(0,1,0f),new RgbColor(.25f,.5f,.7f), 5, 1f, 0f)); //green
    }

    //specifying the parameters of the camera and adding the camera object to scene
    private static void setupCamera(Scene scene){
        Vec3 position = new Vec3(0,0,10);
        Vec3 userUp = new Vec3(0,1,0);
        Vec3 centerOfInterest = new Vec3(0,0,5);
        float viewAngle = 70f;
        float focalLength = 1f;

        scene.createCamera(position,userUp,centerOfInterest,viewAngle,focalLength);
    }

    //specifying the parameters of the lights and adding the light objects to scene.lightList
    private static void setupLights(Scene scene){
        scene.createAmbientLight(new RgbColor(.01f,.01f,.01f));
        scene.createLight(new Vec3(0,9.9f,-5), new RgbColor(1f,1f,1f), "PointLight");
        //scene.createLight(new Vec3(-3,5,-9), new RgbColor(0.5f,0.5f,0.5f), "PointLight");
        //scene.createLight(new Vec3(0,0,0), new RgbColor(0.5f,0.5f,0.5f), "PointLight");
    }

    private static void draw(Window renderWindow, Scene scene){
        raytraceScene(renderWindow, scene);
    }

    private static void raytraceScene(Window renderWindow, Scene scene){
        Raytracer raytracer = new Raytracer(renderWindow, scene, 10, 2);
        raytracer.renderScene();
    }

    private static double stopTime(long tStart){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }
}