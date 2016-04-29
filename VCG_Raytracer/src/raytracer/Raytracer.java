/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1. Send primary ray
    2. intersection test with all shapes
    3. if hit:
    3a: send secondary ray to the light source
    3b: 2
        3b.i: if hit:
            - Shape is in the shade
            - Pixel color = ambient value
        3b.ii: in NO hit:
            - calculate local illumination
    4. if NO hit:
        - set background color

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package raytracer;

import scene.Scene;
import scene.Shape;
import scene.Sphere;
import ui.Window;
import utils.*;
import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private Scene scene;

    //only for testing
    private Sphere sphere = new Sphere(new Vec3(0,0,-5), .1f);
    //
    public Raytracer(Window renderWindow, Scene scene) {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
        this.scene = scene;
    }



    public void renderScene() {
        for (int y = 0; y < 600; y++) {
            for (int x = 0; x < 800; x++) {
                mRenderWindow.setPixel(mBufferedImage, sendPrimaryRay(x, y), new Vec2(x, y));
            }
        }

        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }

    //sends a Ray and throws back an RgbColor
    private RgbColor sendPrimaryRay(int x, int y){
        Ray primaryRay = new Ray(Scene.getCamera().getPosition(), Scene.getCamera().calculateDestinationPoint(x, y), 100);

        //ray does not hit --> set backgroundColor
        if(sphere.intersect(primaryRay)==0){
            return new RgbColor(.1f,.1f,.1f);
        }
        //ray does hit --> set sphereColor
        else {
            return sphere.getColor();
        }
    }
}
