import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class DialogTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogFrame frame = new DialogFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}

/**
 * Фрейм содержащий меню. При выборе пункта File->About
 * отображается диалоговое окно.
 */
class DialogFrame extends JFrame {
    public DialogFrame() {
        setTitle("DialogTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Создание меню File.

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Добавление пунктов меню About и Exit.

        // При выборе пункта About отображается диалоговое окно About

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if (dialog == null) // в первый раз
                    dialog = new AboutDialog(DialogFrame.this);
                dialog.setVisible(true); // отобразить диалог
            }
        });
        fileMenu.add(aboutItem);

        // При активации пункта Exit программа завершается.

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
    }

    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;

    private AboutDialog dialog;
}

/*
 * Модальное диалоговое окно отображает сообщение
 * и ожидает щелчка на кнопке ОК.
 */

class AboutDialog extends JDialog {
    public AboutDialog(JFrame owner) {
        super(owner, "About DialogTest", true);

        // Метка с HTML-форматированием выравнивается по центру.

        add(new JLabel(
                        "<html><h1><i>Все о Java</i></h1><hr>"
                                + "Pro-java.ru уроки Java</html>"),
                BorderLayout.CENTER);

        // При активизации кнопки ОК диалогове окно закрывается.

        JButton ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });

        // Кнопка ОК помещается в нижнюю часть окна.

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(260, 160);
    }
}