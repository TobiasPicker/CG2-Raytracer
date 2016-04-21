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

import scene.Camera;
import ui.Window;
import utils.*;

import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private Camera camera;

    public Raytracer(Window renderWindow,Camera camera) {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
        this.camera = camera;
    }

    public void renderScene() {
        Log.print(this, "Start rendering");

        for (int y = 0; y < 600; y++) {
            for (int x = 0; x < 800; x++) {
                mRenderWindow.setPixel(mBufferedImage, sendPrimaryRay(x, y), new Vec2(x, y));
            }
        }

                IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }


    //sends a Ray and throghs back an RgbColor
    private RgbColor sendPrimaryRay(int x, int y){
        Ray primaryRay = new Ray(camera.calculateRayDirection(x, y), 100);

        //Log.print(primaryRay, String.valueOf(primaryRay.getDirection()));
        return new RgbColor(primaryRay.getDirection().x, primaryRay.getDirection().y, -1* primaryRay.getDirection().z);
    }
}
