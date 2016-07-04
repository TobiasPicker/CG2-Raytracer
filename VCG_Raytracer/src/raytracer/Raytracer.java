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
import ui.Window;
import utils.*;
import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private Scene scene;
    private Camera camera;
    //max how much rays can be reflected/-fracted
    private final int RECURSIONDEPTH;
    private int recursionCount;


    //constructor
    public Raytracer(Window renderWindow, Scene scene, int recursionDepth) {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
        this.scene = scene;
        this.RECURSIONDEPTH = recursionDepth;
        this.recursionCount = RECURSIONDEPTH;
        this.camera = scene.getCamera();
    }

    //method ro render scene
    public void renderScene() {
        for (int y = 0; y < 600; y++) {
            for (int x = 0; x < 800; x++) {
                mRenderWindow.setPixel(mBufferedImage, sendPrimaryRay(x, y), new Vec2(x, y));
            }
        }

        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }

    //sends a Ray through each pixel and throws back an RgbColor
    private RgbColor sendPrimaryRay(int x, int y){

        //dafault pixelColor
        RgbColor pixelColor = new RgbColor(0,0,0);

        Ray primaryRay = new Ray(camera.getPosition(), camera.calculateDestinationPoint(x, y));
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

        //ray does hit --> set shapeColor
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
                /////////////////////////////////////

                //if intersectionPoint is not in shade proceed with lambert/phong
                if(!inShadow) {

                    //non reflective/refractive portion
                    float colorPortion = 1-frontShape.getMaterial().getReflectivity()-frontShape.getMaterial().getRefractivity();//refractivity between 0 and 1, not yet interpreted as snellius!

                    RgbColor lightColor = scene.lightList.get(i).getColor();
                    //calculation of Lambert
                    if (frontShape.getMaterial().materialType.equals("Lambert")) {
                        pixelColor = pixelColor.add(frontShape.getMaterial().calculateLambert(lightVec, intersection.getNormal(), lightColor));
                    }
                    //calculation of Phong
                    else if (frontShape.getMaterial().materialType.equals("Phong")) {
                        Vec3 viewVec = scene.getCamera().getPosition().sub(intersectionPoint);
                        viewVec = viewVec.normalize();
                        pixelColor = pixelColor.add(frontShape.getMaterial().calculatePhong(lightVec, intersection.getNormal(), lightColor, viewVec).multScalar(colorPortion));
                    }
                }

                //first call of reflection/-fraction
                if((frontShape.getMaterial().getReflectivity()>0 || frontShape.getMaterial().getRefractivity()>0) && RECURSIONDEPTH>0){
                    //Log.print(this, ""+RECURSIONDEPTH);
                    pixelColor = pixelColor.add(sendSecondaryRay(intersection));
                    recursionCount = RECURSIONDEPTH;
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

    private RgbColor sendSecondaryRay(Intersection start){

        //if recursionCount reaches 0, no more recursive calls of sendSecondaryRay will occur
        recursionCount--;

        RgbColor pixelColor = new RgbColor(0,0,0);
        Ray inRay = start.getInRay();
        Vec3 inRayVec = inRay.getDirection().multScalar(-1);
        Vec3 normal = start.getNormal();
        float length = 100000;
        Intersection tempIntersection = new Intersection(false);
        //cosine of angle between inRay and normal
        float cosInRayAngle = normal.scalar(inRayVec);

        Vec3 refVec = new Vec3();
        float reflectivity = start.getShape().getMaterial().getReflectivity();
        float refractivity = start.getShape().getMaterial().getRefractivity();

        if(reflectivity>0) {
            //calculating reflection vector
            refVec = normal.multScalar(2 * cosInRayAngle).sub(inRay.getDirection().multScalar(-1));
            refVec = refVec.normalize();
            tempIntersection = start;
        }else if(refractivity>0){//refraction only for spheres

            //calculating refraction vector
            double tempCosOutRayAngle = Math.sqrt(1-Math.pow(1/refractivity, 2)*(1-Math.pow( normal.scalar(inRayVec) ,2))); //cosine of angle between outRay and normal

            Vec3 tempRefVec = normal.multScalar(cosInRayAngle).sub(inRayVec).multScalar(1/refractivity);
            tempRefVec = tempRefVec.sub(normal.multScalar((float)tempCosOutRayAngle));


            Ray tempRay = new Ray(start.getInterSectionPoint(), tempRefVec, length);
            tempIntersection = start.getShape().intersect(tempRay);

            float tempCosInRayAngle = tempIntersection.getNormal().multScalar(-1).scalar(tempRay.getDirection().multScalar(-1));
            double cosOutRayAngle = Math.sqrt(1-(Math.pow(refractivity, 2)*(1-Math.pow( tempIntersection.getNormal().scalar(tempRefVec.multScalar(-1)) ,2))));

            refVec = tempIntersection.getNormal().multScalar(tempCosInRayAngle).sub(tempRefVec.multScalar(-1)).multScalar(refractivity);
            refVec = refVec.sub(tempIntersection.getNormal().multScalar((float)cosOutRayAngle));
            refVec = refVec.normalize();

        }

        //sending the reflected or refracted ray
        Ray secondaryRay = new Ray(tempIntersection.getInterSectionPoint(), refVec, length);

        Intersection intersection = new Intersection(false);
            //check for nearest intersection point
        for(int i=0; i<scene.shapeList.size();i++) {
            if(i==0){
                intersection = scene.shapeList.get(i).intersect(secondaryRay);
            }else{
                Intersection intersectionTemp = scene.shapeList.get(i).intersect(secondaryRay);
                if(intersectionTemp.getDistance()<intersection.getDistance()){
                    intersection = intersectionTemp;
                }
            }
        }

        //ray does not hit --> set backgroundColor
        if (!intersection.isHit()) {
            pixelColor = new RgbColor(0f, .5f, 0f);
        }
        //ray does hit --> set shapeColor
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

                    //non reflective/refractive portion
                    float colorPortion = 1-frontShape.getMaterial().getReflectivity()-frontShape.getMaterial().getRefractivity();//refractivity between 0 and 1, not yet interpreted as snellius!

                    RgbColor lightColor = scene.lightList.get(i).getColor();
                    //calculation of Lambert
                    if (frontShape.getMaterial().materialType.equals("Lambert")) {
                        pixelColor = pixelColor.add(frontShape.getMaterial().calculateLambert(lightVec, intersection.getNormal(), lightColor));
                    }
                    //calculation of Phong
                    else if (frontShape.getMaterial().materialType.equals("Phong")) {
                        Vec3 viewVec = scene.getCamera().getPosition().sub(intersectionPoint);
                        viewVec = viewVec.normalize();
                        pixelColor = pixelColor.add(frontShape.getMaterial().calculatePhong(lightVec, intersection.getNormal(), lightColor, viewVec).multScalar(colorPortion));
                    }
                }

                //recursive call
                if((frontShape.getMaterial().getRefractivity()>0 || frontShape.getMaterial().getReflectivity()>0) && recursionCount>0){
                    pixelColor = pixelColor.add(sendSecondaryRay(intersection));
                }
            }
        }
        return pixelColor;
    }
}

