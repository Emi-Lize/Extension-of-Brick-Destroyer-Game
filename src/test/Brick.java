package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/*
  Created by filippo on 04/09/16.

 */

/**
 * This represents the brick
 */
abstract public class Brick  {

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;


    /**
     * This represents the cracks in the brick
     */
    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private GeneralPath crack;

        private int crackDepth;
        private int steps;

        /**
         * This represents the crack in the brick and initialises it
         * @param steps The number of steps in the crack
         */
        public Crack(int crackDepth, int steps){

            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;

        }


        /**
         * Draws the crack in the brick
         * @return The path of the crack
         */
        public GeneralPath draw(){

            return crack;
        }

        /**
         * Removes all the crack paths in the game
         */
        public void reset(){
            crack.reset();
        }

        /**
         * Finds the start and end point of the crack
         * @param point The coordinates of the point of the ball which hits the brick
         * @param direction The side of the brick which was hit by the ball
         */
        protected void makeCrack(Point2D point, int direction){
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY()); //(x,y) of the part of the ball which hit the brick
            Point start = new Point();
            Point end = new Point();


            switch(direction){
                case LEFT: //look to right of brick
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp); //set crack start from point of impact to random point

                    break;
                case RIGHT: //look to the left of the brick as that is starting point of crack
                    start.setLocation(bounds.getLocation()); //top left
                    end.setLocation(bounds.x, bounds.y + bounds.height); //top left + height
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;

            }
        }

        /**
         * Creates the crack on the brick
         * @param start The coordinates of the start of the crack
         * @param end The coordinates of the end of the crack
         */
        protected void makeCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y); //start of crack

            double w = (end.x - start.x) / (double)steps; //width and height of each step
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x; //setting the x
                y = (i * h) + start.y + randomInBounds(bound); //setting the y - randomise the height of steps

                if(inMiddle(i,CRACK_SECTIONS,steps)) //USELESS
                    y += jumps(jump,JUMP_PROBABILITY);

                path.lineTo(x,y); //draws the line

            }

            path.lineTo(end.x,end.y); //connect to last point
            crack.append(path,true); //crack holds the crack now
        }

        /**
         * Randomises the height of each step
         * @return A random integer
         */
        private int randomInBounds(int bound){ //randomise height of steps
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        private int jumps(int bound,double probability){

            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }

        /**
         * Creates a random point between a range as the end of the crack
         * @param from The coordinates of the beginning of the range
         * @param to The coordinates of the end of the range
         * @param direction The side of the brick the end of the crack appears
         * @return The coordinates of the end of the crack
         */
        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x; //get a random point
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    }

    private static Random rnd;

    private String name;
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    /**
     * This represents the brick and initialises it
     * @param name The type of brick
     * @param pos The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     * @param border The colour of the border of the brick
     * @param inner The colour of the inner part of the brick
     * @param strength The number of times the brick has to be hit to be broken
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
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





