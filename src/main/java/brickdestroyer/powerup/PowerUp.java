package brickdestroyer.powerup;

import brickdestroyer.ball.Ball;
import brickdestroyer.wall.Wall;

import java.awt.*;
import java.awt.Point;
import java.util.Random;

/**
 * New Class - Creates the powerUp object
 * <br>When powerUp is hit, it increases the probability of decreasing magic brick's strenght when the ball hits
 */
public class PowerUp {
    public static final Color BORDER_COLOUR = Color.RED;
    public static final Color INNER_COLOUR = Color.YELLOW;
    private static final int LENGTH = 8;
    private static final int BOUND = 10;
    private static Random rnd;

    private Shape powerUpFace;
    private Dimension size;
    private Point position;

    private Wall wall;

    private int topY;
    private int bottomY;
    private int rightX;
    private boolean broken;

    /**
     * Initialises the properties of powerUp
     * @param drawArea The rectangle shape of the game board
     * @param ball The ball object
     * @param wall The wall object
     */
    public PowerUp(Rectangle drawArea, Ball ball, Wall wall){
        rnd = new Random();
        this.wall=wall;
        position = new Point();

        bottomY=(int)ball.getUp().getY()-LENGTH-BOUND;
        topY=(int)wall.getWallEnd()+BOUND;
        rightX=(int)drawArea.getWidth()-LENGTH;

        size = new Dimension(LENGTH,LENGTH);

        setLocation();
        powerUpFace=makePowerUpFace();

        broken=false;
    }

    /**
     * Creates the shape of the powerUp square
     * @return The shape of the powerUp square
     */
    private Shape makePowerUpFace(){
        return new Rectangle(position, size);
    }

    /**
     * Calculates a random coordinate for the top left corner of powerUp
     */
    private void setLocation(){
        double posX=rnd.nextInt(rightX);
        double posY=rnd.nextInt(bottomY-topY)+topY;
        position.setLocation(posX, posY);
    }

    /**
     * Checks if the ball has hit powerUp
     * @param b The ball object
     * @return A boolean representing is the ball has hit powerUp
     */
    public boolean findImpact(Ball b){
        if(powerUpFace.contains(b.getRight())) {
            broken = true;
            createPowerUp();
            return true;
        }
        else if(powerUpFace.contains(b.getLeft())) {
            broken = true;
            createPowerUp();
            return true;
        }
        else if(powerUpFace.contains(b.getUp())) {
            broken = true;
            createPowerUp();
            return true;
        }
        else if(powerUpFace.contains(b.getDown())) {
            broken = true;
            createPowerUp();
            return true;
        }
        return false;
    }

    /**
     * Checks if the powerUp was hit
     * @return A boolean representing is the powerUp was hit
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * Gets the shape of the powerUp square
     * @return The shape of the powerUp square
     */
    public Shape getPowerUpFace() {
        return powerUpFace;
    }

    /**
     * Recreates the powerUp when broken
     * <br>Only ten powerUps are available for the level
     */
    public void createPowerUp(){
        if(wall.getPowerCount()!=10){
            setLocation();
            powerUpFace=makePowerUpFace();
            broken=false;
        }
    }

}
