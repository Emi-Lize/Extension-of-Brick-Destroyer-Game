package brickdestroyer.homescreen;

import javax.swing.*;
import java.awt.*;

/**
 * New Class - Places an image as the background for home menu and info screen
 */
public class BackgroundImage extends JPanel {
    /**
     * Sets the image in the background
     * @param size The size of the home menu and info screen
     */
    public BackgroundImage(Dimension size) {
        new ImageIcon("brick.jpg").getImage();
        this.setPreferredSize(size);
        setOpaque(false);
    }

}
