package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MeteoriteTest {
    private Meteorite testMeteorite;

    @BeforeEach
    public void setUp() {
        testMeteorite = new Meteorite(Game.SCREEN_WIDTH / 2);
    }

    @Test
    public void testMeteoriteConstructor() {
        assertEquals(0, testMeteorite.getVerticalPosition());
    }

    @Test
    public void testSetVerticalPosition() {
        testMeteorite.setVerticalPosition(0);
        assertEquals(0, testMeteorite.getVerticalPosition());
        testMeteorite.setVerticalPosition(Game.SCREEN_HEIGHT / 2);
        assertEquals(Game.SCREEN_HEIGHT / 2, testMeteorite.getVerticalPosition());
    }
}
