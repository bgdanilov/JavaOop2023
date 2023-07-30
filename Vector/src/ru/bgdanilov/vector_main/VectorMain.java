package ru.bgdanilov.vector_main;

import ru.bgdanilov.vector.Vector;

public class VectorMain {
    public static void main(String[] args) {
        // Конструктор a. - создаем пустой массив.
        Vector vectorA = new Vector(10);
        // Допустим, я в конструктор выше передам n = 0.
        // А строчку ниже - закомментирую. Т.е. попытаюсь вывести длину массива 0.
        // Исключение нас предупредит: n must be > 0,
        // но объект vectorA все равно создастся.
        // И потом все равно будет ошибка при попытке вызова vector.getSize()
        // Не должен был быть объект создаться же?
        vectorA.setComponents(new double[]{1, 2, 2, 2});

        System.out.println(vectorA.toString());
        System.out.println(vectorA.getSize());

        // Конструктор b. - копирование из другого вектора.
        Vector vectorB = new Vector(vectorA);

        System.out.println(vectorB.toString());
        System.out.println(vectorB.getSize());

        // Конструктор с. - заполнение вектора значениями из массива.
        Vector vectorC = new Vector(new double[]{0.1, 0.2, 0.3});

        System.out.println(vectorC.toString());
        System.out.println(vectorC.getSize());

        // Конструктор d. - заполнение вектора значениями из массива.
        double[] array = new double[]{1, 2, 3, 4.32, 5};

        Vector vector3 = new Vector(7, array);
        System.out.println(vector3);
    }
}
