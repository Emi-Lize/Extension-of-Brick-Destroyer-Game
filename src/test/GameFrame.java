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
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;


public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy"; //title of gameframe heading

    private GameBoard gameBoard;
    private HomeMenu homeMenu;

    private boolean gaming;

    public GameFrame(){
        super();

        gaming = false;

        this.setLayout(new BorderLayout()); //create a layout without gaps

        gameBoard = new GameBoard(this); //create a gameboard

        homeMenu = new HomeMenu(this,new Dimension(450,300)); //create home menu

        this.add(homeMenu,BorderLayout.CENTER); //add home menu to center of frame

        this.setUndecorated(true); //no user Windows game frame with the heading and three top right buttons


    }

    public void initialize(){
        this.setTitle(DEF_TITLE); //set game frame heading title
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //close game when closes
        this.pack(); //allow window to be resized - components get automatically adjusted
        this.autoLocate(); //center the game
        this.setVisible(true); //show window
    }

    public void enableGameBoard(){
        this.dispose(); //remove the window
        this.remove(homeMenu); //remove the home menu components
        this.add(gameBoard,BorderLayout.CENTER); //create the gameboard window in the center
        this.setUndecorated(false); //have windows frame
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this); //watch if window is being focused on

    }

    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); //get size of screen
        int x = (size.width - this.getWidth()) / 2; //center the window
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }


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

    @Override
    public void windowLostFocus(WindowEvent windowEvent) { //if window not in focus
        if(gaming)
            gameBoard.onLostFocus();

    }
}
