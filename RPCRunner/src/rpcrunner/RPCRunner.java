
package rpcrunner;

import image.WebcamReader;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Basically oversees running the game.
 * 
 * TODO: get player info
 * TODO: initiate a rock paper and scissors game
 * TODO: send played item to robot
 * TODO: read user input (sign) and record game outcome
 */
public class RPCRunner {

    public static void main(String[] args) {
        WebcamReader reader = new WebcamReader();
        reader.takeImage();
        try {
            ImageIO.write(reader.getImage(), "PNG", new File("test.png"));
        } catch (Exception ex) {
        }
    }
}
