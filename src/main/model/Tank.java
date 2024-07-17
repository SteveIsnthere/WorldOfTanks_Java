package model;

public class Tank extends BattleUnit {
    public static final double sizeFloor = 3; // minimum size of a tank
    public static final double sizeCeiling = 10;// max size of a tank
    private static final double costPerTon = 0.1; // in millions
    private static final double hitPointsPerTon = 1000; // points
    private static final int ammoCapacityPerTon = 1; // number of ammunition per ton
    private static final double weightPerMeterSquare = 1.5; // in tons
    private static final double tankFiringInterval = 2; // in seconds
    private static final int tankDamagePerShot = 40000; // damage to hit-points
    private static final int tankRangePerMeterSize = 100; // shooting range in meters

    /*
     * Constructs a Tank
     * EFFECTS: Tank is positioned at coordinates (positionX, positionY),
     * with size tankSize
     */
    public Tank(int team, double positionX, double positionY, double tankSize) {
        super(team, positionX, positionY, tankCost(tankSize), tankHP(tankSize), tankSize, tankMaxSpeed(tankSize),
                tankMaxAcceleration(tankSize), tankFiringRange(tankSize), tankDamagePerShot,
                tankMaxAmmoCapacity(tankSize), tankFiringInterval);
    }

    /*
     * REQUIRES: tankSize greater than 0
     * MODIFIES: None
     * EFFECTS: return the tankWeight
     */
    private static double tankWeight(double tankSize) {
        return Math.pow(tankSize, 2) * weightPerMeterSquare;
    }

    /*
     * REQUIRES: tankSize greater than 0
     * MODIFIES: None
     * EFFECTS: return the tankCost
     */
    private static double tankCost(double tankSize) {
        return tankWeight(tankSize) * costPerTon;
    }

    /*
     * REQUIRES: tankSize greater than 0
     * MODIFIES: None
     * EFFECTS: return the tankHP
     */
    private static double tankHP(double tankSize) {
        return tankWeight(tankSize) * hitPointsPerTon;
    }

    /*
     * REQUIRES: tankSize greater than 0
     * MODIFIES: None
     * EFFECTS: return the tankMaxSpeed
     */
    private static double tankMaxSpeed(double tankSize) {
        return tankWeight(tankSize) * hitPointsPerTon;
    }

    /*
     * REQUIRES: tankSize greater than 0
     * MODIFIES: None
     * EFFECTS: return the tankMaxAcceleration
     */
    private static double tankMaxAcceleration(double tankSize) {
        return tankWeight(tankSize) * hitPointsPerTon;
    }

    /*
     * REQUIRES: tankSize greater than 0
     * MODIFIES: None
     * EFFECTS: return the tankFiringRange
     */
    private static int tankFiringRange(double tankSize) {
        return (int) (Math.round(tankSize * tankRangePerMeterSize));
    }

    /*
     * REQUIRES: tankSize greater than 0
     * MODIFIES: None
     * EFFECTS: return the tankMaxAmmoCapacity
     */
    private static int tankMaxAmmoCapacity(double tankSize) {
        return (int) Math.round(tankWeight(tankSize) * ammoCapacityPerTon);
    }

    public double getSizeFloor() {
        return sizeFloor;
    }

    public double getSizeCeiling() {
        return sizeCeiling;
    }
}
