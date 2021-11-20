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
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This represents the window which displays the debug panel
 */
public class DebugConsole extends JDialog implements WindowListener{
    private static final String TITLE = "Debug Console";

    private JFrame owner;
    private DebugPanel debugPanel;
    private GameBoard gameBoard;
    private Wall wall;
    private GameSystem gameSystem;

    /**
     * This represents the debug console
     * <br>Change:
     * <ul>
     *     <li>Added a GameSystem object parameter</li>
     *     <li>Added gameSystem as an argument to DebugPanel constructor</li>
     * </ul>
     * @param owner The window the game board is in
     * @param wall The wall object
     * @param gameBoard The gameboard object
     * @param gameSystem The gamesystem object
     */
    public DebugConsole(JFrame owner,Wall wall,GameBoard gameBoard, GameSystem gameSystem){
        this.wall = wall;
        this.gameSystem = gameSystem;
        this.owner = owner; //gameframe based on user computer windows
        this.gameBoard = gameBoard;
        initialize();

        debugPanel = new DebugPanel(wall, gameSystem); //create a debug panel
        this.add(debugPanel,BorderLayout.CENTER); //adds the debug panel to the center of the console
        this.pack(); //can resize debug console and components automatically resized
    }

    /**
     * Setting up the properties of the debug console
     */
    private void initialize(){
        this.setModal(true); //can't touch game until debug console closed
        this.setTitle(TITLE); //set title of debug console
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //close console but application still running in background
        this.setLayout(new BorderLayout()); //no gap between components
        this.addWindowListener(this); //WindowListener in debug console will watch for any event
        this.setFocusable(true); //allow debug console to be focused on
    }

    /**
     * Setting the debug console to be in the center of the game window
     */
    private void setLocation(){ //set location of debug console to center
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    /**
     * Reapplying all the graphics to the game board when the game closes
     * @param windowEvent An object which checks if there's any change to the window status
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) { //close the game
        gameBoard.repaint();
    } //reset the game when game is closed to default - remove changes done by debug console

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    /**
     * Sets the location of the debug console and position of slider when the debug console is activated
     * <br>Change:
     * <ul>
     *     <li>Changed wall.ball to gameSystem.ball</li>
     * </ul>
     * @param windowEvent An object which checks if there's any change to the window status
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) { //open debug console
        setLocation(); //set location of debug console
        Ball b = gameSystem.ball;
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY()); //setting position of slider based on current speed of ball
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

}
