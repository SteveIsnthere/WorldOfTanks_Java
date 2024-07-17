package ui.view;

import model.Tank;
import model.Troop;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class GameSetUpViewA extends BasicFixedSizeView {
    private static final String gameSetupDataPath = "./data/gameSetupData.json";
    private static final double smallTankSize = new Tank(1, 0, 0, 1).getSizeFloor();
    private static final double largeTankSize = new Tank(1, 0, 0, 1).getSizeCeiling();
    private static final double midTankSize = (smallTankSize + largeTankSize) / 2;
    private final JsonWriter jsonWriter = new JsonWriter(gameSetupDataPath);
    private final JsonReader jsonReader = new JsonReader(gameSetupDataPath);
    int displaySize;
    int difficultyLevel;
    int budgetSliderVal;
    private int smallTankCount;
    private int midTankCount;
    private int largeTankCount;
    private int troopCount;
    private double budget;
    private JSONObject gameSetupData;
    private int battleUnitToAdd;
    private double costPerUnit;
    private JLabel budgetLabel;
    private JSlider budgetSlider;


    public GameSetUpViewA(int displaySize, int difficultyLevel) {
        super(displaySize, difficultyLevel, 0, "GameSetUpView");
        this.displaySize = displaySize;
        this.difficultyLevel = difficultyLevel;

        initComponents();

        displaySizeSetup(displaySize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private static double round(double val) {
        int i = (int) Math.pow(10, 1);
        return (double) Math.round(val * i) / i;
    }

    public void randomAddUnits() {
        double tankLCost = new Tank(1, 0, 0, largeTankSize).getCost();
        int tankLCount = (int) Math.floor(budget / tankLCost * Math.random());
        largeTankCount += tankLCount;
        budget -= tankLCost * tankLCount;

        double tankMCost = new Tank(1, 0, 0, midTankSize).getCost();
        int tankMCount = (int) Math.floor(budget / tankMCost * Math.random());
        midTankCount += tankMCount;
        budget -= tankMCost * tankMCount;

        double tankSCost = new Tank(1, 0, 0, smallTankSize).getCost();
        int tankSCount = (int) Math.floor(budget / tankSCost * Math.random());
        smallTankCount += tankSCount;
        budget -= tankSCost * tankSCount;

        double troopCost = new Troop(1, 0, 0).getCost();
        int troopNum = (int) Math.floor(budget / troopCost);
        troopCount += troopNum;
        budget -= troopCost * troopNum;
    }

    private void displaySizeSetup(int displaySize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double aspectRatio = 0.75;
        int shorterScreenLength;

        if (screenSize.width > screenSize.height) {
            shorterScreenLength = screenSize.height;
        } else {
            shorterScreenLength = screenSize.width;
        }
        double screenSizeRatio;
        if (displaySize == 1) {
            screenSizeRatio = 0.45;
        } else if (displaySize == 2) {
            screenSizeRatio = 0.7;
        } else {
            screenSizeRatio = 0.9;
        }
        setSize((int) (shorterScreenLength * screenSizeRatio * aspectRatio), (int) (shorterScreenLength
                * screenSizeRatio));

        int x = screenSize.width / 2 - this.getWidth() / 2;
        int y = screenSize.height / 2 - this.getHeight() / 2;
        this.setLocation(x, y);
        setResizable(false);
    }

    @SuppressWarnings("methodlength")
    private void initComponents() {
        JSONObject gameSetupData = null;
        try {
            gameSetupData = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        budgetSliderVal = gameSetupData.getInt("budget");
        if (budgetSliderVal == 1) {
            budget = 50;
        } else if (budgetSliderVal == 2) {
            budget = 100;
        } else if (budgetSliderVal == 3) {
            budget = 200;
        } else if (budgetSliderVal == 4) {
            budget = 500;
        } else {
            budget = 1000;
        }
        smallTankCount = gameSetupData.getInt("smallTankCount");
        midTankCount = gameSetupData.getInt("midTankCount");
        largeTankCount = gameSetupData.getInt("largeTankCount");
        troopCount = gameSetupData.getInt("troopCount");

        budget -= new Tank(1, 0, 0, smallTankSize).getCost() * smallTankCount;
        budget -= new Tank(1, 0, 0, midTankSize).getCost() * midTankCount;
        budget -= new Tank(1, 0, 0, largeTankSize).getCost() * largeTankCount;
        budget -= new Troop(1, 0, 0).getCost() * troopCount;

        //budgetPane
        JPanel budgetPane = new JPanel();
        budgetPane.setLayout(new BoxLayout(budgetPane, BoxLayout.PAGE_AXIS));

        JPanel topBudgetPane = new JPanel();
        topBudgetPane.setLayout(new BoxLayout(topBudgetPane, BoxLayout.LINE_AXIS));
        budgetLabel = new JLabel("Budget:");
        budgetLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        topBudgetPane.add(budgetLabel);
        topBudgetPane.add(Box.createHorizontalGlue());

        budgetSlider = new JSlider(1, 5, budgetSliderVal);
        budgetSlider.setPaintTrack(true);
        budgetSlider.setPaintTicks(true);
        budgetSlider.setMinorTickSpacing(1);
        budgetSlider.setSnapToTicks(true);

        Hashtable<Integer, JLabel> displaySizeSliderLabelTable = new Hashtable<Integer, JLabel>();
        displaySizeSliderLabelTable.put(new Integer(1), new JLabel("50M"));
        displaySizeSliderLabelTable.put(new Integer(2), new JLabel("100M"));
        displaySizeSliderLabelTable.put(new Integer(3), new JLabel("200M"));
        displaySizeSliderLabelTable.put(new Integer(4), new JLabel("500M"));
        displaySizeSliderLabelTable.put(new Integer(5), new JLabel("1000M"));
        budgetSlider.setLabelTable(displaySizeSliderLabelTable);
        budgetSlider.setPaintLabels(true);

        JPanel budgetInnerPane = new JPanel();
        budgetInnerPane.setLayout(new BoxLayout(budgetInnerPane, BoxLayout.PAGE_AXIS));

        budgetInnerPane.add(budgetSlider);
        budgetInnerPane.setBorder(BorderFactory.createTitledBorder("Budget"));
        budgetInnerPane.setMaximumSize(new Dimension(300, 150));

        budgetPane.add(budgetInnerPane);
        budgetPane.setBorder(BorderFactory.createEmptyBorder(25, 20, 20, 20));

        //armyPane
        JPanel armyPane = new JPanel();
        armyPane.setLayout(new BoxLayout(armyPane, BoxLayout.PAGE_AXIS));

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
                battleUnitToAdd = 1;
                costPerUnit = new Tank(1, 0, 0, smallTankSize).getCost();

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
                battleUnitToAdd = 2;
                costPerUnit = new Tank(1, 0, 0, midTankSize).getCost();

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
                battleUnitToAdd = 3;
                costPerUnit = new Tank(1, 0, 0, largeTankSize).getCost();

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
                battleUnitToAdd = 4;
                costPerUnit = new Troop(1, 0, 0).getCost();

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

        JPanel armyAddPane = new JPanel();
        armyAddPane.setLayout(new BoxLayout(armyAddPane, BoxLayout.PAGE_AXIS));

        JLabel remainingBudget = new JLabel("Remaining: " + round(budget) + "M");
        remainingBudget.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton add1UnitBtn = new JButton("Add 1");
        add1UnitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add1UnitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberAdded = 1;

                double newCost = costPerUnit * numberAdded;

                if (newCost > budget) {
                    JOptionPane.showMessageDialog(null, "Insufficient budget");
                    return;
                }

                budget -= newCost;
                remainingBudget.setText("Remaining: " + round(budget) + "M");

                if (battleUnitToAdd == 1) {
                    smallTankCount += numberAdded;
                } else if (battleUnitToAdd == 2) {
                    midTankCount += numberAdded;
                } else if (battleUnitToAdd == 3) {
                    largeTankCount += numberAdded;
                } else if (battleUnitToAdd == 4) {
                    troopCount += numberAdded;
                }
                tankSmallCountJLabel.setText(Integer.toString(smallTankCount));
                tankMidCountJLabel.setText(Integer.toString(midTankCount));
                tankLargeCountJLabel.setText(Integer.toString(largeTankCount));
                troopCountJLabel.setText(Integer.toString(troopCount));
            }
        });

        JButton add10UnitsBtn = new JButton("Add 10");
        add10UnitsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add10UnitsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberAdded = 10;

                double newCost = costPerUnit * numberAdded;

                if (newCost > budget) {
                    JOptionPane.showMessageDialog(null, "Insufficient budget");
                    return;
                }

                budget -= newCost;
                remainingBudget.setText("Remaining: " + round(budget) + "M");

                if (battleUnitToAdd == 1) {
                    smallTankCount += numberAdded;
                } else if (battleUnitToAdd == 2) {
                    midTankCount += numberAdded;
                } else if (battleUnitToAdd == 3) {
                    largeTankCount += numberAdded;
                } else if (battleUnitToAdd == 4) {
                    troopCount += numberAdded;
                }
                tankSmallCountJLabel.setText(Integer.toString(smallTankCount));
                tankMidCountJLabel.setText(Integer.toString(midTankCount));
                tankLargeCountJLabel.setText(Integer.toString(largeTankCount));
                troopCountJLabel.setText(Integer.toString(troopCount));
            }
        });

        JButton randomBtn = new JButton("Feeling Lucky");
        randomBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        randomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomAddUnits();
                tankSmallCountJLabel.setText(Integer.toString(smallTankCount));
                tankMidCountJLabel.setText(Integer.toString(midTankCount));
                tankLargeCountJLabel.setText(Integer.toString(largeTankCount));
                troopCountJLabel.setText(Integer.toString(troopCount));
                remainingBudget.setText("Remaining: " + round(budget) + "M");
            }
        });

        JButton restoreBtn = new JButton("Restore Default");
        restoreBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restoreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (budgetSliderVal == 1) {
                    budget = 50;
                } else if (budgetSliderVal == 2) {
                    budget = 100;
                } else if (budgetSliderVal == 3) {
                    budget = 200;
                } else if (budgetSliderVal == 4) {
                    budget = 500;
                } else {
                    budget = 1000;
                }

                remainingBudget.setText("Remaining: " + round(budget) + "M");

                smallTankCount = 0;
                midTankCount = 0;
                largeTankCount = 0;
                troopCount = 0;
                tankSmallCountJLabel.setText(Integer.toString(smallTankCount));
                tankMidCountJLabel.setText(Integer.toString(midTankCount));
                tankLargeCountJLabel.setText(Integer.toString(largeTankCount));
                troopCountJLabel.setText(Integer.toString(troopCount));
            }
        });

        budgetSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newBudget;
                budgetSliderVal = budgetSlider.getValue();
                if (budgetSliderVal == 1) {
                    newBudget = 50;
                } else if (budgetSliderVal == 2) {
                    newBudget = 100;
                } else if (budgetSliderVal == 3) {
                    newBudget = 200;
                } else if (budgetSliderVal == 4) {
                    newBudget = 500;
                } else {
                    newBudget = 1000;
                }
                if (newBudget != budget) {
                    budget = newBudget;
                    smallTankCount = 0;
                    midTankCount = 0;
                    largeTankCount = 0;
                    troopCount = 0;

                    tankSmallCountJLabel.setText(Integer.toString(smallTankCount));
                    tankMidCountJLabel.setText(Integer.toString(midTankCount));
                    tankLargeCountJLabel.setText(Integer.toString(largeTankCount));
                    troopCountJLabel.setText(Integer.toString(troopCount));
                    remainingBudget.setText("Remaining: " + round(budget) + "M");
                }
            }
        });
        armyAddPane.add(Box.createRigidArea(new Dimension(10, 25)));
        armyAddPane.add(remainingBudget);
        armyAddPane.add(Box.createRigidArea(new Dimension(10, 20)));
        armyAddPane.add(add1UnitBtn);
        armyAddPane.add(add10UnitsBtn);
        armyAddPane.add(randomBtn);
        armyAddPane.add(Box.createRigidArea(new Dimension(10, 20)));
        armyAddPane.add(restoreBtn);
        armyAddPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Your Army");
        armyPane.add(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        armyPane.add(Box.createRigidArea(new Dimension(10, 20)));
        armyPane.add(armySelectionPane);
        armyPane.add(armyAddPane);
        armyPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

//      mapPanel.setBackground(Color.red);

        //buttonPane
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalGlue());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                new PreGameSetUpView();
            }
        });
        buttonPane.add(backButton);
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject newGameSetupData = new JSONObject();
                newGameSetupData.put("smallTankCount", smallTankCount);
                newGameSetupData.put("midTankCount", midTankCount);
                newGameSetupData.put("largeTankCount", largeTankCount);
                newGameSetupData.put("troopCount", troopCount);
                newGameSetupData.put("budget", budgetSlider.getValue());

                try {
                    jsonWriter.open();
                } catch (FileNotFoundException ee) {
                    ee.printStackTrace();
                }

                jsonWriter.write(newGameSetupData);
                jsonWriter.close();

                setVisible(false);
                dispose();
                new GameSetUpViewB(displaySize, difficultyLevel);
            }
        });
        buttonPane.add(nextButton);

        setLayout(new BorderLayout());

        add(budgetPane, BorderLayout.PAGE_START);
        add(armyPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }
}
