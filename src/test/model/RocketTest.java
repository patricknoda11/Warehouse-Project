package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RocketTest {
    private Rocket testRocket;

    @BeforeEach
    public void setUp() {
        testRocket = new Rocket(Game.SCREEN_WIDTH/2);
    }

    @Test
    public void testRocketConstructor() {
        assertEquals(Game.SCREEN_WIDTH / 2, testRocket.getHorizontalPosition());
        assertTrue(testRocket.getIsGoingRight());
    }

    @Test
    public void testSetHorizontalPositionWithinBoundary() {
        testRocket.setHorizontalPosition((Game.SCREEN_WIDTH / 2) + 2);
        assertEquals( (Game.SCREEN_WIDTH / 2) + 2, testRocket.getHorizontalPosition());
        testRocket.setHorizontalPosition((Game.SCREEN_WIDTH / 2) - 2);
        assertEquals( (Game.SCREEN_WIDTH / 2) - 2, testRocket.getHorizontalPosition());
    }

    @Test
    public void testSetHorizontalPositionAtBoundary() {
        testRocket.setHorizontalPosition(Game.SCREEN_WIDTH);
        assertEquals(Game.SCREEN_WIDTH, testRocket.getHorizontalPosition());
        testRocket.setHorizontalPosition(0);
        assertEquals(0, testRocket.getHorizontalPosition());
    }

    @Test
    public void testSetIsMovingRight() {
        assertTrue(testRocket.getIsGoingRight());
        testRocket.setIsMovingRight(false);
        assertFalse(testRocket.getIsGoingRight());
    }
}
