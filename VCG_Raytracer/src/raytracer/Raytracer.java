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
        RgbColor pixelColor = new RgbColor(.1f,.1f,.1f);
        double distance=0;
        int index = 0;

        for(int i=0; i<scene.shapeList.size();i++) {
            if(i==0){
                distance = scene.shapeList.get(i).intersect(primaryRay);
            }else{
                if(scene.shapeList.get(i).intersect(primaryRay)<distance){
                    distance = scene.shapeList.get(i).intersect(primaryRay);
                    index = i;
                }
            }
        }

        //ray does not hit --> set backgroundColor
        if (scene.shapeList.get(index).intersect(primaryRay) == 0) {
            pixelColor = new RgbColor(0f, 0f, 0f);
        }
        //ray does hit --> set sphereColor
        else {
            Intersection intersection = new Intersection(primaryRay, (float)distance, scene.shapeList.get(index));
            pixelColor = pixelColor.add(scene.shapeList.get(index).getMaterial().calculateAmbient(scene.getAmbientLight().getColor()));
            Log.print(pixelColor, ""+pixelColor);
            for(int i=0; i<scene.lightList.size();i++){
                //Vec3 lightVec = intersection.interSectionPoint.sub(scene.lightList.get(i).getPosition());
                Vec3 lightVec = scene.lightList.get(i).getPosition().sub(intersection.interSectionPoint);
                lightVec = lightVec.normalize();
                RgbColor lightColor = scene.lightList.get(i).getColor();

                if (scene.shapeList.get(i).getMaterial().materialType.equals("Lambert")) {
                    pixelColor = pixelColor.add(scene.shapeList.get(i).getMaterial().calculateLambert(lightVec, intersection.normal, lightColor));
                    //Log.print(pixelColor, ""+pixelColor);
                }
            }
        }


        return pixelColor;
    }
}
