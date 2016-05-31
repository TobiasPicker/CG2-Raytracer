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
                //Log.print(this,"1. " + x+"; "+y);
                mRenderWindow.setPixel(mBufferedImage, sendPrimaryRay(x, y), new Vec2(x, y));

            }
        }

        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }

    //sends a Ray and throws back an RgbColor
    private RgbColor sendPrimaryRay(int x, int y){
        Ray primaryRay = new Ray(scene.getCamera().getPosition(), scene.getCamera().calculateDestinationPoint(x, y), 100);
        RgbColor pixelColor = new RgbColor(.1f,.1f,.1f);
        double distance=0;
        int index = 0;

        Intersection intersection = new Intersection(false);

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

        //Log.print(this, ""+scene.shapeList.get(index).intersect(primaryRay).isHit());

        //ray does not hit --> set backgroundColor
        if (!intersection.isHit()) {

            pixelColor = new RgbColor(.1f, .5f, 0f);

            //Log.print(this, ""+scene.shapeList.get(index).intersect(primaryRay).isHit());
            //Log.print(this, "ray does not hit");

        }
        //ray does hit --> set sphereColor
        else {
            //Log.print(this, "ray hits");
            pixelColor = pixelColor.add(scene.shapeList.get(index).getMaterial().calculateAmbient(scene.getAmbientLight().getColor()));

            for(int i=0; i<scene.lightList.size();i++){

                Vec3 intersectionPoint = intersection.getInterSectionPoint();
                //Log.print(this, "Shape: "+scene.shapeList.get(index) +"; Light: "+scene.lightList.get(i) +"; IntersectionPoint: "+intersection.getInterSectionPoint() +"; IntersectionShape: "+intersection.getShape()+"; IntersectionRay: "+intersection.getInRay());
                Vec3 lightVec = scene.lightList.get(i).getPosition().sub(intersectionPoint);
                lightVec = lightVec.normalize();
                RgbColor lightColor = scene.lightList.get(i).getColor();

                if (scene.shapeList.get(index).getMaterial().materialType.equals("Lambert")) {
                    pixelColor = pixelColor.add(scene.shapeList.get(index).getMaterial().calculateLambert(lightVec, intersection.getNormal(), lightColor));
                }
                else if(scene.shapeList.get(index).getMaterial().materialType.equals("Phong")){

                    Vec3 viewVec = scene.getCamera().getPosition().sub(intersectionPoint);
                    viewVec = viewVec.normalize();
                    pixelColor = pixelColor.add(scene.shapeList.get(index).getMaterial().calculatePhong(lightVec, intersection.getNormal(), lightColor, viewVec));
                }
            }
        }

        return pixelColor;
    }
}
