package utils;

public class RgbColor {

    public Vec3 colors;
    public float red(){ return colors.x; }
    public float green(){ return colors.y; }
    public float blue(){ return colors.z; }

    public static RgbColor RED = new RgbColor(1, 0, 0);
    public static RgbColor GREEN = new RgbColor(0, 1, 0);
    public static RgbColor BLUE = new RgbColor(0, 0, 1);
    public static RgbColor WHITE = new RgbColor(1, 1, 1);
    public static RgbColor BLACK = new RgbColor(0, 0, 0);
    public static RgbColor CYAN = new RgbColor(0, 1, 1);
    public static RgbColor MAGENTA = new RgbColor(1, 0, 1);
    public static RgbColor YELLOW = new RgbColor(1, 1, 0);
    public static RgbColor GRAY = new RgbColor(0.5f, 0.5f, 0.5f);
    public static RgbColor LIGHT_GRAY = new RgbColor(0.25f, 0.25f, 0.25f);

    public RgbColor(float r, float g, float b){
        colors = new Vec3(r, g, b);

        clamp();
    }

    public RgbColor(Vec3 color){
        colors = color;

        clamp();
    }

    public void add(float r, float g, float b){
        colors.x += r;
        colors.y += g;
        colors.z += g;

        clamp();
    }

    public RgbColor add(RgbColor color){
        return new RgbColor( colors.add(color.colors) );
    }

    public RgbColor multRGB(RgbColor color){
        return new RgbColor( colors.x * color.red(),
                             colors.y * color.green(),
                             colors.z * color.blue() );
    }

    public RgbColor multScalar(float value){
        return new RgbColor( colors.multScalar(value) );
    }

    public int getRGB(){
        return ((int) (this.red() * 255f) << 16) + ((int) (this.green() * 255f) << 8) + ((int) (this.blue() * 255f));
    }

    private void clamp(){
        if( this.red() > 1 ) colors.x = 1f;
        if( this.green() > 1 ) colors.y = 1f;
        if( this.blue() > 1 ) colors.z = 1f;

        if( this.red() < 0 ) colors.x = 0f;
        if( this.green() < 0 ) colors.y = 0f;
        if( this.blue() < 0 ) colors.z = 0f;
    }

    @Override
    public String toString(){
        return "( " + this.red() + ", " + this.green() + ", " + this.blue() + " )";
    }

    public boolean equals(RgbColor inColor){
        return inColor.red() == this.red() && inColor.green() == this.green() && inColor.blue() == this.blue();
    }
}
