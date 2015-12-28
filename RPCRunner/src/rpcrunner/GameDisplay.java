
package rpcrunner;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    
    private Stage stage;

    public GridPane gameGridPane(Stage stage, String opponent) {
        this.stage = stage;
        String welcome = "Welcome " + opponent + ". I want to play a game.";
        GridPane grid = rpcGrid();
        grid.add(new Text(welcome), 0, 0);
        grid.add(new Text(COUNT_TEXT), 0, 1);
        Text countDown = new Text("");
        grid.add(countDown, 0, 2);
        addTimelineEffects(countDown);
        return grid;
    }

    private GridPane rpcGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 20, 10));
        return grid;
    }

    private void addTimelineEffects(Text countDown) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(countFor(8, "3", countDown));
        timeline.getKeyFrames().add(countFor(9, "2", countDown));
        timeline.getKeyFrames().add(countFor(10, "1", countDown));
        timeline.getKeyFrames().add(countFor(11, "Play!", countDown));
        timeline.getKeyFrames().add(closeAfter(12));
        timeline.play();
    }

    private KeyFrame countFor(int duration, String count, Text countDown) {
        return new KeyFrame(Duration.seconds(duration),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    countDown.setText(count);
                }
            });
    }

    private KeyFrame closeAfter(int duration) {
        return new KeyFrame(Duration.seconds(duration),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        stage.close();
                        RPCRunner.displayResults(stage);
                    } catch (IOException | InterruptedException ex) {
                    }
                }
            });
    }
}
