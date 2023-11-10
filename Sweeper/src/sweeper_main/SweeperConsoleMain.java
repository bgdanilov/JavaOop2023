package sweeper_main;

import sweeper_controller.SweeperController;
import sweeper_model.SweeperField;
import sweeper_view.SweeperConsoleView;

public class SweeperConsoleMain {
    public static void main(String[] args) {
        SweeperField field = new SweeperField(10, 10, 10);
        SweeperController controller = new SweeperController(field);
        SweeperConsoleView userView = new SweeperConsoleView(controller);

        userView.execute();
    }
}