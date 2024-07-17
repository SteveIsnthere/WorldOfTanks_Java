package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BattleUnitTest {
    double positionX;
    double positionY;
    double sizeFloor = Tank.sizeFloor;
    double sizeCeiling = Tank.sizeCeiling;
    double size;
    private BattleUnit battleUnitSubClass;
    private ArrayList<BattleUnit> awayUnits;
    private ArrayList<BattleUnit> homeUnits;
    private ArrayList<RoadBlock> roadBlocks;
    private ArrayList<Projectile> projectiles;

    @BeforeEach
    void runBefore() {
        positionX = (new Troop(0, 1, 1).getBattleFieldWidth() - 50) * Math.random() + 25;
        positionY = (new Troop(0, 1, 1).getBattleFieldHeight() - 50) * Math.random() + 25;
        size = sizeFloor + (sizeCeiling - sizeFloor) * Math.random();
        battleUnitSubClass = new Tank(1, positionX, positionY, size);

        awayUnits = new ArrayList<>();
        homeUnits = new ArrayList<>();
        homeUnits.add(battleUnitSubClass);
        roadBlocks = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(positionX, battleUnitSubClass.getPositionX());
        assertEquals(positionY, battleUnitSubClass.getPositionY());
        assertEquals(battleUnitSubClass.getMaxAmmoCapacity(), battleUnitSubClass.getAmmoRemaining());
        assertTrue(battleUnitSubClass.getCost() > 0);
        assertTrue(battleUnitSubClass.getHitPoints() > 0);
        assertTrue(battleUnitSubClass.getSize() >= sizeFloor && battleUnitSubClass.getSize() <= sizeCeiling);
        assertTrue(battleUnitSubClass.getFiringRange() > 0);
        assertTrue(battleUnitSubClass.getDamagePerShot() > 0);
        assertTrue(battleUnitSubClass.getFacingDirection() == Math.toRadians(180));
        assertTrue(battleUnitSubClass.getBattleFieldWidth() > 0);
        assertTrue(battleUnitSubClass.getBattleFieldHeight() > 0);
    }

    @Test
    void testSetPositionX() {
        double newPos = Math.random() * 100;
        battleUnitSubClass.setPositionX(newPos);
        assertEquals(battleUnitSubClass.getPositionX(), newPos);
    }

    @Test
    void testSetPositionY() {
        double newPos = Math.random() * 100;
        battleUnitSubClass.setPositionY(newPos);
        assertEquals(battleUnitSubClass.getPositionY(), newPos);
    }


    @Test
    void testMove() {
        for (int i = 0; i < 10; i++) {
            double newSpeed = Math.random() * 10;
            double newHeading = Math.toRadians((Math.random() - 0.5) * 360);
            double dt = Math.random() / 100;
            double oldPosX = battleUnitSubClass.getPositionX();
            double oldPosY = battleUnitSubClass.getPositionY();
            battleUnitSubClass.setCurrentSpeed(newSpeed);
            battleUnitSubClass.setHeading(newHeading);
            battleUnitSubClass.updateMovement(dt);
            assertEquals(oldPosX -= newSpeed * Math.sin(newHeading) * dt, battleUnitSubClass.getPositionX());
            assertEquals(oldPosY += newSpeed * Math.cos(newHeading) * dt, battleUnitSubClass.getPositionY());
        }
        battleUnitSubClass.setPositionX(-10000);
        battleUnitSubClass.updateMovement(1);
        assertTrue(battleUnitSubClass.isDestroyed());

        positionX = Math.random() * 100;
        positionY = Math.random() * 100;
        size = sizeFloor + (sizeCeiling - sizeFloor) * Math.random();
        battleUnitSubClass = new Tank(1, positionX, positionY, size);

        battleUnitSubClass.setPositionX(10000);
        battleUnitSubClass.updateMovement(1);
        assertTrue(battleUnitSubClass.isDestroyed());

        positionX = Math.random() * 100;
        positionY = Math.random() * 100;
        size = sizeFloor + (sizeCeiling - sizeFloor) * Math.random();
        battleUnitSubClass = new Tank(1, positionX, positionY, size);

        battleUnitSubClass.setPositionY(10000);
        battleUnitSubClass.updateMovement(1);
        assertTrue(battleUnitSubClass.isDestroyed());

        positionX = Math.random() * 100;
        positionY = Math.random() * 100;
        size = sizeFloor + (sizeCeiling - sizeFloor) * Math.random();
        battleUnitSubClass = new Tank(1, positionX, positionY, size);

        battleUnitSubClass.setPositionY(-10000);
        battleUnitSubClass.updateMovement(1);
        assertTrue(battleUnitSubClass.isDestroyed());
    }


    @Test
    void testFireShot() {
        battleUnitSubClass.setTime(System.nanoTime());
        battleUnitSubClass.fireShot(projectiles);
        assertEquals(battleUnitSubClass.getAmmoRemaining(), battleUnitSubClass.getMaxAmmoCapacity() - 1);
        assertEquals(battleUnitSubClass.getLastFiredTime(), battleUnitSubClass.getWorldTime());

        Projectile p1 = new Projectile();
        p1.fire(0, 0, 0, 1);
        Projectile p2 = new Projectile();
        Projectile p3 = new Projectile();
        projectiles.add(p1);
        projectiles.add(p2);
        projectiles.add(p3);
        battleUnitSubClass.setTime(System.nanoTime() + 1000);
        battleUnitSubClass.fireShot(projectiles);
        assertFalse(p2.isDestroyed());
        assertEquals(battleUnitSubClass.getLastFiredTime(), battleUnitSubClass.getWorldTime());
        battleUnitSubClass.ammoRemaining = 0;
        battleUnitSubClass.setTime(System.nanoTime() + 1000);
        battleUnitSubClass.update(1, 1, awayUnits, homeUnits, roadBlocks, projectiles);
        assertTrue(p3.isDestroyed());
        battleUnitSubClass.isFiring = false;
        battleUnitSubClass.setTime(System.nanoTime() + 1000);
        battleUnitSubClass.update(1, 1, awayUnits, homeUnits, roadBlocks, projectiles);
        assertTrue(p3.isDestroyed());
    }

    @Test
    void testGetShot() {
        double oldHP = battleUnitSubClass.getHitPoints();
        battleUnitSubClass.getShot(1);
        assertEquals(oldHP - 1, battleUnitSubClass.getHitPoints());
        assertFalse(battleUnitSubClass.isDestroyed());
        battleUnitSubClass.getShot(1000000000);
        assertTrue(battleUnitSubClass.isDestroyed());
    }

    @Test
    void testReloadAmmo() {
        battleUnitSubClass.fireShot(projectiles);
        battleUnitSubClass.reload();
        assertEquals(battleUnitSubClass.getAmmoRemaining(), battleUnitSubClass.getMaxAmmoCapacity());
    }

    @Test
    void testUpdate() {
        double worldTime = 0;
        for (int i = 0; i < 10; i++) {
            double deltaTime = Math.random();
            double aggression = Math.random();
            worldTime += deltaTime;
            battleUnitSubClass.update(deltaTime, aggression, awayUnits, homeUnits, roadBlocks, projectiles);
        }
        assertEquals(worldTime, battleUnitSubClass.getWorldTime());
        assertFalse(battleUnitSubClass.isDestroyed());
        awayUnits.add(new Tank(2, positionX + 100, positionY + 100, 100));
        BattleUnit destroyedTank = new Tank(2, positionX + 100, positionY + 100, 100);
        destroyedTank.getShot(1000000000);
        awayUnits.add(destroyedTank);
        assertTrue(destroyedTank.isDestroyed());
        battleUnitSubClass.update(1, 1, awayUnits, homeUnits, roadBlocks, projectiles);
        assertFalse(battleUnitSubClass.isDestroyed());

        awayUnits.add(new Tank(2, positionX, positionY, 100));
        battleUnitSubClass.update(1, 1, awayUnits, homeUnits, roadBlocks, projectiles);
        assertTrue(battleUnitSubClass.isDestroyed());

        battleUnitSubClass.getShot(100000);
        battleUnitSubClass.update(1, 1, awayUnits, homeUnits, roadBlocks, projectiles);
    }

}