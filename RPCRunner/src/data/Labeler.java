
package data;

import image.ImageWriter;
import image.WebcamReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Takes and image, asks for a label and saves it in the database.
 * This class is used to build data for machine learning purposes.
 */
public class Labeler {
    public static final String IMAGE_DIRECTORY = "data/images/";
    public static final Path DATA = Paths.get("data/data");
    public static final Path LABELS = Paths.get("data/labels");

    public static void main(String[] args) throws IOException {
        takeNewDatasetImage();
        labelDatasetImage();

        System.out.println(Arrays.toString(Files.readAllBytes(DATA)));
        System.out.println(Files.readAllLines(LABELS, Charset.forName("UTF-8")));
        System.out.println(Files.readAllBytes(DATA).length);
    }

    private static void takeNewDatasetImage() throws IOException {
        BufferedImage image = WebcamReader.takeBinaryImage();
        ImageWriter.saveImageToFile(image);
        ImageWriter.saveBytesToFile(image);
    }

    private static void labelDatasetImage() throws IOException {
        String label = readLabelFromUser();
        Files.write(LABELS, Arrays.asList(label), Charset.forName("UTF-8"),
                StandardOpenOption.APPEND);
    }

    private static String readLabelFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose image label:");
        System.out.println("0 = Rock");
        System.out.println("1 = Paper");
        System.out.println("2 = Scissors");
        return scanner.next();
    }
}
