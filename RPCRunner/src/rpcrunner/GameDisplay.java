
package rpcrunner;

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
    private Stage stage;
    private String opponent;

    public GridPane gameGridPane(Stage stage, String opponent) {
        this.stage = stage;
        this.opponent = opponent;
        GridPane grid = rpcGrid();
        grid.add(new Text("Welcome " + opponent + ". I want to play a game."), 0, 0);
        grid.add(new Text("Let's start by counting down from 3. On zero, reveal your rock, paper or scissors."), 0, 1);
        Text countDown = new Text("");
        grid.add(countDown, 0, 2);
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(countFor(5, "3", countDown));
        timeline.getKeyFrames().add(countFor(6, "2", countDown));
        timeline.getKeyFrames().add(countFor(7, "1", countDown));
        timeline.getKeyFrames().add(countFor(8, "Play!", countDown));
        timeline.play();
        return grid;
    }

    private GridPane rpcGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 20, 10));
        return grid;
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
}
