package sweeper_view;

import sweeper_controller.SweeperController;
import sweeper_model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class SweeperDesktopView {
    private final SweeperController controller;
    private final SweeperCell[][] mineField;
    private final int imageSize = 30;
    private final int rowsAmount;
    private final int columnsAmount;
    private JFrame gameFrame;
    private final SweeperTimer gameTimer = new SweeperTimer();

    public SweeperDesktopView(SweeperController controller) {
        // Конструируем фрейм один раз, чтобы "Новая игра" повторно этого не делала.
        this.controller = controller;
        // Получаем минное поле без клеток, для размера фрейма.
        mineField = controller.getMineField();
        rowsAmount = mineField.length;
        columnsAmount = mineField[0].length;

        loadCellImages();
    }

    public void execute() {

        controller.fillingMineField();
        // Включаем режим первого беспроигрышного клика.
        controller.setGameStatus(SweeperGameStatus.FIRST_CLICK);

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                // Создаем Фрейм только один раз.
                if (gameFrame == null) {
                    generateFrame();
                }

                // headerPanel.
                JPanel headerPanel = new JPanel();
                headerPanel.setPreferredSize(new Dimension(columnsAmount * imageSize, 30));
                JLabel headerTextLabel = new JLabel();
                headerPanel.add(headerTextLabel);
                headerTextLabel.setText(getHeaderMessage());

                JPanel mineFieldPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        for (int i = 0; i < rowsAmount; i++) {
                            for (int j = 0; j < columnsAmount; j++) {
                                String cellDisplaying = "closed";

                                if (mineField[i][j].getStatus() == SweeperCellStatus.OPENED) {
                                    if (mineField[i][j].isMine()) {
                                        cellDisplaying = "exploded";
                                    } else {
                                        int minesAmount = mineField[i][j].getAdjacentMinesAmount();

                                        if (minesAmount == 0) {
                                            cellDisplaying = "opened";
                                        } else {
                                            cellDisplaying = "number" + minesAmount;
                                        }
                                    }
                                } else if (mineField[i][j].getStatus() == SweeperCellStatus.MARKED) {
                                    cellDisplaying = "marked";
                                }

                                // TODO: Сделать все мины видимыми для отладки (раскомментировать if).
                                // if (MINE_FIELD[i][j].isMine()) cellDisplaying = "exploded";
                                g.drawImage(SweeperCellImage.valueOf(cellDisplaying.toUpperCase()).getImage(), imageSize * j, imageSize * i, this);
                            }
                        }
                    }
                };

                mineFieldPanel.setPreferredSize(new Dimension(imageSize * columnsAmount, imageSize * rowsAmount));

                // bottomPanel.
                JPanel bottomPanel = new JPanel();
                JButton button = new JButton("Новая игра");
                JLabel gameTime = new JLabel("0");
                bottomPanel.add(gameTime);
                bottomPanel.add(button);

                button.addActionListener(e -> {
                    controller.setGameStatus(SweeperGameStatus.FIRST_CLICK);
                    gameTimer.stop();
                    execute();
                });

                gameFrame.add(headerPanel, BorderLayout.NORTH);
                gameFrame.add(mineFieldPanel, BorderLayout.CENTER);
                gameFrame.add(bottomPanel, BorderLayout.SOUTH);

                // Получаем размер внутренней панели и растягиваем frame ею.
                gameFrame.pack();
                // Поставил включение видимости в конец, чтобы не видно было процессов применения размеров.
                // Чтобы все посчиталось, создалось и готовое отобразилось.
                gameFrame.setVisible(true);

                // Запускаем таймер после полной отрисовки игры.
                gameTimer.start(gameTime, 0);

                mineFieldPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isGameOver()) {
                            return;
                        }

                        int column = e.getX() / imageSize;
                        int row = e.getY() / imageSize;

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            controller.processUserAction(1, row, column);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            controller.processUserAction(2, row, column);
                        }

                        // Перерисовываем поле paintComponent().
                        mineFieldPanel.repaint();
                        headerTextLabel.setText(getHeaderMessage());
                    }
                });
            }

            public void generateFrame() {
                gameFrame = new JFrame();
                gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameFrame.setTitle("Сапёр");
                gameFrame.setResizable(false);

                // Первичное позиционирование игрового фрейма по центру экрана.
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                gameFrame.setLocation(screenSize.width / 2 - imageSize * columnsAmount / 2,
                        screenSize.height / 2 - imageSize * rowsAmount / 2);

                // TODO: Можно потом сюда перенести header.
            }
        });
    }

    public boolean isGameOver() {
        return controller.getGameStatus() != SweeperGameStatus.PLAY
                && controller.getGameStatus() != SweeperGameStatus.FIRST_CLICK;
    }

    private String getHeaderMessage() {
        return switch (controller.getGameStatus()) {
            case PLAY -> "Мин/флагов осталось: " + controller.getFlagsAmount();
            case LOOSE -> "Вы проиграли!";
            case WIN -> "Вы выиграли!";
            default -> "Добро пожаловать. " + gameTimer.getYourTime();
        };
    }

    private Image getCellImage(String imageName) {
        String fileName = imageName + ".png";
        String imagesPath = System.getProperty("user.dir") + "/Sweeper/images/" + fileName;
        ImageIcon icon = new ImageIcon(imagesPath);

        return icon.getImage();
    }

    private void loadCellImages() {
        for (SweeperCellImage cellImage : SweeperCellImage.values()) {
            cellImage.setImage(getCellImage(cellImage.name().toLowerCase()));
        }
    }
}