package brickdestroyer.midgamescreen;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreTest {
    private static HighScore highScore;

    @BeforeAll
    static void setHighScore(){
        highScore = new HighScore();
    }

    @Test
    void displayScore() {
        highScore.checkScore(60, 1);
        assertEquals("00:00:000", highScore.displayScore(0));
    }

    @Test
    void displayPlayerScore() {
        highScore.checkScore(60, 1);
        assertEquals("00:00:000", highScore.displayPlayerScore());
    }

    @Test
    void formatScore() {
        assertEquals("00:00:060", highScore.formatScore(60));
    }
}