package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.Controller;
import ru.bgdanilov.temperature.model.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class DesktopView implements View {
    private final Controller controller;
    public DesktopView(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start() {
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
            GridBagLayout gridBagLayout = new GridBagLayout();
            frame.setLayout(gridBagLayout);
            GridBagConstraints c = new GridBagConstraints();

            // Исходные значения сетки.
            c.gridx = 0; // Координата по x.
            c.gridy = 0; // Координата по y.
            c.gridwidth = 1; // Количество занимаемых столбцов.
            c.gridheight = 1; // Количество занимаемых строк.
            c.anchor = GridBagConstraints.NORTHWEST; // Выравнивание по краю.
            c.fill = GridBagConstraints.VERTICAL; // Заполнение по доступному пространству.
            c.insets = new Insets(0, 0, 10, 5); // Margin.
            c.ipadx = 3; // Padding.
            c.ipady = 3; // padding.
            c.weightx = 1.0; // Распределение доступной ширины на количество элементов
            c.weighty = 1.0; // Распределение доступной высоты на количество элементов.

            // inputTemperatureLabel.
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            frame.add(inputTemperatureLabel, c);

            // Приведение к измененных стилей к исходным.
            c.gridwidth = 1;
            c.anchor = GridBagConstraints.NORTHWEST;

            // inputTemperatureField.
            c.gridx = 0;
            c.gridy = 1;
            frame.add(inputTemperatureField, c);

            // inputScaleComboBox.
            c.gridx = 1;
            c.gridy = 1;
            frame.add(inputScaleComboBox, c);

            // outputTemperatureLabel.
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            frame.add(outputTemperatureLabel, c);

            // Приведение к измененных стилей к исходным.
            c.gridwidth = 1;
            c.anchor = GridBagConstraints.NORTHWEST;

            // outputTemperatureField
            c.gridx = 0;
            c.gridy = 3;
            frame.add(outputTemperatureField, c);

            // outputScaleComboBox.
            c.gridx = 1;
            c.gridy = 3;
            frame.add(outputScaleComboBox, c);

            // convertButton.
            c.gridx = 0;
            c.gridy = 4;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(0, 0, 30, 0);
            c.ipady = 0;
            frame.add(convertButton, c);

            // resetButton.
            c.gridx = 0;
            c.gridy = 5;
            c.insets = new Insets(0, 0, 0, 0);
            c.ipady = 0;
            frame.add(resetButton, c);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Ограничение количества вводимых символов.
            inputTemperatureField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent event) {
                    if (inputTemperatureField.getText().length() >= INPUT_SYMBOLS_AMOUNT_MAX) {
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
                    String outputTemperatureMessage = ViewUtilities.getRoundedTemperatureLine(outputTemperature);

                    outputTemperatureField.setText(outputTemperatureMessage);
                } catch (NumberFormatException exception) {
                    showMessageDialog(frame, "Вы должны ввести число.", "Сообщение!", JOptionPane.WARNING_MESSAGE, null);
                    inputTemperatureLabel.setText("Введите температуру:");
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
}