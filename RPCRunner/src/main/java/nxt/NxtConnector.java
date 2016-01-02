
package nxt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Handles communications between the main program and NXT brick connector.
 */
public class NxtConnector {
    private boolean nxtInUse;
    private File pipe;

    public NxtConnector(String nxtpipe) {
        pipe = new File(nxtpipe);
        if (pipe.exists()) {
            nxtInUse = true;
            System.out.println("Using nxt robot.");
        } else {
            nxtInUse = false;
            System.out.println("No robot detected.");
        }
    }

    public void prepareForGame() {
        if (nxtInUse) {
            writeToPipe("3");
        }
    }

    public void playAGame(int sign) {
        if (nxtInUse) {
            System.out.println("Playing a game of " + sign);
            writeToPipe("" + sign);
        }
    }

    public void closeRobot() {
        if (nxtInUse) {
            writeToPipe("-1");
        }
    }

    private void writeToPipe(String message) {
        try {
            Files.write(pipe.toPath(),
                    Arrays.asList(message),
                    Charset.forName("UTF-8"),
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
        }
    }
}
