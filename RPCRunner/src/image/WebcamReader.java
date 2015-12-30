
package image;

import com.github.sarxos.webcam.Webcam;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Read images from computer webcam.
 */
public class WebcamReader {
    private final Webcam webcam;
    private final int scaledWidth;
    private final int scaledHeight;
    private final DataWriter writer;

    public WebcamReader(int scaledWidth, int scaledHeight, DataWriter writer) {
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
        this.writer = writer;
        this.webcam = Webcam.getDefault();
    }

    public WebcamReader(DataWriter writer) {
        this(80, 65, writer);
    }

    /**
     * Take an image with the computer primary webcam and return a small black
     * and white version of it. The resulting image is 80 x 65 pixels in size.
     * The image is also saved to disc as temp file.
     * @return binary version of image taken
     */
    public BufferedImage takeBinaryImage() throws IOException {        
        BufferedImage image = takeImage();
        BufferedImage binary = new BufferedImage(scaledWidth, scaledHeight,
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g = binary.createGraphics();
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        
        writer.saveTempToFile(binary);
        return binary;
    }

	private BufferedImage takeImage() {
        webcam.open();
        BufferedImage image = webcam.getImage();
        webcam.close();
        return image;
	}
}
