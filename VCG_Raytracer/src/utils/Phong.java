package utils;

public class Phong extends Material {

    RgbColor diffuse;
    RgbColor specular;
    float n;

    //constructor
    public Phong(RgbColor ambient, RgbColor diffuse, RgbColor specular,float n){
        super(ambient, "Phong");
        this.diffuse = diffuse;
        this.specular = specular;
        this.n = n;
    }

    //calculation of phong color
    public RgbColor calculatePhong(Vec3 lightVec, Vec3 normal, RgbColor lightColor, Vec3 viewVec){
        //calculation of reflection vector
        Vec3 refVec = normal.multScalar(2 * normal.scalar(lightVec)).sub(lightVec);
        refVec = refVec.normalize();

        return lightColor.multRGB(diffuse.multScalar(normal.scalar(lightVec)).add(specular.multScalar((float)Math.pow(viewVec.scalar(refVec), n))));
    }
}




