package test.game.design;

import test.game.system.HomeMenu;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * New Class - Moved code from HomeMenu related to the design of the home menu
 * <br>
 * <ul>
 *     <li>MenuDesign is now a child of Design</li>
 * </ul>
 */
public class MenuDesign extends Design {
    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";

    private Rectangle startButton;
    private Rectangle exitButton;

    private Font greetingsFont;
    private Font creditsFont;

    private HomeMenu homeMenu;

    /**
     * This represents the design of the home menu
     * @param area The dimensions of the homeMenu
     * @param homeMenu The home menu object
     */
    public MenuDesign(Dimension area, HomeMenu homeMenu){
        super(area);
        this.homeMenu = homeMenu;
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);

        fontDesign();
    }

    /**
     * New Method - Sets the font of each string
     */
    private void fontDesign(){
        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
    }

    /**
     * Draws the text on the home menu at its correct position
     * <br>Change:
     * <ul>
     *     <li>Moved code to draw the text to setText</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void drawText(Graphics2D g2d){
        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc); //get boundary of each text
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sY = (int)(menuFace.getHeight() / 4);
        setText(sY, greetingsRect, greetingsFont, GREETINGS, g2d);

        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings
        setText(sY, gameTitleRect, gameTitleFont, GAME_TITLE, g2d);

        sY += (int) creditsRect.getHeight() * 1.1;
        setText(sY, creditsRect, creditsFont, CREDITS, g2d);
    }

    /**
     * New Method - Moved code to draw the text from method drawText
     * @param sY The y coordinate of the top left of the text
     * @param rectangle The rectangle around the text
     * @param font The font of the text
     * @param text The text string
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void setText(int sY, Rectangle2D rectangle, Font font, String text, Graphics2D g2d){
        int sX = (int)(menuFace.getWidth() - rectangle.getWidth()) / 2;

        g2d.setFont(font);
        g2d.drawString(text,sX,sY);
    }

    /**
     * Draws the Start and Exit button on the home menu
     * <br>Change:
     * <ul>
     *     <li>Moved code to draw the button and text to setButton</li>
     *     <li>Removed code to declare x and y again</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void drawButton(Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc); //get boundary of text
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2; //center button
        int y =(int) ((menuFace.height - startButton.height) * 0.8);

        startButton.setLocation(x,y); //position of rectangle

        setButton(txtRect, g2d, homeMenu.isStartClicked(), startButton, START_TEXT);

        y *= 1.2;

        exitButton.setLocation(x,y); //location of exit button

        setButton(mTxtRect, g2d, homeMenu.isExitClicked(), exitButton, EXIT_TEXT);
    }

    /**
     * New Method - Moved code to draw the button and text from drawButton
     * @param rectangle The rectangle around the text
     * @param g2d An object which draws the 2D components
     * @param clicked A boolean which represents if the button has been clicked
     * @param button A rectangle representing the button
     * @param text The string in the button
     */
    @Override
    protected void setButton(Rectangle2D rectangle, Graphics2D g2d, Boolean clicked, Rectangle button, String text){
        int x = (int)(button.getWidth() - rectangle.getWidth()) / 2; //center text in rectangle
        int y = (int)(button.getHeight() - rectangle.getHeight()) / 2;

        x += button.x; //position of text
        y += button.y + (startButton.height * 0.9);

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
     * New Method - Gets the Start Button
     * @return The rectangle of the start button
     */
    public Rectangle getStartButton() {
        return startButton;
    }

    /**
     * New Method - Gets the Exit Button
     * @return The rectangle of the exit button
     */
    public Rectangle getExitButton() {
        return exitButton;
    }
}
