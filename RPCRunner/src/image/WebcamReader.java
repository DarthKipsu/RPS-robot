
package image;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.log.WebcamLogConfigurator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Read images from computer webcam.
 */
public class WebcamReader {

    /**
     * Take an image with the computer primary webcam.
     * @return image taken
     */
	public static BufferedImage takeImage() {
        WebcamLogConfigurator.configure("logback.xml");
		Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage image = webcam.getImage();
        webcam.close();
        return image;
	}

    /**
     * Take an image with the computer primary webcam and return a small black
     * and white version of it. The resulting image is 80 x 65 pixels in size.
     * @return binary version of image taken
     */
    public static BufferedImage takeBinaryImage() {
        int scaledWidth = 80;
        int scaledHeight = 65;
        
        BufferedImage image = takeImage();
        
        BufferedImage binary = new BufferedImage(scaledWidth, scaledHeight,
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g = binary.createGraphics();
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        return binary;
    }
}
