import sweeper_controller.Controller;
import sweeper_model.Model;
import sweeper_view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        try {
            Model model = new Model();
            Controller controller = new Controller(model);
            ConsoleView userView = new ConsoleView(controller);

            userView.execute();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}