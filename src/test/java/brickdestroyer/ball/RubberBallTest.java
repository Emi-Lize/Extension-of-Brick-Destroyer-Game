package brickdestroyer.ball;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class RubberBallTest {
    private static RubberBall rubberBall;
    private static Point2D center = new Point(100, 100);

    @BeforeAll
    static void setRubberBall() {
        rubberBall = new RubberBall(center);
    }

    @Test
    void makeBall() {
        rubberBall.makeBall(center, 10);
        assertEquals(new Ellipse2D.Double(95.0, 95.0, 10, 10), rubberBall.getBallFace());
    }
}