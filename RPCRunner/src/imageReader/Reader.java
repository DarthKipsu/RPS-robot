
package imageReader;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.log.WebcamLogConfigurator;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Read and save an image from computer webcam to be used later.
 */
public class Reader {

	public void readImage() throws Exception {
        WebcamLogConfigurator.configure("logback.xml");
		Webcam webcam = Webcam.getDefault();
        webcam.open();
        ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
	}
}
