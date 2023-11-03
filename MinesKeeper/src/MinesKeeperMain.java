import controller.MinesKeeperController;
import model.MinesKeeperField;
import view.MineKeeperView;

public class MinesKeeperMain {
    public static void main(String[] args) {
        MinesKeeperField field = new MinesKeeperField(5, 5, 5);
        MinesKeeperController controller = new MinesKeeperController(field);
        MineKeeperView userView = new MineKeeperView(controller);
        userView.execute();
    }
}
