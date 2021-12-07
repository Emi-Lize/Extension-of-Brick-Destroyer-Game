package brickdestroyer.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import brickdestroyer.ball.Ball;
import brickdestroyer.ball.RubberBall;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static Player player;
    private static Point ballPoint = new Point(300,430);
    private static int width = 150;
    private static int height = 10;
    private static Rectangle drawArea = new Rectangle(0, 0, 600, 450);
    private static Dimension sizeTest = new Dimension(width, height);

    @BeforeAll
    static void setPlayer(){
        player = new Player(ballPoint, width, height, drawArea);
    }

    @Test
    void hitBall() {
        player.reset(new Point(300,430));
        Ball ball = new RubberBall(new Point(350, 430));
        assertTrue(player.hitBall(ball));
        ball.reset(new Point(100, 200));
        assertFalse(player.hitBall(ball));
    }

    @Test
    void move() {
        player.reset(new Point(525, 430));
        player.moveRight();
        player.move();
        Point pointTest = new Point(450,430);
        assertEquals(new Rectangle(pointTest, sizeTest), player.getPlayerFace());
    }

    @Test
    void moveLeft() {
        player.reset(new Point(300,430));
        player.moveLeft();
        player.move();
        Point pointTest = new Point(220,430);
        assertEquals(new Rectangle(pointTest, sizeTest), player.getPlayerFace());
    }

    @Test
    void moveRight() {
        player.reset(new Point(300,430));
        player.moveRight();
        player.move();
        Point pointTest = new Point(230,430);
        assertEquals(new Rectangle(pointTest, sizeTest), player.getPlayerFace());
    }

    @Test
    void stop() {
        player.reset(new Point(300,430));
        player.stop();
        player.move();
        Point pointTest = new Point(225,430);
        assertEquals(new Rectangle(pointTest, sizeTest), player.getPlayerFace());
    }

    @Test
    void reset() {
        player.reset(new Point(100,200));
        assertEquals(new Rectangle(new Point(25,200), sizeTest), player.getPlayerFace());
    }

    @Test
    void reduceSize() {
        Player smallPlayerTest = new Player(new Point(300,430), width, height, drawArea);
        smallPlayerTest.reduceSize();
        assertEquals(new Rectangle(new Point(250, 430), new Dimension(100,10)), smallPlayerTest.getPlayerFace());
    }
}