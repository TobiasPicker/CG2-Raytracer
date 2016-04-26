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

import scene.Camera;
import ui.Window;
import raytracer.Raytracer;
import utils.Log;
import utils.Vec3;

// Main application class. This is the routine called by the JVM to run the program.
public class Main {

    static int IMAGE_WIDTH = 800;
    static int IMAGE_HEIGHT = 600;

    //I don't know if this should belong here, maybe we have to get camera out again
    static Camera camera;

    // Initial method. This is where the show begins.
    public static void main(String[] args){
        long tStart = System.currentTimeMillis();

        Window renderWindow = new Window(IMAGE_WIDTH, IMAGE_HEIGHT);

        //creating a camera
        camera = setupCamera();

        draw(renderWindow, camera);

        renderWindow.setTimeToLabel(String.valueOf(stopTime(tStart)));

    }

    //added an object camera in draw und raytraceScene so the direction of a ray could be calculated in class Raytracer
    private static void draw(Window renderWindow, Camera camera){
        raytraceScene(renderWindow, camera);
    }

    //specifying the parameters of the camera and creating the camera object;
    private static Camera setupCamera(){
        Vec3 position = new Vec3(0,0,1);
        Vec3 centerOfInterest = new Vec3(0,0,0);
        Vec3 userUp = new Vec3(0,1,0);
        float viewAngle =  3.1349f;
        float focalLength = 1.0f;

        Camera camera = new Camera(position, userUp, centerOfInterest, viewAngle, focalLength);
        return camera;
    }

    //added camera
    private static void raytraceScene(Window renderWindow, Camera camera){
        Raytracer raytracer = new Raytracer(renderWindow, camera);

        raytracer.renderScene();
    }

    private static double stopTime(long tStart){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }
}