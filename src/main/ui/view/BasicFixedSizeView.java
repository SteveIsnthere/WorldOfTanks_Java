package ui.view;

import model.Tank;

import javax.swing.*;
import java.awt.*;

public class BasicFixedSizeView extends JFrame {
    int difficultyLevel;
    double battleFieldWidth = new Tank(1, 0, 0, 1).getBattleFieldWidth();
    double battleFieldHeight = new Tank(1, 0, 0, 1).getBattleFieldHeight();
    int displayWidth;
    int displayHeight;

    public BasicFixedSizeView(int displaySize, int difficultyLevel, int fixedHeight, String name) {
        super(name);
        displaySetup(displaySize, fixedHeight);
        this.difficultyLevel = difficultyLevel;
    }

    public void displaySetup(int displaySize, int fixedHeight) {
        double aspectRatio = battleFieldWidth / battleFieldHeight;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        double screenSizeRatio;
        if (displaySize == 1) {
            screenSizeRatio = 0.5;
        } else if (displaySize == 2) {
            screenSizeRatio = 0.7;
        } else {
            screenSizeRatio = 0.9;
        }

        displayHeight = (int) (screenHeight * screenSizeRatio);
        displayWidth = (int) ((displayHeight - fixedHeight) * aspectRatio);

        setSize(displayWidth, displayHeight);

        int x = screenWidth / 2 - this.getWidth() / 2;
        int y = screenHeight / 2 - this.getHeight() / 2;
        this.setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
}
