package model;

import java.util.ArrayList;

/*
 * Represents a BattleUnit.
 */
public abstract class BattleUnit {
    protected final double cost; // Cost to build in million dollars
    protected final double size; // radius of the BattleUnit in meters
    protected final double maxSpeed; // maximum speed of the BattleUnit in meters per second
    protected final double maxAcceleration; // maximum acceleration of the BattleUnit in meters per second squared
    protected final double currentAcceleration = 0; // acceleration of the BattleUnit in meters per second squared
    protected final int firingRange; // the distance that BattleUnit shoot in meters
    protected final int damagePerShot; // The damage that BattleUnit deal in one shot
    protected final int maxAmmoCapacity;// The amount of ammunition BattleUnit can carry
    protected final double firingInterval;// the time interval BattleUnit can fire a shot
    protected boolean isFiring = true;// should the BattleUnit open fire or not
    protected double positionX; // x coordinate position of the BattleUnit
    protected double positionY; // y coordinate position of the BattleUnit
    protected double hitPoints; // total remaining hitPoints
    protected double heading = 0; //the direction of the BattleUnit heading in Radian
    protected double facingDirection = 0; //the direction of the BattleUnit's gun facing in Radian
    protected double currentSpeed = 0; // speed of the BattleUnit in meters per second
    protected double worldTime = 0; // current timestamp of the world
    protected int ammoRemaining;// The amount ammunition BattleUnit has remaining
    protected double lastFiredTime = 0;// the timestamp that BattleUnit last fired a shot
    protected boolean isDestroyed = false;// is the BattleUnit Destroyed
    protected double battleFieldWidth = 600;
    protected double battleFieldHeight = 600;
    protected boolean shouldMove = false;
    protected double homeLine;
    protected double awayLine;

    /*
     * Constructs an BattleUnit
     * EFFECTS: BattleUnit is positioned at coordinates (positionX, positionY),
     * with the cost as cost, hitPoints as hitPoints, size as size, maxSpeed as maxSpeed,
     * maxAcceleration as maxAcceleration, firingRange as firingRange, damagePerShot as damagePerShot,
     * maxAmmoCapacity as maxAmmoCapacity, firingInterval as firingInterval
     */
    public BattleUnit(int team, double positionX, double positionY, double cost, double hitPoints, double size,
                      double maxSpeed, double maxAcceleration, int firingRange, int damagePerShot, int maxAmmoCapacity,
                      double firingInterval) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.cost = cost;
        this.hitPoints = hitPoints;
        this.size = size;
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
        this.firingRange = firingRange;
        this.damagePerShot = damagePerShot;
        this.maxAmmoCapacity = maxAmmoCapacity;
        this.firingInterval = firingInterval;

        this.ammoRemaining = maxAmmoCapacity;

        if (team == 1) {
            homeLine = battleFieldHeight;
            awayLine = 0;
            heading = Math.toRadians(180);
        } else if (team == 2) {
            homeLine = 0;
            awayLine = battleFieldHeight;
            heading = Math.toRadians(0);
        }
        facingDirection = heading;
    }

    public double getFacingDirection() {
        return facingDirection;
    }

    public double getBattleFieldWidth() {
        return battleFieldWidth;
    }

    public double getBattleFieldHeight() {
        return battleFieldHeight;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public double getCost() {
        return cost;
    }

    public int getFiringRange() {
        return firingRange;
    }

    public double getSize() {
        return size;
    }

    public int getDamagePerShot() {
        return damagePerShot;
    }

    public int getAmmoRemaining() {
        return ammoRemaining;
    }

    public int getMaxAmmoCapacity() {
        return maxAmmoCapacity;
    }

    public double getWorldTime() {
        return worldTime;
    }

    public double getLastFiredTime() {
        return lastFiredTime;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setTime(double time) {
        worldTime = time;
    }

    /*
     * REQUIRES: deltaTime greater than 0
     * MODIFIES: this
     * EFFECTS: add the displacement happened in deltaTime to the position
     */
    public void updateMovement(double deltaTime) {
        positionX -= currentSpeed * Math.sin(heading) * deltaTime;
        positionY += currentSpeed * Math.cos(heading) * deltaTime;

//        if (shouldMove) {
//            if (currentSpeed < maxSpeed) {
//                currentSpeed += maxAcceleration * deltaTime;
//            }
//        } else {
//            if (currentSpeed > 0) {
//                currentSpeed -= maxAcceleration * deltaTime;
//            } else {
//                currentSpeed = 0;
//            }
//        }

        if (positionX - size < 0) {
            die();
        } else if (positionX + size > battleFieldWidth) {
            die();
        }

        if (positionY - size < 0) {
            die();
        } else if (positionY + size > battleFieldHeight) {
            die();
        }
    }

    public void die() {
        isDestroyed = true;
    }

    /*
     * REQUIRES: ammoRemaining greater than 0
     * MODIFIES: this
     * EFFECTS: ammoRemaining minus 1
     * update lastFiredTime with the worldTime
     */
    public void fireShot(ArrayList<Projectile> projectiles) {
        if (worldTime > lastFiredTime + firingInterval) {
            ammoRemaining -= 1;
            lastFiredTime = worldTime;
            EventLog.getInstance().logEvent(new Event("A battle unit fired a shot!" + " unit cost: " + cost));
            for (Projectile projectile : projectiles) {
                if (projectile.isDestroyed()) {
                    projectile.fire(positionX - size * Math.sin(facingDirection) * 1.1,
                            positionY + size * Math.cos(facingDirection) * 1.1,
                            facingDirection,
                            damagePerShot);
                    return;
                }
            }
        }
    }

    /*
     * REQUIRES: newHitPoints greater than 0
     * MODIFIES: this
     * EFFECTS: hitPoints minus damage
     * set BattleUnit to dead if hitPoints less than 0
     */
    public void getShot(double damage) {
        double newHitPoints = hitPoints - damage;
        if (newHitPoints > 0) {
            hitPoints = newHitPoints;
            EventLog.getInstance().logEvent(new Event("A battle unit got shot!" + " Remaining hp: "
                    + hitPoints));
        } else {
            isDestroyed = true;
        }
    }

//    public void addToTeam(double posX, double posY, ArrayList<BattleUnit> team) {
//        int collidedUnitIndex = -2;
//        int loopCount = 0;
//        while (collidedUnitIndex != -1) {
//            if (loopCount >= 50) {
//                break;
//            }
//            positionX = posX;
//            positionY = posY;
//            loopCount++;
//        }
//        team.add(this);
//    }

    public int isCollided(ArrayList<BattleUnit> units) {
        int index = 0;
        for (BattleUnit unit : units) {
            if (unit.equals(this) || unit.isDestroyed()) {
                continue;
            }
            double safeDistance = size + unit.size;
            double distance = Math.sqrt(Math.pow((positionX - unit.positionX), 2)
                    + Math.pow((positionY - unit.positionY), 2));
            if (distance < safeDistance) {
                return index;
            }
            index++;
        }
        return -1;
    }


    public void collisionDetection(ArrayList<BattleUnit> awayUnits,
                                   ArrayList<BattleUnit> homeUnits, ArrayList<RoadBlock> roadBlocks,
                                   ArrayList<Projectile> projectiles) {

        ArrayList<BattleUnit> allUnits = new ArrayList<>();
        allUnits.addAll(awayUnits);
        allUnits.addAll(homeUnits);
        allUnits.addAll(roadBlocks);
        allUnits.addAll(projectiles);

        int unitsCollidedIndex = isCollided(allUnits);
        if (unitsCollidedIndex != -1) {
            BattleUnit unitsCollided = allUnits.get(unitsCollidedIndex);
            double awayHP = unitsCollided.hitPoints;
            unitsCollided.getShot(hitPoints);
            getShot(awayHP);
        }
    }

    /*
     * REQUIRES: None
     * MODIFIES: this
     * EFFECTS: set ammoRemaining to maxAmmoCapacity
     */
    public void reload() {
        ammoRemaining = maxAmmoCapacity;
    }

    public void unitBehavior(double deltaTime, double aggression, ArrayList<BattleUnit> awayUnits,
                             ArrayList<BattleUnit> homeUnits, ArrayList<RoadBlock> roadBlocks,
                             ArrayList<Projectile> projectiles) {
        if (isFiring && ammoRemaining >= 1) {
            fireShot(projectiles);
        }
    }

    // Updates BattleUnit on clock tick
    // REQUIRES: None
    // modifies: this
    // effects:  Based on different implementation
    public void update(double deltaTime, double aggression, ArrayList<BattleUnit> awayUnits,
                       ArrayList<BattleUnit> homeUnits, ArrayList<RoadBlock> roadBlocks,
                       ArrayList<Projectile> projectiles) {
        worldTime += deltaTime;
        if (isDestroyed) {
            return;
        }
        updateMovement(deltaTime);
        unitBehavior(deltaTime, aggression, awayUnits, homeUnits, roadBlocks, projectiles);
        collisionDetection(awayUnits, homeUnits, roadBlocks, projectiles);
    }

}
