
package rpcrunner;

import static data.Labeler.LABELS;
import data.ProgramExecuter;
import image.ImageWriter;
import image.WebcamReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
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
    
    private Text predictionText = new Text();
    private int prediction;

    public GridPane handleImageInput() throws IOException, InterruptedException {
        BufferedImage image = WebcamReader.takeBinaryImage();
        ImageWriter.saveTempToFile(image);
        prediction = exe.execute("../MachineLearning/prophet.py");
        return buildImageFrame(image);
    }

    private GridPane buildImageFrame(BufferedImage image) {
        GridPane grid = RPCGrid();
        updatePredictionText();
        grid.add(viewFrom(image), 0, 0);
        grid.add(predictionText, 1, 0);
        grid.add(buttons(grid, image), 0, 1, 2, 1);
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

    private void updatePredictionText() {
        predictionText.setText("Predicted \"" + SIGNS[prediction] + "\"");
    }

    private HBox buttons(GridPane grid, BufferedImage image) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Button acceptButton = new Button("ACCEPT");
        acceptButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                try {
                    ImageWriter.saveBytesToFile(image);
                    ImageWriter.saveImageToFile(image);
                    Files.write(LABELS, Arrays.asList("" + prediction), Charset.forName("UTF-8"),
                        StandardOpenOption.APPEND);
                    Stage stage = (Stage) acceptButton.getScene().getWindow();
                    stage.close();
                } catch (Exception ex) {
                }
            }
        });
        Button undoButton = new Button("Retake photo");
        undoButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                try {
                    BufferedImage image = WebcamReader.takeBinaryImage();
                    ImageWriter.saveTempToFile(image);
                    prediction = exe.execute("../MachineLearning/prophet.py");
                    System.out.println(prediction);
                    updatePredictionText();
                    grid.add(viewFrom(image), 0, 0);
                } catch (Exception ex) {
                }
            }
        });
        Text text = new Text("It really was:");
        hbox.getChildren().addAll(acceptButton, undoButton, text);
        for (int i = 0; i < SIGNS.length; i++) {
            if (i == prediction) continue;
            Button signButton = new Button(SIGNS[i]);
            hbox.getChildren().add(signButton);
            String sign = "" + i;
            signButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override public void handle(ActionEvent e) {
                    try {
                        ImageWriter.saveBytesToFile(image);
                        ImageWriter.saveImageToFile(image);
                        Files.write(LABELS, Arrays.asList(sign), Charset.forName("UTF-8"),
                            StandardOpenOption.APPEND);
                        Stage stage = (Stage) signButton.getScene().getWindow();
                        stage.close();
                    } catch (Exception ex) {
                    }
                }
            });
        }
        return hbox;
    }
}
