package ru.bgdanilov.matrix_main;

import ru.bgdanilov.matrix.Matrix;
import ru.bgdanilov.vector.Vector;

public class MatrixMain {
    public static void main(String[] args) {
        // 1.a. Конструктор - создание пустой матрицы размера n*m.
        System.out.println("1.a. Конструктор - создание пустой матрицы размера n*m.");
        Matrix matrix1a = new Matrix(2, 3);
        System.out.println("matrix1a размером 2 на 3: " + matrix1a);
        System.out.println();

        // 1.c.	Конструктор - создание из Matrix(double[][]) – из двумерного массива.
        double[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9, 10}};
        Matrix matrix1c = new Matrix(array);
        System.out.println("matrix1c : " + matrix1c);

        // 1.b. Конструктор копирования - Matrix(Matrix).
        Matrix matrix1b = new Matrix(matrix1c);
        System.out.println("matrix1b : " + matrix1b);

        // 1.d. Конструктор	Matrix(Vector[]) – из массива векторов-строк.
        Vector[] vectors = new Vector[]{new Vector(2), new Vector(4)};
        Matrix matrix1d = new Matrix(vectors);
        System.out.println("matrix1d : " + matrix1d);
        System.out.println();

        // 2.b. Метод. Получение и задание вектора-строки по индексу.
        System.out.println("2.b. Метод. Получение и задание вектора-строки по индексу.");
        System.out.println("Получение по индексу 2: Исходная матрица matrix1b" + matrix1b);
        Vector vector2b2 = matrix1b.getRow(2);
        System.out.println("vector2b2 : " + vector2b2);
        System.out.println();

        Vector sourceVector = new Vector(new double[] {9, 10, 11});
        matrix1a.setRow(sourceVector, 1);
        System.out.println("matrix1a[0] <-- {9, 10, 11} : " + matrix1a);
        System.out.println();

        // 2.c. Метод. Получение вектора-столбца по индексу.
        System.out.println("2.c. Метод. Получение вектора-столбца по индексу");
        System.out.println("Получение по индексу 0: Исходная матрица matrix1a" + matrix1a);
        Vector vector2c = matrix1a.getColumn(1);
        System.out.println(vector2c);
        System.out.println();

        System.out.println(matrix1c.getColumn(1));
    }
}