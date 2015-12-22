
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
    
    public BufferedImage getGreyImage() {
        BufferedImage gray = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g = gray.createGraphics();
        g.drawImage(image, 0, 0, null);
        return gray;
    }
}
