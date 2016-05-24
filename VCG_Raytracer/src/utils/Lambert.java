package utils;

public class Lambert extends Material {

    RgbColor diffuse;

    public Lambert(RgbColor ambient,RgbColor diffuse){
        super(ambient, "Lambert");
        this.diffuse = diffuse;
    }

    public RgbColor calculateLambert(Vec3 lightVec, Vec3 normal, RgbColor lightColor){
        RgbColor lambert = lightColor.multRGB(diffuse.multScalar(normal.scalar(lightVec)));
        //Log.print(lambert, ""+lambert);
        return lambert;
    }
}
