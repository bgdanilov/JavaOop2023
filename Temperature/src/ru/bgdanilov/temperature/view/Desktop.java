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

public class Desktop {
    private final ControllerInterface controller;

    public Desktop(ControllerInterface controller) {
        this.controller = controller;
    }

    public void execute() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Конвертер температур");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

            // В Mac иконка не появляется, а в Win появляется.
            Image icon = Toolkit.getDefaultToolkit().getImage("Temperature/iConvertIcon.png");
            frame.setIconImage(icon);

            // Получаем коллекцию объектов шкал температур.
            List<ScaleInterface> temperatureScales = controller.getTemperatureScales();

            // Исходные данные.
            JLabel inputTemperatureLabel = new JLabel("Исходная температура:");
            JTextField inputTemperatureField = new JTextField("0", 13);

            JComboBox<ScaleInterface> inputScaleComboBox = new JComboBox<>();
            fillComboBoxByTemperatureScales(inputScaleComboBox, temperatureScales);

            // Результирующие данные.
            JLabel outputTemperatureLabel = new JLabel(" Результирующая температура:");
            JTextField outputTemperatureField = new JTextField("0", 13);
            outputTemperatureField.setEditable(false);

            JComboBox<ScaleInterface> outputScaleComboBox = new JComboBox<>();
            fillComboBoxByTemperatureScales(outputScaleComboBox, temperatureScales);

            // Кнопки.
            JButton convertButton = new JButton("Конвертировать");
            JButton resetButton = new JButton("Сброс");

            // Создаем менеджер расположения компонентов.
            GridBagLayout bagLayout = new GridBagLayout();
            frame.setLayout(bagLayout);
            GridBagConstraints c = new GridBagConstraints();

            // Исходные значения сетки.
            c.gridx = 0; // Координата по x.
            c.gridy = 0; // Координата по y.
            // c.gridwidth = 1; // Количество занимаемых столбцов.
            c.gridheight = 1; // Количество занимаемых строк.
            c.weightx = 0; // Вес элемента по горизонтали.
            c.weighty = 0; // Вес элемента по вертикали.
            // c.anchor = GridBagConstraints.NORTHWEST; // Выравнивание.
            c.fill = GridBagConstraints.NONE; // Заполнение по доступному пространству.
            c.insets = new Insets(5, 0, 0, 0); // Margin CSS.
            c.ipadx = 0; // Дополнительный отступ по x.
            c.ipady = 0; // Дополнительный отступ по y.

            // inputTemperatureLabel.
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            bagLayout.setConstraints(inputTemperatureLabel, c);
            frame.add(inputTemperatureLabel);

            // Сброс на исходные значения сетки.
            c.gridwidth = 1;
            c.anchor = GridBagConstraints.NORTHWEST;

            // inputTemperatureField.
            c.gridx = 0;
            c.gridy = 1;
            bagLayout.setConstraints(inputTemperatureField, c);
            frame.add(inputTemperatureField);

            // inputScaleComboBox.
            c.gridx = 1;
            c.gridy = 1;
            bagLayout.setConstraints(inputScaleComboBox, c);
            frame.add(inputScaleComboBox);

            // outputTemperatureLabel.
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            bagLayout.setConstraints(outputTemperatureLabel, c);
            frame.add(outputTemperatureLabel);

            // Сброс на исходные значения сетки.
            c.gridwidth = 1;
            c.anchor = GridBagConstraints.NORTHWEST;

            // outputTemperatureField
            c.gridx = 0;
            c.gridy = 3;
            bagLayout.setConstraints(outputTemperatureField, c);
            frame.add(outputTemperatureField);

            // outputScaleComboBox.
            c.gridx = 1;
            c.gridy = 3;
            bagLayout.setConstraints(outputScaleComboBox, c);
            frame.add(outputScaleComboBox);

            // convertButton.
            c.gridx = 0;
            c.gridy = 4;
            bagLayout.setConstraints(convertButton, c);
            frame.add(convertButton);

            // resetButton.
            c.gridx = 0;
            c.gridy = 5;
            bagLayout.setConstraints(resetButton, c);
            frame.add(resetButton);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Ограничение количества вводимых символов.
            inputTemperatureField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent event) {
                    super.keyTyped(event);
                    if (inputTemperatureField.getText().length() >= 18) {
                        event.consume();
                    }
                }
            });

            convertButton.addActionListener(event -> {
                try {
                    double inputTemperature = Double.parseDouble(inputTemperatureField.getText());
                    ScaleInterface inputScale = (ScaleInterface) inputScaleComboBox.getSelectedItem();
                    ScaleInterface outputScale = (ScaleInterface) outputScaleComboBox.getSelectedItem();

                    double outputTemperature = controller
                            .convertTemperature(inputTemperature, inputScale, outputScale);

                    // Формируем сообщение с данными исходной температуры.
                    String outputTemperatureMessage = getRoundedTemperatureLine(outputTemperature);

                    outputTemperatureField.setText(outputTemperatureMessage);
                } catch (NumberFormatException exception) {
                    showMessageDialog(frame, "Вы должны ввести число.", "Сообщение!", JOptionPane.INFORMATION_MESSAGE);
                    inputTemperatureLabel.setText("Введите температуру:");
                    inputTemperatureField.setText("0");
                }
            });

            resetButton.addActionListener(e -> {
                inputTemperatureField.setText("0");
                outputTemperatureField.setText("0");
                inputScaleComboBox.setSelectedIndex(0);
                outputScaleComboBox.setSelectedIndex(0);
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