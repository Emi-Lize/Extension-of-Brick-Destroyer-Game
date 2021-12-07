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
package brickdestroyer.debug;

import brickdestroyer.game.GameBoard;
import brickdestroyer.game.GameSystem;
import brickdestroyer.wall.Wall;
import brickdestroyer.ball.Ball;

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
    private GameSystem gameSystem;

    /**
     * Initialises the window
     * <br>Change:
     * <ul>
     *     <li>Added a GameSystem object parameter</li>
     *     <li>Added gameSystem as an argument to DebugPanel constructor</li>
     *     <li>Wall converted to a local variable</li>
     * </ul>
     * @param owner The window the game board is in
     * @param wall The wall object
     * @param gameBoard The gameboard object
     * @param gameSystem The gamesystem object
     */
    public DebugConsole(JFrame owner,Wall wall,GameBoard gameBoard, GameSystem gameSystem){
        this.gameSystem = gameSystem;
        this.owner = owner;
        this.gameBoard = gameBoard;
        initialize();

        debugPanel = new DebugPanel(wall, gameSystem);
        this.add(debugPanel,BorderLayout.CENTER);
        this.pack();
    }

    /**
     * Setting up the properties of the window
     */
    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }

    /**
     * Setting the debug console to be in the center of the game window
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
    }

    /**
     * Calls for method paint in gameBoard
     * @param windowEvent An object which checks if there's any change to the window status
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameBoard.repaint();
    }

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
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = gameSystem.ball;
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY());
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

}
