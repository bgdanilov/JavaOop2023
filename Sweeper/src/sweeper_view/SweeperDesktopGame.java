package sweeper_view;

import sweeper_controller.SweeperController;
import sweeper_model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static java.awt.BorderLayout.NORTH;


public class SweeperDesktopGame {
    private final SweeperController controller;
    private SweeperCell[][] mineField;
    private final int imageSize = 30;
    private int rowsAmount;
    private int columnsAmount;
    private JFrame gameFrame; // Это тут нужно.
    private final SweeperTimer gameTimer = new SweeperTimer();

    private SweeperDifficultyLevel difficultyLevel = SweeperDifficultyLevel.EASY;

    private SweeperRecord record;

    private String[][] matrix;


    public SweeperDesktopGame(SweeperController controller) {
        // Конструируем фрейм один раз, чтобы "Новая игра" повторно этого не делала.
        this.controller = controller;
        // Загружаем картинки.
        loadCellImages();
        record = new SweeperRecord();
        matrix = record.getMatrix();
    }

    public void startGame() {
        // Получаем минное поле без клеток, для размера фрейма.
        mineField = controller.getMineField();
        rowsAmount = mineField.length;
        columnsAmount = mineField[0].length;
        // Заполняем минное поле минами.
        controller.fillingMineField();
        // Включаем режим первого беспроигрышного клика.
        controller.setGameStatus(SweeperGameStatus.FIRST_CLICK);

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

        gameFrame.add(headerPanel, NORTH);
        gameFrame.add(mineFieldPanel, BorderLayout.CENTER);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);
        gameFrame.revalidate();

        // Поставил включение видимости в конец, чтобы не видно было процессов применения размеров.
        // Чтобы все посчиталось, создалось и готовое отобразилось.
        gameFrame.setVisible(true);

        // newGameButton.
        newGameButton.addActionListener(e -> {
            controller.setGameStatus(SweeperGameStatus.FIRST_CLICK);
            headerTextLabel.setText(generateHeaderMessage());
            gameTimer.stop();
            startGame();
        });

        // settingsButton.
        settingsButton.addActionListener(e -> {
            SweeperSettingsDialog settingsDialog = new SweeperSettingsDialog(gameFrame, "Settings", true, this);
            //settingsDialog.setParent(this);
            settingsDialog.setVisible(true); // отобразить диалог
        });

        // mouse.
        mineFieldPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // GameOver - это когда статус не PLAY и не FIRST_CLICK,
                // т.е. это и выигрыш и проигрыш.
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

                headerTextLabel.setText(generateHeaderMessage());
                // Перерисовываем поле paintComponent().
                mineFieldPanel.repaint();
                // Записываем рекорд.
                LocalDate currentDate = LocalDate.now();



                if (controller.getGameStatus() == SweeperGameStatus.WIN) {
                    // Ищем в матрице не побили ли мы рекорд.
                    int newRecordRowIndex
                            = getNewRecordRowIndex(difficultyLevel, matrix, gameTimer.getYourTimeInSeconds());

                    System.out.println(newRecordRowIndex);
                    // Зписываем рекорд в матрицу.
                    if (newRecordRowIndex != -1) {
                        writeInMatrix(newRecordRowIndex);
                    }
                }


//                // Зписываем рекорд в матрицу.
//                if (controller.getGameStatus() == SweeperGameStatus.LOOSE) {
//                    writeInMatrix(difficultyLevel, 0);
//                }


                writeRecord(gameTimer.getYourTimeInSeconds() + "; "
                        + currentDate + "; "
                        + difficultyLevel.getLabel() + "; "
                        + "Безымянный");
            }
        });

        // Запускаем таймер после полной отрисовки игры.
        gameTimer.start(gameTime, 0);
    }

    public void generateFrame() {
        gameFrame = new JFrame();
        gameFrame.setSize(imageSize * columnsAmount, imageSize * rowsAmount + 100);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setTitle("Сапёр");
        gameFrame.setResizable(false);


        // Первичное позиционирование игрового фрейма по центру экрана.
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        gameFrame.setLocation(screenSize.width / 2 - imageSize * columnsAmount / 2,
//                screenSize.height / 2 - imageSize * rowsAmount / 2);

        // TODO: Можно потом сюда перенести header.
        // Добавить позиционирование по центру только, если новый Фрейм создается.
    }

    private int getNewRecordRowIndex(SweeperDifficultyLevel level, String[][] matrix, int newTime) {
        int rowFrom = 0;
        int rowTo = 0;

        if (level == SweeperDifficultyLevel.EASY) {
            rowTo = 2;
        } else if (level == SweeperDifficultyLevel.MEDIUM) {
            rowFrom = 3;
            rowTo = 5;
        } else if (level == SweeperDifficultyLevel.HARD) {
            rowFrom = 6;
            rowTo = 8;
        }

        for (int i = rowFrom; i <= rowTo; i++) {
            if (matrix[i][0] == null) {
                return i;
            }
        }

        int minRiznost = Integer.parseInt(matrix[rowFrom][0]) - newTime; // -1
        int result = rowFrom; // [0]

        if (minRiznost <= 0) {
           minRiznost = Integer.parseInt(matrix[rowFrom][0]); // 4
        }

        for (int i = rowFrom + 1; i < rowTo; i++) {
            if (Integer.parseInt(matrix[i][0]) - newTime <= 0) { // 5 no
                minRiznost = Integer.parseInt(matrix[rowFrom][0]);
            } else if (Integer.parseInt(matrix[i][0]) - newTime < minRiznost) { // 5 no
                minRiznost = Integer.parseInt(matrix[i][0]); // 3
                result = i; // [1]
            }
        }

        return result;
    }

    private void writeInMatrix(int row) {
        LocalDate currentDate = LocalDate.now();

        matrix[row][0] = String.valueOf(getGameTimer().getYourTimeInSeconds());
        matrix[row][1] = String.valueOf(currentDate);
        matrix[row][2] = String.valueOf(difficultyLevel.getLabel());
        matrix[row][3] = "Имя";

        System.out.println(record.toString(matrix));
        //System.out.println(matrix[0][0]);
    }

    private void writeRecord(String recordLine) {
        // Запись в файл.
        if (controller.getGameStatus() == SweeperGameStatus.LOOSE) {
            SweeperRecord record = new SweeperRecord();

            try {
                record.writeToFile(recordLine);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            // Получение рекорда.
            getBestRecord();
        }
    }

    private void getBestRecord() {
        if (controller.getGameStatus() == SweeperGameStatus.LOOSE) {
            SweeperRecord eee = new SweeperRecord();

            String line = eee.getBestRecord(difficultyLevel);
            displayRecordDialog(line);
        }
    }

    private void displayRecordDialog(String line) {
        SweeperRecordDialog recordDialog = new SweeperRecordDialog(gameFrame, "Рекорды", true, this);
        recordDialog.generateMessage(line);
        recordDialog.setVisible(true); // отобразить диалог
    }

    public SweeperController getController() {
        return controller;
    }

    public SweeperTimer getGameTimer() {
        return gameTimer;
    }

    public boolean isGameOver() {
        return controller.getGameStatus() != SweeperGameStatus.PLAY
                && controller.getGameStatus() != SweeperGameStatus.FIRST_CLICK;
    }

    private String generateHeaderMessage() {
        return switch (controller.getGameStatus()) {
            case PLAY -> "Мин/флагов осталось: " + controller.getFlagsAmount();
            case LOOSE -> "Вы проиграли!";
            case WIN -> "Вы выиграли!";
            default -> "Добро пожаловать.";
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

    public void setDifficultyLevel(SweeperDifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}