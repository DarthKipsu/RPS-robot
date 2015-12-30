
package image;

import java.awt.image.BufferedImage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WebcamReaderTest {

    private WebcamReader webcam;

    @Before
    public void setUp() {
        webcam = new WebcamReader();
    }

    @Test
    public void testBinaryImageTypeIsByteBinary() throws Exception {
        BufferedImage test = webcam.takeBinaryImage();
        assertEquals(test.getType(), BufferedImage.TYPE_BYTE_BINARY);
    }

    @Test
    public void testDefaultBinaryImageSize() throws Exception {
        BufferedImage test = webcam.takeBinaryImage();
        assertEquals(test.getTileHeight(), 65);
        assertEquals(test.getTileWidth(), 80);
    }
}
