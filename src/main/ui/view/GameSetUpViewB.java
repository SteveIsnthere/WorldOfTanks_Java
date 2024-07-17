package ui.view;

import model.BattleUnit;
import model.Tank;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.view.compoents.GameAddUnitView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameSetUpViewB extends BasicFixedSizeView {
    private static final String gameSetupDataPath = "./data/gameSetupData.json";
    private static final double smallTankSize = new Tank(1, 0, 0, 1).getSizeFloor();
    private static final double largeTankSize = new Tank(1, 0, 0, 1).getSizeCeiling();
    private static final double midTankSize = (smallTankSize + largeTankSize) / 2;
    private final JsonWriter jsonWriter = new JsonWriter(gameSetupDataPath);
    private final JsonReader jsonReader = new JsonReader(gameSetupDataPath);
    int displaySize;
    int difficultyLevel;
    private BattleUnit battleUnitToAdd;

    private int smallTankCount;
    private int midTankCount;
    private int largeTankCount;
    private int troopCount;

    public GameSetUpViewB(int displaySize, int difficultyLevel) {
        super(displaySize, difficultyLevel, 120, "setup");
        initComponents();
    }


    @SuppressWarnings("methodlength")
    private void initComponents() {

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

        GameAddUnitView game = new GameAddUnitView();

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));

        JPanel buttonUpperPane = new JPanel();
        buttonUpperPane.setLayout(new BoxLayout(buttonUpperPane, BoxLayout.LINE_AXIS));
        //
        JPanel armySelectionPane = new JPanel();
        armySelectionPane.setLayout(new BoxLayout(armySelectionPane, BoxLayout.LINE_AXIS));

        JPanel tankSmall = new JPanel();
        tankSmall.setLayout(new BoxLayout(tankSmall, BoxLayout.PAGE_AXIS));
        JPanel tankMid = new JPanel();
        tankMid.setLayout(new BoxLayout(tankMid, BoxLayout.PAGE_AXIS));
        JPanel tankLarge = new JPanel();
        tankLarge.setLayout(new BoxLayout(tankLarge, BoxLayout.PAGE_AXIS));
        JPanel troop = new JPanel();
        troop.setLayout(new BoxLayout(troop, BoxLayout.PAGE_AXIS));

        JButton tankSmallButton = new JButton("Tank (S)");
        tankSmallButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton tankMidButton = new JButton("Tank (M)");
        tankMidButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton tankLargeButton = new JButton("Tank (L)");
        tankLargeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton troopButton = new JButton("Troop");
        troopButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tankSmallCountJLabel = new JLabel(Integer.toString(smallTankCount));
        tankSmallCountJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel tankMidCountJLabel = new JLabel(Integer.toString(midTankCount));
        tankMidCountJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel tankLargeCountJLabel = new JLabel(Integer.toString(largeTankCount));
        tankLargeCountJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel troopCountJLabel = new JLabel(Integer.toString(troopCount));
        troopCountJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tankSmallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setBattleUnitToAdd(1);

                tankSmallButton.setForeground(Color.BLACK);
                tankMidButton.setForeground(Color.BLACK);
                tankLargeButton.setForeground(Color.BLACK);
                troopButton.setForeground(Color.BLACK);

                tankSmallButton.setForeground(Color.GREEN);
            }
        });

        tankMidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setBattleUnitToAdd(2);

                tankSmallButton.setForeground(Color.BLACK);
                tankMidButton.setForeground(Color.BLACK);
                tankLargeButton.setForeground(Color.BLACK);
                troopButton.setForeground(Color.BLACK);

                tankMidButton.setForeground(Color.GREEN);
            }
        });


        tankLargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setBattleUnitToAdd(3);

                tankSmallButton.setForeground(Color.BLACK);
                tankMidButton.setForeground(Color.BLACK);
                tankLargeButton.setForeground(Color.BLACK);
                troopButton.setForeground(Color.BLACK);

                tankLargeButton.setForeground(Color.GREEN);
            }
        });


        troopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setBattleUnitToAdd(4);

                tankSmallButton.setForeground(Color.BLACK);
                tankMidButton.setForeground(Color.BLACK);
                tankLargeButton.setForeground(Color.BLACK);
                troopButton.setForeground(Color.BLACK);

                troopButton.setForeground(Color.GREEN);
            }
        });

        tankSmall.add(tankSmallButton);
        tankSmall.add(tankSmallCountJLabel);
        tankMid.add(tankMidButton);
        tankMid.add(tankMidCountJLabel);
        tankLarge.add(tankLargeButton);
        tankLarge.add(tankLargeCountJLabel);
        troop.add(troopButton);
        troop.add(troopCountJLabel);

        armySelectionPane.add(tankSmall);
        armySelectionPane.add(tankMid);
        armySelectionPane.add(tankLarge);
        armySelectionPane.add(troop);
        //
        buttonUpperPane.add(armySelectionPane);

        JPanel buttonLowerPane = new JPanel();
        buttonLowerPane.setLayout(new BoxLayout(buttonLowerPane, BoxLayout.LINE_AXIS));
        buttonLowerPane.add(Box.createHorizontalGlue());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                new GameSetUpViewA(displaySize, difficultyLevel);
            }
        });
        buttonLowerPane.add(backButton);
        buttonLowerPane.add(Box.createRigidArea(new Dimension(5, 0)));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameView(displaySize, difficultyLevel, game.awayUnits, game.homeUnits, game.roadBlocks);
                setVisible(false);
                dispose();
            }
        });
        buttonLowerPane.add(nextButton);

        buttonPane.add(buttonUpperPane);
        buttonPane.add(Box.createVerticalGlue());
        buttonPane.add(buttonLowerPane);

        setLayout(new BorderLayout());
        add(game, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }
}
