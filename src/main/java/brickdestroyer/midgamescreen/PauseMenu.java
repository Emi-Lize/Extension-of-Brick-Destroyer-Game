package brickdestroyer.midgamescreen;

import brickdestroyer.game.GameBoard;

import java.awt.*;
import java.awt.font.FontRenderContext;

/**
 * New Class - Moved code related to drawing the pause menu from GameDesign
 */
public class PauseMenu extends MidGameDesign {
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";

    private int strLen;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;

    private GameBoard gameBoard;

    /**
     * Initialises the variables to draw the pause menu
     * @param gameBoard The gameboard object
     */
    public PauseMenu(GameBoard gameBoard){
        super();
        this.gameBoard=gameBoard;
        strLen=0;
    }

    /**
     * Draws the pause menu
     * <br>Change:
     * <ul>
     *     <li>Moved code to create the button to drawPauseMenuButton</li>
     *     <li>"this" was replaced with gameBoard</li>
     *     <li>Used getters and setters for strLen</li>
     *     <li>Renamed method from drawPauseMenu to drawMenu</li>
     * </ul>
     * @param g2d An object which draws 2D components
     */
    @Override
    protected void drawMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen=menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (gameBoard.getWidth() - strLen) / 2;
        int y = gameBoard.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = gameBoard.getWidth() / 8;
        y = gameBoard.getHeight() / 4;
        continueButtonRect=drawPauseMenuButton(g2d, y, continueButtonRect, x);
        g2d.drawString(CONTINUE,x,y);

        y *= 2;
        restartButtonRect=drawPauseMenuButton(g2d, y, restartButtonRect, x);
        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;
        exitButtonRect=drawPauseMenuButton(g2d, y, exitButtonRect, x);
        g2d.drawString(EXIT,x,y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * New Method - Moved code to create the buttons in pause menu from drawMenu
     * <br>Change:
     * <ul>
     *     <li>"this" was changed with gameBoard</li>
     * </ul>
     * @param g2d An object which draws 2D components
     * @param y The y-coordinate of the top left of the text
     * @param rectangle The rectangle of the button
     * @param x The x-coordinate of the top left of the text
     * @return The rectangle of the button
     */
    private Rectangle drawPauseMenuButton(Graphics2D g2d, int y, Rectangle rectangle, int x){
        if (rectangle==null){
            FontRenderContext frc = g2d.getFontRenderContext();
            rectangle = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            rectangle.setLocation(x,y-rectangle.height);
        }
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
