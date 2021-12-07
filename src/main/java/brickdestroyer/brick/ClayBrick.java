package brickdestroyer.brick;

import java.awt.*;
import java.awt.Point;

/**
 * This represents the clay brick which inherits from brick
 */
public class ClayBrick extends Brick {
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    /**
     * Calls the constructor in Brick
     * @param point The coordinates of the top left corner of the brick
     * @param size The width and height of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
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
