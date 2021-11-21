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
package test.wall;

import test.brick.Brick;
import test.brick.CementBrick;
import test.brick.ClayBrick;
import test.brick.SteelBrick;

import java.awt.*;

/**
 * This represents the set of bricks
 * <br>Change:
 * <ul>
 *     <li>Removed methods on ball and player and placed it to a new class GameSystem</li>
 *     <li>Set the variable bricks as private</li>
 * </ul>
 */
public class Wall {
    private static final int LEVELS_COUNT = 4;
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private Brick[] bricks;

    private Brick[][] levels;
    private int level;
    private int brickCount;

    /**
     * This represents the set of bricks
     * <br>Changes:
     * <ul>
     *     <li>Moved code to initialise ball and player to GameSystem class</li>
     *     <li>Removed ballPos parameter</li>
     * </ul>
     * @param drawArea The rectangle shape of the game board
     * @param brickCount The number of bricks per level
     * @param lineCount The number of lines of bricks per level
     * @param brickDimensionRatio The height to width ratio of the brick
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        levels = setUpLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
    }

    /**
     * New Method - merged both makeSingleLevels and makeChessboardLevels to reduce code duplicate
     * <br>Changes:
     * <ul>
     *     <li>Added an if statement to differentiate between Single Levels and Chessboard Levels</li>
     *     <li>Method takes in two parameters - typeA and typeB</li>
     *     <li>Moved code to create chessboard level to createChessboardLevel</li>
     * </ul>
     * @param drawArea The rectangle shape of the game board
     * @param brickCnt The number of bricks in the level
     * @param lineCnt The number of lines of bricks in the level
     * @param brickSizeRatio The height to width ratio of the brick
     * @param typeA The first type of brick
     * @param typeB The second type of brick
     * @return An array containing brick objects
     */
    private Brick[] createLevels(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
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
        for(i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine; //line is INT - line is the row no.
            if (line == lineCnt) //when all bricks are made
                break;
            double x = (i % brickOnLine) * brickLen; //set x of bricks per line
            x =(line % 2 == 0) ? x : (x - (brickLen / 2)); //for second line, x starts with (-half brick length) so half omitted from screen
            double y = (line) * brickHgt; //set top y of bricks per line
            p.setLocation(x,y); //top left of brick
            if (typeA!=typeB){
                boolean b = createChessboardLevel(line, brickOnLine, i);
                tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB); // if 2 row and center bricks then type B
            }
            else{
                tmp[i] = makeBrick(p, brickSize, typeA);
            }
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){ //creating second row last brick
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * New Method - Moved code to create chessboard level from createLevels
     * @param line The line number from the top
     * @param brickOnLine The number of bricks in one line
     * @param i The index of the brick
     * @return A boolean representing if it is in the first or third row and the brick index is even
     */
    private boolean createChessboardLevel(int line, int brickOnLine, int i){
        int posX = i % brickOnLine;
        int centerLeft = brickOnLine / 2 - 1; // 4
        int centerRight = brickOnLine / 2 + 1; // 6
        return ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight)); //if 1/3 row and odd brick then type A
    }

    /**
     * Creates each level in the game
     * <br>Changes:
     * <ul>
     *     <li>Changed method name from makeLevels to setUpLevels for better naming</li>
     * </ul>
     * @param drawArea The rectangle shape of the game board
     * @param brickCount The number of bricks in the level
     * @param lineCount The number of lines of bricks in the level
     * @param brickDimensionRatio The height to width ratio of the brick
     * @return An array of the levels and brick objects
     */
    private Brick[][] setUpLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][]; //tmp[level][bricks]
        tmp[0] = createLevels(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CLAY);
        tmp[1] = createLevels(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = createLevels(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = createLevels(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        return tmp;
    }

    /**
     * Gets the number of bricks per level
     * @return The number of bricks per level
     */
    public int getBrickCount(){
        return brickCount;
    }

    /**
     * New Method - decreases the brick count when a brick is broken
     * @param brickCount The number of bricks broken
     */
    public void setBrickCount(int brickCount) {
        this.brickCount -= brickCount;
    }

    /**
     * Resets the bricks in a level to its initial position and property
     */
    public void wallReset(){
        for(Brick b : bricks) //bring back all the bricks
            b.repair();
        brickCount = bricks.length;
    }

    /**
     * Moves to the next level
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    /**
     * Creates a brick
     * <br>Change:
     * <ul>
     *     <li>Enhanced the switch statement</li>
     *     <li>Removed variable out</li>
     * </ul>
     * @param point The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     * @param type The type of brick
     * @return A brick object
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        return switch (type) {
            case CLAY -> new ClayBrick(point, size);
            case STEEL -> new SteelBrick(point, size);
            case CEMENT -> new CementBrick(point, size);
            default -> throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        };
    }

    /**
     * Gets the current level
     * @return The level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the levels array containing the layout of the bricks for each level
     * @return The levels array
     */
    public Brick[][] getLevels() {
        return levels;
    }

    /**
     * New Method - Gets the brick layout
     * @return An array containing the layout of the bricks
     */
    public Brick[] getBricks() {
        return bricks;
    }
}
