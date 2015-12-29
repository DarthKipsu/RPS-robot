
package rpcrunner;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Display for the actual game stage, where player and AI select what they will
 * be playing.
 */
public class GameDisplay {
    private final String COUNT_TEXT = "Let's start by counting down from 3. On "
            + "zero, reveal your rock, paper or scissors.";

    private Timeline timeline = new Timeline();
    private Stage stage;
    private Text countDown;

    /**
     * Display the initial game stage, where game introduction and instructions
     * are shown. Te game will automatically start after 10 seconds. This method
     * is also used to initialize the class (when opponent is known).
     * @param opponent the username for the player
     */
    public GridPane gameGridPane(Stage stage, String opponent) {
        this.stage = stage;
        String welcome = "Welcome " + opponent + ". I want to play a game.";
        GridPane grid = rpcGrid();
        grid.add(new Text(welcome), 0, 0);
        grid.add(new Text(COUNT_TEXT), 0, 1);
        countDown = new Text("");
        grid.add(countDown, 0, 2);
        addTimelineEffects(10);
        return grid;
    }

    /**
     * Display a window allowing player to play another game or quit. Uses the
     * same username as with previous games.
     * 
     * TODO: Change username
     */
    public GridPane playAgainGridPane() {
        String again = "Play again? [enter]";
        String quit = "Quit? [esc]";
        countDown = new Text("");
        GridPane grid = rpcGrid();
        grid.add(new Text(again), 0, 0);
        grid.add(new Text(quit), 0, 1);
        grid.add(countDown, 0, 3);
        return grid;
    }

    /**
     * Creates a new countdown for Text field identified earlier as countdown
     * field. The countdown will start after a given wait time and count down
     * from 3 with each following frame (each frames duration is 1 second).
     * After the count reaches 0, it will close the current scene and display
     * game results.
     * @param waitTime seconds to wait before starting the countdown
     */
    public void addTimelineEffects(int waitTime) {
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(countFor(waitTime + 0, "3", countDown));
        timeline.getKeyFrames().add(countFor(waitTime + 1, "2", countDown));
        timeline.getKeyFrames().add(countFor(waitTime + 2, "1", countDown));
        timeline.getKeyFrames().add(countFor(waitTime + 3, "Play!", countDown));
        timeline.getKeyFrames().add(closeAfter(waitTime + 4));
        timeline.play();
    }

    private GridPane rpcGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 20, 10));
        return grid;
    }

    private KeyFrame countFor(int duration, String count, Text countDown) {
        return new KeyFrame(Duration.seconds(duration), (ActionEvent event) -> {
            countDown.setText(count);
        });
    }

    private KeyFrame closeAfter(int duration) {
        return new KeyFrame(Duration.seconds(duration), (ActionEvent event) -> {
            try {
                stage.close();
                RPCRunner.displayResults(stage);
            } catch (IOException | InterruptedException ex) {
            }
        });
    }
}
