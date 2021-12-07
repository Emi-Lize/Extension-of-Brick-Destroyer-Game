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

import brickdestroyer.midgamescreen.HighScore;
import brickdestroyer.midgamescreen.PauseMenu;
import brickdestroyer.wall.Wall;
import brickdestroyer.debug.DebugConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This represents the mechanics of the game
 * <br>Change:
 * <ul>
 *     <li>Moved design variables and methods to GameDesign and PauseMenu</li>
 *     <li>Added variable time to save the time at that instance</li>
 *     <li>Added variable score to save the total time taken for the level</li>
 *     <li>Added variable showScore to determine if the score screen is being shown</li>
 *     <li>Added variable gameOver to determine if the game has been completed</li>
 * </ul>
 */
public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 450;

    private Timer gameTimer;
    private Wall wall;
    private String message;

    private boolean showPauseMenu;
    private long time;
    private long score;
    private boolean showScore;
    private boolean gameOver;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;

    private DebugConsole debugConsole;
    private GameSystem gameSystem;
    private GameDesign gameDesign;
    private HighScore highScore;

    /**
     * Initialises the variables and creates objects of various classes
     * <br>Change:
     * <ul>
     *     <li>Created an object of class GameSystem</li>
     *     <li>Edited method calls for methods which have been moved from Wall to GameSystem</li>
     *     <li>Added gameSystem object as an argument for DebugConsole constructor</li>
     *     <li>Moved gameTimer to method runGame</li>
     *     <li>Created an object of class GameDesign</li>
     *     <li>Type casted "6/2" argument in wall to double</li>
     *     <li>Initialised score to 0</li>
     *     <li>Initialised showScore to false</li>
     *     <li>Created an object of class HighScore</li>
     *     <li>Initialised gameOver to false</li>
     * </ul>
     * @param owner The window the game board is in
     */
    public GameBoard(JFrame owner){
        super();

        showPauseMenu = false;
        showScore = false;
        gameOver = false;
        score=0;
        message = "";

        initialize();

        highScore=new HighScore();
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,(double)6/2);
        gameSystem = new GameSystem(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT), new Point(300,430), wall);
        gameDesign = new GameDesign(this, gameSystem, wall, highScore);
        debugConsole = new DebugConsole(owner,wall,this, gameSystem);

        wall.nextLevel();
        runGame();
    }

    /**
     * New Method - Moved gameTimer from GameBoard constructor
     * <br>Change:
     * <ul>
     *     <li>Added an else statement to if(gameSystem.ballEnd())</li>
     *     <li>Added code to calculate the time taken using time and score</li>
     *     <li>Called method checkScore to see if the score has beat the high score</li>
     *     <li>Called method writeScore to edit the score file</li>
     *     <li>Set gameOver to true when there are no more levels</li>
     * </ul>
     */
    private void runGame(){
        gameTimer = new Timer(10,e ->{
            gameSystem.move();
            gameSystem.findImpacts();
            message = String.format("Bricks: %d Balls %d",wall.getBrickCount(),gameSystem.getBallCount());
            score+=System.nanoTime()-time;
            time=System.nanoTime();
            if(gameSystem.isBallLost()){
                if(gameSystem.ballEnd()){
                    score=0;
                    reset();
                    message = "Game over";
                }
                else{
                    score+=System.nanoTime()-time;
                    gameSystem.ballReset();
                }
                gameTimer.stop();
            }
            else if(gameSystem.isDone()){
                score+=System.nanoTime()-time;
                highScore.checkScore(score,wall.getLevel());
                showScore=true;
                highScore.writeScore();
                if(gameSystem.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    reset();
                    repaint();
                    wall.nextLevel();
                    score=0;
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                    gameOver=true;
                }
            }
            repaint();
        });
    }

    /**
     * New Method - resets the bricks, position of ball and player and the ball count
     */
    private void reset(){
        gameSystem.ballReset();
        wall.wallReset();
        gameSystem.resetBallCount();
    }

    /**
     * Sets up the window properties of the game board
     * <br>Change:
     * <ul>
     *     <li>Moved design code to GameDesign</li>
     * </ul>
     */
    private void initialize(){
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Draws the background, text, ball, bricks, player, pause menu and score screen
     * <br>Change:
     * <ul>
     *     <li>Changed wall.ball to gameSystem.ball</li>
     *     <li>Changed wall.player to gameSystem.player</li>
     *     <li>Moved method calls to method draw in GameDesign</li>
     *     <li>Call method draw instead of each individual method</li>
     *     <li>Used getters from GameDesign to get the pause menu buttons</li>
     * </ul>
     * @param g An object which draws the components
     */
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        PauseMenu pauseMenu=gameDesign.draw(g2d, message, showPauseMenu, showScore);

        continueButtonRect=pauseMenu.getContinueButtonRect();
        exitButtonRect=pauseMenu.getExitButtonRect();
        restartButtonRect= pauseMenu.getRestartButtonRect();

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * Check if a specific key is pressed and performs the specified code if a specific key is pressed
     * <br>Change:
     * <ul>
     *     <li>Changed wall.player to gameSystem.player</li>
     *     <li>Edited typo in method name from movRight to moveRight</li>
     *     <li>Added code to calculate the time taken using time and score</li>
     *     <li>Added if statement to check if score screen is not displayed and not last level</li>
     *     <li>Added if statement to check if pause menu not shown and game is running</li>
     * </ul>
     * @param keyEvent An object which checks for which key was pressed
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                gameSystem.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                gameSystem.player.moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                if (!showPauseMenu && gameTimer.isRunning()){
                    score+=System.nanoTime()-time;
                }
                if (!showScore) {
                    showPauseMenu = !showPauseMenu;
                    repaint();
                    gameTimer.stop();
                }
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(!showScore && !gameOver) {
                        if (gameTimer.isRunning()) {
                            score += System.nanoTime() - time;
                            gameTimer.stop();
                        } else {
                            time = System.nanoTime();
                            gameTimer.start();
                        }
                    }
                    else{
                        showScore=false;
                        repaint();
                    }
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                gameSystem.player.stop();
        }
    }

    /**
     * Stops the player from moving when the key is released
     * <br>Change:
     * <ul>
     *      <li>Changed wall.player to gameSystem.player</li>
     * </ul>
     * @param keyEvent An object which checks for which key was pressed
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameSystem.player.stop();
    }

    /**
     * Checks which button in the pause menu was clicked
     * <br>Change:
     * <ul>
     *      <li>Changed wall.ballReset to gameSystem.ballReset</li>
     *      <li>Changed wall.resetBallCount to gameSystem.resetBallCount</li>
     *      <li>Added code to calculate the time taken using time and score</li>
     *      <li>Set score to 0 when restart is clicked</li>
     * </ul>
     * @param mouseEvent An object which checks if there was any action from the mouse
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            score=0;
            message = "Restarting Game...";
            reset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
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
     * Checks if the cursor is hovering over a button and changes the cursor to a hand icon if so
     * @param mouseEvent An object which checks if there was any action from the mouse
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Checks if the game is not in focus and stops the timer if so
     * <br>Change:
     * <ul>
     *     <li>Added code to calculate the time taken using time and score</li>
     *     <li>Added an if statement to save the time elapsed</li>
     * </ul>
     */
    public void onLostFocus(){
        if (gameTimer.isRunning()) {
            score += System.nanoTime() - time;
        }
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

}
