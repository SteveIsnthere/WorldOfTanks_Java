package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectileTest {
    double positionX;
    double positionY;
    private Projectile projectile;

    @BeforeEach
    void runBefore() {
        projectile = new Projectile();
    }

    @Test
    void testConstructor() {
        assertTrue(projectile.isDestroyed());
    }

    @Test
    void testFire() {
        projectile.fire(1, 1, 1, 1);
        assertFalse(projectile.isDestroyed());
    }

}
