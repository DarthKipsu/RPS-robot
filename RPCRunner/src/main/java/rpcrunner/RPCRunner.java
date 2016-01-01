
package rpcrunner;

import data.OpponentDB;
import image.ImageWriter;

import com.github.sarxos.webcam.log.WebcamLogConfigurator;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lejos.pc.comm.NXTCommException;

/**
 * Initiates different stages displayed for player as the game runs.
 */
public class RPCRunner extends Application {
    private final String DATA_DIR = "data";

    private static OpponentDB db;
    private static MachineVisionDisplay vision;
    private static PlayerSelectorDisplay playerSelector;
    private static GameDisplay game;

    private static Stage stage;

    public RPCRunner() throws IOException, NXTCommException {
        db = new OpponentDB(DATA_DIR);
        vision = new MachineVisionDisplay(new ImageWriter(DATA_DIR), db);
        playerSelector = new PlayerSelectorDisplay();
        game = new GameDisplay("00:16:53:08:D4:4D");
    }

    /**
     * Configures webcam and launches the application.
     */
    public static void main(String[] args)
            throws IOException, InterruptedException {
        WebcamLogConfigurator.configure("logback.xml");
        launch(args);
    }

    /**
     * Starts the game by calling a display to ask players username.
     */
    @Override
    public void start(Stage stage) throws IOException {
        RPCRunner.stage = stage;
        RPCRunner.stage.setTitle("RPC runner");
        RPCRunner.stage.setScene(
                new Scene(playerSelector.opponentNameGridPane(db)));
        RPCRunner.stage.show();
    }

    /**
     * Calls a display to start the actual game after getting players username.
     * @param opponent the username of the player
     */
    public static void continueFromPlayerSelection(String opponent)
            throws IOException, InterruptedException {
        Scene scene = new Scene(game.gameGridPane(opponent));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, startGameEarly());
        changeSceneTo(scene);
    }

    /**
     * Calls a display to show the outcome of the game just played.
     */
    public static void displayResults()
            throws IOException, InterruptedException {
        changeSceneTo(new Scene(vision.resultGridPane()));
    }

    /**
     * Calls a display to ask whether or not the player wants to play again or
     * quit. If a rematch is selected the stage will also handle that.
     */
    public static void playAgain()
            throws IOException, InterruptedException {
        Scene scene = new Scene(game.playAgainGridPane());
        scene.addEventHandler(KeyEvent.KEY_PRESSED, startGameEarly());
        scene.addEventHandler(KeyEvent.KEY_PRESSED, closeGame());
        changeSceneTo(scene);
    }

    private static void changeSceneTo(Scene scene) {
        RPCRunner.stage.close();
        RPCRunner.stage.setScene(scene);
        RPCRunner.stage.show();
    }

    private static EventHandler startGameEarly() {
        return (EventHandler<KeyEvent>) (KeyEvent t) -> {
            if (t.getCode().equals(KeyCode.ENTER)) {
                game.addTimelineEffects(1);
            }
        };
    }

    private static EventHandler closeGame() {
        return (EventHandler<KeyEvent>) (KeyEvent t) -> {
            if (t.getCode().equals(KeyCode.ESCAPE)) {
                RPCRunner.stage.close();
            }
        };
    }
}
