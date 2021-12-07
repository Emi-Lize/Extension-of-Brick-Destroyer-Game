package brickdestroyer.game;

import brickdestroyer.powerup.PowerUp;
import brickdestroyer.player.Player;
import brickdestroyer.wall.Wall;
import brickdestroyer.ball.Ball;
import brickdestroyer.ball.RubberBall;
import brickdestroyer.brick.Brick;
import brickdestroyer.brick.Crack;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * New class - This class controls how the game operates
 * <br>Added methods pertaining to ball and player from Wall class
 */
public class GameSystem {
    private Rectangle area;
    private Point startPoint;

    private int ballCount;
    private boolean ballLost;

    public Ball ball;
    public Player player;
    public Wall wall;
    public PowerUp powerUp;

    /**
     * Initialises the ball, player and powerUp
     * <br>Change:
     * <ul>
     *     <li>Removed random speed and set speed to 3</li>
     *     <li>Created an object of class PowerUp</li>
     * </ul>
     * @param drawArea The rectangle shape of the game board
     * @param ballPos The coordinates of the center of the ball
     * @param wall The wall object
     */
    public GameSystem(Rectangle drawArea, Point ballPos, Wall wall){
        this.wall=wall;
        this.startPoint = new Point(ballPos);

        ballCount = 3;
        ballLost = false;

        makeBall(ballPos);
        ball.setSpeed(3,-3);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);
        powerUp = new PowerUp(drawArea, ball, wall);

        area = drawArea;
    }

    /**
     * Creates a ball
     * @param ballPos The coordinates of the center of the ball
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**
     * Moves the player and the ball
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * Checks if ball has hit either the player, powerup, brick or the boundary of the game frame
     * <br>Change:
     * <ul>
     *     <li>Calls method in wall if ball touches PowerUp</li>
     * </ul>
     */
    public void findImpacts(){
        if(player.hitBall(ball)){
            ball.reverseY();
        }
        else if(powerUp.findImpact(ball)){
            wall.setPowerCount();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            wall.setBrickCount(1);
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * Checks whether the ball hit a brick and if so, which part of the brick was hit by the ball
     * <br>Changes:
     * <ul>
     *     <li>Changed all second argument of setImpact to Crack. instead of Brick.Crack.</li>
     *     <li>Changed the array in the for loop to wall.bricks instead of bricks</li>
     *     <li>Used getter to get the bricks from Wall</li>
     *     <li>Enhanced the switch statement</li>
     * </ul>
     * @return A boolean which represents if the ball had hit a brick
     */
    private boolean impactWall(){
        for(Brick b : wall.getBricks()){
            switch (b.findImpact(ball)) {
                case Brick.UP_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.getDown(), Crack.UP);
                }
                case Brick.DOWN_IMPACT -> {
                    ball.reverseY();
                    return b.setImpact(ball.getUp(), Crack.DOWN);
                }
                case Brick.LEFT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.getRight(), Crack.RIGHT);
                }
                case Brick.RIGHT_IMPACT -> {
                    ball.reverseX();
                    return b.setImpact(ball.getLeft(), Crack.LEFT);
                }
            }
        }

        return false;
    }

    /**
     * Checks if the ball hit the left or right edge of the game board
     * @return A boolean which represents if the ball hit the left or right edge of the game board
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * Gets the number of balls left
     * @return The number of balls left
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * Checks if the ball was lost
     * @return A boolean which represents if the ball was lost
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * Resets the position of the ball, powerup and player and the speed of the ball
     * <br>Change:
     * <ul>
     *     <li>Speed of ball is constant at 3</li>
     * </ul>
     */
    public void ballReset(){
        player.reset(startPoint);
        ball.reset(startPoint);
        powerUp.createPowerUp();
        ball.setSpeed(3,-3);
        ballLost = false;
    }

    /**
     * Checks if there is no more balls left
     * @return A boolean which represents that there are no balls left
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * Checks if the level is cleared
     * <br>Change:
     * <ul>
     *     <li>Used method getBrickCount from wall to get the value of the variable</li>
     * </ul>
     * @return A boolean which represents that there are no bricks left in the level
     */
    public boolean isDone(){
        return wall.getBrickCount()==0;
    }

    /**
     * Checks if there are still more levels
     * <br>Change:
     * <ul>
     *     <li>Used method getLevel and getLevels to access the variables in Wall class</li>
     * </ul>
     * @return A boolean which represents if there are still more levels
     */
    public boolean hasLevel(){
        return wall.getLevel() < wall.getLevels().length;
    }

    /**
     * Sets the speed of the ball along the x-axis
     * @param s The speed of the ball along the x-axis
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * Sets the speed of the ball along the y-axis
     * @param s The speed of the ball along the y-axis
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /**
     * Resets the number of balls
     */
    public void resetBallCount(){
        ballCount = 3;
    }

}
