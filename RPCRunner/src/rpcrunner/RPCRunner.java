
package rpcrunner;

import data.Labeler;

/**
 * Basically oversees running the game.
 * 
 * TODO: get player info
 * TODO: initiate a rock paper and scissors game
 * TODO: send played item to robot
 * TODO: read user input (sign) and record game outcome
 */
public class RPCRunner {

    private static MachineVisionDisplay vision = new MachineVisionDisplay(
            new Labeler());

    public static void main(String[] args) {
        vision.handleImageInput();
    }
}
