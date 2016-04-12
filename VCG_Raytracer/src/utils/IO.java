package utils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class IO {
    public static void saveImageToPng(BufferedImage image, String fileName){
        File outFile = new File(fileName);
        try {
            ImageIO.write(image, "png", outFile);
        } catch (Exception e) {
            System.err.println(e.getMessage()); // print any IO errors to stderr.
        }
    }
}
