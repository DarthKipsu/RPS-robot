
package rpcrunner;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Basically oversees running the game.
 * 
 * TODO: get player info
 * TODO: initiate a rock paper and scissors game
 * TODO: send played item to robot
 * TODO: read user input (sign) and record game outcome
 */
public class RPCRunner extends Application {

    private static MachineVisionDisplay vision = new MachineVisionDisplay();

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("RPC runner");
        stage.setScene(new Scene(vision.handleImageInput(stage)));
        stage.show();
    }
}
