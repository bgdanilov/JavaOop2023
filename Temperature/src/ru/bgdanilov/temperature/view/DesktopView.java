package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class DesktopView {
    private final Controller controller;

    public DesktopView(Controller controller) {
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
            List<Scale> temperatureScales = controller.getTemperatureScales();

            // Исходные данные.
            JLabel inputTemperatureLabel = new JLabel("Исходная температура:");
            JTextField inputTemperatureField = new JTextField("0", 13);

            // Тут мы заполняем шкалами температур наш JComboBox.
            // JComboBox'у нужно передать массив шкал.
            // Передаем массив, создаваемый из списка наших шкал методом toArray,
            // который принимает массив Scale[0] нулевой длины и преобразуется в длину,
            // равную списку шкал - так что все шкалы влезают.
            JComboBox<Scale> inputScaleComboBox = new JComboBox<>(temperatureScales.toArray(new Scale[0]));

            // Результирующие данные.
            JLabel outputTemperatureLabel = new JLabel(" Результирующая температура:");
            JTextField outputTemperatureField = new JTextField("0", 13);
            outputTemperatureField.setEditable(false);

            JComboBox<Scale> outputScaleComboBox = new JComboBox<>(temperatureScales.toArray(new Scale[0]));

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
            // c.anchor = GridBagConstraints.NORTHWEST; // Выравнивание по краю.
            c.fill = GridBagConstraints.NONE; // Заполнение по доступному пространству.
            c.insets = new Insets(0, 0, 5, 0); // Margin CSS.
            c.ipadx = 0; // Дополнительный отступ.
            c.ipady = 0; // Дополнительный отступ.
            c.weightx = 0.0; // Распределение доступной ширины на количество элементов
            c.weighty = 0.0; // Распределение доступной высоты на количество элементов.

            // inputTemperatureLabel.
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            bagLayout.setConstraints(inputTemperatureLabel, c);
            frame.add(inputTemperatureLabel);

            // Приведение к измененных стилей к исходным.
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

            // Приведение к измененных стилей к исходным.
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

            // resetButton.
            c.gridx = 0;
            c.gridy = 4;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(0, 0, 30, 0);
            bagLayout.setConstraints(resetButton, c);
            frame.add(resetButton);

            // convertButton.
            c.gridx = 0;
            c.gridy = 5;
            c.insets = new Insets(0, 0, 0, 0);
            bagLayout.setConstraints(convertButton, c);
            frame.add(convertButton);

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
                    Scale inputScale = (Scale) inputScaleComboBox.getSelectedItem();
                    Scale outputScale = (Scale) outputScaleComboBox.getSelectedItem();

                    double outputTemperature = controller.convertTemperature(inputTemperature, inputScale, outputScale);

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

    // Округление температуры.
    private static String getRoundedTemperatureLine(double temperature) {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return temperature < 10000
                ? String.valueOf((double) Math.round(temperature * 100) / 100)
                : temperatureFormat.format(temperature);
    }
}