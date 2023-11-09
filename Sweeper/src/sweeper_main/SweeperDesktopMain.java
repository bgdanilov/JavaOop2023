package sweeper_main;

import sweeper_controller.SweeperController;
import sweeper_model.SweeperField;
import sweeper_view.MinesKeeperDesktopView;

public class SweeperDesktopMain {
    public static void main(String[] args) {
        // TODO: После отладки, сделать число мин автоматически равным 10% от количества всех клеток.
        SweeperField mineField = new SweeperField(10, 10, 10);
        SweeperController controller = new SweeperController(mineField);
        MinesKeeperDesktopView userView = new MinesKeeperDesktopView(controller);

        userView.execute();
    }
}