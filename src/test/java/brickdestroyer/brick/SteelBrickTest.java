package brickdestroyer.brick;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {
    private static SteelBrick steelBrick;
    private static Point point = new Point(50,20);
    private static Dimension size = new Dimension(60, 20);

    @BeforeAll
    static void setSteelBrick(){
        steelBrick=new SteelBrick(point, size);
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point, size), steelBrick.makeBrickFace(point, size));
    }
}