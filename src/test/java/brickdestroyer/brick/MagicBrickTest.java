package brickdestroyer.brick;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import brickdestroyer.wall.Wall;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MagicBrickTest {
    private static MagicBrick magicBrick;
    private static Point point = new Point(50,20);
    private static Dimension size = new Dimension(60, 20);

    @BeforeAll
    static void setMagicBrick(){
        Wall wall = new Wall(new Rectangle(0,0, 600, 450), 30, 3, (double)6/2);
        magicBrick=new MagicBrick(point, size, wall);
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point, size), magicBrick.makeBrickFace(point, size));
    }
}