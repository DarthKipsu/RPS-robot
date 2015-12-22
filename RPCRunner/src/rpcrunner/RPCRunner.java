
package rpcrunner;

import imageReader.Reader;

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
        try {
            new Reader().readImage();
        } catch (Exception ex) {
        }
    }
    
}
