package sweeper_view;

import sweeper_model.SweeperDifficultyLevel;

import javax.swing.*;
import java.awt.*;

public class SweeperSettingsDialog {
    JDialog dialog;
    private final SweeperDesktopGame parentObject;

    public SweeperSettingsDialog(JFrame frameOwner, String title, boolean modality, SweeperDesktopGame object) {
        parentObject = object; // Чтобы работать с методами игры.
        dialog = new JDialog(frameOwner, title, modality);
        dialog.setSize(250, 150);
        dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        dialog.setLocationRelativeTo(frameOwner);

        JPanel panel = new JPanel();
        GridBagLayout bagLayout = new GridBagLayout();
        panel.setLayout(bagLayout);

        GridBagConstraints c = new GridBagConstraints();
        // Исходные значения сетки.
        c.gridx = 0; // Координата по x.
        c.gridy = 0; // Координата по y.
        c.gridwidth = 2; // Количество занимаемых столбцов.
        c.gridheight = 1; // Количество занимаемых строк.
        c.anchor = GridBagConstraints.CENTER; // Выравнивание по краю.
        c.fill = GridBagConstraints.CENTER; // Заполнение по доступному пространству.
        //c.insets = new Insets(0, 0, 5, 0); // Margin CSS.
        c.ipadx = 0; // Дополнительный отступ.
        c.ipady = 0; // Дополнительный отступ.
        c.weightx = 0.0; // Распределение доступной ширины на количество элементов
        c.weighty = 0.0; // Распределение доступной высоты на количество элементов.

        // header.
        c.insets = new Insets(0, 0, 15, 0); // Margin CSS.
        JLabel header = new JLabel("Выберете уровень сложности:");
        bagLayout.setConstraints(header, c);

        // difficultyLevelComboBox.
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1; // Количество занимаемых столбцов.
        c.insets = new Insets(0, 0, 5, 0); // Margin CSS.
        JComboBox<SweeperDifficultyLevel> difficultyLevelComboBox = new JComboBox<>(SweeperDifficultyLevel.values());
        bagLayout.setConstraints(difficultyLevelComboBox, c);

        // ok.
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1; // Количество занимаемых столбцов.
        c.insets = new Insets(0, 0, 5, 0); // Margin CSS.
        JButton ok = new JButton("Выбрать");
        bagLayout.setConstraints(ok, c);
        ok.addActionListener(e -> {
            dialog.setVisible(false);

            SweeperDifficultyLevel difficultyLevel
                    = (SweeperDifficultyLevel) difficultyLevelComboBox.getSelectedItem();

            // Считываем из выбранного уровня сложности
            // количества строк, столбцов и мин.
            // Создаем в объекте игры новое минное поле с такими параметрами.
            if (difficultyLevel != null) {
                parentObject.getController().newMineField(
                        difficultyLevel.getRowsAmount(),
                        difficultyLevel.getColumnsAmount(),
                        difficultyLevel.getMinesAmount());
            }

            // Останавливаем таймер. Новый запустится вместе с новой игрой.
            parentObject.getGameTimer().stop();
            // Устанавливаем выбранный уровень сложности в игру.
            parentObject.setDifficultyLevel(difficultyLevel);
            parentObject.startGame();
        });

        panel.add(header);
        panel.add(difficultyLevelComboBox);
        panel.add(ok);

        dialog.add(panel);
    }
    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }

/* Не используется.
    public void setParentObj(SweeperDesktopGame parentObj) {
        this.parentObj = parentObj;
    }
*/
}
