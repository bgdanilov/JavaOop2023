package ru.bgdanilov.vector;

import java.util.Arrays;

public class Vector {
    // У вектора есть единственное поле - массив его координат.
    private double[] components;

    //// Конструкторы с перегрузкой:
    // a. Конструктор - создание пустого вектора размерностью n.
    public Vector(int n) {
        try {
            // Если n < 0 - бросаем исключение.
            if (n <= 0) {
                throw new IllegalArgumentException();
            }
            // Код, выполняемый в отсутствии исключения.
            this.components = new double[n];
        }
        // Обрабатываем исключение.
        catch (IllegalArgumentException e) {
            System.out.println("n must be > 0");
        }
    }

    // b. Конструктор - копирование из другого вектора.
    public Vector(Vector vector) {
        int n = vector.getSize(); // Размер вектора-источника.
        this.components = Arrays.copyOf(vector.components, n);
    }

    // c. Конструктор - заполнение вектора значениями из массива.
    public Vector(double[] components) {
        try {
            if (components.length == 0) {
                throw new IllegalArgumentException();
            }

            this.components = components;
        } catch (IllegalArgumentException e) {
            System.out.println("Массив не может иметь нулевую длину!");
        }
    }

    // d. Конструктор - заполнение вектора значениями из массива.
    // Если длины массивов не совпадают,
    // то результирующий массив обрежется или заполнится нулями.
    public Vector(int n, double[] array) {
        try {
            if (n <= 0 || array.length == 0) {
                throw new IllegalArgumentException();
            }

            this.components = Arrays.copyOf(array, n);
        } catch (IllegalArgumentException e) {
            System.out.println("n must be > 0");
        }
    }


    //// Методы:
    // Получение размерности вектора - по-сути длину массива координат.
    // Своего рода - геттер.
    public int getSize() {
        return this.components.length;
    }

    // Задание компонент существующего вектора вручную.
    // Сеттер.
    public void setComponents(double[] components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.components);
    }
}