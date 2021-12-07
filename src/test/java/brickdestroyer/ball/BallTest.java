package brickdestroyer.ball;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {
    private static Ball ball;
    private static Point2D center = new Point(100, 100);

    @BeforeAll
    static void setBall() {
        ball = new RubberBall(center);
    }

    @Test
    void move() {
        ball.setSpeed(5,5);
        ball.move();
        assertEquals(new Point(105,105),ball.getPosition());
    }

    @Test
    void setSpeed() {
        ball.setSpeed(5,5);
        assertEquals(5, ball.getSpeedX());
        assertEquals(5, ball.getSpeedY());
    }

    @Test
    void reverseX() {
        ball.setXSpeed(10);
        ball.reverseX();
        assertEquals(-10, ball.getSpeedX());
    }

    @Test
    void reverseY() {
        ball.setYSpeed(10);
        ball.reverseY();
        assertEquals(-10, ball.getSpeedY());
    }

    @Test
    void getPosition() {
        assertEquals(center, ball.getPosition());
    }

    @Test
    void reset() {
        ball.reset(new Point(120,120));
        assertEquals(new Point(120,120), ball.getPosition());
    }

    @Test
    void moveBall() {
        ball.moveBall();
        assertEquals(new Point2D.Double(100.0, 95.0), ball.getUp());
        assertEquals(new Point2D.Double(100.0, 105.0), ball.getDown());
        assertEquals(new Point2D.Double(95.0, 100.0), ball.getLeft());
        assertEquals(new Point2D.Double(105.0, 100.0), ball.getRight());
    }
}