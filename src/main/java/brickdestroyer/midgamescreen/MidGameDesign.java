package brickdestroyer.midgamescreen;

import brickdestroyer.game.GameBoard;

import java.awt.*;

/**
 * New Class - The design of the mid game screens such as the pause menu and score screen
 */
abstract public class MidGameDesign {
    protected static final Color MENU_COLOR = new Color(0,255,0);
    protected static final int TEXT_SIZE = 30;

    protected Font menuFont;

    /**
     * Initialises the font
     */
    public MidGameDesign(){
        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
    }

    /**
     * Sets up a tint and draws the midgame screen design
     * @param g2d An object which draws 2D components
     */
    public void draw(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawMenu(g2d);
    }

    /**
     * Applies a tint over the game board when the midgame screen appears
     * @param g2d An object which draws 2D components
     */
    private void obscureGameBoard(Graphics2D g2d){
        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f); //darkened effect during pause menu
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK); //colour of effect
        g2d.fillRect(0,0, GameBoard.DEF_WIDTH,GameBoard.DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * Draws the mid game components
     * @param g2d An object which draws 2D components
     */
    abstract protected void drawMenu(Graphics2D g2d);

}
