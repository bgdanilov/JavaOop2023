package ru.bgdanilov.shape;

/** Создаем интерфейс - кнопки-заготовки на пульте без функций.
 * потом каждую кнопку в классе каждой фигуры переопределим.
 */

// Изменения в ветке main на пробу.
// Изменения - исправление ошибки в main. Ветка hotfix.

public interface Shape {
    double getHeight();
    double getWidth();
    double getPerimeter();
    double getArea();
}
