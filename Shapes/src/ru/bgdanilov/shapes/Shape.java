package ru.bgdanilov.shapes;

/**
 * Создаем интерфейс - кнопки-заготовки на пульте без функций.
 * Потом каждую кнопку в классе каждой фигуры переопределим.
 */
public interface Shape {
    double getHeight();

    double getWidth();

    double getPerimeter();

    double getArea();
}