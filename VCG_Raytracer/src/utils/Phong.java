package utils;

/**
 * Created by PraktikumCG on 17.05.2016.
 */
public class Phong extends Material {

    RgbColor diffuse;
    RgbColor specular;
    float n;

    public Phong(RgbColor ambient, RgbColor diffuse, RgbColor specular,float n){
        super(ambient, "Phong");
        this.diffuse = diffuse;
        this.specular = specular;
        this.n = n;
    }

    public RgbColor calculatePhong(Vec3 lightVec, Vec3 normal, RgbColor lightColor, Vec3 viewVec){

        Vec3 refVec = normal.multScalar(2 * normal.scalar(lightVec)).sub(lightVec);
        refVec = refVec.normalize();

        if(viewVec.scalar(refVec)<0) {
            return lightColor.multRGB(diffuse.multScalar(normal.scalar(lightVec)));
        }else {
            return lightColor.multRGB(diffuse.multScalar(normal.scalar(lightVec)).add(specular.multScalar((float) Math.pow(viewVec.scalar(refVec), n))));
        }

    }
}




