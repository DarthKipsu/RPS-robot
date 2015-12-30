
package image;

import java.awt.image.BufferedImage;
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

    private ImageWriter writer;
    private BufferedImage testImage;

    public ImageWriterTest() throws Exception {
        FileUtils.deleteDirectory(new File("testdata"));
        testImage = new BufferedImage(80, 65, 2);
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
    public void willUseAppendWhenWritingALabelToFile() throws Exception {
        writer.saveLabelToFile(0);
        writer.saveLabelToFile(1);
        writer.saveLabelToFile(2);
        List<String> labels = Files.readAllLines(LABELS, Charset.forName("UTF-8"));
        assertEquals("0", labels.get(0));
        assertEquals("1", labels.get(1));
        assertEquals("2", labels.get(2));
    }
}
