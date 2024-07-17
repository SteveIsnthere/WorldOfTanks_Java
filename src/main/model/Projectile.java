package model;

public class Projectile extends BattleUnit {
    static double projectileSize = 0.1;
    static int projectileSpeed = 280;

    public Projectile() {
        super(0, 0, 0, 0, 0, projectileSize, 0, 0,
                0, 0, 0, 0);
        this.isDestroyed = true;
    }

    public void fire(double posX, double posY, double direction, double damage) {
        this.isDestroyed = false;
        positionX = posX;
        positionY = posY;
        heading = direction;
        hitPoints = damage;
        setCurrentSpeed(projectileSpeed);
    }
}
