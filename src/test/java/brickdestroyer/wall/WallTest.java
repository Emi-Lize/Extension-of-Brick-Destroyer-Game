package brickdestroyer.wall;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {
    private static Wall wall;
    private static Rectangle drawArea = new Rectangle(0,0,600,450);
    private static int brickCount = 30;
    private static int lineCount = 3;
    private static double brickDimensionRatio = 6/2;

    @BeforeAll
    static void setWall(){
        wall = new Wall(drawArea, brickCount, lineCount, brickDimensionRatio);
    }

    @Test
    void wallReset() {
        assertEquals(30, wall.getBrickCount());
        assertEquals(0, wall.getPowerCount());
    }

    @Test
    void nextLevel() {
        assertEquals(30, wall.getBrickCount());
    }
}