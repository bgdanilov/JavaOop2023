import controller.MinesKeeperController;
import model.MinesKeeperField;
import view.MinesKeeperDesktopView;

public class MineKeepersDesktopMain {
    public static void main(String[] args) {
        MinesKeeperField field = new MinesKeeperField(6, 6, 3);
        MinesKeeperController controller = new MinesKeeperController(field);
        MinesKeeperDesktopView userView = new MinesKeeperDesktopView(controller);

        userView.execute();
    }
}
