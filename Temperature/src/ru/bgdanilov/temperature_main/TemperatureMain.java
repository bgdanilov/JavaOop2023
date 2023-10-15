package ru.bgdanilov.temperature_main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureMain {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        // Принимаем и реализуем функциональный интерфейс Runnable с методом Run()
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

            }
        });
         */

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("My application");
            frame.setSize(600, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JButton button = new JButton("Button");
            frame.add(button);

            // Добавим событие на кнопку.
            button.addActionListener(e -> {
                // А именно - вывод диалогового окна.
                JOptionPane.showMessageDialog(frame, "Message");
            });

            frame.setVisible(true);
        });
    }
}


/*
* 1. Вся работа с графическими компонентами должна вестись в потоке-диспетчере событий
* (Event dispatch thread).
* 2. Все компоненты (например, кнопки, панели, поля ввода и так далее) наследуются от класса Component
* 3. Менеджер компоновки (Layout manager) – специальный класс,
* который отвечает за правила расположения дочерних элементов внутри контейнера.
* 4. Панель JPanel представляет собой прямоугольную область-контейнер,
* предназначенную для группировки и расположения элементов.
* 5. Для регистрации обработчиков событий в Java используется шаблон проектирования Listener (слушатель)
*
*/