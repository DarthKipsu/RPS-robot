
package rpcrunner;

import data.ProgramExecuter;
import image.ImageWriter;
import image.WebcamReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Displays user input as JFrame.
 * Works as the eyes of the RPC runner and handles seeing user hand sign as well
 * as labeling and saving process for adding the data to the database.
 */
public class MachineVisionDisplay {
    private final String[] SIGNS = new String[]{"Rock", "Paper", "Scissors"};
    private final ProgramExecuter exe = new ProgramExecuter();
    private Stage stage;
    private File opponentFile;
    
    private Text predictionText = new Text();
    private int prediction;
    private BufferedImage image;

    public GridPane resultGridPane(Stage stage,
            File opponentFile) throws IOException, InterruptedException {
        this.stage = stage;
        this.opponentFile = opponentFile;
        takeNewImage();
        return buildImageFrame();
    }

    private BufferedImage takeNewImage()
            throws IOException, InterruptedException {
        image = WebcamReader.takeBinaryImage();
        prediction = exe.predictImageSign();
        updatePredictionText();
        return image;
    }

    private GridPane buildImageFrame() {
        GridPane grid = rpcGrid();
        grid.add(imageInFxFormat(), 0, 0);
        grid.add(predictionText, 1, 0);
        grid.add(buttons(grid), 0, 1, 2, 1);
        return grid;
    }

    private GridPane rpcGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 20, 10));
        return grid;
    }

    private ImageView imageInFxFormat() {
        return new ImageView(SwingFXUtils.toFXImage(image,
                new WritableImage(image.getWidth(), image.getHeight())));
    }

    private void updatePredictionText() {
        predictionText.setText("AI saw \"" + SIGNS[prediction] + "\"");
    }

    private HBox buttons(GridPane grid) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(
                newButton("ACCEPT", imageSaveEvent()),
                newButton("Retake photo", newImageEvent(grid)),
                new Text("It really was:"));
        addSignCorrectionButtonsFor(hbox);
        return hbox;
    }

    private EventHandler imageSaveEvent(int... label) {
        return (EventHandler<ActionEvent>) (ActionEvent t) -> {
            if (label.length > 0) prediction = label[0];
            try {
                ImageWriter.saveBytesToFile(image);
                ImageWriter.saveImageToFile(image);
                ImageWriter.saveLabelToFile(prediction);
                stage.close();
            } catch (IOException ex) {}
        };
    }

    private EventHandler newImageEvent(GridPane grid) {
        return (EventHandler<ActionEvent>) (ActionEvent e) -> {
            try {
                image = takeNewImage();
                grid.add(imageInFxFormat(), 0, 0);
            } catch (IOException | InterruptedException ex) {}
        };
    }

    private Button newButton(String buttonText, EventHandler eventHandler) {
        Button button = new Button(buttonText);
        button.setOnAction(eventHandler);
        return button;
    }

    private void addSignCorrectionButtonsFor(HBox hbox) {
        for (int i = 0; i < SIGNS.length; i++) {
            hbox.getChildren().add(newButton(SIGNS[i], imageSaveEvent(i)));
        }
    }
}
