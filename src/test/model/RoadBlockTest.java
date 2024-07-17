package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoadBlockTest {
    double positionX;
    double positionY;
    private RoadBlock roadBlock;

    @BeforeEach
    void runBefore() {
        positionX = Math.random() * 100;
        positionY = Math.random() * 100;

        roadBlock = new RoadBlock(positionX, positionY);
    }

    @Test
    void testConstructor() {
        assertEquals(positionX, roadBlock.getPositionX());
        assertEquals(positionY, roadBlock.getPositionY());
        assertEquals(roadBlock.getMaxAmmoCapacity(), roadBlock.getAmmoRemaining());
        assertTrue(roadBlock.getCost() == 0);
        assertTrue(roadBlock.getHitPoints() > 0);
    }

}
