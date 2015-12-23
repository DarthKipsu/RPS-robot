
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
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Takes and image, asks for a label and saves it in the database.
 */
public class Labeler {
    private static WebcamReader reader;

    public static void main(String[] args) throws IOException {
        Path data = Paths.get("data/data");
        Path labels = Paths.get("data/labels");
        
        reader = new WebcamReader();
        reader.takeImage();
        BufferedImage image = reader.getBinaryImage();
        
        ImageIO.write(image, "PNG", new File("data/images/" + Files.readAllLines(labels).size() + ".png"));
        byte[] imgarray = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        System.out.println(Arrays.toString(imgarray));
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose image label:");
        System.out.println("0 = Rock");
        System.out.println("1 = Paper");
        System.out.println("2 = Scissors");
        String label = scanner.next();
        
        Files.write(data, imgarray, StandardOpenOption.APPEND);
        Files.write(labels, Arrays.asList(label), Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        
        System.out.println(Arrays.toString(Files.readAllBytes(data)));
        System.out.println(Files.readAllLines(labels, Charset.forName("UTF-8")));
        
        System.out.println(Files.readAllBytes(data).length);
}
}
