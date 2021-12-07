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
 * This represents the home menu of the game
 * <br>Change:
 * <ul>
 *     <li>Changed name of MENU_TEXT to EXIT_TEXT to improve clarity</li>
 *     <li>Changed name of menuButton to exitButton to improve clarity</li>
 *     <li>Changed name of menuClicked to exitClicked to improve clarity</li>
 *     <li>Moved code to design the homemenu to HomeDesign</li>
 *     <li>Added an info button</li>
 * </ul>
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {
    private Rectangle startButton;
    private Rectangle exitButton;
    private Rectangle infoButton;

    private GameFrame owner;
    private HomeDesign homeDesign;

    private boolean startClicked;
    private boolean exitClicked;
    private boolean infoClicked;

    /**
     * Initialises the home menu
     * <br>Change:
     * <ul>
     *     <li>Moved code to set the properties of the window to method initialise</li>
     *     <li>Moved code to set the border design to method setBorderDesign</li>
     *     <li>Moved code to set the font of the string to method setFont</li>
     * </ul>
     * @param owner The window the game frame is in
     * @param area The area of the home menu
     */
    public HomeMenu(GameFrame owner,Dimension area){
        initialise();
        this.owner = owner;

        this.setPreferredSize(area);
        homeDesign = new HomeDesign(area, this);
    }

    /**
     * New Method - Sets the property of the home menu window
     */
    public void initialise(){
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Draws the home menu components
     * <br>Change:
     * <ul>
     *     <li>Calls drawMenu method from homeDesign to design HomeMenu</li>
     * </ul>
     * @param g An object which draws the components
     */
    public void paint(Graphics g){
        homeDesign.drawMenu((Graphics2D)g);

        startButton= homeDesign.getStartButton();
        exitButton= homeDesign.getExitButton();
        infoButton= homeDesign.getInfoButton();
    }

    /**
     * Gets the boolean representing if the start button is clicked
     * @return A boolean representing if the start button is clicked
     */
    public boolean isStartClicked() {
        return startClicked;
    }

    /**
     * Gets the boolean representing if the exit button is clicked
     * @return A boolean representing if the exit button is clicked
     */
    public boolean isExitClicked() {
        return exitClicked;
    }

    /**
     * Gets the boolean representing if the info button is clicked
     * @return A boolean representing if the info button is clicked
     */
    public boolean isInfoClicked(){
        return infoClicked;
    }

    /**
     * Checks if the button was clicked and invokes a method if so
     * <br>Change:
     * <ul>
     *     <li>Added a condition if the info button is clicked</li>
     * </ul>
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
        }
        else if(infoButton.contains(p)){
            owner.enableInfo();
        }
        else if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }

    /**
     * Checks when the mouse is pressed and changes the colour of the button
     * <br>Change:
     * <ul>
     *      <li>Added a condition if the info button is clicked</li>
     * </ul>
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(infoButton.contains(p)){
            infoClicked=true;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
        else if(exitButton.contains(p)){
            exitClicked = true;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
    }

    /**
     * Checks when the mouse is released and changes the colour of the button back to its initial colour
     * <br>Change:
     * <ul>
     *      <li>Added a condition if the info button is clicked</li>
     * </ul>
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(infoClicked){
            infoClicked=false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
        else if(exitClicked){
            exitClicked = false;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
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
     * Checks if the mouse has moved and changes it to a hand shape if hovering over a button
     * <br>Change:
     * <ul>
     *      <li>Added a condition if the info button is clicked</li>
     * </ul>
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || exitButton.contains(p) || infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

}
