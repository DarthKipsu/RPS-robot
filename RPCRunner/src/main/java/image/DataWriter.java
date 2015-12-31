
package image;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface for data writers, that write images on file.
 */
public interface DataWriter {
    void saveBytesToFile(BufferedImage image) throws IOException;
    void saveImageToFile(BufferedImage image) throws IOException;
    void saveTempToFile(BufferedImage image) throws IOException;
    void saveLabelToFile(int label) throws IOException;
}
