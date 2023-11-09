package view;

import controller.MinesKeeperController;
import model.MineKeeperCell;
import model.MineKeeperCellImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesKeeperDesktopView {
    private final MinesKeeperController controller;

    public MinesKeeperDesktopView(MinesKeeperController controller) {
        this.controller = controller;
    }

    public void execute() {
        controller.makeMineField();
        controller.setGameStatus('P');
        MineKeeperCell[][] mineField = controller.getMineField();

        int imageSize = 30;
        int rowsAmount = mineField.length;
        int columnsAmount = mineField[0].length;

        // Создаем frame.
        JFrame frame = new JFrame();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        frame.setLocation(
                screenWidth / 2 - columnsAmount * imageSize / 2,
                screenHeight / 2 - rowsAmount * imageSize / 2);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Sweeper");
        frame.setResizable(false);
        //frame.setVisible(true);

        // Header.
        JPanel headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(columnsAmount * imageSize, 30));
        JLabel display = new JLabel();
        headerPanel.add(display);
        display.setText(getHeaderMessage());

        // Загружаем enum с картинками.
        setImages();

        // Читал, что в invokeLater не нужно лишнее передавать?
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel mineFieldPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        for (int i = 0; i < rowsAmount; i++) {
                            for (int j = 0; j < columnsAmount; j++) {
                                String msg = "closed";

                                if (mineField[i][j].getStatus() == 'O') {
                                    if (mineField[i][j].getIsMine()) {
                                        msg = "exploded";
                                    } else {
                                        int minesAmount = mineField[i][j].getMinesAroundAmount();

                                        if (minesAmount == 0) {
                                            msg = "opened";
                                        } else {
                                            msg = "number" + minesAmount;
                                        }
                                    }
                                } else if (mineField[i][j].getStatus() == 'F') {
                                    msg = "marked";
                                }

                                // Сделать все мины видимыми для отладки.
                                // if (mineField[i][j].getIsMine()) msg = "exploded";
                                g.drawImage(getImages(msg), imageSize * j, imageSize * i, this);
                            }
                        }
                    }
                };

                mineFieldPanel.setPreferredSize(new Dimension(
                        imageSize * columnsAmount,
                        imageSize * rowsAmount));

                JPanel bottomPanel = new JPanel();
                // Кнопка новой игры. Пока не используется.
                JButton button = new JButton("Новая игра");
                bottomPanel.add(button);

                button.addActionListener(e -> {
                    controller.setGameStatus('P');
                    execute();
                });

                frame.add(headerPanel, BorderLayout.NORTH);
                frame.add(mineFieldPanel, BorderLayout.CENTER);
                frame.add(bottomPanel, BorderLayout.SOUTH);

                // Получаем размер внутренней панели и растягиваем frame ею.
                frame.pack();
                // Поставил включение видимости в конец, чтобы не видно было процессов применения размеров..
                // Чтобы все посчиталось, создалось и готовое отобразилось.
                frame.setVisible(true);

                mineFieldPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isGameOver()) {
                            return;
                        }

                        int column = e.getX() / imageSize;
                        int row = e.getY() / imageSize;
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            controller.makeAction(1, row, column);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            controller.makeAction(2, row, column);
                        }

                        // Перерисовываем поле paintComponent().
                        mineFieldPanel.repaint();
                        display.setText(getHeaderMessage());
                    }
                });
            }
        });
    }

    public boolean isGameOver() {
        return controller.getGameStatus() != 'P';
    }

    private String getHeaderMessage() {
        switch (controller.getGameStatus()) {
            case 'P' -> {
                return "Мин/флагов осталось: " + controller.getFlagsAmount();
            }
            case 'L' -> {
                return "Вы проиграли!";
            }
            case 'W' -> {
                return "Вы выиграли!";
            }
            default -> {
                return "Добро пожаловать.";
            }
        }
    }

    private void setImages() {
        for (MineKeeperCellImage cellImage : MineKeeperCellImage.values()) {
            cellImage.image = getImages(cellImage.name().toLowerCase());
        }
    }

    private Image getImages(String imageName) {
        String fileName = imageName + ".png";
        String imagesPath = System.getProperty("user.dir") + "/MinesKeeper/images/" + fileName;
        ImageIcon icon = new ImageIcon(imagesPath);

        return icon.getImage();
    }
}