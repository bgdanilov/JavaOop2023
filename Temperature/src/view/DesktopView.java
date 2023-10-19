package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class DesktopView extends JFrame {
    private final Controller controller;

    public DesktopView(Controller controller) {
        this.controller = controller;
    }

    public void execute() {
        setTitle("iConvert Temperature");
        setSize(200, 300);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Иконка. Не получается.
        // Программа стартует вроде с корня - JavaOop2023, значит путь правильный?
        Image icon = Toolkit.getDefaultToolkit().getImage("Temperature/iConvertIcon.png");
        setIconImage(icon);

        JLabel header = new JLabel("Введите температуру:");
        JTextField textField = new JTextField("0", 13);

        String[] temperatureRanges = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
        JComboBox<String> inputTemperatureComboBox = new JComboBox<>(temperatureRanges);
        JLabel toText = new JLabel("конвертировать в:");
        JComboBox<String> outputTemperatureComboBox = new JComboBox<>(temperatureRanges);

        JButton convertButton = new JButton("Convert");
        JButton resetButton = new JButton("Reset");
        JPanel panel = new JPanel();

        add(panel, BorderLayout.CENTER);
        panel.add(header);
        panel.add(textField);
        panel.add(inputTemperatureComboBox);
        panel.add(toText);
        panel.add(outputTemperatureComboBox);
        panel.add(convertButton);
        panel.add(resetButton);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                super.keyTyped(event);
                if (textField.getText().length() >= 18) {
                    event.consume();
                }
            }
        });

        convertButton.addActionListener(event -> {
            try {
                double inputTemperature = Double.parseDouble(textField.getText());
                char inputTemperatureRange = ((String) inputTemperatureComboBox.getSelectedItem()).charAt(0);
                char outputTemperatureRange = ((String) outputTemperatureComboBox.getSelectedItem()).charAt(0);

                // Записываем данные исходной температуры в модель.
                controller.setTemperatureData(inputTemperature, inputTemperatureRange);

                // Формируем сообщение с данными исходной температуры.
                String inputTemperatureMessage = controller.getTemperatureData();

                // Конвертируем температуру, согласно заданию. Изменяем состояние модели.
                controller.convertTemperature(outputTemperatureRange);

                // Формируем сообщение с данными выходной температуры.
                String outputTemperatureMessage = controller.getTemperatureData();

                textField.setText(inputTemperatureMessage + " = " + outputTemperatureMessage);
                textField.setEditable(false);
            } catch (NumberFormatException exception) {
                showMessageDialog(this, "Вы должны ввести число.");
                textField.setText("0");
                // Эта ситуация тут невозможна, но на всякий случай.
                // Почему NullPointerException не ловит для rangeOutput.getSelectedItem()).charAt(0) ?
            } catch (StringIndexOutOfBoundsException exception) {
                showMessageDialog(this, "Вы должны ввести C, K или F.");
            } catch (NullPointerException exception) {
                showMessageDialog(this, "Не ловит?");
            }
        });

        resetButton.addActionListener(e -> {
            // TODO Использовать замыкание, чтобы при сбросе подставлялась исходная температура.
            //  Вдруг я захочу одну и ту же температуру по разным шкалам конвертировать.
            textField.setEditable(true);
            textField.setText("0");
            inputTemperatureComboBox.setSelectedIndex(0);
            outputTemperatureComboBox.setSelectedIndex(0);
        });
    }
}