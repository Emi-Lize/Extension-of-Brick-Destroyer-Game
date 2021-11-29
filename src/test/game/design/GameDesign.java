package test.game.design;

import test.game.system.GameSystem;
import test.game.system.HighScore;
import test.player.Player;
import test.wall.Wall;
import test.ball.Ball;
import test.brick.Brick;
import test.game.system.GameBoard;

import javax.swing.*;
import java.awt.*;

/**
 * New Class - Moved design related variables and methods from GameBoard to this class
 */
public class GameDesign extends JComponent{
    private static final Color BG_COLOR = Color.WHITE;

    private GameBoard gameBoard;
    private GameSystem gameSystem;
    private Wall wall;
    private PauseMenu pauseMenu;
    private ScoreDesign scoreDesign;

    /**
     * This represents the design of the game
     * @param gameBoard The gameboard object
     * @param gameSystem The gamesystem object
     * @param wall The wall object
     * @param highScore The highscore object
     */
    public GameDesign(GameBoard gameBoard, GameSystem gameSystem, Wall wall, HighScore highScore) {
        this.gameBoard=gameBoard;
        this.gameSystem=gameSystem;
        this.wall=wall;
        initialize();
        pauseMenu = new PauseMenu(gameBoard);
        scoreDesign = new ScoreDesign(gameBoard, highScore);
    }

    /**
     * Sets up the properties of the game board
     */
    private void initialize(){
        gameBoard.setPreferredSize(new Dimension(GameBoard.DEF_WIDTH,GameBoard.DEF_HEIGHT)); //size of gameboard
        gameBoard.setFocusable(true); //can be focused on
        gameBoard.requestFocusInWindow(); //focus on this window after debug console closed
    }

    /**
     * New Method - Calls methods to draw the components of the gameboard
     * <br>Change:
     * <ul>
     *     <li>Method clear changed to colourBackground</li>
     *     <li>Used getter to get the layout of the bricks from Wall</li>
     *     <li>Methods to draw the pause menu moved to PauseMenu class</li>
     *     <li>Added method call to draw the score screen</li>
     * </ul>
     * @param g2d An object which draws 2D components
     * @param message The string to be displayed
     * @param showPauseMenu A boolean representing if the pause menu is to be shown
     * @param showScore A boolean representing if the score screen is to be shown
     * @return The pause menu object
     */
    public PauseMenu draw(Graphics2D g2d, String message, boolean showPauseMenu, boolean showScore){
        colourBackground(g2d); //set background

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225); //print blue message

        drawBall(gameSystem.ball,g2d); //draw ball

        for(Brick b : wall.getBricks()) //create bricks
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(gameSystem.player,g2d); //draw player

        if(showPauseMenu)
            pauseMenu.draw(g2d); //pause menu

        if(showScore){
            scoreDesign.draw(g2d);
        }

        return pauseMenu;
    }
    /**
     * Sets the background of the game to white
     * <br>Change:
     * <ul>
     *     <li>Changed method name to colourBackground</li>
     * </ul>
     * @param g2d An object which draws 2D components
     */
    private void colourBackground(Graphics2D g2d){
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
    private void drawBall(Ball ball, Graphics2D g2d){
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
    private void drawPlayer(Player p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace(); //shape of player
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s); //fill player colour

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s); //draw player outline

        g2d.setColor(tmp);
    }


}
