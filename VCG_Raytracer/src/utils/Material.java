package utils;

public class Material {

    public String materialType;
    protected float reflectivity = 0;
    protected float refractivity = 0;
    RgbColor ambient;

    public Material(RgbColor ambient, String materialType,float reflective, float refractive){
        this.ambient = ambient;
        this.materialType = materialType;
        this.reflectivity = reflective;
        this.refractivity = refractive;
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


    //getter&setter
    public float getReflectivity() {
        return reflectivity;
    }

    public float getRefractivity() {
        return refractivity;
    }
}
