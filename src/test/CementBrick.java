package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This represents the cement brick which inherits brick
 */
public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;

    /**
     * This represents cement brick and initialises it
     * Changes:
     * <ul>
     *     <li>Creating object crack of class Crack does not require any arguments</li>
     * </ul>
     * @param point The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack();
        brickFace = super.brickFace;
    }

    /**
     * Creates the shape of the brick
     * @param pos The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     * @return The shape of the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * Checks if the ball has hit a brick which has not been broken and draws a crack if the brick is not broken
     * Changes:
     * <ul>
     *     <li>Added super.brickFace as an argument to the method makeCrack</li>
     * </ul>
     * @param point The coordinates of the point of the ball which hits the brick
     * @param dir The side of the brick which was hit
     * @return A boolean representing if the brick has broken
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(super.brickFace, point,dir); //create crack
            updateBrick(); //apply crack
            return false; //brick did not break
        }
        return true; //brick broke
    }

    /**
     * Gets the shape of the brick
     * @return The shape of the brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * Draws a crack on the brick shape
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * Resets the brick to its initial property and removes the crack
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
