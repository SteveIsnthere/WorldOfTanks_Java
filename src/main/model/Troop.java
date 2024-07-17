package model;

public class Troop extends BattleUnit {
    private static final double costPerTroop = 0.1; // millions
    private static final double hitPointsPerTroop = 1500; // points
    private static final int ammoCapacityPerTroop = 10; // points
    private static final double troopFiringInterval = 5; // in seconds
    private static final int troopDamagePerShot = 1000;
    private static final int troopRange = 50;
    private static final double troopMaxAcceleration = 10;
    private static final double troopMaxSpeed = 10;
    private static final double troopSize = 1;

    /*
     * Constructs a Troop
     * EFFECTS: Troop is positioned at coordinates (positionX, positionY),
     */
    public Troop(int team, double positionX, double positionY) {
        super(team, positionX, positionY, costPerTroop, hitPointsPerTroop, troopSize, troopMaxSpeed,
                troopMaxAcceleration, troopRange, troopDamagePerShot,
                ammoCapacityPerTroop, troopFiringInterval);
    }
}
