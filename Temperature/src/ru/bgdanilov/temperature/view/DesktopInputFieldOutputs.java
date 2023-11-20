package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.ControllerInterface;
import ru.bgdanilov.temperature.model.ScaleInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class DesktopInputFieldOutputs {
    private final ControllerInterface controller;

    private boolean isPressed;
    private double inputTemperature;

    public DesktopInputFieldOutputs(ControllerInterface controller) {
        this.controller = controller;
    }

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

            List<ScaleInterface> temperatureScales = controller.getTemperatureScales();

            // inputScaleComboBox, toText, outputScaleComboBox, buttons.
            JComboBox<ScaleInterface> inputScaleComboBox = new JComboBox<>();
            fillComboBoxByTemperatureScales(inputScaleComboBox, temperatureScales);

            JLabel toText = new JLabel("конвертировать в:");

            JComboBox<ScaleInterface> outputScaleComboBox = new JComboBox<>();
            fillComboBoxByTemperatureScales(outputScaleComboBox, temperatureScales);

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
                    ScaleInterface inputScale = (ScaleInterface) inputScaleComboBox.getSelectedItem();
                    ScaleInterface outputScale = (ScaleInterface) outputScaleComboBox.getSelectedItem();

                    double outputTemperature = controller
                            .convertTemperature(inputTemperature, inputScale, outputScale);

                    // Формируем сообщение с данными исходной температуры.
                    assert inputScale != null;
                    String inputTemperatureMessage = getRoundedTemperatureLine(inputTemperature) + " " + inputScale.key();
                    String outputTemperatureMessage = getRoundedTemperatureLine(outputTemperature)
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

    // Наполнение JComboBox.
    private static void fillComboBoxByTemperatureScales(JComboBox<ScaleInterface> comboBox,
                                                        List<ScaleInterface> temperatureScales) {
        for (ScaleInterface comboBoxItem : temperatureScales) {
            comboBox.addItem(comboBoxItem);
        }
    }

    // Округление температуры.
    private static String getRoundedTemperatureLine(double temperature) {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return temperature < 10000
                ? String.valueOf((double) Math.round(temperature * 100) / 100)
                : temperatureFormat.format(temperature);
    }
}