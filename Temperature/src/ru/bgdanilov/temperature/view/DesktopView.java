package ru.bgdanilov.temperature.view;

import ru.bgdanilov.temperature.controller.IController;
import ru.bgdanilov.temperature.model.IScale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class DesktopView {
    private final IController controller;

    public DesktopView(IController controller) {
        this.controller = controller;
    }

    public void execute() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Конвертер температур");
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

                // inputTemperatureComboBox, toText, outputTemperatureComboBox, buttons.
                List<IScale> temperatureScales = controller.getTemperatureScales();
                JComboBox<IScale> inputTemperatureComboBox = new JComboBox<>();
                fillTemperatureScalesComboBox(inputTemperatureComboBox, temperatureScales);

                JLabel toText = new JLabel("конвертировать в:");

                JComboBox<IScale> outputTemperatureComboBox = new JComboBox<>();
                fillTemperatureScalesComboBox(outputTemperatureComboBox, temperatureScales);

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
                bagLayout.setConstraints(textField, bagConstraints);
                frame.add(textField);

                // inputTemperatureComboBox.
                bagLayout.setConstraints(inputTemperatureComboBox, bagConstraints);
                frame.add(inputTemperatureComboBox);

                // toText.
                bagLayout.setConstraints(toText, bagConstraints);
                frame.add(toText);

                // outputTemperatureComboBox.
                bagLayout.setConstraints(outputTemperatureComboBox, bagConstraints);
                frame.add(outputTemperatureComboBox);

                // resetButton.
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
                double[] inputTemperature = {0};
                boolean[] isPressed = {false};

                convertButton.addActionListener(event -> {
                    try {
                        inputTemperature[0] = Double.parseDouble(textField.getText());
                        IScale inputScale = (IScale) inputTemperatureComboBox.getSelectedItem();
                        IScale outputScale = (IScale) outputTemperatureComboBox.getSelectedItem();

                        double outputTemperature = controller
                                .convertTemperature(inputTemperature[0], inputScale, outputScale);

                        // Формируем сообщение с данными исходной температуры.
                        assert inputScale != null;
                        String inputTemperatureMessage = getRoundedTemperatureLine(inputTemperature[0]) + " " + inputScale.key();
                        String outputTemperatureMessage = getRoundedTemperatureLine(outputTemperature)
                                + " " + (outputScale != null ? outputScale.key() : ' '); // Был warning на key().

                        textField.setText(inputTemperatureMessage + " = " + outputTemperatureMessage);
                        textField.setEditable(false);
                        isPressed[0] = false;
                    } catch (NumberFormatException exception) {
                        showMessageDialog(frame, "Вы должны ввести число.");
                        header.setText("Введите температуру:");
                        textField.setText("0");
                        textField.setEditable(true);
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

    // Наполнение JComboBox.
    private static void fillTemperatureScalesComboBox(JComboBox<IScale> temperatureJComboBox,
                                                      List<IScale> temperatureScales) {
        for (IScale item : temperatureScales) {
            temperatureJComboBox.addItem(item);
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