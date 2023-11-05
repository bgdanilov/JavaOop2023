package view;

import controller.MinesKeeperController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MinesKeeperDesktopView {
    private final MinesKeeperController controller;
    private final int COLUMNS = 15;
    private final int ROWS = 1;
    private final int IMAGE_SIZE = 50;

    public MinesKeeperDesktopView(MinesKeeperController controller) {
        this.controller = controller;
    }

    public void execute() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("MinesKeeper");
                frame.pack();
                //frame.setSize(COLUMNS * IMAGE_SIZE, ROWS * IMAGE_SIZE);
                frame.setSize(500, 500);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                final BufferedImage bomb;

                try {
                    bomb = ImageIO.read(new File("res/img/bomb.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                JPanel panel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(bomb, 0, 0, null);
                    }
                };

                panel.setPreferredSize(new Dimension(COLUMNS * IMAGE_SIZE, ROWS * IMAGE_SIZE));

                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        int x = e.getX() / IMAGE_SIZE;
                        int y = e.getY() / IMAGE_SIZE;
                        //Coord coord = new Coord (x, y);
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            // game.pressLeftButton (coord); //
                            panel.repaint(); // Перерисовка панели.
                        }
                    }
                });

                frame.add(panel);
            }
        });
    }

//    public Image getImage(String imageName) {
//        String fileName = "img/" + imageName.toLowerCase() + ".png";
//        ImageIcon icon = new ImageIcon(getClass().getResource(imageName));
//
//        return icon.getImage();
//    }
}
