import controller.Controller;
import model.Model;
import view.ConsoleView;

public class  Main {
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