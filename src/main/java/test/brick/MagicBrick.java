package test.brick;

import test.wall.Wall;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * New Class - Brick of new level created which is the last level
 * <br>Brick requires two hits to be fully broken
 * <br>There is a probability of a brick being hit when ball touches
 * <br>Probability goes up if PowerUp is taken
 */
public class MagicBrick extends Brick{
    private static final Color DEF_INNER = new Color(0, 0, 204);
    private static final Color DEF_BORDER = new Color(255, 255, 0);
    private static final int MAGIC_STRENGTH = 2;
    private static final double MAGIC_PROBABILITY = 0.4;

    private double probability;
    private Crack crack;
    private Random rnd;
    private Shape brickFace;
    private Wall wall;

    private boolean brickCracked;

    /**
     * This represents the brick of the newly created last level
     * @param point The coordinate of the top left corner of the brick
     * @param size The size of the brick
     * @param wall The wall object
     */
    public MagicBrick(Point point, Dimension size, Wall wall){
        super(point, size, DEF_BORDER, DEF_INNER, MAGIC_STRENGTH);
        this.wall=wall;
        rnd = new Random();
        crack = new Crack();
        brickFace = super.brickFace;
        probability=MAGIC_PROBABILITY;
    }

    /**
     * Creates the shape of the brick
     * @param pos The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     * @return The shape of the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * Gets the shape of the brick
     * @return The shape of the brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * Checks if the ball has hit a brick which has not been broken
     * <br>A crack is drawn following the probability
     * @param point The coordinates of the point of the ball which hits a brick
     * @param dir The side of the brick which was hit
     * @return A boolean representing if the brick has broken
     */
    @Override
    public boolean setImpact(Point2D point, int dir){
        if(super.isBroken())
            return false;
        if (wall.getPowerCount()==0){
            probability=MAGIC_PROBABILITY;
        }
        else{
            increaseProbability();
        }
        impact();
        if(!super.isBroken()){
            if (brickCracked){
                crack.setCrackPoints(super.brickFace, point,dir); //create crack
                drawCrack(); //apply crack
            }
            return false;
        }
        return true; //brick broke
    }

    /**
     * Checks if the brick has broken
     * <br>If the random number generated is less than the given probability, the brick will be considered hit
     */
    @Override
    public void impact(){
        if (rnd.nextDouble() < probability){
            super.impact();
            brickCracked=true;
        }
        else{
            brickCracked=false;
        }
    }

    /**
     * Draws a crack on the brick shape
     */
    private void drawCrack(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * Resets the brick to its initial property and removes the crack
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }

    /**
     * Increases the probability of the brick being hit by 0.05 for every PowerUp taken
     */
    private void increaseProbability(){
        probability+=0.05*wall.getPowerCount();
    }

}
