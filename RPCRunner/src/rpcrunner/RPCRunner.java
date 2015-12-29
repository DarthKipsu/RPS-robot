
package rpcrunner;

import com.github.sarxos.webcam.log.WebcamLogConfigurator;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private static final MachineVisionDisplay vision =
            new MachineVisionDisplay();
    private static final PlayerSelectorDisplay playerSelector =
            new PlayerSelectorDisplay();
    private static final GameDisplay game = new GameDisplay();

    private static File opponentFile;

    public static void main(String[] args)
            throws IOException, InterruptedException {
        WebcamLogConfigurator.configure("logback.xml");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("RPC runner");
        stage.setScene(new Scene(playerSelector.opponentNameGridPane(stage)));
        stage.show();
    }

    public static void continueFromPlayerSelection(Stage stage,
            String opponent,
            File file) throws IOException, InterruptedException {
        opponentFile = file;
        Scene scene = new Scene(game.gameGridPane(stage, opponent));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, startGameEarly());
        stage.setScene(scene);
        stage.show();
    }

    public static void displayResults(Stage stage)
            throws IOException, InterruptedException {
        stage.setScene(new Scene(vision.resultGridPane(stage, opponentFile)));
        stage.show();
    }

    private static EventHandler startGameEarly() {
        return (EventHandler<KeyEvent>) (KeyEvent t) -> {
            if (t.getCode().equals(KeyCode.ENTER)) {
                game.addTimelineEffects(1);
            }
        };
    }
}
