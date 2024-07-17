package ui.view;

import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class PreGameSetUpView extends JFrame implements ActionListener {
    private static final String settingsDataPath = "./data/settings.json";
    private final JsonWriter jsonWriter = new JsonWriter(settingsDataPath);
    private final JsonReader jsonReader = new JsonReader(settingsDataPath);

    private JLabel displaySizeLabel;
    private JSlider displaySizeSlider;
    private JLabel difficultyLevelLabel;
    private JSlider difficultyLevelSlider;
    private JButton submitButton;

    public PreGameSetUpView() {
        super("UserSetUp");

        initComponents();

        setSize(250, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width / 2 - this.getWidth() / 2;
        int y = screenSize.height / 2 - this.getHeight() / 2;
        this.setLocation(x, y);

        setVisible(true);

    }

    @SuppressWarnings("methodlength")
    private void initComponents() {
        JSONObject settingsData = null;
        try {
            settingsData = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displaySizeLabel = new JLabel("DisplaySize");
        displaySizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        displaySizeLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 10));

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        JPanel mainSubPaneA = new JPanel();
        mainSubPaneA.setLayout(new BoxLayout(mainSubPaneA, BoxLayout.PAGE_AXIS));
        JPanel mainSubPaneB = new JPanel();
        mainSubPaneB.setLayout(new BoxLayout(mainSubPaneB, BoxLayout.PAGE_AXIS));

        displaySizeSlider = new JSlider(1, 3, settingsData.getInt("displaySize"));
        displaySizeSlider.setPaintTrack(true);
        displaySizeSlider.setPaintTicks(true);
        displaySizeSlider.setMinorTickSpacing(1);
        displaySizeSlider.setSnapToTicks(true);

        mainSubPaneA.add(displaySizeSlider);
        mainSubPaneA.setBorder(BorderFactory.createTitledBorder("DisplaySize"));

        Hashtable<Integer, JLabel> displaySizeSliderLabelTable =
                new Hashtable<Integer, JLabel>();
        displaySizeSliderLabelTable.put(new Integer(1),
                new JLabel("Small"));
        displaySizeSliderLabelTable.put(new Integer(2),
                new JLabel("Medium"));
        displaySizeSliderLabelTable.put(new Integer(3),
                new JLabel("Large"));
        displaySizeSlider.setLabelTable(displaySizeSliderLabelTable);
        displaySizeSlider.setPaintLabels(true);

        difficultyLevelLabel = new JLabel("Difficulty");
        difficultyLevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        difficultyLevelSlider = new JSlider(1, 3, settingsData.getInt("difficultyLevel"));
        difficultyLevelSlider.setPaintTrack(true);
        difficultyLevelSlider.setPaintTicks(true);
        difficultyLevelSlider.setMinorTickSpacing(1);
        difficultyLevelSlider.setSnapToTicks(true);

        Hashtable<Integer, JLabel> difficultyLevelLabelTable =
                new Hashtable<Integer, JLabel>();
        difficultyLevelLabelTable.put(new Integer(1),
                new JLabel("Easy"));
        difficultyLevelLabelTable.put(new Integer(2),
                new JLabel("Normal"));
        difficultyLevelLabelTable.put(new Integer(3),
                new JLabel("Hard"));
        difficultyLevelSlider.setLabelTable(difficultyLevelLabelTable);
        difficultyLevelSlider.setPaintLabels(true);

        mainSubPaneB.add(difficultyLevelSlider);
        mainSubPaneB.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        mainSubPaneB.setBorder(BorderFactory.createTitledBorder("Difficulty"));

        mainPane.add(mainSubPaneA);
        mainPane.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPane.add(mainSubPaneB);
        mainPane.setBorder(BorderFactory.createEmptyBorder(25, 20, 20, 20));

        submitButton = new JButton("Finish Set Up");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BorderLayout());

        add(mainPane, BorderLayout.CENTER);
        add(submitButton, BorderLayout.PAGE_END);


        submitButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        int displaySize = displaySizeSlider.getValue();
        int difficultyLevel = difficultyLevelSlider.getValue();

        JSONObject settingsJsonObj = new JSONObject();

        settingsJsonObj.put("displaySize", displaySize);
        settingsJsonObj.put("difficultyLevel", difficultyLevel);

        try {
            jsonWriter.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        jsonWriter.write(settingsJsonObj);
        jsonWriter.close();

//        JOptionPane.showMessageDialog(this, "Done");
        setVisible(false);
        dispose();
        new GameSetUpViewA(displaySize, difficultyLevel);
    }
}
