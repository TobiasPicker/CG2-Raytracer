package scene;

import utils.Material;
import utils.Vec3;

public class Plane extends Shape {

    private Vec3 normal;

    public Plane(Vec3 position, Vec3 normal, Material material){
        super(position, material, "Plane");
        this.normal = normal;
    }
}
