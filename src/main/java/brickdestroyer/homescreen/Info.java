/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package brickdestroyer.homescreen;

import brickdestroyer.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * New Class - The info screen which explains the game and controls
 */
public class Info extends JComponent implements MouseListener, MouseMotionListener {
    private Rectangle backButton;

    private InfoDesign infoDesign;
    private GameFrame owner;

    private boolean backClicked;

    /**
     * This represents the info screen of the game and initialises it
     * @param owner The window the game frame is in
     * @param area The area of the home menu
     */
    public Info(GameFrame owner, Dimension area){
        initialise();
        this.owner=owner;

        this.setPreferredSize(area); //set size
        infoDesign = new InfoDesign(area, this);

    }

    /**
     * Sets the property of the info window
     */
    public void initialise(){
        this.setFocusable(true); //focus on home menu
        this.requestFocusInWindow(); //ensure it is focused on
        this.addMouseListener(this); //WindowListener watches for mouse
        this.addMouseMotionListener(this);
    }

    /**
     * Draws the info screen
     * @param g An object which draws the components
     */
    public void paint(Graphics g){
        infoDesign.drawMenu((Graphics2D)g);
        backButton=infoDesign.getBackButton();
    }

    /**
     * Gets the boolean of backClicked
     * @return A boolean representing if the back button is clicked
     */
    public boolean isBackClicked() {
        return backClicked;
    }

    /**
     * Checks if the button was clicked and invokes a method if so
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            owner.enableHomeMenu();
        }
    }

    /**
     * Checks when the mouse is pressed and changes the colour of the button
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) { //change colour when button clicked
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            backClicked = true;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
        }
    }

    /**
     * Checks when the mouse is released and changes the colour of the button back to its initial colour
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) { //change colour back when button not clicked
        if(backClicked ){
            backClicked = false;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * Checks if the mouse has moved and where it is
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) { //change cursor shape
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

}
