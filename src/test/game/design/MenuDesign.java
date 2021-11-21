package test.game.design;

import test.game.system.HomeMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * New Class - Moved code from HomeMenu related to the design of the home menu
 */
public class MenuDesign extends JComponent {
    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";

    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(16, 52, 166);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle exitButton;

    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private HomeMenu homeMenu;

    /**
     * This represents the design of the home menu
     * @param homeMenu The homeMenu object
     * @param area The dimensions of the homeMenu
     */
    public MenuDesign(HomeMenu homeMenu, Dimension area){
        this.homeMenu=homeMenu;
        menuFace = new Rectangle(new Point(0,0),area); //create home menu
        Dimension btnDim = new Dimension(area.width / 3, area.height / 12); //size of start and exit
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);

        setBorderDesign();
        setFont();
    }

    /**
     * New Method - Sets the font of each string
     */
    private void setFont(){
        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);
    }

    /**
     * New Method - Sets the design of the border
     */
    private void setBorderDesign(){
        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0); //red part of menu border
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND); //yellow part of menu border
    }

    /**
     * Draws the components in the home menu
     * <br>Change:
     * <ul>
     *     <li>Added two parameters to the method</li>
     *     <li>drawButton takes in two more arguments</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    public void drawMenu(Graphics2D g2d, boolean startClicked, boolean exitClicked){
        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y); //reference point is (0,0)

        //methods calls
        drawText(g2d);
        drawButton(g2d, startClicked, exitClicked);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * Draws the border of the home menu frame and sets the background colour
     * <br>Change:
     * <ul>
     *     <li>Moved code to draw the border to drawBorder</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace); //fill background colour

        Stroke tmp = g2d.getStroke();

        drawBorder(borderStoke_noDashes, DASH_BORDER_COLOR, g2d); //draw yellow part of border
        drawBorder(borderStoke, BORDER_COLOR, g2d); //draw red part of border

        g2d.setStroke(tmp);
        g2d.setColor(prev);
    }

    /**
     * New Method - Moved the code to draw the border from drawContainer
     * @param stroke The border outline
     * @param colour The colour of the border
     * @param g2d An object which draws the 2D components
     */
    private void drawBorder(BasicStroke stroke, Color colour, Graphics2D g2d){
        g2d.setStroke(stroke);
        g2d.setColor(colour);
        g2d.draw(menuFace);
    }

    /**
     * Draws the text on the home menu at its correct position
     * <br>Change:
     * <ul>
     *     <li>Moved code to draw the text to setText</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    private void drawText(Graphics2D g2d){
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
    private void setText(int sY, Rectangle2D rectangle, Font font, String text, Graphics2D g2d){
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
     *     <li>drawButton takes 2 more parameters</li>
     * </ul>
     * @param g2d An object which draws the 2D components
     */
    private void drawButton(Graphics2D g2d, boolean startClicked, boolean exitClicked){
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc); //get boundary of text
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2; //center button
        int y =(int) ((menuFace.height - startButton.height) * 0.8);

        startButton.setLocation(x,y); //position of rectangle

        setButton(txtRect, g2d, startClicked, startButton, START_TEXT);

        y *= 1.2;

        exitButton.setLocation(x,y); //location of exit button

        setButton(mTxtRect, g2d, exitClicked, exitButton, EXIT_TEXT);
    }

    /**
     * New Method - Moved code to draw the button and text from drawButton
     * @param rectangle The rectangle around the text
     * @param g2d An object which draws the 2D components
     * @param clicked A boolean which represents if the button has been clicked
     * @param button A rectangle representing the button
     * @param text The string in the button
     */
    private void setButton(Rectangle2D rectangle, Graphics2D g2d, Boolean clicked, Rectangle button, String text){
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
