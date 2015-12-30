
package image;

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

import javax.imageio.ImageIO;

/**
 * Writes images used as data to the disc.
 */
public class ImageWriter implements DataWriter {
    private final String IMAGE_DIRECTORY;
    private final Path TEMPDATA;
    private final Path DATA;
    private final Path LABELS;

    public ImageWriter(String dataDirectory) {
        IMAGE_DIRECTORY = dataDirectory + "images/";
        TEMPDATA = Paths.get(dataDirectory + "temp");
        DATA = Paths.get(dataDirectory + "data");
        LABELS = Paths.get(dataDirectory + "labels");
    }

    /**
     * Save an image file to the disc as a byte array. The array is 650 bytes
     * per one image and all images are appended after each other on the same
     * array. Location of the file written is determined in Labeler class.
     * @param image to be written
     * @throws java.io.IOException
     */
    @Override
    public void saveBytesToFile(BufferedImage image) throws IOException {
        byte[] imgarray = ((DataBufferByte)image.getRaster()
                .getDataBuffer())
                .getData();

        Files.write(DATA, imgarray, StandardOpenOption.APPEND);
    }

    /**
     * Save an image file to the disc as a png image. Location of the file
     * written is determined in Labeler class.
     * @param image to be written
     * @throws java.io.IOException
     */
    @Override
    public void saveImageToFile(BufferedImage image) throws IOException {
        ImageIO.write(image, "PNG", new File(IMAGE_DIRECTORY +
                Files.readAllLines(LABELS).size() + ".png"));
    }

    /**
     * Saves an image temporarily to disc as a byte array. The array is 650
     * byptes long and can be appended to the end of data byte list later when
     * an image label is figured out.
     * @param image to be written
     * @throws java.io.IOException
     */
    @Override
    public void saveTempToFile(BufferedImage image) throws IOException {
        byte[] imgarray = ((DataBufferByte)image.getRaster()
                .getDataBuffer())
                .getData();

        Files.write(TEMPDATA, imgarray);
    }

    /**
     * Saves a label to file. Labels are saved as strings, each label on their
     * corresponding line, matching the same image index (in byte array format).
     * @param label 
     * @throws java.io.IOException 
     */
    @Override
    public void saveLabelToFile(int label) throws IOException {
        Files.write(LABELS,
                Arrays.asList(""+label),
                Charset.forName("UTF-8"),
                StandardOpenOption.APPEND);
    }
}
