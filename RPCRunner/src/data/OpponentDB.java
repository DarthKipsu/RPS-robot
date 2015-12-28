
package data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Opponent database will handle writing and reading of opponent data from file.
 */
public class OpponentDB {
    private final Path opponentDir = Paths.get("data/players/");
    private final Path currentPath = Paths.get("data/current");

    private Stage stage;

    public String currentOpponent;

    public OpponentDB() {
        try {
            currentOpponent = Files.readAllLines(currentPath,
                    Charset.forName("UTF-8")).get(0);
        } catch (IOException ex) {
            currentOpponent = "";
        }
    }

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
        TextField name = new TextField(currentOpponent);
        name.setPromptText("Player name");
        return name;
    }

    private Button submitButton(TextField name) {
        Button submit = new Button("Submit");
        submit.setOnAction(changePlayerName(name));
        return submit;
    }

    private EventHandler changePlayerName(TextField name) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (nameIsValid(name.getText())) {
                    currentOpponent = name.getText();
                    stage.close();
                } else {
                    name.setText("Choose name first!");
                }
            }
        };
    }

    private boolean nameIsValid(String name) {
        return name != null
                && !name.isEmpty()
                && !name.equals("Choose name first!");
    }
}
