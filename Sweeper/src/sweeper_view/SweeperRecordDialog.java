package sweeper_view;

import sweeper_model.SweeperDifficultyLevel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class SweeperRecordDialog {
    private final JDialog dialog;
    private SweeperDesktopGame parentObject;
    private final JPanel contentPanel;
    private final JLabel dialogMessage;
    private final JTextField inputName;
    private final JButton writeButton;
    private final JButton cancelButton;
    private int newGameTime;
    private int currentRecordTime;
    private String newGameDate;

    public SweeperRecordDialog(JFrame frameOwner, String title, boolean modality) {
        dialog = new JDialog(frameOwner, title, modality);
        dialog.setSize(250, 250);
        dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        dialog.setLocationRelativeTo(frameOwner);

        JPanel wrapPanel = new JPanel();

        contentPanel = new JPanel();
        GridBagLayout bagLayout = new GridBagLayout();
        contentPanel.setLayout(bagLayout);

        GridBagConstraints c = new GridBagConstraints();
        // Исходные значения сетки.
        c.gridx = 0; // Координата по x.
        c.gridy = 0; // Координата по y.
        c.gridwidth = 2; // Количество занимаемых столбцов.
        c.gridheight = 1; // Количество занимаемых строк.
        c.anchor = GridBagConstraints.CENTER; // Выравнивание по краю.
        c.fill = GridBagConstraints.CENTER; // Заполнение по доступному пространству.
        c.insets = new Insets(0, 0, 5, 0); // Margin CSS.
        c.ipadx = 0; // Дополнительный отступ.
        c.ipady = 0; // Дополнительный отступ.
        c.weightx = 0.0; // Распределение доступной ширины на количество элементов
        c.weighty = 0.0; // Распределение доступной высоты на количество элементов.

        JLabel header = new JLabel("<html><p align='center'><strong>Вы выиграли!</strong></p></html>");

        // dialogMessage.
        c.insets = new Insets(15, 0, 15, 0); // Margin CSS.
        dialogMessage = new JLabel();
        bagLayout.setConstraints(dialogMessage, c);

        // inputName.
        c.gridy = 1; // Координата по y.
        c.insets = new Insets(0, 0, 15, 0); // Margin CSS.
        inputName = new JTextField("Ваше имя", 13);
        bagLayout.setConstraints(inputName, c);

        // writeButton.
        c.gridwidth = 1; // Количество занимаемых столбцов.
        c.gridx = 0; // Координата по x.
        c.gridy = 2; // Координата по y.
        writeButton = new JButton("Записать");
        bagLayout.setConstraints(writeButton, c);
        writeButton.addActionListener(e -> {
            writeGameResultsToFile();
            dialog.setVisible(false);
        });

        // cancelButton.
        c.gridwidth = 1; // Количество занимаемых столбцов.
        c.gridx = 1; // Координата по x.
        c.gridy = 2; // Координата по y.
        cancelButton = new JButton("Отмена");
        bagLayout.setConstraints(cancelButton, c);
        cancelButton.addActionListener(e -> dialog.setVisible(false));

        wrapPanel.add(header);
        wrapPanel.add(contentPanel);

        dialog.add(wrapPanel);
    }

    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }

    public void setParentObject(SweeperDesktopGame parentObject) {
        this.parentObject = parentObject;
    }

    // Эта функция просто фиксирует результат игры:
    // новое время и готовит соответствующее сообщение.
    // Записывать или нет рекорд решает пользователь далее.
    public void generateAndSetGameResults() {
        // Фиксируем время нового рекорда и дату в поле объекта.
        LocalDate currentDate = LocalDate.now();
        newGameDate = String.valueOf(currentDate);
        newGameTime = parentObject.getGameTimer().getYourTimeAtSeconds();
        String newGameTimeAtWatch = formatTime(newGameTime);

        // Фиксируем время старого рекорда в поле объекта.
        currentRecordTime = Integer.parseInt(parentObject.getDifficultyLevel().getRecordEntry()[0]);
        String currentRecordTimeAtWatch = formatTime(currentRecordTime);

        contentPanel.add(dialogMessage);

        if (newGameTime < currentRecordTime) {
            String bodyText = "<html><p align='center'>"
                    + "Ваше время: " + newGameTimeAtWatch + "<br /><br />"
                    + "Предыдущий чемпион: " + "<br />"
                    + "<i>" + parentObject.getDifficultyLevel().getRecordEntry()[3] + "</i>"
                    + ": " + currentRecordTimeAtWatch
                    + "</p></html>";
            dialogMessage.setText(bodyText);

            contentPanel.add(inputName);
            contentPanel.add(writeButton);
            contentPanel.add(cancelButton);
        } else {
            dialogMessage.setText("<html>Ваше время: " + newGameTimeAtWatch + "<br /><br />"
                    + "<p align='center'>Текущий рекорд: <br />"
                    + "<i>" + parentObject.getDifficultyLevel().getRecordEntry()[3] + "</i>: "
                    + currentRecordTimeAtWatch
                    + "</p></html>");
            cancelButton.setText("Хорошо");
            contentPanel.add(cancelButton);
        }
    }

    public void writeGameResultsToFile() {
        if (newGameTime < currentRecordTime) {
            parentObject.getDifficultyLevel().setRecordEntry(new String[]{
                    String.valueOf(newGameTime),
                    newGameDate,
                    parentObject.getDifficultyLevel().getLabel(),
                    inputName.getText()});

            SweeperDifficultyLevel.writeEnumToFile();
        }
    }

    private static String formatTime(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }
}