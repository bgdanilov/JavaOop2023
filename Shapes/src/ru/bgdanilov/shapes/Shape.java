package ru.bgdanilov.shapes;

/** Создаем интерфейс - кнопки-заготовки на пульте без функций.
 * потом каждую кнопку в классе каждой фигуры переопределим.
 */

// Начнем работать в ветке Shapes, исправлять домашку и потом вольем ее в main.

public interface Shape {
    double getHeight();
    double getWidth();
    double getPerimeter();
    double getArea();
}
