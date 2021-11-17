package test;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;

/**
 * New Class - Moved design related variables and methods from GameBoard to this class
 */
public class GameDesign extends JComponent{

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);
    private static final Color BG_COLOR = Color.WHITE;

    private Font menuFont;
    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;

    private GameBoard gameBoard;

    /**
     * This represents the design of the game
     * @param gameBoard The gameboard object
     */
    public GameDesign(GameBoard gameBoard) {
        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);

        this.gameBoard=gameBoard;
        initialize();
    }

    /**
     * Sets up the properties of the game board
     */
    private void initialize(){
        gameBoard.setPreferredSize(new Dimension(gameBoard.DEF_WIDTH,gameBoard.DEF_HEIGHT)); //size of gameboard
        gameBoard.setFocusable(true); //can be focused on
        gameBoard.requestFocusInWindow(); //focus on this window after debug console closed
    }

    /**
     * Sets the background of the game to white
     * @param g2d An object which draws 2D components
     */
    public void clear(Graphics2D g2d){
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
    public void drawBrick(Brick brick,Graphics2D g2d){
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
    public void drawBall(Ball ball,Graphics2D g2d){
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
    public void drawPlayer(Player p,Graphics2D g2d){
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
    public void drawMenu(Graphics2D g2d){
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
        g2d.fillRect(0,0,gameBoard.DEF_WIDTH,gameBoard.DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * Draws the pause menu
     * Change:
     * <ul>
     *     <li>Moved code to create the button to drawPauseMenuButton</li>
     *     <li>"this" was replaced with gameBoard</li>
     *     <li>Used getters and setters for strLen</li>
     * </ul>
     * @param g2d An object which draws 2D components
     */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR); //set font colour

        if(gameBoard.getStrLen() == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            gameBoard.setStrLen(menuFont.getStringBounds(PAUSE,frc).getBounds().width); //gets width of word PAUSE with that font
        }

        int x = (gameBoard.getWidth() - gameBoard.getStrLen()) / 2; //center title
        int y = gameBoard.getHeight() / 10;

        g2d.drawString(PAUSE,x,y); //put PAUSE MENU

        y = gameBoard.getHeight() / 4; //bottom of first quarter
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
     * Change:
     * <ul>
     *     <li>"this" was changed with gameBoard</li>
     * </ul>
     * @param g2d An object which draws 2D components
     * @param y The y-coordinate of the top left of the text
     * @param rectangle The rectangle of the button
     * @param text The string in the button
     * @return The rectangle of the button
     */
    private Rectangle drawPauseMenuButton(Graphics2D g2d, int y, Rectangle rectangle, String text){
        int x = gameBoard.getWidth() / 8; //margin for 3 options
        if (rectangle==null){
            FontRenderContext frc = g2d.getFontRenderContext();
            rectangle = menuFont.getStringBounds(CONTINUE,frc).getBounds(); //get width of word CONTINUE
            rectangle.setLocation(x,y-rectangle.height); //put the button - button sits on the line of quarter
        }
        g2d.drawString(text,x,y); //put text
        return rectangle;
    }

    /**
     * New Method - Gets the CONTINUE button
     * @return The rectangle of the button
     */
    public Rectangle getContinueButtonRect() {
        return continueButtonRect;
    }

    /**
     * New Method - Gets the EXIT button
     * @return The rectangle of the button
     */
    public Rectangle getExitButtonRect() {
        return exitButtonRect;
    }

    /**
     * New Method - Gets the RESTART button
     * @return The rectangle of the button
     */
    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }
}
