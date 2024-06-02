package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class DesktopSingleFieldView implements View {
    private final Controller controller;
    private boolean isPressed;
    private double inputTemperature;

    public DesktopSingleFieldView(Controller controller) {
        this.controller = controller;
    }
    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Конвертер температур");
            frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // В Mac иконка не появляется, а в Win появляется.
            Image icon = Toolkit.getDefaultToolkit().getImage("Temperature/iConvertIcon.png");
            frame.setIconImage(icon);

            // header, textField.
            JLabel header = new JLabel("Введите температуру:");
            JTextField textField = new JTextField("0", 13);

            List<Scale> temperatureScales = controller.getTemperatureScales();

            // inputScaleComboBox, toText, outputScaleComboBox, buttons.
            JComboBox<Scale> inputScaleComboBox = new JComboBox<>(temperatureScales.toArray(new Scale[0]));

            JLabel toText = new JLabel("конвертировать в:");

            JComboBox<Scale> outputScaleComboBox = new JComboBox<>(temperatureScales.toArray(new Scale[0]));

            JButton convertButton = new JButton("Конвертировать");
            JButton resetButton = new JButton("Назад / Сброс");

            // Создаем менеджер расположения компонентов.
            GridBagLayout bagLayout = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            frame.setLayout(bagLayout);

            // header.
            c.gridx = 1; // Номер столбца.
            c.gridy = GridBagConstraints.RELATIVE; // Инкремент номера строк автоматически.
            c.gridheight = 1; // Количество занимаемых строк.
            c.gridwidth = 1; // Количество занимаемых столбцов.
            c.anchor = GridBagConstraints.NORTH; // Выравнивание по краю.
            c.fill = GridBagConstraints.NONE; // Заполнение по доступному пространству.
            c.insets = new Insets(0, 0, 5, 0); // Margin CSS.
            c.ipadx = 0; // Дополнительный отступ.
            c.ipady = 0; // Дополнительный отступ.
            c.weightx = 0.0; // Распределение доступной ширины на количество элементов
            c.weighty = 0.0; // Распределение доступной высоты на количество элементов.

            bagLayout.setConstraints(header, c);
            frame.add(header);

            // textField.
            bagLayout.setConstraints(textField, c);
            frame.add(textField);

            // inputScaleComboBox.
            bagLayout.setConstraints(inputScaleComboBox, c);
            frame.add(inputScaleComboBox);

            // toText.
            bagLayout.setConstraints(toText, c);
            frame.add(toText);

            // outputScaleComboBox.
            bagLayout.setConstraints(outputScaleComboBox, c);
            frame.add(outputScaleComboBox);

            // resetButton.
            c.insets = new Insets(0, 0, 30, 0); //  отступы
            bagLayout.setConstraints(resetButton, c);
            frame.add(resetButton);

            // convertButton.
            c.insets = new Insets(0, 0, 0, 0); //  отступы
            bagLayout.setConstraints(convertButton, c);
            frame.add(convertButton);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Ограничение количества вводимых символов.
            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent event) {
                    super.keyTyped(event);
                    if (textField.getText().length() >= 18) {
                        event.consume();
                    }
                }
            });

            isPressed = false;
            inputTemperature = 0;

            convertButton.addActionListener(event -> {
                try {
                    inputTemperature = Double.parseDouble(textField.getText());
                    Scale inputScale = (Scale) inputScaleComboBox.getSelectedItem();
                    Scale outputScale = (Scale) outputScaleComboBox.getSelectedItem();

                    double outputTemperature = controller.convertTemperature(inputTemperature, inputScale, outputScale);

                    // Формируем сообщение с данными исходной температуры.
                    assert inputScale != null;
                    String inputTemperatureMessage = ViewUtilities.getRoundedTemperatureLine(inputTemperature) + " " + inputScale.key();
                    String outputTemperatureMessage = ViewUtilities.getRoundedTemperatureLine(outputTemperature)
                            + " " + (outputScale != null ? outputScale.key() : ' '); // Был warning на key().

                    textField.setText(inputTemperatureMessage + " = " + outputTemperatureMessage);
                    textField.setEditable(false);
                    isPressed = false;
                } catch (NumberFormatException exception) {
                    showMessageDialog(frame, "Вы должны ввести число.", "Сообщение!", JOptionPane.INFORMATION_MESSAGE);
                    header.setText("Введите температуру:");
                    textField.setText("0");
                    textField.setEditable(true);
                }
            });

            resetButton.addActionListener(e -> {
                // Только через массив получается.
                textField.setEditable(true);

                if (!isPressed) {
                    textField.setText(String.valueOf(inputTemperature));
                    isPressed = true;
                } else {
                    textField.setText("0");
                    inputScaleComboBox.setSelectedIndex(0);
                    outputScaleComboBox.setSelectedIndex(0);
                }
            });
        });
    }
}