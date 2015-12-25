
package rpcrunner;

import data.Labeler;
import image.WebcamReader;
import java.awt.BorderLayout;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * Displays user input as JFrame.
 * Works as the eyes of the RPC runner and handles seeing user hand sign as well
 * as labeling and saving process for adding the data to the database.
 */
public class MachineVisionDisplay {

    private Labeler labeler;

    public MachineVisionDisplay(Labeler labeler) {
        this.labeler = labeler;
    }

    public void handleImageInput() {
        BufferedImage image = WebcamReader.takeBinaryImage();
        buildImageFrame(image);
    }

    private void buildImageFrame(BufferedImage image) {
        JFrame frame = buildDefaultFrame();
        addImageTo(frame, image);
        frame.setVisible(true);
    }

    private JFrame buildDefaultFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        return frame;
    }

    private void addImageTo(JFrame frame, BufferedImage image) {
        JLabel imageIcon = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(imageIcon, BorderLayout.CENTER);
    }
}
