package test.brick;

import test.brick.Brick;

import java.awt.*;
import java.awt.Point;

/*
  Created by filippo on 04/09/16.

 */

/**
 * This represents the clay brick which inherits from brick
 */
public class ClayBrick extends Brick {
    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    /**
     * This represents clay brick and initialises it
     * @param point The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
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
     * Gets the shape of the brick
     * @return The shape of the brick
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }

}
