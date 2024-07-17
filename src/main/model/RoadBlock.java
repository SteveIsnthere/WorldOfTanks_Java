package model;

public class RoadBlock extends BattleUnit {
    static int roadBlockHP = 2000;
    static int roadBlockSize = 5;

    public RoadBlock(double positionX, double positionY) {
        super(0, positionX, positionY, 0, roadBlockHP, roadBlockSize, 0, 0, 0,
                0, 0, 0);
    }
}
