package ru.bgdanilov.temperature_view;

import ru.bgdanilov.temperature_controller.ITemperatureController;
import ru.bgdanilov.temperature_model.ITemperature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import static javax.swing.JOptionPane.showMessageDialog;

public class TemperatureDesktopView {
    private final ITemperatureController controller;

    public TemperatureDesktopView(ITemperatureController controller) {
        this.controller = controller;
    }

    public void execute() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("iConvertTemperature");
                frame.setSize(300, 300);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // В Mac иконка не появляется, а в Win появляется.
                Image icon = Toolkit.getDefaultToolkit().getImage("Temperature/iConvertIcon.png");
                frame.setIconImage(icon);

                // header, textField.
                JLabel header = new JLabel("Введите температуру:");
                JTextField textField = new JTextField("0", 13);

                // Выбираем названия шкал для ComboBox.
                String[] temperatureScales = controller.getTemperatureScales().stream()
                        .map(ITemperature::name)
                        .toArray(String[]::new);

                // inputTemperatureComboBox, toText, outputTemperatureComboBox, buttons.
                JComboBox<String> inputTemperatureComboBox = new JComboBox<>(temperatureScales);
                JLabel toText = new JLabel("конвертировать в:");
                JComboBox<String> outputTemperatureComboBox = new JComboBox<>(temperatureScales);
                JButton convertButton = new JButton("Конвертировать");
                JButton resetButton = new JButton("Назад / Сброс");

                // Создаем менеджер расположения компонентов.
                GridBagLayout bagLayout = new GridBagLayout();
                GridBagConstraints bagConstraints = new GridBagConstraints();
                frame.setLayout(bagLayout);

                // header.
                bagConstraints.anchor = GridBagConstraints.NORTH; // Выравнивание по краю.
                bagConstraints.fill = GridBagConstraints.NONE; // Не изменяет размеров и не растягивается.
                bagConstraints.gridheight = 1; // Занимает одну ячейку.
                bagConstraints.gridwidth = GridBagConstraints.REMAINDER; // Занимает все место в строке.
                bagConstraints.gridx = 1; // В одном столбце.
                bagConstraints.gridy = GridBagConstraints.RELATIVE; // Инкремент строк автоматически.
                bagConstraints.insets = new Insets(5, 0, 0, 0); // Margin CSS.
                bagConstraints.ipadx = 0; // Номер столбца.
                bagConstraints.ipady = 0; // Номер строки.
                bagConstraints.weightx = 0.0; // Распределение доступной ширины на количество элементов
                bagConstraints.weighty = 0.0; // Распределение доступной высоты на количество элементов.

                bagLayout.setConstraints(header, bagConstraints);
                frame.add(header);

                // textField.
                bagConstraints.insets = new Insets(5, 0, 0, 0); //  отступы
                bagLayout.setConstraints(textField, bagConstraints);
                frame.add(textField);

                // inputTemperatureComboBox.
                bagConstraints.insets = new Insets(5, 0, 0, 0); //  отступы
                bagLayout.setConstraints(inputTemperatureComboBox, bagConstraints);
                frame.add(inputTemperatureComboBox);

                // toText.
                bagConstraints.insets = new Insets(5, 0, 0, 0); //  отступы
                bagLayout.setConstraints(toText, bagConstraints);
                frame.add(toText);

                // outputTemperatureComboBox.
                bagConstraints.insets = new Insets(5, 0, 0, 0); //  отступы
                bagLayout.setConstraints(outputTemperatureComboBox, bagConstraints);
                frame.add(outputTemperatureComboBox);

                // resetButton.
                bagConstraints.insets = new Insets(5, 0, 0, 0); //  отступы
                bagLayout.setConstraints(resetButton, bagConstraints);
                frame.add(resetButton);

                // convertButton.
                bagConstraints.insets = new Insets(35, 0, 0, 0); //  отступы
                bagLayout.setConstraints(convertButton, bagConstraints);
                frame.add(convertButton);

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

                // Хотел сделать замыкание для кнопки "Назад / Сброс", но работает только через Массив.
                // Значения этих переменных должны быть доступны в resetButton.addActionListener ниже.
                final double[] inputTemperature = {0};
                final boolean[] isPressed = {false};

                convertButton.addActionListener(event -> {
                    try {
                        inputTemperature[0] = Double.parseDouble(textField.getText());
                        String inputTemperatureRange = (String) inputTemperatureComboBox.getSelectedItem();
                        String outputTemperatureRange = ((String) outputTemperatureComboBox.getSelectedItem());

                        double outputTemperature = controller
                                .convertTemperature(inputTemperature[0], inputTemperatureRange, outputTemperatureRange);

                        // Формируем сообщение с данными исходной температуры.
                        String inputTemperatureMessage = getRoundedTemperatureLine(inputTemperature[0])
                                + " " + controller.getTemperatureScaleKey(inputTemperatureRange);
                        String outputTemperatureMessage = getRoundedTemperatureLine(outputTemperature)
                                + " " + controller.getTemperatureScaleKey(outputTemperatureRange);

                        textField.setText(inputTemperatureMessage + " = " + outputTemperatureMessage);
                        textField.setEditable(false);
                        isPressed[0] = false;
                    } catch (NumberFormatException exception) {
                        showMessageDialog(frame, "Вы должны ввести число.");
                        header.setText("Введите температуру:");
                        textField.setText("0");
                    }
                });

                resetButton.addActionListener(e -> {
                    // Только через массив получается.
                    textField.setEditable(true);

                    if (!isPressed[0]) {
                        textField.setText(String.valueOf(inputTemperature[0]));
                        isPressed[0] = true;
                    } else {
                        textField.setText("0");
                        inputTemperatureComboBox.setSelectedIndex(0);
                        outputTemperatureComboBox.setSelectedIndex(0);
                    }
                });
            }

        });
    }

    // Округление температуры.
    public static String getRoundedTemperatureLine(double temperature) {
        DecimalFormat temperatureFormat = new DecimalFormat("0.00E00");

        return temperature < 10000
                ? String.valueOf((double) Math.round(temperature * 100) / 100)
                : temperatureFormat.format(temperature);
    }
}