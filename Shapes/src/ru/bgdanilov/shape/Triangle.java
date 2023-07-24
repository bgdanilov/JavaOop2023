package ru.bgdanilov.shape;

public class Triangle implements Shape {
    // Не буду делать геттеры и сеттеры - 6 координат, 12 функций!
    // Будем считать, что как родили треугольник, так и нечего его менять.
    // Делаем поля final, т.к. нет сеттеров и это константы по смыслу тогда.
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final double x3;
    private final double y3;

    double sideA = 0;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }
   // Переменные объявленные в методе - недоступны нигде кроме этого метода.
   // Что бы переменная стала доступна другим методам ее нужно объявить в классе.
   // Такая переменная называется полем класса.
    @Override
    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    @Override
    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    @Override
    public double getPerimeter() {
        return  getSideLength(x1, x2, y1, y2) + getSideLength(x2, x3, y2, y3) + getSideLength(x1, x3, y1, y3);
    }

    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;

        double sideALength = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double sideBLength = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double sideCLength = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));

        // Для этого существует блок "Не нужно" ниже.
        // double sideALength = getSideALength();
        // double sideBLength = getSideLength(x2, x3, y2, y3);
        // double sideCLength = getSideLength(x1, x3, y1, y3);

        return Math.sqrt(halfPerimeter
                * (halfPerimeter - sideALength) * (halfPerimeter - sideBLength) * (halfPerimeter - sideCLength));
    }

    // Вспомогательные функции. private - только внутрии класса.
    // В Main ее не вызвать, т.к. она там не нужна.
    private static double getSideLength(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    // Не нужно.
    // Это все ниже - не нужно, но пока оставлю. Может в другую ветку сохраню для истории.
    // Почему так можно...
    private double getSideALength() {
        return getSideLength(x1, x2, y1, y2);
    }
    // ...а так нельзя?
    //double sideALength = getSideLength(x1, x2, y1, y2);

    // Я хочу чтобы у меня в кламме были просто переменные для многократного использования.
    // Получается, нужно каждую переменную в функцию оборачивать?

    // В идеале, вообще вот так бы:
    // Формулы длин сторон.
    //double sideALength = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    //double sideBLength = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
    //double sideCLength = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
}