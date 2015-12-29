
package rpcrunner;

import data.OpponentDB;

import java.io.File;
import java.io.IOException;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Displays player selection box before starting a game.
 */
public class PlayerSelectorDisplay {

    private final OpponentDB db = new OpponentDB();
    private Stage stage;

    public GridPane opponentNameGridPane(Stage stage) {
        this.stage = stage;
        GridPane grid = rpcGrid();
        TextField nameField = nameField();
        grid.add(new Text("Player name: "), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(submitButton(nameField), 2, 0);
        return grid;
    }

    private GridPane rpcGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 20, 10));
        return grid;
    }

    private TextField nameField() {
        TextField name = new TextField(db.getOpponent());
        name.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            if (t.getCode().equals(KeyCode.ENTER)) {
                handlePlayerSelectionEvent(name);
            }
        });
        return name;
    }

    private Button submitButton(TextField name) {
        Button submit = new Button("Submit");
        submit.setOnAction((EventHandler) (Event t) -> {
            handlePlayerSelectionEvent(name);
        });
        return submit;
    }

    private void handlePlayerSelectionEvent(TextField name) {
        if (db.nameIsValid(name.getText())) {
            selectPalyerAndContinue(name);
        } else {
            name.setText("Choose name first!");
        }
    }

    private void selectPalyerAndContinue(TextField name) {
        try {
            File playList = db.selectOpponent(name.getText());
            stage.close();
            RPCRunner.continueFromPlayerSelection(stage,
                    db.getOpponent(),
                    playList);
        } catch (IOException | InterruptedException ex) {
        }
    }
}
