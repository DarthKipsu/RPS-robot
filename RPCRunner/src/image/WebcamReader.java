
package image;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.log.WebcamLogConfigurator;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Read and save an image from computer webcam to be used later.
 */
public class WebcamReader {

    BufferedImage image;

    /**
     * Take an image with the computer primary webcam.
     */
	public void takeImage() {
        WebcamLogConfigurator.configure("logback.xml");
		Webcam webcam = Webcam.getDefault();
        webcam.open();
        image = webcam.getImage();
	}

    public BufferedImage getImage() {
        return image;
    }
    
    public BufferedImage getBinaryImage() {
        int scaledWidth = 80;
        int scaledHeight = 65;
        BufferedImage binary = new BufferedImage(scaledWidth, scaledHeight,
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g = binary.createGraphics();
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        return binary;
    }
}
