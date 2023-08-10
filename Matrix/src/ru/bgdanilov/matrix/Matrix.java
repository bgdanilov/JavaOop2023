package ru.bgdanilov.matrix;

import ru.bgdanilov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private final Vector[] matrix;

    // 1.a. Конструктор - создание пустой матрицы размера n*m.
    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Размерность матрицы не может быть ноль на ноль и меньше.");
        }

        // Создаем строки матрицы - массив объектов Vector c n-элементами.
        this.matrix = new Vector[n];

        // Теперь нужно в каждый элемент массива matrix, поместить вектор с m-элементами.
        for (int i = 0; i < n; i++) {
            // Используем создание конструктор Vector 1.a.
            this.matrix[i] = new Vector(m);
        }
    }

    // 1.b. Конструктор копирования - Matrix(Matrix).
    public Matrix(Matrix sourceMatrix) {
        // Получаем количество строк (векторов) в исходной матрице.
        int n = sourceMatrix.getRowsAmount();

        // Создаем новый массив векторов с тем же количеством элементов.
        this.matrix = new Vector[n];
        // Заполняем новыми векторами.
        for (int i = 0; i < n; i++) {
            // Почему подчеркивает Vector(sourceMatrix[i])
            // - это же i-й элемент массива векторов sourceMatrix
            // - значит вызываем 1.b. Конструктор - копирование из другого вектора. ?
            //this.matrix[i] = new Vector(sourceMatrix[i]);
        }
    }

    // 1.c.	Конструктор - создание из Matrix(double[][]) – из двумерного массива.
    public Matrix(double[][] sourceMatrix) {
        int n = sourceMatrix.length; // Число строк.
        int m = sourceMatrix[0].length; // Число столбцов - размерность векторов.
        // Создаем новый массив векторов с тем же количеством элементов.
        this.matrix = new Vector[n];

        for (int i = 0; i < n; i++) {
            // Используем 1.d. Конструктор - заполнение вектора значениями из массива.
            this.matrix[i] = new Vector(m, sourceMatrix[i]);
        }
    }

    // 1.d. Конструктор	Matrix(Vector[]) – из массива векторов-строк.
    public Matrix(Vector[] sourceVectors) {
        //int n = vectors.length;
        int size = 1;

        // Ищем максимальный размер среди векторов массива.
        for (Vector vector : sourceVectors) {
            if (vector.getSize() > size) {
                size = vector.getSize();
            }
        }

        // Добиваем нулями векторы исходного массива.
        for (Vector vector : sourceVectors) {
            improveSize(vector, size);
        }

        // Терзают меня сомнения. Слишком просто.
        this.matrix = sourceVectors;
    }

    public int getRowsAmount() {
        return matrix.length; // n
    }

    public int getColumnsAmount() {
        return matrix[0].getSize(); // m
    }

    // 2.g.	Метод toString определить так, чтобы результат получался в таком виде:
    // {{1, 2}, {2, 3}}
    @Override
    public String toString() {
        int n = this.matrix.length;
        StringBuilder sb = new StringBuilder().append("{ ").append(this.matrix[0]);

        for (int i = 1; i < n; i++) {
            sb.append(", ").append(matrix[i]);
        }

        return sb.append(" }").toString();
    }

    // Дополнение нулями короткого вектора до размера size, если надо.
    private void improveSize(Vector vector, int size) {
        if (vector.getSize() < size) {
            Vector additionalVector = new Vector(size);
            vector.addVector(additionalVector);
        }
    }
}

/*
    Описание класса:
    ===============================
    1. toString()
    - используется toString() из Vector для вывода внутренних векторов.

    Вопросы:
    ===============================
    1. Изначально у меня импортировался встроенный класс Vector
    и я ломал голову, что методы не работают.
    Я попыталься вручную прописать импорт своего Vector, но не получалось,
    IDE предложила добавить некую связь. Можно про это по подробнее, что за связь?
    Почему она нужна?

    n - число строк.
    m - число колонок.
 */

/* TODO
    1. Сделать проверку-добивку нулями в отдельную функцию.
    - сделано: improveSize.
    2. Конструктор 1.d: проверить this.matrix = vectors;
    - это у нас просто ссылка копируется или новый объект создается как положено?
    - вообще, разобраться с ссылочными данными и как они копируются, создаются, где хранятся.
 */
