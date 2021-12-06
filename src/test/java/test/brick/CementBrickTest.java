package test.brick;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CementBrickTest {
    private static CementBrick cementBrick;
    private static Point point = new Point(50,20);
    private static Dimension size = new Dimension(60, 20);

    @BeforeAll
    static void setCementBrick(){
        cementBrick=new CementBrick(point, size);
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point, size), cementBrick.makeBrickFace(point, size));
    }

    @Test
    void setImpact() {
        assertFalse(cementBrick.setImpact(new Point(45,25), 30));
        assertTrue(cementBrick.setImpact(new Point(45,25), 30));
        assertFalse(cementBrick.setImpact(new Point(45,25), 30));
    }

    @Test
    void repair() {
        cementBrick.setImpact(new Point(45,25), 30);
        cementBrick.setImpact(new Point(45,25), 30);
        cementBrick.repair();
        assertFalse(cementBrick.isBroken());
    }
}