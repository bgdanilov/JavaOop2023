package ru.bgdanilov.matrix_main;

import ru.bgdanilov.matrix.Matrix;
import ru.bgdanilov.vector.Vector;

public class MatrixMain {
    public static void main(String[] args) {
        // 1.a. Конструктор - создание пустой матрицы размера n*m.
        System.out.println("1.a. Конструктор - создание пустой матрицы размера 2 * 3.");
        Matrix matrix1a = new Matrix(2, 3);
        System.out.println("matrix1a размером 2 на 3: " + matrix1a);
        System.out.println();

        // 1.c.	Конструктор - создание из Matrix(double[][]) – из двумерного массива.
        System.out.println("1.c.Конструктор - создание из Matrix(double[][]) – из двумерного массива.");
        double[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9, 10}};
        Matrix matrix1c = new Matrix(array);
        System.out.println("matrix1c : " + matrix1c);
        System.out.println();

        // 1.b. Конструктор копирования - Matrix(Matrix).
        System.out.println("1.b. Конструктор копирования из matrix1c: " + matrix1c);
        Matrix matrix1b = new Matrix(matrix1c);
        System.out.println("matrix1b : " + matrix1b);
        System.out.println();

        // 1.d. Конструктор	Matrix(Vector[]) – из массива векторов-строк.
        System.out.println("1.d. Конструктор Matrix(Vector[]) – из массива векторов-строк.");
        // TODO Тут потенциальная ошибка в конструкторе Vector 1.d.
        // TODO new Vector(2, new double[]{1, 2, 4}) - создаст {1, 2}
        // TODO - т.e. обрежет и не будет ругаться.
        Vector[] vectors = new Vector[]{new Vector(3, new double[]{1, 2, 4}), new Vector(2, new double[]{3, 4}) };
        Matrix matrix1d = new Matrix(vectors);
        System.out.println("matrix1d : " + matrix1d);
        System.out.println();

        System.out.println("Итого, исходные матрицы:");
        System.out.println("matrix1a: " + matrix1a);
        System.out.println("matrix1b: " + matrix1b);
        System.out.println("matrix1c: " + matrix1c);
        System.out.println("matrix1d: " + matrix1d);
        System.out.println();

        // 2.b. Метод. Получение и задание вектора-строки по индексу.
        System.out.println("2.b. Метод. Получение и задание вектора-строки по индексу.");
        System.out.println("     Получение вектора-строки по индексу 2:");
        System.out.println("     Исходная матрица matrix1b: " + matrix1b);
        Vector vector2b = matrix1b.getRow(2);
        System.out.println("     vector2b : " + vector2b);
        System.out.println();

        System.out.println("     Задание вектора-строки по индексу 1:");
        Vector sourceVector = new Vector(new double[] {9, 10, 11});
        System.out.println("     Вектор-строка: " + sourceVector);
        System.out.println("     Исходная матрица matrix1a: " + matrix1a);
        matrix1a.setRow(sourceVector, 1);
        System.out.println("     Результат - matrix1a: " + matrix1a);
        System.out.println();

        // 2.c. Метод. Получение вектора-столбца по индексу.
        System.out.println("2.c. Метод. Получение вектора-столбца по индексу.");
        System.out.println("     Получение по индексу 1:");
        System.out.println("     Исходная матрица matrix1a: " + matrix1a);
        Vector vector2c = matrix1a.getColumn(2);
        System.out.println("     Результат - vector2c: " + vector2c);
        System.out.println();

        // 2.d. Метод. Транспонирование матрицы.
        System.out.println("2.d. Метод. Транспонирование матрицы.");
        System.out.println("     Исходная матрица matrix1d: " + matrix1d);
        matrix1d.transpose();
        System.out.println("     Результат: " + matrix1d);
        System.out.println();

        // 2.e. Метод. Умножение на скаляр.
        System.out.println("2.e. Метод. Умножение на скаляр.");
        System.out.println("     Исходная матрица matrix1d: " + matrix1d);
        matrix1d.multiplyByScalar(10);
        System.out.println("     Результат: " + matrix1d);
        System.out.println();

        // 2.i. Метод. Сложение матриц.
        System.out.println("2.i. Метод. Сложение матриц.");
        System.out.println("     Исходная матрица matrix1d: " + matrix1d);
        Matrix matrix1e = new Matrix(matrix1d);
        System.out.println("     Исходная матрица matrix1e: " + matrix1e);
        matrix1d.add(matrix1e);
        System.out.println("     Результат: матрица matrix1d: " + matrix1d);
        System.out.println();

        // 2.j. Метод. Вычитание матриц.
        System.out.println("2.j. Метод. Вычитание матриц.");
        matrix1d.subtract(matrix1e);
        System.out.println("     Из matrix1d вычесть matrix1e: " + matrix1d);
        System.out.println();

        /*Vector[] vectorsD = new Vector[]{
                new Vector(5, new double[]{0, 0, 3, 4, 5}),
                new Vector(5, new double[]{4, 7, 8, 9, 5}),
                new Vector(5, new double[]{10, 11, 12, 5, 5}),
                new Vector(5, new double[]{13, 14, 15, 0, 2}),
                new Vector(5, new double[]{13, 14, 15, 0, 12})
        };*/
        Vector[] vectorsD = new Vector[]{
                new Vector(3, new double[]{1, 2, 3}),
                new Vector(3, new double[]{1, 2, 3}),
                new Vector(3, new double[]{1, 2, 3})
        };

        Matrix matrixD = new Matrix(vectorsD);

        System.out.println(matrixD);
        System.out.println(matrixD.getDeterminant());
        System.out.println(matrixD);

    }
}