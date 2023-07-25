package ru.bgdanilov.shape_main;
import ru.bgdanilov.shape.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShapeMain {
    public static void main(String[] args) {
        // Создаем объекты.
        Square square1 = new Square(10);
        Triangle triangle1 = new Triangle(1, 1, 4, 1, 4, 4);
        Rectangle rectangle1 = new Rectangle(2, 4);
        Circle circle1 = new Circle(1);

        // Добавляем объекты в безразмерный массив-список.
        List<Shape> shapes = new ArrayList<>();
        shapes.add(square1);
        shapes.add(triangle1);
        shapes.add(rectangle1);
        shapes.add(circle1);
        
        // Сравниваем по площади.
        Comparator<Shape> compareByArea = Comparator.comparing(Shape::getArea);
        // Сортируем по площади, по возрастанию (по умолчанию).
        shapes.sort(compareByArea);
        // Соответственно, размер списка-1 - это последний, самый большой объект.
        System.out.println("Максимальная площадь среди списка фигур равна: "
                + shapes.get(shapes.size() - 1).getArea());

        // Можно метод reversed() применить.
        Comparator<Shape> compareByPerimeter = Comparator.comparing(Shape::getPerimeter).reversed();
        shapes.sort(compareByPerimeter);
        System.out.println("Второй по величине периметр среди списка фигур равен: "
                + shapes.get(2).getPerimeter());
    }
}