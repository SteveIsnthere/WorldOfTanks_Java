package ui.view;

import model.BattleUnit;
import model.Event;
import model.EventLog;
import model.RoadBlock;
import ui.view.compoents.GameRendererView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameView extends BasicFixedSizeView {

    protected ArrayList<BattleUnit> awayUnits;
    protected ArrayList<BattleUnit> homeUnits;
    protected ArrayList<RoadBlock> roadBlocks;
    int displaySize;

    public GameView(int displaySize, int difficultyLevel, ArrayList<BattleUnit> awayUnits,
                    ArrayList<BattleUnit> homeUnits, ArrayList<RoadBlock> roadBlocks) {
        super(displaySize, difficultyLevel, 50, "Game");
        this.awayUnits = awayUnits;
        this.homeUnits = homeUnits;
        this.roadBlocks = roadBlocks;
        this.displaySize = displaySize;
        initComponents();
    }

    @SuppressWarnings("methodlength")
    private void initComponents() {
        GameRendererView game = new GameRendererView(difficultyLevel, awayUnits,
                homeUnits, roadBlocks);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));

        buttonPane.add(Box.createHorizontalGlue());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                new GameSetUpViewB(displaySize, difficultyLevel);
            }
        });
        buttonPane.add(backButton);
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        JButton nextButton = new JButton("Exit");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }

                setVisible(false);
                dispose();
            }
        });
        buttonPane.add(nextButton);

        setLayout(new BorderLayout());
        add(game, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

}
