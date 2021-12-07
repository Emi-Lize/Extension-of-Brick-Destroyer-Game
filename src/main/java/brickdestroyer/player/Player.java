/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package brickdestroyer.player;

import brickdestroyer.ball.Ball;
import brickdestroyer.wall.Wall;

import java.awt.*;

/**
 * This represents the rectangle the player controls
 */
public class Player {
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Rectangle container;
    private Point ballPoint;

    private int moveAmount;
    private int min;
    private int max;
    private int width;

    /**
     * Creates the player's rectangle and initialises the variables
     * <br>Change:
     * <ul>
     *     <li>Calls method setBoundary to find the min and max x values that the player can be</li>
     *     <li>Stores the value of width and container in the class</li>
     * </ul>
     * @param ballPoint The coordinates of the center of the ball
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     * @param container The rectangle shape of the game board
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        this.width=width;
        playerFace = makeRectangle(width, height);
        this.container=container;
        setBoundary(width);
    }

    /**
     * Creates the shape of the player's rectangle
     * @param width The width of the player's rectangle
     * @param height The height of the player's rectangle
     * @return The shape of the player's rectangle
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * Checks if the player's rectangle has hit the ball
     * <br>Change:
     * <ul>
     *     <li>Changed method name from impact to hitBall</li>
     * </ul>
     * @param b The ball object
     * @return A boolean which represents if the player's rectangle has hit the ball
     */
    public boolean hitBall(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;
    }

    /**
     * Moves the position of the player's rectangle following the moveAmount
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Moves the player's rectangle to the left
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * Moves the player's rectangle to the right
     * <br>Change:
     * <ul>
     *     <li>Edited typo in method name from movRight to moveRight</li>
     * </ul>
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * Stops the player's rectangle from moving
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * Gets the shape of the player's rectangle
     * @return The shape of the player's rectangle
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * Resets the position of the player's rectangle to the initial position
     * <br>Change:
     * <ul>
     *     <li>Changed method name from moveTo to reset</li>
     * </ul>
     * @param p The center of the ball at its initial position
     */
    public void reset(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Reduce the width of the player during the last level
     */
    public void reduceSize(){
        playerFace=makeRectangle(width/3*2,playerFace.height);
        setBoundary(width/3*2);
    }

    /**
     * Finds the minimum and maximum x value that the player can be
     * @param width The width of the player bar
     */
    private void setBoundary(int width){
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

}
