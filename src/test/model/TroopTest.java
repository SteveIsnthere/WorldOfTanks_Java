package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TroopTest {
    double positionX;
    double positionY;
    private Troop troop;

    @BeforeEach
    void runBefore() {
        positionX = Math.random() * 100;
        positionY = Math.random() * 100;

        troop = new Troop(1, positionX, positionY);
    }

    @Test
    void testConstructor() {
        assertEquals(positionX, troop.getPositionX());
        assertEquals(positionY, troop.getPositionY());
        assertEquals(troop.getMaxAmmoCapacity(), troop.getAmmoRemaining());
        assertTrue(troop.getCost() > 0);
        assertTrue(troop.getHitPoints() > 0);
        assertTrue(troop.getSize() == 1);
    }

}
