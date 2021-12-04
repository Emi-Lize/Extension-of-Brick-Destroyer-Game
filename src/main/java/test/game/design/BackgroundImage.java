package test.game.design;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel with a background image
 */
public class BackgroundImage extends JPanel {
    /**
     * Sets the image in the background
     * @param size The size of the home menu screen
     */
    public BackgroundImage(Dimension size) {
        new ImageIcon("brick.jpg").getImage();
        this.setPreferredSize(size);
        setOpaque(false);
    }
}
