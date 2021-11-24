package test.game.design;

import test.game.system.Info;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * New Class - Designs the info screen
 */
public class InfoDesign extends Design{
    private static final String INFO_TITLE = "Game Info";
    private static final String LEVELS = "There are 4 levels in this game.";
    private static final String FINISH = "You have to destroy all the bricks to pass the level";
    private static final String CONTROLS = "Controls";
    private static final String PRESS_A = "A - Move Left";
    private static final String PRESS_D = "D - Move Right";
    private static final String PRESS_SPACE = "SPACE - Launch the ball";
    private static final String PRESS_ESC = "ESC - Pause Menu";
    private static final String BACK_TEXT = "Back";

    private Rectangle backButton;

    private Font titleFont;
    private Font infoFont;

    private Info info;

    /**
     * Initialises the InfoDesign class
     * @param area The dimensions of the window
     * @param info The info object
     */
    public InfoDesign(Dimension area, Info info){
        super(area);
        this.info = info;
        backButton = new Rectangle(btnDim);

        fontDesign();
    }

    /**
     * New Method - Sets the font of each string
     */
    private void fontDesign(){
        infoFont = new Font("Noto Mono",Font.PLAIN,14);
        titleFont = new Font("Noto Mono", Font.BOLD, 24);
    }

    /**
     * Sets the text at correct position
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void setText(Graphics2D g2d) {
        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D infoTitleRect = titleFont.getStringBounds(INFO_TITLE,frc);
        Rectangle2D levelsRect = infoFont.getStringBounds(LEVELS,frc);
        Rectangle2D finishRect = infoFont.getStringBounds(FINISH,frc);
        Rectangle2D controlsRect = titleFont.getStringBounds(CONTROLS,frc);
        Rectangle2D pressARect = infoFont.getStringBounds(PRESS_A,frc);
        Rectangle2D pressDRect = infoFont.getStringBounds(PRESS_D,frc);
        Rectangle2D pressSpaceRect = infoFont.getStringBounds(PRESS_SPACE,frc);
        Rectangle2D pressEscRect = infoFont.getStringBounds(PRESS_ESC,frc);

        int sY = (int)(menuFace.getHeight() / 8);
        drawText(sY, infoTitleRect, titleFont, INFO_TITLE, g2d);

        sY += infoTitleRect.getHeight();
        drawText(sY, levelsRect, infoFont, LEVELS, g2d);

        sY += finishRect.getHeight();
        drawText(sY, finishRect, infoFont, FINISH, g2d);

        sY = (int)(menuFace.getHeight() / 4)*2;
        drawText(sY, controlsRect, titleFont, CONTROLS, g2d);

        sY += controlsRect.getHeight();
        drawText(sY, pressARect, infoFont, PRESS_A, g2d);

        sY += pressARect.getHeight();
        drawText(sY, pressDRect, infoFont, PRESS_D, g2d);

        sY += pressDRect.getHeight();
        drawText(sY, pressSpaceRect, infoFont, PRESS_SPACE, g2d);

        sY += pressSpaceRect.getHeight();
        drawText(sY, pressEscRect, infoFont, PRESS_ESC, g2d);

    }

    /**
     * Draws the string of text
     * @param sY The y coordinate of the top left of the text
     * @param rectangle The rectangle around the text
     * @param font The font of the text
     * @param text The text string
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void drawText(int sY, Rectangle2D rectangle, Font font, String text, Graphics2D g2d) {
        int sX = (int)(menuFace.getWidth() / 10);

        g2d.setFont(font);
        g2d.drawString(text,sX,sY);
    }

    /**
     * Sets the buttons at the correct position
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void setButton(Graphics2D g2d) {
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(BACK_TEXT,frc); //get boundary of text

        g2d.setFont(buttonFont);

        int x = (menuFace.width - backButton.width) / 2; //center button
        int y =(int) ((menuFace.height - backButton.height) * 0.9);

        backButton.setLocation(x,y); //position of rectangle

        drawButton(txtRect, g2d, info.isBackClicked(), backButton, BACK_TEXT);
    }

    /**
     * Draws the shape of the button and text in button
     * @param rectangle The rectangle around the text
     * @param g2d An object which draws the 2D components
     * @param clicked A boolean which represents if the button has been clicked
     * @param button A rectangle representing the button
     * @param text The string in the button
     */
    @Override
    protected void drawButton(Rectangle2D rectangle, Graphics2D g2d, Boolean clicked, Rectangle button, String text) {
        int x = (int)(button.getWidth() - rectangle.getWidth()) / 2; //center text in rectangle
        int y = (int)(button.getHeight() - rectangle.getHeight()) / 2;

        x += button.x; //position of text
        y += button.y + (button.height * 0.9);

        if(clicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(button); //draw button in different colour
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(text,x,y); //draw text in different colour
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(button); //draw button
            g2d.drawString(text,x,y); //draw text following position of text
        }
    }

    /**
     * Gets the Back button
     * @return The rectangle of the back button
     */
    public Rectangle getBackButton() {
        return backButton;
    }
}
