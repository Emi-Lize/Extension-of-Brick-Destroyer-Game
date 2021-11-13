package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * New class - This class controls how the game operates
 * Added methods pertaining to ball and player from Wall class
 */
public class GameSystem {
    private Random rnd;
    private Rectangle area;

    private Point startPoint;
    private int ballCount;
    private boolean ballLost;

    Ball ball;
    Player player;
    Wall wall;

    /**
     * This represents the game system and initialises the ball and the player
     * Change:
     * <ul>
     *     <li>Moved code to set initial speed of ball to method newBallSpeed</li>
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
        rnd = new Random();
        makeBall(ballPos);
        newBallSpeed();

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

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
     * Checks if ball has hit either the player, brick and the boundary of the game frame
     */
    public void findImpacts(){
        if(player.hitBall(ball)){ //if ball hits player
            ball.reverseY();
        }
        else if(impactWall()){ //check if brick broke
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            wall.setBrickCount(1);
        }
        else if(impactBorder()) { //if hit left and right of game boundary
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){ //if hit top of game boundary
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){ //if hit bottom of game boundary
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * Checks whether the ball hit a brick and if so, which part of the brick was hit by the ball
     * Changes:
     * <ul>
     *     <li>Changed all second argument of setImpact to Crack. instead of Brick.Crack.</li>
     *     <li>Changed the array in the for loop to wall.bricks instead of bricks</li>
     * </ul>
     * @return A boolean which represents if the ball had hit a brick
     */
    private boolean impactWall(){
        for(Brick b : wall.bricks){
            switch(b.findImpact(ball)) { //check where brick got hit - change trajectory of ball - if neither means no brick
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Crack.LEFT);
            }
        }
        return false; //no impact on brick
    }

    /**
     * Checks if the ball hit the left or right of the game board
     * @return A boolean which represents if the ball hit the left or right of the game board
     */
    private boolean impactBorder(){ //if ball hit left and right of game boundary
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
     * Resets the position of the ball and player and the speed of the ball
     */
    public void ballReset(){
        player.reset(startPoint);
        ball.reset(startPoint);
        newBallSpeed();
        ballLost = false;
    }

    /**
     * New Method - Sets a new random speed for the ball
     */
    public void newBallSpeed(){
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
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
     * Change:
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
     * Change:
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
