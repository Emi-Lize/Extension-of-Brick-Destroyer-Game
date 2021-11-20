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
package test;

import java.awt.*;

/**
 * This represents the rectangle the player controls
 */
public class Player {
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point ballPoint; //coordinate of ball
    private int moveAmount;
    private int min;
    private int max;

    /**
     * This represents the rectangle the player controls and initialises it
     * @param ballPoint The coordinates of the center of the ball
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     * @param container The rectangle shape of the game board
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint; //ball coordinate
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2); //game boundary
        max = min + container.width - width;
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
     * <br>It does this by checking if the bottom of the ball has come in contact with the player's rectangle
     * <br>Change:
     * <ul>
     *     <li>Changed method name from impact to hitBall</li>
     * </ul>
     * @param b The ball object
     * @return A boolean which represents if the player's rectangle has hit the ball
     */
    public boolean hitBall(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    } //check if ball bottom hits the player

    /**
     * Moves the position of the player's rectangle
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount; //add to x the amount moved
        if(x < min || x > max) //if player hits game boundary
            return;
        ballPoint.setLocation(x,ballPoint.getY()); //save position of top center of player
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y); //change coordinate of top left corner of player
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
     * @return Returns the shape of the player's rectangle
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
    public void reset(Point p){ //resetting position
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y); //player reset so that ball is centered on it
    }

}
