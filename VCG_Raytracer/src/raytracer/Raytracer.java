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

import ui.Window;
import utils.*;

import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;

    public Raytracer(Window renderWindow) {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene() {
        Log.print(this, "Start rendering");

        for (float y = 0; y < 600; y++) {
            for (float x = 0; x < 800; x++) {
                mRenderWindow.setPixel(mBufferedImage, new RgbColor(x/800, y/600, 0), new Vec2(x, y));
            }
        }

                IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }
}
