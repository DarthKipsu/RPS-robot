
package rpcrunner;

import data.OpponentDB;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kipsu
 */
public class AIPlayerTest {
    private final AIPlayer ai = new AIPlayer(new OpponentDB("testdata"));

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("testdata"));
    }

    @Test
    public void playReturnsAnIntegerBetween0To2() {
        for (int i = 0; i < 100; i++) {
            int result = ai.play();
            assertTrue(result >= 0);
            assertTrue(result <= 2);
        }
    }
    
}
