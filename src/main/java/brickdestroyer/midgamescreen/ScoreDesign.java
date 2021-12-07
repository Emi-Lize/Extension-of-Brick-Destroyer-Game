package brickdestroyer.midgamescreen;

import brickdestroyer.game.GameBoard;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * New Class - This represents the design of the score screen
 */
public class ScoreDesign extends MidGameDesign {
    private static final String HIGHSCORE = "High Score";
    private static final String SCORE = "00:00:000";
    private static final String CONTINUE = "Press SPACE to continue";
    private static final String PLAYER = "Your Score";

    private GameBoard gameBoard;
    private HighScore highScore;

    private Font scoreFont;
    private Font titleFont;

    /**
     * Initialises the fonts to be used in the score screen
     * @param gameBoard The gameboard object
     * @param highScore The highscore object
     */
    public ScoreDesign(GameBoard gameBoard, HighScore highScore){
        super();
        this.gameBoard = gameBoard;
        this.highScore = highScore;
        titleFont = new Font("Monospaced", Font.PLAIN, 22);
        scoreFont = new Font("Monospaced", Font.PLAIN, 18);
    }

    /**
     * Draws the strings in the score screen
     * @param g2d An object which draws 2D components
     */
    @Override
    protected void drawMenu(Graphics2D g2d) {
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        FontRenderContext frc = g2d.getFontRenderContext();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        int x = centerText(menuFont, PLAYER, frc);
        int y = gameBoard.getHeight() / 10;
        g2d.drawString(PLAYER,x,y);

        x = centerText(menuFont, SCORE, frc);
        y *=2;
        g2d.drawString(highScore.displayPlayerScore(),x,y);

        g2d.setFont(titleFont);

        x = gameBoard.getWidth() / 8;
        y = gameBoard.getHeight() / 3;
        g2d.drawString(HIGHSCORE,x,y);
        Rectangle2D titleRect = titleFont.getStringBounds(HIGHSCORE, frc);

        g2d.setFont(scoreFont);

        Rectangle2D scoreRect = scoreFont.getStringBounds(SCORE, frc);
        y+=titleRect.getHeight();
        for (int i=0; i<5; i++) {
            String score = highScore.displayScore(i);
            g2d.drawString(score, x, y);
            y += scoreRect.getHeight();
        }

        g2d.setFont(titleFont);

        x = centerText(titleFont, CONTINUE, frc);
        y = gameBoard.getHeight() / 10 * 9;
        g2d.drawString(CONTINUE,x,y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * Finds the x-coordinate so that the string is centered in the window
     * @param font The font design
     * @param text The string of text
     * @param frc The fontrendercontext object
     * @return The x value of the top left corner of the string
     */
    private int centerText(Font font, String text, FontRenderContext frc){
        int strLen=font.getStringBounds(text,frc).getBounds().width;
        return (gameBoard.getWidth() - strLen) / 2;
    }

}
