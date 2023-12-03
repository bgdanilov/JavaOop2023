package ru.bgdanilov.minesweeper.view;

import ru.bgdanilov.minesweeper.controller.Controller;
import ru.bgdanilov.minesweeper.model.*;
import ru.bgdanilov.minesweeper.model.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DesktopGame {
    private final Controller controller;
    private final Timer gameTimer;
    private DifficultyLevel difficultyLevel;
    private Cell[][] mineField;
    private final int imageSize = 30;
    private int rowsAmount;
    private int columnsAmount;
    private JFrame gameFrame; // Это тут нужно.


    public DesktopGame(Controller controller) {
        // Конструируем фрейм один раз, чтобы "Новая игра" повторно этого не делала.
        this.controller = controller;
        gameTimer = new Timer();
        difficultyLevel = DifficultyLevel.EASY;
        // Загружаем картинки.
        loadCellImages();
        // Загружаем рекорды из файла в объект Enum.
        DifficultyLevel.readFileToEnum();
    }

    public void startGame() {
        // Получаем минное поле.
        // А оно создается своим конструктором в зависимости от уровня сложности.
        mineField = controller.getMineField();
        // Записываем в поля этого объекта игры размеры минного поля.
        rowsAmount = mineField.length;
        columnsAmount = mineField[0].length;
        // Заполняем минное поле минами.
        controller.fillingMineField();
        // Включаем режим первого беспроигрышного клика.
        controller.setGameStatus(GameStatus.FIRST_CLICK);

        // Создаем Фрейм только один раз. Далее только обновляем.
        if (gameFrame == null) {
            generateFrame();
        } else {
            gameFrame.getContentPane().removeAll();
            // TODO: Разобраться что это такое. Почему просто gameFrame.removeAll(); не работает.
            gameFrame.setSize(imageSize * columnsAmount, imageSize * rowsAmount + 100);
        }

        gameFrame.setLocationRelativeTo(null);

        // headerPanel.
        JPanel headerPanel = new JPanel();
        headerPanel.setSize(imageSize * columnsAmount, 80);
        JLabel headerTextLabel = new JLabel();
        headerPanel.add(headerTextLabel);
        headerTextLabel.setText(generateHeaderMessage());

        // mineFieldPanel.
        JPanel mineFieldPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < rowsAmount; i++) {
                    for (int j = 0; j < columnsAmount; j++) {
                        String cellDisplaying = "closed";

                        if (mineField[i][j].getStatus() == CellStatus.OPENED) {
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
                        } else if (mineField[i][j].getStatus() == CellStatus.MARKED) {
                            cellDisplaying = "marked";
                        }

                        // TODO: Сделать все мины видимыми для отладки (раскомментировать if).
                        // if (mineField[i][j].isMine()) cellDisplaying = "exploded";
                        g.drawImage(CellImage.valueOf(cellDisplaying.toUpperCase()).getCellImage(), imageSize * j, imageSize * i, this);
                    }
                }
            }
        };

        mineFieldPanel.setSize(imageSize * columnsAmount, imageSize * rowsAmount);

        // bottomPanel.
        JPanel bottomPanel = new JPanel();
        bottomPanel.setSize(imageSize * columnsAmount, 80);
        JButton newGameButton = new JButton("Новая игра");
        JButton settingsButton = new JButton("Настройки");
        JLabel gameTime = new JLabel();

        bottomPanel.add(gameTime);
        bottomPanel.add(newGameButton);
        bottomPanel.add(settingsButton);

        // Добавляем компоненты игрового поля на Фрейм.
        gameFrame.add(headerPanel, BorderLayout.NORTH);
        gameFrame.add(mineFieldPanel, BorderLayout.CENTER);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);
        gameFrame.revalidate();

        // Поставил включение видимости в конец, чтобы не видно было процессов применения размеров.
        // Чтобы все посчиталось, создалось и готовое отобразилось.
        gameFrame.setVisible(true);

        // newGameButton.
        newGameButton.addActionListener(e -> {
            controller.setGameStatus(GameStatus.FIRST_CLICK);
            headerTextLabel.setText(generateHeaderMessage());
            gameTimer.stop();
            startGame();
        });

        // settingsButton.
        settingsButton.addActionListener(e -> {
            DesktopSettingsDialog desktopSettingsDialog
                    = new DesktopSettingsDialog(gameFrame, "Настройки", true, this);
            desktopSettingsDialog.setVisible(true); // отобразить диалог
        });

        // mouse.
        // TODO: Как-то по-разному передаются parentObj.
        DesktopRecordDialog desktopRecordDialog = new DesktopRecordDialog(gameFrame, "Рекорды", true);
        desktopRecordDialog.setParentObject(this);
        mineFieldPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = e.getX() / imageSize;
                int row = e.getY() / imageSize;

                if (e.getButton() == MouseEvent.BUTTON1) {
                    controller.processUserAction(1, row, column);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    controller.processUserAction(2, row, column);
                }

                headerTextLabel.setText(generateHeaderMessage());
                // Перерисовываем поле paintComponent().
                mineFieldPanel.repaint();

                // Проверяем статус игры.
                // GameOver - это когда статус не PLAY и не FIRST_CLICK,
                // т.е. это и выигрыш и проигрыш.
                if (isGameOver()) {
                    gameTimer.stop();
                }

                // Проверка рекорда и вывод диалога.
                if (controller.getGameStatus() == GameStatus.WIN) {
                    // Фиксируем новое время.
                    desktopRecordDialog.generateAndSetGameResults();
                    desktopRecordDialog.setVisible(true);
                }
            }
        });

        // Запускаем таймер после полной отрисовки игры.
        gameTimer.start(gameTime, 0);
    }

    public Controller getController() {
        return controller;
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    private void generateFrame() {
        gameFrame = new JFrame();
        gameFrame.setSize(imageSize * columnsAmount, imageSize * rowsAmount + 100);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setTitle("Сапёр");
        gameFrame.setResizable(false);

        /*
        TODO: Добавить позиционирование по центру только, если новый Фрейм создается.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gameFrame.setLocation(screenSize.width / 2 - imageSize * columnsAmount / 2,
                screenSize.height / 2 - imageSize * rowsAmount / 2);
        */
    }

    private String generateHeaderMessage() {
        return switch (controller.getGameStatus()) {
            case PLAY -> "Мин/флагов осталось: " + controller.getFlagsAmount();
            case LOOSE -> "Вы проиграли!";
            case WIN -> "Вы выиграли!";
            default -> "Добро пожаловать."; // FIRST_CLICK
        };
    }

    private boolean isGameOver() {
        return controller.getGameStatus() != GameStatus.PLAY
                && controller.getGameStatus() != GameStatus.FIRST_CLICK;
    }

    private static Image getCellImage(String imageName) {
        String fileName = imageName + ".png";
        String imagesPath = System.getProperty("user.dir") + "/Minesweeper/src/images/" + fileName;
        ImageIcon icon = new ImageIcon(imagesPath);

        return icon.getImage();
    }

    private static void loadCellImages() {
        for (CellImage cellImage : CellImage.values()) {
            cellImage.setCellImage(getCellImage(cellImage.name().toLowerCase()));
        }
    }
}