package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game testGame;
    private Rocket testRocket;

    @BeforeEach
    public void setUp() {
        testGame = new Game();
        testRocket = testGame.getRocket();
    }

    @Test
    public void testGameConstructor() {
        assertFalse(testGame.getIsGameOver());
        assertEquals(Collections.emptyList(), testGame.getMeteorites());
        assertEquals(Game.SCREEN_WIDTH / 2,testRocket.getHorizontalPosition());
    }

    @Test
    public void testOnTickOnceConstrainRocketOnRightSideBoundary() {
        testRocket.setIsMovingRight(true);
        testRocket.setHorizontalPosition(testGame.SCREEN_WIDTH);
        assertEquals(testGame.SCREEN_WIDTH, testRocket.getHorizontalPosition());
        testGame.onTick();
        assertEquals(testGame.SCREEN_WIDTH, testRocket.getHorizontalPosition());
    }

    @Test
    public void testOnTickOnceConstrainRocketOnLeftSideBoundary() {
        testRocket.setIsMovingRight(false);
        testRocket.setHorizontalPosition(0);
        assertEquals(0, testRocket.getHorizontalPosition());
        testGame.onTick();
        assertEquals(0, testRocket.getHorizontalPosition());
    }
    @Test
    public void testOnTickOnceRocketInBoundaryMiddle() {
        testRocket.setIsMovingRight(true);
        testRocket.setHorizontalPosition(testGame.SCREEN_WIDTH / 2);
        assertEquals(testGame.SCREEN_WIDTH / 2, testRocket.getHorizontalPosition());
        testGame.onTick();
        assertEquals((testGame.SCREEN_WIDTH / 2) + testRocket.SPEED, testRocket.getHorizontalPosition());
    }

    @Test
    public void testOnTickMultipleTimes() {
        testRocket.setIsMovingRight(true);
        testRocket.setHorizontalPosition(testGame.SCREEN_WIDTH / 2);
        assertEquals(testGame.SCREEN_WIDTH / 2, testRocket.getHorizontalPosition());
        testGame.onTick();
        assertEquals((testGame.SCREEN_WIDTH / 2) + testRocket.SPEED, testRocket.getHorizontalPosition());

        testGame.onTick();
        assertEquals((testGame.SCREEN_WIDTH / 2) + (2 * testRocket.SPEED), testRocket.getHorizontalPosition());
    }

    @Test
    public void testOnTickMeteoriteHitsRocket() {
        List<Meteorite> meteoritesList = testGame.getMeteorites();
        Meteorite testMeteorite = new Meteorite(Game.SCREEN_WIDTH / 2);
        assertEquals(0, meteoritesList.size());
        meteoritesList.add(testMeteorite);
        assertEquals(1, meteoritesList.size());
        assertEquals(Game.SCREEN_WIDTH / 2, meteoritesList.get(0).getHorizontalPosition());
        testMeteorite.setVerticalPosition(Rocket.VERTICAL_POSITION - Meteorite.VERTICAL_SPEED);
        testGame.onTick();
        assertTrue(testGame.getIsGameOver());
    }

}