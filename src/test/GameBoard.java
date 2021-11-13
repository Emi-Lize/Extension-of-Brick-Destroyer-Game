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
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * This represents the design of the game and the mechanics of the game
 */
public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;
    private Wall wall;
    private String message;

    private boolean showPauseMenu;

    private Font menuFont;
    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;
    private GameSystem gameSystem;

    /**
     * This represents the design of the game and how the game operates
     * Change:
     * <ul>
     *     <li>Created an object of class GameSystem</li>
     *     <li>Edited method calls for methods which have been moved from Wall to GameSystem</li>
     *     <li>Added gameSystem object as an argument for DebugConsole constructor</li>
     *     <li>Moved gameTimer to runGame</li>
     * </ul>
     * @param owner The window the game board is in
     */
    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);

        this.initialize();
        message = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2); //create levels
        gameSystem = new GameSystem(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT), new Point(300,430), wall);
        debugConsole = new DebugConsole(owner,wall,this, gameSystem); //create debug console

        //initialize the first level
        wall.nextLevel();
        runGame();
    }

    /**
     * New Method - Moved gameTimer from GameBoard constructor
     * Change:
     * <ul>
     *     <li>Added an else statement to if(gameSystem.ballEnd())</li>
     * </ul>
     */
    private void runGame(){
        gameTimer = new Timer(10,e ->{ //will keep running with 10ms interval
            gameSystem.move(); //moving player and ball
            gameSystem.findImpacts(); //check if ball hits anything
            message = String.format("Bricks: %d Balls %d",wall.getBrickCount(),gameSystem.getBallCount());
            if(gameSystem.isBallLost()){
                if(gameSystem.ballEnd()){ //if no balls left
                    reset();
                    message = "Game over";
                }
                else{
                    gameSystem.ballReset(); //bring back ball and player to initial
                }
                gameTimer.stop(); //stop running
            }
            else if(gameSystem.isDone()){ //if no more bricks
                if(gameSystem.hasLevel()){ //if not last level
                    message = "Go to Next Level";
                    gameTimer.stop(); //stop running
                    reset();
                    wall.nextLevel(); //setup bricks
                }
                else{ //last level done
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                }
            }

            repaint(); //removes any designs left on gameboard
        });
    }

    /**
     * New Method - resets the bricks, position of ball and player and the ball count
     */
    private void reset(){
        gameSystem.ballReset(); //reset ball and player
        wall.wallReset(); //reset bricks
        gameSystem.resetBallCount();
    }

    /**
     * Sets up the properties of the game board
     */
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT)); //size of gameboard
        this.setFocusable(true); //can be focused on
        this.requestFocusInWindow(); //focus on this window after debug console closed
        this.addKeyListener(this); //WindowListener in GameBoard will watch for keyboard and mouse
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Draws the background, text, ball, bricks, player and pause menu
     * Change:
     * <ul>
     *     <li>Changed wall.ball to gameSystem.ball</li>
     *     <li>Changed wall.player to gameSystem.player</li>
     * </ul>
     * @param g An object which draws the components
     */
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g; //allow it to draw objects

        clear(g2d); //set background

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225); //print blue message

        drawBall(gameSystem.ball,g2d); //draw ball

        for(Brick b : wall.bricks) //create bricks
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(gameSystem.player,g2d); //draw player

        if(showPauseMenu)
            drawMenu(g2d); //pause menu

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Sets the background of the game to white
     * @param g2d An object which draws 2D components
     */
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR); //set background to white
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp); //get back default colour
    }

    /**
     * Draws the brick
     * @param brick The brick object
     * @param g2d An object which draws 2D components
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick()); //fill brick colour

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick()); //draw brick outline

        g2d.setColor(tmp); //get back default color
    }

    /**
     * Draws the ball
     * @param ball The ball object
     * @param g2d An object which draws 2D components
     */
    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace(); //shape of ball

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s); //fill ball colour

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s); //draw ball outline

        g2d.setColor(tmp);
    }

    /**
     * Draws the player's rectangle
     * @param p The player object
     * @param g2d An object which draws 2D components
     */
    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace(); //shape of player
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s); //fill player colour

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s); //draw player outline

        g2d.setColor(tmp);
    }

    /**
     * Sets up a tint and draws the pause menu
     * @param g2d An object which draws 2D components
     */
    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * Applies a tint over the game board when the pause menu is in effect
     * @param g2d An object which draws 2D components
     */
    private void obscureGameBoard(Graphics2D g2d){
        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f); //darkened effect during pause menu
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK); //colour of effect
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * Draws the pause menu
     * Change:
     * <ul>
     *     <li>Moved code to create the button to drawPauseMenuButton</li>
     * </ul>
     * @param g2d An object which draws 2D components
     */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR); //set font colour

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width; //gets width of word PAUSE with that font
        }

        int x = (this.getWidth() - strLen) / 2; //center title
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y); //put PAUSE MENU

        y = this.getHeight() / 4; //bottom of first quarter

        continueButtonRect=drawPauseMenuButton(g2d, y, continueButtonRect, CONTINUE);

        y *= 2; //second quarter

        restartButtonRect=drawPauseMenuButton(g2d, y, restartButtonRect, RESTART);

        y *= 3.0/2; //third quarter

        exitButtonRect=drawPauseMenuButton(g2d, y, exitButtonRect, EXIT);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * New Method - Moved code to create the buttons in pause menu from drawPauseMenu
     * @param g2d An object which draws 2D components
     * @param y The y-coordinate of the top left of the text
     * @param rectangle The rectangle of the button
     * @param text The string in the button
     * @return The rectangle of the button
     */
    private Rectangle drawPauseMenuButton(Graphics2D g2d, int y, Rectangle rectangle, String text){
        int x = this.getWidth() / 8; //margin for 3 options
        if (rectangle==null){
            FontRenderContext frc = g2d.getFontRenderContext();
            rectangle = menuFont.getStringBounds(CONTINUE,frc).getBounds(); //get width of word CONTINUE
            rectangle.setLocation(x,y-rectangle.height); //put the button - button sits on the line of quarter
        }
        g2d.drawString(text,x,y); //put text
        return rectangle;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * Check if a specific key is pressed and perform a method if a specific key is pressed
     * Change:
     * <ul>
     *     <li>Changed wall.player to gameSystem.player</li>
     *     <li>Edited typo in method name from movRight to moveRight</li>
     * </ul>
     * @param keyEvent An object which checks for which key was pressed
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){ //find key pressed
            case KeyEvent.VK_A: //A pressed
                gameSystem.player.moveLeft(); //left
                break;
            case KeyEvent.VK_D: //D pressed
                gameSystem.player.moveRight(); //right
                break;
            case KeyEvent.VK_ESCAPE: //ESC pressed
                showPauseMenu = !showPauseMenu; //pause menu
                repaint();
                gameTimer.stop(); //stop game
                break;
            case KeyEvent.VK_SPACE: //space pressed
                if(!showPauseMenu) //if not pause menu
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1: //F1 key
                if(keyEvent.isAltDown() && keyEvent.isShiftDown()) //call for debug console
                    debugConsole.setVisible(true); //show debug console
            default:
                gameSystem.player.stop(); //nothing happens
        }
    }

    /**
     * Stops the player from moving when no key is pressed
     * Change:
     * <ul>
     *      <li>Changed wall.player to gameSystem.player</li>
     * </ul>
     * @param keyEvent An object which checks for which key was pressed
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameSystem.player.stop();
    } //stop moving

    /**
     * Checks which button in the pause menu was clicked
     * Change:
     * <ul>
     *      <li>Changed wall.ballReset to gameSystem.ballReset</li>
     *      <li>Changed wall.resetBallCount to gameSystem.resetBallCount</li>
     * </ul>
     * @param mouseEvent An object which checks if there was any action from the mouse
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu) //if not pause menu nothing happens
            return;
        if(continueButtonRect.contains(p)){ //if continue pressed
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){ //if restart pressed
            message = "Restarting Game...";
            reset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){ //if exit pressed
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
     * Checks if the cursor is hovering over a button and changes the cursor to a hand if so
     * @param mouseEvent An object which checks if there was any action from the mouse
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //change cursor to hand if hovering over
            else
                this.setCursor(Cursor.getDefaultCursor()); //if not normal cursor
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Checks if the game is not in focus and stops the timer if so
     */
    public void onLostFocus(){ //if not focused on game
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

}
