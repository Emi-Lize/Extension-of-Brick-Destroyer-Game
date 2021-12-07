package brickdestroyer.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import brickdestroyer.wall.Wall;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameSystemTest {
    private static GameSystem gameSystem;
    private static Rectangle drawArea = new Rectangle(600,450);
    private static Point ballPos = new Point(300,430);
    private static Wall wall = new Wall(drawArea, 30, 3, (double)6/2);

    @BeforeAll
    static void setGameSystem(){
        gameSystem = new GameSystem(drawArea, ballPos, wall);
    }

    @Test
    void move() {
        gameSystem.ballReset();
        gameSystem.move();
        assertEquals(new Point(303, 427), gameSystem.ball.getPosition());
    }

    @Test
    void findImpacts() {
        gameSystem.ballReset();
        gameSystem.wall.nextLevel();
        gameSystem.findImpacts();
        assertEquals(3, gameSystem.ball.getSpeedY());
        gameSystem.ball.reset(new Point(605, 200));
        gameSystem.findImpacts();
        assertEquals(-3, gameSystem.ball.getSpeedX());
        gameSystem.ball.reset(new Point(300, 0));
        gameSystem.findImpacts();
        assertEquals(3, gameSystem.ball.getSpeedY());
        gameSystem.ball.reset(new Point(300, 500));
        gameSystem.findImpacts();
        assertEquals(2, gameSystem.getBallCount());
        assertTrue(gameSystem.isBallLost());
    }

    @Test
    void ballReset() {
        gameSystem.ballReset();
        assertEquals(new Rectangle(new Point(225,430), new Dimension(150, 10)), gameSystem.player.getPlayerFace());
        assertEquals(ballPos, gameSystem.ball.getPosition());
        assertEquals(3, gameSystem.ball.getSpeedX());
        assertEquals(-3, gameSystem.ball.getSpeedY());
        assertFalse(gameSystem.isBallLost());
    }

    @Test
    void ballEnd() {
        assertFalse(gameSystem.ballEnd());
    }

    @Test
    void isDone() {
        gameSystem.wall.nextLevel();
        assertFalse(gameSystem.isDone());
    }

    @Test
    void hasLevel() {
        assertTrue(gameSystem.hasLevel());
    }

    @Test
    void setBallXSpeed() {
        gameSystem.setBallXSpeed(10);
        assertEquals(10, gameSystem.ball.getSpeedX());
    }

    @Test
    void setBallYSpeed() {
        gameSystem.setBallYSpeed(20);
        assertEquals(20, gameSystem.ball.getSpeedY());
    }

    @Test
    void resetBallCount() {
        gameSystem.resetBallCount();
        assertEquals(3, gameSystem.getBallCount());
    }
}