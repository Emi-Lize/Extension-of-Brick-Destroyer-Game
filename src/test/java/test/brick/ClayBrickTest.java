package test.brick;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ClayBrickTest {
    private static ClayBrick clayBrick;
    private static Point point = new Point(50,20);
    private static Dimension size = new Dimension(60, 20);

    @BeforeAll
    static void setClayBrick(){
        clayBrick=new ClayBrick(point, size);
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point, size), clayBrick.makeBrickFace(point, size));
    }
}