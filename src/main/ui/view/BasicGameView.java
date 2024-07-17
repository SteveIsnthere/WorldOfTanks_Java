package ui.view;

import model.BattleUnit;
import model.Projectile;
import model.RoadBlock;
import model.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static java.awt.Color.BLACK;

public class BasicGameView extends JPanel implements MouseMotionListener, MouseListener {
    protected int dispWidth;
    protected int dispHeight;
    protected double battleFieldWidth = new Tank(1, 0, 0, 1).getBattleFieldWidth();
    protected double battleFieldHeight = new Tank(1, 0, 0, 1).getBattleFieldHeight();

    protected int frameRendered = 0;
    protected int updatePerSecond = 100;
    protected int difficultyLevel;

    protected int mouseX;
    protected int mouseY;

    protected ArrayList<BattleUnit> awayUnits;
    protected ArrayList<BattleUnit> homeUnits;
    protected ArrayList<RoadBlock> roadBlocks;
    protected ArrayList<Projectile> projectiles = new ArrayList<>();

    protected Color roadBlockColor = new Color(150, 150, 150);
    protected Color homeColor = new Color(14, 170, 255, 255);
    protected Color awayColor = new Color(231, 19, 47, 255);
    protected Color tankColor = new Color(84, 84, 84, 255);
    protected Color barrelColor = new Color(121, 102, 40, 255);
    protected Color backGroundColor = new Color(176, 176, 176, 255);

    protected double tankGunBarrelSizeRatio = 1.8;
    protected double tankGunBarrelAspectRatio = 0.21;

    public BasicGameView(int difficultyLevel, ArrayList<BattleUnit> awayUnits,
                         ArrayList<BattleUnit> homeUnits, ArrayList<RoadBlock> roadBlocks) {
        addMouseMotionListener(this);
        addMouseListener(this);
        this.awayUnits = awayUnits;
        this.homeUnits = homeUnits;
        this.roadBlocks = roadBlocks;
        this.difficultyLevel = difficultyLevel;
    }


    public void sleep(int timeInMs) {
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public int translateToPixel(double inMeter) {
        return (int) (dispWidth / battleFieldWidth * inMeter);
    }

    public int translateToMeter(double pix) {
        return (int) (battleFieldWidth / dispWidth * pix);
    }

    @SuppressWarnings("methodlength")
    public void doDrawing(Graphics g) {
        frameRendered++;
        dispHeight = getSize().height;
        dispWidth = getSize().width;
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        //BackGround
        g2d.setPaint(backGroundColor);
        g2d.fillRect(0, 0,
                translateToPixel(battleFieldWidth), translateToPixel(battleFieldHeight));

        //roadBlocks
        g2d.setPaint(roadBlockColor);
        for (RoadBlock rb : roadBlocks) {
            if (rb.isDestroyed()) {
                continue;
            }
            double posX = rb.getPositionX();
            double posY = rb.getPositionY();
            double size = rb.getSize();
            g2d.fillRect(translateToPixel(posX - size), translateToPixel(posY - size),
                    translateToPixel(size * 2), translateToPixel(size * 2));
        }

        //homeUnits
        for (BattleUnit unit : homeUnits) {
            if (unit.isDestroyed()) {
                continue;
            }
            double posX = unit.getPositionX();
            double posY = unit.getPositionY();
            double size = unit.getSize();

            double tankGunBarrelLength = size * tankGunBarrelSizeRatio;
            double tankGunBarrelWidth = tankGunBarrelLength * tankGunBarrelAspectRatio;
            if (size < 3) {
                size = 2;
                g2d.setPaint(homeColor);
                g2d.fillRect(translateToPixel(posX - size), translateToPixel(posY - size),
                        translateToPixel(size * 2), translateToPixel(size * 2));
            } else {
                g2d.setPaint(tankColor);
                g2d.fillArc(translateToPixel(posX - size),
                        translateToPixel(posY - size),
                        translateToPixel(size * 2),
                        translateToPixel(size * 2),
                        0,
                        360);

                g2d.setPaint(barrelColor);
                AffineTransform old = g2d.getTransform();
                g2d.rotate(unit.getFacingDirection(), translateToPixel(posX), translateToPixel(posY));
                g2d.fillRect(translateToPixel(posX - tankGunBarrelWidth / 2), translateToPixel(posY),
                        translateToPixel(tankGunBarrelWidth), translateToPixel(tankGunBarrelLength));
                g2d.setTransform(old);

                g2d.setPaint(homeColor);
                g2d.fillArc(translateToPixel(posX - size / 2),
                        translateToPixel(posY - size / 2),
                        translateToPixel(size),
                        translateToPixel(size),
                        0,
                        360);
            }
        }

        //awayUnits
        for (BattleUnit unit : awayUnits) {
            if (unit.isDestroyed()) {
                continue;
            }
            double posX = unit.getPositionX();
            double posY = unit.getPositionY();
            double size = unit.getSize();

            double tankGunBarrelLength = size * tankGunBarrelSizeRatio;
            double tankGunBarrelWidth = tankGunBarrelLength * tankGunBarrelAspectRatio;
            if (size < 3) {
                size = 2;
                g2d.setPaint(awayColor);
                g2d.fillRect(translateToPixel(posX - size), translateToPixel(posY - size),
                        translateToPixel(size * 2), translateToPixel(size * 2));
            } else {
                g2d.setPaint(tankColor);
                g2d.fillArc(translateToPixel(posX - size),
                        translateToPixel(posY - size),
                        translateToPixel(size * 2),
                        translateToPixel(size * 2),
                        0,
                        360);

                g2d.setPaint(barrelColor);
                AffineTransform old = g2d.getTransform();
                g2d.rotate(unit.getFacingDirection(), translateToPixel(posX), translateToPixel(posY));
                g2d.fillRect(translateToPixel(posX - tankGunBarrelWidth / 2), translateToPixel(posY),
                        translateToPixel(tankGunBarrelWidth), translateToPixel(tankGunBarrelLength));
                g2d.setTransform(old);

                g2d.setPaint(awayColor);
                g2d.fillArc(translateToPixel(posX - size / 2),
                        translateToPixel(posY - size / 2),
                        translateToPixel(size),
                        translateToPixel(size),
                        0,
                        360);
            }
        }

        //projectile
        g2d.setPaint(BLACK);
        for (Projectile unit : projectiles) {
            if (unit.isDestroyed()) {
                continue;
            }
            double posX = unit.getPositionX();
            double posY = unit.getPositionY();
            double size = 2;
            g2d.fillRect(translateToPixel(posX - size), translateToPixel(posY - size),
                    translateToPixel(size * 2), translateToPixel(size * 2));
        }
    }

    public void updateState() {
        return;
    }

    public int dt() {
        return 1000 / updatePerSecond;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateState();
        doDrawing(g);
        sleep(dt());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = translateToMeter(e.getX());
        mouseY = translateToMeter(e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
