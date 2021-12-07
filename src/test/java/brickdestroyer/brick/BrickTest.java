package brickdestroyer.brick;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import brickdestroyer.ball.Ball;
import brickdestroyer.ball.RubberBall;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BrickTest {
    private static Brick brick;
    private static Point point = new Point(50,20);
    private static Dimension size = new Dimension(60, 20);

    @BeforeAll
    static void setBrick(){
        brick=new ClayBrick(point, size);
    }

    @Test
    void setImpact() {
        assertTrue(brick.setImpact(point, 0));
    }

    @Test
    void findImpact() {
        Ball ball = new RubberBall(new Point(45, 25));
        assertEquals(300, brick.findImpact(ball));
        ball.reset(new Point(110, 25));
        assertEquals(400, brick.findImpact(ball));
        ball.reset(new Point(100, 15));
        assertEquals(100, brick.findImpact(ball));
        ball.reset(new Point(80, 40));
        assertEquals(200, brick.findImpact(ball));
    }

    @Test
    void repair() {
        brick.repair();
        assertFalse(brick.isBroken());
    }

    @Test
    void impact() {
        brick.impact();
        assertTrue(brick.isBroken());
    }
}