package ui.view.compoents;

import model.BattleUnit;
import model.Projectile;
import model.RoadBlock;
import ui.view.BasicGameView;

import java.util.ArrayList;

public class GameRendererView extends BasicGameView {
    public GameRendererView(int difficultyLevel, ArrayList<BattleUnit> awayUnits, ArrayList<BattleUnit> homeUnits,
                            ArrayList<RoadBlock> roadBlocks) {
        super(difficultyLevel, awayUnits, homeUnits, roadBlocks);
        for (int i = 0; i < (awayUnits.size() + homeUnits.size()) * 5; i++) {
            projectiles.add(new Projectile());
        }
    }

    @Override
    public void updateState() {
        double dt = Double.valueOf(dt()) / 1000;
        for (BattleUnit unit : homeUnits) {
            unit.update(dt, 1, awayUnits, homeUnits, roadBlocks, projectiles);
        }

        for (BattleUnit unit : awayUnits) {
            unit.update(dt, 1, awayUnits, homeUnits, roadBlocks, projectiles);
        }

        for (BattleUnit unit : projectiles) {
            unit.update(dt, 1, awayUnits, homeUnits, roadBlocks, projectiles);
        }
    }
}
