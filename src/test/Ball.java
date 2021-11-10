package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/*
  Created by filippo on 04/09/16.

 */

/**
 * This represents the ball
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    /**
     * This represents and initialises the ball
     * @param center The coordinates of the center of the ball
     * @param radiusA The vertical radius of the ball
     * @param radiusB The horizontal radius of the ball
     * @param inner The colour of the inner part of the ball
     * @param border The colour of the border of the ball
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double(); //creating coordinates
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2)); //setting coordinates
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB); //creating ball
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    /**
     * Creates the shape of the ball
     * @param center The coordinates of the center of the ball
     * @param radiusA The vertical radius of the ball
     * @param radiusB The horizontal radius of the ball
     * @return The shape of the ball
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB); //implemented in rubberball.java

    /**
     * Moves the position of the ball based on the speed
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace; //create a temporary square frame
        center.setLocation((center.getX() + speedX),(center.getY() + speedY)); //change the center of the ball
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h); //create the square frame of the ball (x,y,width,height)
        setPoints(w,h); //change the 4 coordinates of the ball


        ballFace = tmp; //replace with the original ball
    }

    /**
     * Sets the speed of the ball
     * @param x The speed of the ball along the x-axis
     * @param y The speed of the ball along the y-axis
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * Sets the speed of the ball along the x-axis
     * @param s The speed of the ball along the x-axis
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * Sets the speed of the ball along the y-axis
     * @param s The speed of the ball along the y-axis
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * Reverses the horizontal direction of the ball
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * Reverses the vertical direction of the ball
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * Gets the colour of the border of the ball
     * @return The colour of the border of the ball
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * Gets the colour of the inner part of the ball
     * @return The colour of the inner part of the ball
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * Gets the position of the ball
     * @return The coordinates of the center of the ball
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * Gets the shape of the ball
     * @return The shape of the ball
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * Resets the position of the ball to the initial position
     * @param p The coordinates of the center of the ball at the initial position
     */
    public void moveTo(Point p){ //used in wall.java - ballreset
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace; //same concept as move
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * Sets the coordinates of the four points of the ball
     * @param width The width of the ball
     * @param height The height of the ball
     */
    private void setPoints(double width,double height){ //setting 4 coordinates of ball
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * Gets the speed of the ball along the x-axis
     * @return The speed of the ball along the x-axis
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * Gets the speed of the ball along the y-axis
     * @return The speed of the ball along the y-axis
     */
    public int getSpeedY(){
        return speedY;
    }


}