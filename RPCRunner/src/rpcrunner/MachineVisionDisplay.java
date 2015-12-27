
package rpcrunner;

import data.ProgramExecuter;
import image.ImageWriter;
import image.WebcamReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Displays user input as JFrame.
 * Works as the eyes of the RPC runner and handles seeing user hand sign as well
 * as labeling and saving process for adding the data to the database.
 */
public class MachineVisionDisplay {
    private final String[] SIGNS = new String[]{"Rock", "Paper", "Scissors"};

    public GridPane handleImageInput() throws IOException, InterruptedException {
        ProgramExecuter exe = new ProgramExecuter();
        BufferedImage image = WebcamReader.takeBinaryImage();
        ImageWriter.saveTempToFile(image);
        int prediction = exe.execute("../MachineLearning/prophet.py");
        return buildImageFrame(image, prediction);
    }

    private GridPane buildImageFrame(BufferedImage image, int prediction) {
        GridPane grid = RPCGrid();
        grid.add(viewFrom(image), 0, 0);
        grid.add(predictionText(prediction), 1, 0);
        grid.add(buttons(prediction), 0, 1, 2, 1);
        return grid;
    }

    private GridPane RPCGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 20, 10));
        return grid;
    }

    private ImageView viewFrom(BufferedImage image) {
        return new ImageView(SwingFXUtils.toFXImage(image,
                new WritableImage(image.getWidth(), image.getHeight())));
    }

    private Text predictionText(int prediction) {
        return new Text("Predicted \"" + SIGNS[prediction] + "\"");
    }

    private HBox buttons(int prediction) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Button acceptButton = new Button("ACCEPT");
        Button undoButton = new Button("Cancel");
        Text text = new Text("It really was:");
        hbox.getChildren().addAll(acceptButton, undoButton, text);
        for (int i = 0; i < SIGNS.length; i++) {
            if (i == prediction) continue;
            Button signButton = new Button(SIGNS[i]);
            hbox.getChildren().add(signButton);
        }
        return hbox;
    }
}
