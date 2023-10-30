/* Это не нужно в данной редакции.

 import controller.Controller;
 import model.Model;
 import model.Model2;
*/

import view.ConsoleView;

public class ConsoleMain {
    public static void main(String[] args) {
        try {
/* Это не нужно в данной редакции.
             Можно передать другую, модель, реализующую интерфейс по-другому.
             Model2 model = new Model2();
             Model model = new Model();
             Controller controller = new Controller(model);
             ConsoleView userView = new ConsoleView(controller);
*/
            ConsoleView userView = new ConsoleView();

            userView.execute();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}