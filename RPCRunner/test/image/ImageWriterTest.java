
package image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ImageWriterTest {
    private static Path LABELS = Paths.get("testdata/labels");
    private static Path DATA = Paths.get("testdata/data");
    private static String IMAGES = "testdata/images/";

    private ImageWriter writer;
    private BufferedImage testImage;

    public ImageWriterTest() throws Exception {
        FileUtils.deleteDirectory(new File("testdata"));
        testImage = new BufferedImage(3, 5, BufferedImage.TYPE_BYTE_BINARY);
        byte[] testData = ((DataBufferByte)testImage.getRaster().getDataBuffer()).getData();
        byte[] imageBytes = new byte[]{0x30,0x30,0x30,0x30,0x30};
        System.arraycopy(imageBytes, 0, testData, 0, testData.length);
    }

    @Before
    public void setUp() throws Exception {
        writer = new ImageWriter("testdata");
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("testdata"));
    }

    @Test
    public void willCreateDataDirectoryIfItsMissing() throws Exception {
        try {
            writer.saveImageToFile(testImage);
            writer.saveLabelToFile(0);
        } catch (NoSuchFileException e) {
            fail("Should have created missing directories and files");
        }
    }

    @Test
    public void willWriteALabelToFile() throws Exception {
        writer.saveLabelToFile(0);
        List<String> labels = Files.readAllLines(LABELS, Charset.forName("UTF-8"));
        assertEquals("0", labels.get(0));
    }

    @Test
    public void willUseAppendWhenWritingLabelsToFile() throws Exception {
        writer.saveLabelToFile(0);
        writer.saveLabelToFile(2);
        writer.saveLabelToFile(1);
        List<String> labels = Files.readAllLines(LABELS, Charset.forName("UTF-8"));
        assertEquals("0", labels.get(0));
        assertEquals("2", labels.get(1));
        assertEquals("1", labels.get(2));
    }

    @Test
    public void willWriteABinaryImageToFile() throws Exception {
        writer.saveBytesToFile(testImage);
        List<String> data = Files.readAllLines(DATA);
        assertEquals("00000", data.get(0));
    }

    @Test
    public void willUseAppendWhenWritingBinaryImagesToFile() throws Exception {
        writer.saveBytesToFile(testImage);
        writer.saveBytesToFile(testImage);
        writer.saveBytesToFile(testImage);
        List<String> data = Files.readAllLines(DATA);
        assertEquals("000000000000000", data.get(0));
    }

    @Test
    public void savesAnImageToDiscAsPng() throws Exception {
        writer.saveImageToFile(testImage);
        assertTrue(Files.exists(Paths.get(IMAGES + "0.png")));
    }

    @Test
    public void savesSeveralImagesToFileWithIncreasingNumbers() throws Exception {
        writer.saveImageToFile(testImage);
        writer.saveLabelToFile(0);
        writer.saveImageToFile(testImage);
        writer.saveLabelToFile(0);
        writer.saveImageToFile(testImage);
        writer.saveLabelToFile(0);
        assertTrue(Files.exists(Paths.get(IMAGES + "0.png")));
        assertTrue(Files.exists(Paths.get(IMAGES + "1.png")));
        assertTrue(Files.exists(Paths.get(IMAGES + "2.png")));
    }

    @Test
    public void savesAnImageOnTopOfOldOneWhenNoLabelHasBeenAssigned() throws Exception {
        writer.saveImageToFile(testImage);
        writer.saveImageToFile(testImage);
        assertTrue(Files.exists(Paths.get(IMAGES + "0.png")));
        assertFalse(Files.exists(Paths.get(IMAGES + "1.png")));
    }
}
