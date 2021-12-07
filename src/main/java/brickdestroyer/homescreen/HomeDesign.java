package brickdestroyer.homescreen;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * New Class - Moved code from HomeMenu related to the design of the home menu
 * <br>Change:
 * <ul>
 *     <li>HomeDesign is now a child of MenuDesign</li>
 *     <li>Added the info button</li>
 * </ul>
 */
public class HomeDesign extends MenuDesign {
    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";
    private static final String INFO_TEXT = "Info";

    private Rectangle startButton;
    private Rectangle exitButton;
    private Rectangle infoButton;

    private Font greetingsFont;
    private Font creditsFont;
    protected Font gameTitleFont;

    private HomeMenu homeMenu;

    /**
     * Calls the constructor of MenuDesign and creates the button objects
     * @param area The dimensions of the homeMenu
     * @param homeMenu The home menu object
     */
    public HomeDesign(Dimension area, HomeMenu homeMenu){
        super(area);

        this.homeMenu = homeMenu;

        startButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);

        fontDesign();
    }

    /**
     * New Method - Sets the font of each string
     */
    private void fontDesign(){
        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
    }

    /**
     * Sets the text at the correct position
     * <br>Change:
     * <ul>
     *     <li>Moved code to draw the text to drawText</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void setText(Graphics2D g2d){
        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sY = (int)(menuFace.getHeight() / 5);
        drawText(sY, greetingsRect, greetingsFont, GREETINGS, g2d);

        sY += (int) gameTitleRect.getHeight() * 1.1;
        drawText(sY, gameTitleRect, gameTitleFont, GAME_TITLE, g2d);

        sY += (int) creditsRect.getHeight() * 1.1;
        drawText(sY, creditsRect, creditsFont, CREDITS, g2d);
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
    protected void drawText(int sY, Rectangle2D rectangle, Font font, String text, Graphics2D g2d){
        int sX = (int)(menuFace.getWidth() - rectangle.getWidth()) / 2;

        g2d.setFont(font);
        g2d.drawString(text,sX,sY);
    }

    /**
     * Sets the buttons at the correct position
     * <br>Change:
     * <ul>
     *     <li>Moved code to draw the button and text to drawButton</li>
     *     <li>Removed code to declare x and y again</li>
     *     <li>Created the info button</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    @Override
    protected void setButton(Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INFO_TEXT, frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.6);

        startButton.setLocation(x,y);
        drawButton(txtRect, g2d, homeMenu.isStartClicked(), startButton, START_TEXT);

        y *= 1.2;
        infoButton.setLocation(x,y);
        drawButton(iTxtRect, g2d, homeMenu.isInfoClicked(), infoButton, INFO_TEXT);

        y*=1.2;
        exitButton.setLocation(x,y);
        drawButton(mTxtRect, g2d, homeMenu.isExitClicked(), exitButton, EXIT_TEXT);
    }

    /**
     * Draws the shape of the button and the text in the button
     * @param rectangle The rectangle around the text
     * @param g2d An object which draws the 2D components
     * @param clicked A boolean which represents if the button has been clicked
     * @param button A rectangle representing the button
     * @param text The string in the button
     */
    @Override
    protected void drawButton(Rectangle2D rectangle, Graphics2D g2d, Boolean clicked, Rectangle button, String text){
        int x = (int)(button.getWidth() - rectangle.getWidth()) / 2;
        int y = (int)(button.getHeight() - rectangle.getHeight()) / 2;

        x += button.x;
        y += button.y + (startButton.height * 0.9);

        if(clicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(button);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(text,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(button);
            g2d.drawString(text,x,y);
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

    /**
     * New Method - Gets the Info Button
     * @return The rectangle of the info button
     */
    public Rectangle getInfoButton(){
        return infoButton;
    }

}
