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
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This represents the set of bricks
 */
public class Wall {

    private static final int LEVELS_COUNT = 4;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private Random rnd;
    private Rectangle area;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    /**
     * This represents the set of bricks
     * @param drawArea The rectangle shape of the game board
     * @param brickCount The number of bricks per level
     * @param lineCount The number of lines of bricks per level
     * @param brickDimensionRatio The height to width ratio of the brick
     * @param ballPos The coordinates of the center of the ball
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


    }

    /**
     * Make level 1 of the game
     * @param drawArea The rectangle shape of the game board
     * @param brickCnt The number of bricks in the level
     * @param lineCnt The number of lines of bricks in the level
     * @param brickSizeRatio The height to width ratio of the brick
     * @param type The type of brick
     * @return An array containing brick objects
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt; //10 bricks each line

        double brickLen = drawArea.getWidth() / brickOnLine; //get length of brick
        double brickHgt = brickLen / brickSizeRatio; //length divide by 3 is height

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt]; //list of brick objects

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine; //line is INT - line is the row no.
            if(line == lineCnt) //when all bricks are made
                break;
            double x = (i % brickOnLine) * brickLen; //set x of bricks per line
            x =(line % 2 == 0) ? x : (x - (brickLen / 2)); //for second line, x starts with (-half brick length) so half omitted from screen
            double y = (line) * brickHgt; //set top y of bricks per line
            p.setLocation(x,y); //top left of brick
            tmp[i] = makeBrick(p,brickSize,type); //make brick
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){ //creating second row last brick
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;

    }

    /**
     * Make level 2, 3 and 4 of the game
     * @param drawArea The rectangle shape of the game board
     * @param brickCnt The number of bricks in the level
     * @param lineCnt The number of lines of bricks in the level
     * @param brickSizeRatio The height to width ratio of the brick
     * @param typeA The first type of brick
     * @param typeB The second type of brick
     * @return An array containing brick objects
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1; // 4
        int centerRight = brickOnLine / 2 + 1; // 6

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine; //index of brick per row
            double x = posX * brickLen; //x of brick
            x =(line % 2 == 0) ? x : (x - (brickLen / 2)); //for second line, x starts with (-half brick length) so half omitted from screen
            double y = (line) * brickHgt; //set top y of bricks per line
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight)); //if 1/3 row and odd brick then type A
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB); // if 2 row and center bricks then type B
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){ //making second row last brick
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * Creates a ball
     * @param ballPos The coordinates of the center of the ball
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**
     * Creates each level in the game
     * @param drawArea The rectangle shape of the game board
     * @param brickCount The number of bricks in the level
     * @param lineCount The number of lines of bricks in the level
     * @param brickDimensionRatio The height to width ratio of the brick
     * @return An array of the levels and brick objects
     */
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][]; //tmp[level][bricks]
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        return tmp;
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
        if(player.impact(ball)){ //if ball hits player
            ball.reverseY();
        }
        else if(impactWall()){ //check if brick broke
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
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
     * @return A boolean which represents if the ball had hit a brick
     */
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) { //check where brick got hit - change trajectory of ball - if neither means no brick
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, Brick.Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Brick.Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Brick.Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Brick.Crack.LEFT);
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
     * Gets the number of bricks per level
     * @return The number of bricks per level
     */
    public int getBrickCount(){
        return brickCount;
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
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    /**
     * Resets the bricks in a level to its initial position and property
     */
    public void wallReset(){
        for(Brick b : bricks) //bring back all the bricks
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
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
     * @return A boolean which represents that there are no bricks left in the level
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * Moves to the next level
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    /**
     * Checks if there are still more levels
     * @return A boolean which represents if there are still more levels
     */
    public boolean hasLevel(){
        return level < levels.length;
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

    /**
     * Creates a brick
     * @param point The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     * @param type The type of brick
     * @return A brick object
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

}
