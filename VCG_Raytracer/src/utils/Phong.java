package utils;

/**
 * Created by PraktikumCG on 17.05.2016.
 */
public class Phong extends Material {

    RgbColor diffuse;
    RgbColor specular;

    public Phong(RgbColor color){
        super(color, "Phong");
    }

    public RgbColor calculatePhong(Vec3 lightVec, Vec3 normal, RgbColor lightColor, Vec3 viewVec, float n){

        RgbColor phong = new RgbColor(0,0,0);

        return phong;
    }
}




