package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;

/*
  Created by filippo on 04/09/16.

 */

/**
 * This represents the brick
 * Change:
 * <ul>
 *     <li>Removed Crack class and placed it in its own class</li>
 * </ul>
 */
abstract public class Brick  {

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private String name;
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    /**
     * This represents the brick and initialises its name, border colour, inner colour and strength.
     * Change:
     * <ul>
     *     <li>Removed variable rnd as it's not used in Brick class</li>
     * </ul>
     * @param name The type of brick
     * @param pos The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     * @param border The colour of the border of the brick
     * @param inner The colour of the inner part of the brick
     * @param strength The number of times the brick has to be hit to be broken
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    /**
     * Creates the shape of the brick
     * @param pos The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     * @return The shape of the brick
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * Checks if the ball has hit a brick which has not been broken
     * @param point The coordinates of the point of the ball which hits a brick
     * @param dir The side of the brick which was hit
     * @return A boolean representing if a brick has been broken
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact(); //reduces strength
        return  broken;
    }

    /**
     * Gets the shape of the brick
     * @return The shape of the brick
     */
    public abstract Shape getBrick();


    /**
     * Gets the colour of the border of the brick
     * @return The colour of the border of the brick
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * Gets the colour of the inner part of the brick
     * @return The colour of the inner part of the brick
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * Finds which part of the brick was hit by the ball
     * @param b The ball object
     * @return The side of the brick which was hit by the ball
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right)) //if brick in contact with the right of the ball
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * Checks if the brick is broken
     * @return A boolean representing if the brick is broken
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * Resets the brick to its initial property
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * Reduces the strength of the brick and decides if it's broken when hit
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }



}





