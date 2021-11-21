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
package test.game.system;

import test.game.design.MenuDesign;
import test.game.design.GameFrame;

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
 *     <li>Moved code to design the homemenu to MenuDesign</li>
 * </ul>
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {
    private Rectangle startButton;
    private Rectangle exitButton;

    private GameFrame owner;
    private MenuDesign menuDesign;

    private boolean startClicked;
    private boolean exitClicked;

    /**
     * This represents the home menu of the game and initialises it
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

        this.setPreferredSize(area); //set size
        menuDesign = new MenuDesign(this, area);

    }

    /**
     * New Method - Sets the property of the home menu window
     */
    private void initialise(){
        this.setFocusable(true); //focus on home menu
        this.requestFocusInWindow(); //ensure it is focused on
        this.addMouseListener(this); //WindowListener watches for mouse
        this.addMouseMotionListener(this);
    }

    /**
     * Draws the home menu
     * <br>Change:
     * <ul>
     *     <li>Calls drawMenu method from menuDesign to design HomeMenu</li>
     *     <li>Added two arguments to drawMenu</li>
     * </ul>
     * @param g An object which draws the components
     */
    public void paint(Graphics g){
        menuDesign.drawMenu((Graphics2D)g, startClicked, exitClicked);
        startButton=menuDesign.getStartButton();
        exitButton=menuDesign.getExitButton();
    }

    /**
     * Checks if the button was clicked and invokes a method if so
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
        }
        else if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }

    /**
     * Checks when the mouse is pressed and changes the colour of the button
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) { //change colour when button clicked
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(exitButton.contains(p)){
            exitClicked = true;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
    }

    /**
     * Checks when the mouse is released and changes the colour of the button back to its initial colour
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) { //change colour back when button unclicked
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
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
     * Checks if the mouse has moved and where it is
     * @param mouseEvent An object which checks if there's any action from the mouse
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) { //change cursor shape
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || exitButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

}
