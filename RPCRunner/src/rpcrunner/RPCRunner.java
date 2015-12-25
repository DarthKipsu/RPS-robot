
package rpcrunner;

import data.Labeler;
import java.io.IOException;

/**
 * Basically oversees running the game.
 * 
 * TODO: get player info
 * TODO: initiate a rock paper and scissors game
 * TODO: send played item to robot
 * TODO: read user input (sign) and record game outcome
 */
public class RPCRunner {

    private static MachineVisionDisplay vision = new MachineVisionDisplay();

    public static void main(String[] args) throws IOException, InterruptedException {
        vision.handleImageInput();
    }
}
