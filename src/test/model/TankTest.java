package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TankTest {
    double positionX;
    double positionY;
    double sizeFloor = Tank.sizeFloor;
    double sizeCeiling = Tank.sizeCeiling;
    double size;
    private Tank tank;

    @BeforeEach
    void runBefore() {
        positionX = Math.random() * 100;
        positionY = Math.random() * 100;
        size = sizeFloor + (sizeCeiling - sizeFloor) * Math.random();
        tank = new Tank(1, positionX, positionY, size);
    }

    @Test
    void testConstructor() {
        assertEquals(positionX, tank.getPositionX());
        assertEquals(positionY, tank.getPositionY());
        assertEquals(tank.getMaxAmmoCapacity(), tank.getAmmoRemaining());
        assertTrue(tank.getCost() > 0);
        assertTrue(tank.getHitPoints() > 0);
        assertTrue(tank.getSize() >= sizeFloor && tank.getSize() <= sizeCeiling);
    }

    @Test
    void testGetSizeFloor() {
        assertTrue(tank.getSizeFloor() > 0);
    }

    @Test
    void testGetSizeCeiling() {
        assertTrue(tank.getSizeCeiling() > 0);
        assertTrue(tank.getSizeCeiling() > tank.getSizeFloor());
    }
}

