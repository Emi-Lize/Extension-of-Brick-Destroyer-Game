package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * New Class - This represents the cracks in the brick.
 * <br>Change:
 * <ul>
 *     <li>Created a sole class for Crack</li>
 *     <li>Deleted MIN_CRACK as it was not used</li>
 *     <li>Renamed all object of class GeneralPath from crack to crackPath</li>
 *     <li>Removed CRACK_SECTIONS and JUMP_PROBABILITY as methods which used it were removed</li>
 * </ul>
 */
public class Crack{
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    private GeneralPath crackPath;
    private static Random rnd;
    private int crackDepth;
    private int steps;

    /**
     * This represents the crack in the brick and initialises it
     * <br>Changes:
     * <ul>
     *     <li>Removed parameters</li>
     *     <li>Created object rnd of class Random</li>
     * </ul>
     */
    public Crack(){
        rnd = new Random();
        crackPath = new GeneralPath();
        this.crackDepth = DEF_CRACK_DEPTH;
        this.steps = DEF_STEPS;
    }

    /**
     * Draws the crack in the brick
     * @return The path of the crack
     */
    public GeneralPath draw(){
        return crackPath;
    }

    /**
     * Removes all the crack paths in the game
     */
    public void reset(){
        crackPath.reset();
    }

    /**
     * Finds the start and end point of the crack
     * <br>Changes:
     * <ul>
     *     <li>Changed method name from makeCrack to setCrackPoints as there is another method called makeCrack</li>
     *     <li>Added brickFace parameter</li>
     *     <li>Moved method call for makeRandomPoint and makeCrack to outside of the switch statement body</li>
     *     <li>Enhanced the switch statement</li>
     * </ul>
     * @param brickFace The shape of the brick
     * @param point The coordinates of the point of the ball which hits the brick
     * @param direction The side of the brick which was hit by the ball
     */
    protected void setCrackPoints(Shape brickFace, Point2D point, int direction){
        int side=0;
        Rectangle bounds = brickFace.getBounds();

        Point impact = new Point((int)point.getX(),(int)point.getY()); //(x,y) of the part of the ball which hit the brick
        Point start = new Point();
        Point end = new Point();

        switch (direction) {
            case LEFT -> { //look to right of brick
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                side = VERTICAL;
            }
            case RIGHT -> { //look to the left of the brick as that is starting point of crack
                start.setLocation(bounds.getLocation()); //top left
                end.setLocation(bounds.x, bounds.y + bounds.height); //top left - height
                side = VERTICAL;
            }
            case UP -> {
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                side = HORIZONTAL;
            }
            case DOWN -> {
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                side = HORIZONTAL;
            }
        }
        Point tmp = makeRandomPoint(start,end,side);
        makeCrack(impact,tmp); //set crack start from point of impact to random point
    }

    /**
     * Creates the crack on the brick
     * <br>Changes:
     * <ul>
     *     <li>Removed inMiddle method as it is only true when i=1</li>
     *     <li>Removed jumps method as it just randomises the first step of the crack</li>
     * </ul>
     * @param start The coordinates of the start of the crack
     * @param end The coordinates of the end of the crack
     */
    protected void makeCrack(Point start, Point end){
        GeneralPath path = new GeneralPath();
        path.moveTo(start.x,start.y); //start of crack

        double w = (end.x - start.x) / (double)steps; //width and height of each step
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        double x,y;

        for(int i = 1; i < steps;i++){
            x = (i * w) + start.x; //setting the x
            y = (i * h) + start.y + randomInBounds(bound); //setting the y - randomise the height of steps
            path.lineTo(x,y); //draws the line
        }

        path.lineTo(end.x,end.y); //connect to last point
        crackPath.append(path,true); //crack holds the crack now
    }

    /**
     * Randomises the height of each step in the crack
     * @param bound An integer
     * @return A random integer from -1 to 1 inclusive
     */
    private int randomInBounds(int bound){ //randomise height of steps in crack
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    /**
     * Creates a random point between a range as the end of the crack
     * <br>Changes:
     * <ul>
     *     <li>Enhanced the switch statement</li>
     * </ul>
     * @param from The coordinates of the beginning of the range
     * @param to The coordinates of the end of the range
     * @param direction The side of the brick the end of the crack appears
     * @return The coordinates of the end of the crack
     */
    private Point makeRandomPoint(Point from,Point to, int direction){
        Point out = new Point();
        int pos;

        switch (direction) {
            case HORIZONTAL -> {
                pos = rnd.nextInt(to.x - from.x) + from.x; //get a random point
                out.setLocation(pos, to.y);
            }
            case VERTICAL -> {
                pos = rnd.nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x, pos);
            }
        }
        return out;
    }

}
