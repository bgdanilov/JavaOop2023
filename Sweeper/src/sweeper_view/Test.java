package sweeper_view;

import sweeper_model.SweeperDifficultyLevel;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(100, 100);

        JComboBox<SweeperDifficultyLevel> comboBox = new JComboBox<>(SweeperDifficultyLevel.values());

        JButton button = new JButton("ok");

        button.addActionListener(e -> {
            SweeperDifficultyLevel level = (SweeperDifficultyLevel) comboBox.getSelectedItem();
            System.out.println(level.getRows());
        });

        frame.add(comboBox, BorderLayout.SOUTH);
        frame.add(button, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
