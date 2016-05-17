package scene;

import utils.Vec3;

public class SceneObject {
    protected Vec3 position = new Vec3();

    public SceneObject(Vec3 position){
        this.position = position;
    }

    public Vec3 getPosition() {
        return position;
    }
}
