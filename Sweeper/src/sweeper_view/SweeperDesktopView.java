package sweeper_view;

import sweeper_controller.SweeperController;
import sweeper_model.SweeperCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SweeperDesktopView {
    private final SweeperController controller;
    private final SweeperCell[][] MINE_FIELD;
    private final int IMAGE_SIZE = 30;
    private final int ROWS_AMOUNT;
    private final int COLUMNS_AMOUNT;
    private final JFrame GAME_FRAME;

    public SweeperDesktopView(SweeperController controller) {
        // Конструируем фрейм один раз, чтобы "Новая игра" повторно этого не делала.
        this.controller = controller;
        // Получаем минное поле без клеток, для размера фрейма.
        MINE_FIELD = controller.getMineField();
        ROWS_AMOUNT = MINE_FIELD.length;
        COLUMNS_AMOUNT = MINE_FIELD[0].length;

        GAME_FRAME = new JFrame();
        GAME_FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GAME_FRAME.setTitle("Сапёр");
        GAME_FRAME.setResizable(false);

        // Первичное позиционирование игрового фрейма по центру экрана.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GAME_FRAME.setLocation(screenSize.width / 2 - IMAGE_SIZE * COLUMNS_AMOUNT / 2,
                screenSize.height / 2 - IMAGE_SIZE * ROWS_AMOUNT / 2);
    }

    public void execute() {
        controller.fillingMineField();
        controller.setGameStatus('P');

        // headerPanel.
        JPanel headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(COLUMNS_AMOUNT * IMAGE_SIZE, 30));
        JLabel headerTextLabel = new JLabel();
        headerPanel.add(headerTextLabel);
        headerTextLabel.setText(getHeaderMessage());

        // Читал, что в invokeLater не нужно лишнее передавать.
        // Поэтому headerPanel и bottomPanel за его пределами.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel mineFieldPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        for (int i = 0; i < ROWS_AMOUNT; i++) {
                            for (int j = 0; j < COLUMNS_AMOUNT; j++) {
                                String cellDisplaying = "closed";

                                if (MINE_FIELD[i][j].getStatus() == 'O') {
                                    if (MINE_FIELD[i][j].isMine()) {
                                        cellDisplaying = "exploded";
                                    } else {
                                        int minesAmount = MINE_FIELD[i][j].getAdjacentMinesAmount();

                                        if (minesAmount == 0) {
                                            cellDisplaying = "opened";
                                        } else {
                                            cellDisplaying = "number" + minesAmount;
                                        }
                                    }
                                } else if (MINE_FIELD[i][j].getStatus() == 'F') {
                                    cellDisplaying = "marked";
                                }

                                // TODO: Сделать все мины видимыми для отладки (раскомментировать if).
                                // if (MINE_FIELD[i][j].getIsMine()) cellDisplaying = "exploded";
                                g.drawImage(getCellImage(cellDisplaying), IMAGE_SIZE * j, IMAGE_SIZE * i, this);
                            }
                        }
                    }
                };

                mineFieldPanel.setPreferredSize(new Dimension(IMAGE_SIZE * COLUMNS_AMOUNT, IMAGE_SIZE * ROWS_AMOUNT));

                // bottomPanel.
                JPanel bottomPanel = new JPanel();
                JButton button = new JButton("Новая игра");
                bottomPanel.add(button);

                button.addActionListener(e -> {
                    controller.setGameStatus('P');
                    execute();
                });

                GAME_FRAME.add(headerPanel, BorderLayout.NORTH);
                GAME_FRAME.add(mineFieldPanel, BorderLayout.CENTER);
                GAME_FRAME.add(bottomPanel, BorderLayout.SOUTH);

                // Получаем размер внутренней панели и растягиваем frame ею.
                GAME_FRAME.pack();
                // Поставил включение видимости в конец, чтобы не видно было процессов применения размеров.
                // Чтобы все посчиталось, создалось и готовое отобразилось.
                GAME_FRAME.setVisible(true);

                mineFieldPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isGameOver()) {
                            return;
                        }

                        int column = e.getX() / IMAGE_SIZE;
                        int row = e.getY() / IMAGE_SIZE;

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
        });
    }

    public boolean isGameOver() {
        return controller.getGameStatus() != 'P';
    }

    private String getHeaderMessage() {
        return switch (controller.getGameStatus()) {
            case 'P' -> "Мин/флагов осталось: " + controller.getFlagsAmount();
            case 'L' -> "Вы проиграли!";
            case 'W' -> "Вы выиграли!";
            default -> "Добро пожаловать.";
        };
    }

    private Image getCellImage(String imageName) {
        String fileName = imageName + ".png";
        String imagesPath = System.getProperty("user.dir") + "/Sweeper/images/" + fileName;
        ImageIcon icon = new ImageIcon(imagesPath);

        return icon.getImage();
    }
}