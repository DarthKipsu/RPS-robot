
package image;

import java.awt.image.BufferedImage;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class WebcamReaderTest {

    private WebcamReader webcam;
    private BufferedImage test;

    /* 
    * The same image is used with all tests to save time running them. Taking
    * the image is the biggest single time consumer within the program.
    */
    public WebcamReaderTest() throws Exception {
        webcam = new WebcamReader(new ImageWriter("testdata"));
        test = webcam.takeBinaryImage();
        FileUtils.deleteDirectory(new File("testdata"));
    }

    @Test
    public void testBinaryImageTypeIsByteBinary() {
        assertEquals(test.getType(), BufferedImage.TYPE_BYTE_BINARY);
    }

    @Test
    public void testDefaultBinaryImageSize() {
        assertEquals(test.getTileHeight(), 65);
        assertEquals(test.getTileWidth(), 80);
    }
}
