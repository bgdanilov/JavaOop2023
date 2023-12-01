package sweeper_view;

import javax.swing.*;

public class SweeperRecordDialog {
    JDialog dialog;
    private SweeperDesktopGame parentObj;

    JLabel body;

    public SweeperRecordDialog(JFrame owner, String title, boolean modality, SweeperDesktopGame obj) {
        parentObj = obj;
        dialog = new JDialog(owner, title, modality);
        dialog.setSize(260, 160);
        dialog.setLocationRelativeTo(owner);

        JPanel panel = new JPanel();
        JLabel header = new JLabel("Ваши рекорды:");
        body = new JLabel();

        JButton ok = new JButton("ok");

        ok.addActionListener(e -> {
            dialog.setVisible(false);
        });

        panel.add(header);
        panel.add(body);
        panel.add(ok);

        dialog.add(panel);
    }
    public void setVisible(boolean b) {
        dialog.setVisible(b);
    }

    public void generateMessage(String line) {
        this.body.setText(line);
    }
}
