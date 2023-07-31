package ru.bgdanilov.vector_main;

import ru.bgdanilov.vector.Vector;

public class VectorMain {
    public static void main(String[] args) {
        // 1. Конструкторы.
        // -------------------------
        // 1.a. Создаем нулевой вектор размерностью 10.
        Vector vectorA = new Vector(10);
        System.out.println("Вектор A: " + vectorA.toString());

        // 1.c. Создаем вектор из значений массива.
        Vector vectorC = new Vector(new double[] {1, 2, 3, 4, 5});
        System.out.println("Вектор C: " + vectorC.toString());

        // 1.b. Создаем вектор копированием из другого вектора.
        Vector vectorB = new Vector(vectorC);
        System.out.println("Вектор B: " + vectorB.toString());

        // 1.d. Создаем вектор и производим заполнение вектора значениями из массива.
        Vector vectorD = new Vector(10, new double[] {1, 2, 3, 4, 5});
        System.out.println("Вектор D: " + vectorD.toString());

        // 2. Метод getSize() для получения размерности вектора
        System.out.println("Размер вектора D: " + vectorD.getSize());

        // 3. Реализовать метод toString() - проверено выше.
        System.out.println();

        // 4. Реализовать  нестатические методы:
        // ---------------------------------------
        System.out.println("4. Реализовать нестатические методы:");
        // 4.a. Прибавление к вектору другого вектора.
        vectorB.addVector(vectorD);
        System.out.println("4.a. К Вектору B добавляем Вектор D: " + vectorB.toString());




        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        /*
        // Конструктор a. - создаем пустой массив.
        Vector vectorA = new Vector(10);
        // Допустим, я в конструктор выше передам n = 0.
        // А строчку ниже - закомментирую. Т.е. попытаюсь вывести длину массива 0.
        // Исключение нас предупредит: n must be > 0,
        // но объект vectorA все равно создастся.
        // И потом все равно будет ошибка при попытке вызова vector.getSize()
        // Не должен был быть объект создаться же?
        vectorA.setComponents(new double[] {1, 2, 2, 2});

        System.out.println("Вектор A: " + vectorA.toString());
        System.out.println(vectorA.getSize());

        // Конструктор b. - копирование из другого вектора.
        Vector vectorB = new Vector(vectorA);

        System.out.println("Вектор B: " + vectorB.toString());
        System.out.println(vectorB.getSize());

        // Конструктор с. - заполнение вектора значениями из массива.
        Vector vectorC = new Vector(new double[] {0.1, 0.2, 0.3, 4, 45, 99});

        System.out.println("Вектор C: " + vectorC.toString());
        System.out.println(vectorC.getSize());

        // Конструктор d. - заполнение вектора значениями из массива.
        double[] array = new double[] {1, 2, 3, 4.32, 5};

        Vector vectorD = new Vector(3, array);
        System.out.println("Вектор D: " + vectorD);

        // Добавление вектора.
        System.out.println("Вектор C: " + vectorC.toString());
        System.out.println("+");
        System.out.println("Вектор D: " + vectorD);
        System.out.println("=");

        vectorC.addVector(vectorD);
        System.out.println("Вектор C: " + vectorC.toString());

        // Умножение вектора на скаляр.
        vectorC.multiplicationByScalar(10);
        System.out.println("Вектор C: " + vectorC.toString());

        // Разворот вектора. Умножение на -1.
        vectorC.reverseVector();
        System.out.println("Вектор C: " + vectorC.toString());

         */
    }
}
