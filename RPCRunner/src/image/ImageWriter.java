
package image;

import static data.Labeler.DATA;
import static data.Labeler.IMAGE_DIRECTORY;
import static data.Labeler.LABELS;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;

/**
 * Writes images used as data to the disc.
 */
public class ImageWriter {
    public static final Path TEMPDATA = Paths.get("data/temp");

    /**
     * Save an image file to the disc as a byte array. The array is 650 bytes
     * per one image and all images are appended after each other on the same
     * array. Location of the file written is determined in Labeler class.
     * @param image to be written
     * @throws IOException 
     */
    public static void saveBytesToFile(BufferedImage image) throws IOException {
        byte[] imgarray = ((DataBufferByte)image.getRaster()
                .getDataBuffer())
                .getData();

        Files.write(DATA, imgarray, StandardOpenOption.APPEND);
    }

    /**
     * Save an image file to the disc as a png image. Location of the file
     * written is determined in Labeler class.
     * @param image to be written
     * @throws IOException 
     */
    public static void saveImageToFile(BufferedImage image) throws IOException {
        ImageIO.write(image, "PNG", new File(IMAGE_DIRECTORY +
                Files.readAllLines(LABELS).size() + ".png"));
    }

    /**
     * Saves an image temporarily to disc as a byte array. The array is 650
     * byptes long and can be appended to the end of data byte list later when
     * an image label is figured out.
     * @param image to be written
     * @throws IOException 
     */
    public static void saveTempToFile(BufferedImage image) throws IOException {
        byte[] imgarray = ((DataBufferByte)image.getRaster()
                .getDataBuffer())
                .getData();

        Files.write(TEMPDATA, imgarray);
    }
}
