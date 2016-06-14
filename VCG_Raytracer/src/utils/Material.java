package utils;

public class Material {

    public String materialType;
    RgbColor ambient;

    public Material(RgbColor ambient, String materialType){
        this.ambient = ambient;
        this.materialType = materialType;
    }

    //calculation of ambient color
    public RgbColor calculateAmbient(RgbColor ambientLightColor){
        return ambient.multRGB(ambientLightColor);
    }

    ///// publication of methods for color calculation /////

    public RgbColor calculateLambert(Vec3 lightVec, Vec3 normal, RgbColor lightColor){
        return ambient;
    }

    public RgbColor calculatePhong(Vec3 lightVec, Vec3 normal, RgbColor lightColor, Vec3 viewVec){
        return ambient;
    }
}
