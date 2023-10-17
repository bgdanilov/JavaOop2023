import controller.Controller;
import model.Model;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        try {
            Model inputTemperature = new Model();
            Controller inputTemperatureController = new Controller(inputTemperature);

            Model outputTemperature = new Model();
            Controller outputTemperatureController = new Controller(outputTemperature);

            ConsoleView userView = new ConsoleView(inputTemperatureController, outputTemperatureController);

            userView.execute();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}