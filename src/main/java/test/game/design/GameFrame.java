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
package test.game.design;

import test.game.system.HomeMenu;
import test.game.system.GameBoard;
import test.game.system.Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * The window the game board is in
 */
public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy"; //title of gameframe heading

    private GameBoard gameBoard;
    private HomeMenu homeMenu;
    private Info info;

    private boolean gaming;

    /**
     * This represents the window the game board is in and calls for the game board and home menu to be created
     * <br>Change:
     * <ul>
     *     <li>Created an object of class info</li>
     * </ul>
     */
    public GameFrame(){
        super();

        gaming = false;

        this.setLayout(new BorderLayout()); //create a layout without gaps

        gameBoard = new GameBoard(this); //create a gameboard
        homeMenu = new HomeMenu(this,new Dimension(450,300)); //create home menu
        info = new Info(this, new Dimension(450, 300));

        this.add(homeMenu,BorderLayout.CENTER); //add home menu to center of frame
        this.setUndecorated(true); //no user Windows game frame with the heading and three top right buttons
    }

    /**
     * Initialises the window property
     */
    public void initialize(){
        this.setTitle(DEF_TITLE); //set game frame heading title
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //close game when closes
        this.pack(); //allow window to be resized - components get automatically adjusted
        this.centerBoard(); //center the game
        this.setVisible(true); //show window
    }

    /**
     * Removes the home menu window and starts the game board
     */
    public void enableGameBoard(){
        this.dispose(); //remove the window
        this.remove(homeMenu); //remove the home menu components
        this.add(gameBoard,BorderLayout.CENTER); //create the gameboard window in the center
        this.setUndecorated(false); //have windows frame
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this); //watch if window is being focused on
    }

    /**
     * New Method - Removes the components of home menu and adds info components
     */
    public void enableInfo(){
        this.remove(homeMenu); //remove the home menu components
        this.add(info,BorderLayout.CENTER); //create the gameboard window in the center
        this.revalidate();
        this.repaint();
        info.initialise();
    }

    /**
     * New Method - Removes the components of info and adds home menu components
     */
    public void enableHomeMenu(){
        this.remove(info); //remove the home menu components
        this.add(homeMenu,BorderLayout.CENTER); //create the gameboard window in the center
        this.revalidate();
        this.repaint();
        homeMenu.initialise();
    }

    /**
     * Sets the home menu to the center of the screen
     * <br>Change:
     * <ul>
     *     <li>Changed method name from autoLocate to centerBoard</li>
     * </ul>
     */
    private void centerBoard(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); //get size of screen
        int x = (size.width - this.getWidth()) / 2; //center the window
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }


    /**
     * Checks if the window is focused
     * @param windowEvent An object which checks if there's any change to the window status
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true; //if in focus then user is playing
    }

    /**
     * Checks if the window isn't focused then calls a method
     * @param windowEvent An object which checks if there's any change to the window status
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) { //if window not in focus
        if(gaming)
            gameBoard.onLostFocus();

    }
}
