
package image;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.log.WebcamLogConfigurator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Read images from computer webcam.
 */
public class WebcamReader {
    private static Webcam webcam = Webcam.getDefault();

    /**
     * Take an image with the computer primary webcam and return a small black
     * and white version of it. The resulting image is 80 x 65 pixels in size.
     * The image is also saved to disc as temp file.
     * @return binary version of image taken
     */
    public static BufferedImage takeBinaryImage() throws IOException {
        int scaledWidth = 80;
        int scaledHeight = 65;
        
        BufferedImage image = takeImage();
        BufferedImage binary = new BufferedImage(scaledWidth, scaledHeight,
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g = binary.createGraphics();
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        
        ImageWriter.saveTempToFile(binary);
        return binary;
    }

	private static BufferedImage takeImage() {
        webcam.open();
        BufferedImage image = webcam.getImage();
        webcam.close();
        return image;
	}
}
