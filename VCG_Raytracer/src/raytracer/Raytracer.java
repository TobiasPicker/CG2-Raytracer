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
import scene.Scene;
import scene.Shape;
import sun.security.ssl.Debug;
import ui.Window;
import utils.*;
import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private Scene scene;

    //constructor
    public Raytracer(Window renderWindow, Scene scene) {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
        this.scene = scene;
    }

    //method ro render scene
    public void renderScene() {
        for (int y = 0; y < 600; y++) {
            for (int x = 0; x < 800; x++) {
                //Log.print(this,"1. " + x+"; "+y);
                mRenderWindow.setPixel(mBufferedImage, sendPrimaryRay(x, y), new Vec2(x, y));
            }
        }

        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }

    //sends a Ray through each pixel and throws back an RgbColor
    private RgbColor sendPrimaryRay(int x, int y){
        Ray primaryRay = new Ray(scene.getCamera().getPosition(), scene.getCamera().calculateDestinationPoint(x, y));
        RgbColor pixelColor = new RgbColor(.1f,.1f,.1f);
        Intersection intersection = new Intersection(false);

        //check for nearest intersection point
        for(int i=0; i<scene.shapeList.size();i++) {
            if(i==0){
                intersection = scene.shapeList.get(i).intersect(primaryRay);
            }else{
                Intersection intersectionTemp = scene.shapeList.get(i).intersect(primaryRay);
                if(intersectionTemp.getDistance()<intersection.getDistance()){
                    intersection = intersectionTemp;
                }
            }
        }

        //ray does not hit --> set backgroundColor
        if (!intersection.isHit()) {
            pixelColor = new RgbColor(0f, .5f, 0f);
        }

        //ray does hit --> set sphereColor
        else {
            Shape frontShape = intersection.getShape();
            pixelColor = pixelColor.add(frontShape.getMaterial().calculateAmbient(scene.getAmbientLight().getColor()));

            //run through all lights
            for(int i=0; i<scene.lightList.size();i++){

                //parameter for secondaryRay
                Vec3 intersectionPoint = intersection.getInterSectionPoint();
                Vec3 lightVec = scene.lightList.get(i).getPosition().sub(intersectionPoint);
                float shadowRayLength = lightVec.length();
                lightVec = lightVec.normalize();

                ////////////shadow-ray///////////////
                boolean inShadow = sendShadowRay(intersectionPoint, lightVec, shadowRayLength);

                //if intersectionPoint is not in shade proceed with lambert/phong
                if(!inShadow) {

                    RgbColor lightColor = scene.lightList.get(i).getColor();
                    //calculation of Lambert
                    if (frontShape.getMaterial().materialType.equals("Lambert")) {
                        pixelColor = pixelColor.add(frontShape.getMaterial().calculateLambert(lightVec, intersection.getNormal(), lightColor));
                    }
                    //calculation of Phong
                    else if (frontShape.getMaterial().materialType.equals("Phong")) {
                        Vec3 viewVec = scene.getCamera().getPosition().sub(intersectionPoint);
                        viewVec = viewVec.normalize();
                        pixelColor = pixelColor.add(frontShape.getMaterial().calculatePhong(lightVec, intersection.getNormal(), lightColor, viewVec));
                    }
                }
            }
        }

        return pixelColor;
    }

    private boolean sendShadowRay(Vec3 intersectionPoint, Vec3 lightVec, float shadowRayLength){

        Ray shadowRay = new Ray(intersectionPoint, lightVec, shadowRayLength);

        Intersection intersection;
        boolean inShadow = false;

        for(int i=0; i<scene.shapeList.size();i++) {

            intersection = scene.shapeList.get(i).intersect(shadowRay);

            if(intersection.isHit()&&(intersection.getDistance()<shadowRayLength)){

                inShadow = true;
                break;
            }
        }

        return inShadow;
    }
}

