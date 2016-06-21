package utils;

public class Lambert extends Material {

    RgbColor diffuse;

    //constructor
    public Lambert(RgbColor ambient,RgbColor diffuse){
        super(ambient, "Lambert", 0, 0);
        this.diffuse = diffuse;
    }

    //calculation of lambert color
    public RgbColor calculateLambert(Vec3 lightVec, Vec3 normal, RgbColor lightColor){
        RgbColor lambert = lightColor.multRGB(diffuse.multScalar(normal.scalar(lightVec)));
        return lambert;
    }
}