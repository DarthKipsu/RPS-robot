
package data;

import image.WebcamReader;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Takes and image, asks for a label and saves it in the database.
 */
public class Labeler {
    private static WebcamReader reader;

    public static void main(String[] args) throws IOException {
        reader = new WebcamReader();
        System.out.println("Hello world");
        reader.takeImage();
        BufferedImage grey = reader.getGreyImage();
        
        ImageIO.write(grey, "PNG", new File("test.png"));
        byte[] imgarray = ((DataBufferByte)grey.getRaster().getDataBuffer()).getData();
        System.out.println(Arrays.toString(imgarray));
        
        Scanner reader = new Scanner(System.in);
        System.out.println("Write image label:");
        String label = reader.next();
        
        Path data = Paths.get("data/data");
        Path labels = Paths.get("data/labels");
        
        Files.write(data, imgarray);
        Files.write(labels, Arrays.asList(label), Charset.forName("UTF-8"));
        
        System.out.println(Arrays.toString(Files.readAllBytes(data)));
        System.out.println(Files.readAllLines(labels, Charset.forName("UTF-8")));
}
}
