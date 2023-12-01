package sweeper_view;

import sweeper_model.SweeperDifficultyLevel;

import javax.swing.*;

public class SweeperSettingsDialog {
    JDialog dialog;

    private SweeperDesktopGame parentObj;

    public SweeperSettingsDialog(JFrame owner, String title, boolean modality, SweeperDesktopGame obj) {
        parentObj = obj;
        dialog = new JDialog(owner, title, modality);
        dialog.setSize(260, 160);
        dialog.setLocationRelativeTo(owner);

        JPanel panel = new JPanel();
        JLabel header = new JLabel("Выберете уровень сложности:");

        JComboBox<SweeperDifficultyLevel> difficultyLevelComboBox = new JComboBox<>(SweeperDifficultyLevel.values());

        JButton ok = new JButton("ok");
        ok.addActionListener(e -> {
            dialog.setVisible(false);

            SweeperDifficultyLevel difficultyLevel
                    = (SweeperDifficultyLevel) difficultyLevelComboBox.getSelectedItem();

            if (difficultyLevel != null) {
                parentObj.getController().newMineField(
                        difficultyLevel.getRows(),
                        difficultyLevel.getColumns(),
                        difficultyLevel.getMines());
            }

            parentObj.getGameTimer().stop();
            parentObj.setDifficultyLevel(difficultyLevel);
            parentObj.startGame();
        });

        panel.add(header);
        panel.add(difficultyLevelComboBox);
        panel.add(ok);

        dialog.add(panel);
    }
    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }
}
