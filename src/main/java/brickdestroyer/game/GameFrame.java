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
package brickdestroyer.game;

import brickdestroyer.homescreen.BackgroundImage;
import brickdestroyer.homescreen.HomeMenu;
import brickdestroyer.homescreen.Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * This represents the window of the whole game
 */
public class GameFrame extends JFrame implements WindowFocusListener {
    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    private HomeMenu homeMenu;
    private Info info;

    private boolean gaming;

    /**
     * Creates objects of various classes and calls method enableHomeMenu
     * <br>Change:
     * <ul>
     *     <li>Created an object of class info</li>
     *     <li>Calls the constructor of the class backgroundImage to place the image at the background</li>
     *     <li>Calls the home menu through the method enableHomeMenu method</li>
     * </ul>
     */
    public GameFrame(){
        super();

        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);
        homeMenu = new HomeMenu(this,new Dimension(450,300));
        info = new Info(this, new Dimension(450, 300));
        new BackgroundImage(new Dimension(450,300));

        this.setUndecorated(true);
        enableHomeMenu();
    }

    /**
     * Initialises the window property
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.centerBoard();
        this.setVisible(true);
    }

    /**
     * Removes the home menu components and sets the game board components
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameBoard,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        this.addWindowFocusListener(this);
    }

    /**
     * New Method - Removes the components of home menu and adds info components
     */
    public void enableInfo(){
        this.remove(homeMenu);
        this.add(info,BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        info.initialise();
    }

    /**
     * New Method - Removes the components of info and adds home menu components
     */
    public void enableHomeMenu(){
        this.remove(info);
        this.add(homeMenu,BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        homeMenu.initialise();
    }

    /**
     * Sets the home menu and info screen to the center of the screen
     * <br>Change:
     * <ul>
     *     <li>Changed method name from autoLocate to centerBoard</li>
     * </ul>
     */
    private void centerBoard(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    /**
     * Checks if the window is focused
     * @param windowEvent An object which checks if there's any change to the window status
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        gaming = true;
    }

    /**
     * Checks if the window has lost focus then calls a method
     * @param windowEvent An object which checks if there's any change to the window status
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.onLostFocus();
    }

}
