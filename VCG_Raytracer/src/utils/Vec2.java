package utils;


public class Vec2 {
    public float x;
    public float y;

    public Vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "( " + this.x + ", " + this.y + ")";
    }
}
