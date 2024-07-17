package ui.view.compoents;

import model.BattleUnit;
import model.RoadBlock;
import model.Tank;
import model.Troop;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.view.BasicGameView;
import ui.view.GameSetUpViewB;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class GameAddUnitView extends BasicGameView {
    private static final String gameSetupDataPath = "./data/gameSetupData.json";
    private static final double smallTankSize = new Tank(1, 0, 0, 1).getSizeFloor();
    private static final double largeTankSize = new Tank(1, 0, 0, 1).getSizeCeiling();
    private static final double midTankSize = (smallTankSize + largeTankSize) / 2;
    private final JsonWriter jsonWriter = new JsonWriter(gameSetupDataPath);
    private final JsonReader jsonReader = new JsonReader(gameSetupDataPath);
    int time = 0;
    double dispWidth;
    double dispHeight;
    int battleUnitToAdd = -1;
    double mouseX;
    double mouseY;
    GameSetUpViewB gameSetUpViewB;
    private int smallTankCount;
    private int midTankCount;
    private int largeTankCount;
    private int troopCount;

    public GameAddUnitView() {
        super(1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.gameSetUpViewB = gameSetUpViewB;
        JSONObject gameSetupData = null;
        try {
            gameSetupData = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        smallTankCount = gameSetupData.getInt("smallTankCount");
        midTankCount = gameSetupData.getInt("midTankCount");
        largeTankCount = gameSetupData.getInt("largeTankCount");
        troopCount = gameSetupData.getInt("troopCount");

    }

    public void setBattleUnitToAdd(int battleUnitToAdd) {
        this.battleUnitToAdd = battleUnitToAdd;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = translateToMeter(e.getX());
        mouseY = translateToMeter(e.getY());
    }


    @SuppressWarnings("methodlength")
    @Override
    public void mousePressed(MouseEvent e) {
        BattleUnit newBattleUnit = null;
        BattleUnit newBattleUnit2 = null;
        if (battleUnitToAdd == -1) {
            JOptionPane.showMessageDialog(null, "Please select your unit first");
        } else {
            if (battleUnitToAdd == 0) {
                newBattleUnit = new RoadBlock(0, 0);
            } else if (battleUnitToAdd == 1) {
                if (smallTankCount >= 1) {
                    smallTankCount -= 1;
                    newBattleUnit = new Tank(1, 0, 0, smallTankSize);
                    newBattleUnit2 = new Tank(2, 0, 0, smallTankSize);
                } else {
                    //JOptionPane.showMessageDialog(null, "Run out of this Unit!");
                    return;
                }
            } else if (battleUnitToAdd == 2) {
                if (midTankCount >= 1) {
                    midTankCount -= 1;
                    newBattleUnit = new Tank(1, 0, 0, midTankSize);
                    newBattleUnit2 = new Tank(2, 0, 0, midTankSize);
                } else {
                    //JOptionPane.showMessageDialog(null, "Run out of this Unit!");
                    return;
                }
            } else if (battleUnitToAdd == 3) {
                if (largeTankCount >= 1) {
                    largeTankCount -= 1;
                    newBattleUnit = new Tank(1, 0, 0, largeTankSize);
                    newBattleUnit2 = new Tank(2, 0, 0, largeTankSize);
                } else {
                    //JOptionPane.showMessageDialog(null, "Run out of this Unit!");
                    return;
                }
            } else if (battleUnitToAdd == 4) {
                if (troopCount >= 1) {
                    troopCount -= 1;
                    newBattleUnit = new Troop(1, 0, 0);
                    newBattleUnit2 = new Troop(2, 0, 0);
                } else {
                    //JOptionPane.showMessageDialog(null, "Run out of this Unit!");
                    return;
                }
            }
            newBattleUnit.setPositionX(mouseX);
            newBattleUnit.setPositionY(mouseY);
            if (newBattleUnit.isCollided(homeUnits) == -1) {
                homeUnits.add(newBattleUnit);
            }
//            newBattleUnit.addToTeam(mouseX, mouseY, homeUnits);
            newBattleUnit2.setPositionX(battleFieldWidth * Math.random());
            newBattleUnit2.setPositionY((battleFieldHeight - mouseY) * Math.random());
            awayUnits.add(newBattleUnit2);
        }
    }
}
