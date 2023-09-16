package ru.bgdanilov.matrix_main;

import ru.bgdanilov.matrix.Matrix;
import ru.bgdanilov.vector.Vector;

public class MatrixMain {
    public static void main(String[] args) {
        try {
            // 1.a. Конструктор - создание пустой матрицы размера n*m.
            System.out.println("1.a. Конструктор - создание пустой матрицы размера 2 * 3.");
            Matrix matrix1a = new Matrix(2, 3);
            System.out.println("     matrix1a размером 2 на 3: " + matrix1a);
            System.out.println();

            // 1.c.	Конструктор - создание из Matrix(double[][]) – из двумерного массива.
            System.out.println("1.c.Конструктор - создание из Matrix(double[][]) – из двумерного массива.");
            double[][] array = {{}, {4, 5, 6}, {7, 8, 9, 10}};
            Matrix matrix1c = new Matrix(array);
            System.out.println("    matrix1c : " + matrix1c);
            System.out.println();

            // 1.b. Конструктор копирования - Matrix(Matrix).
            System.out.println("1.b. Конструктор копирования из matrix1c: " + matrix1c);
            Matrix matrix1b = new Matrix(matrix1c);
            System.out.println("     matrix1b : " + matrix1b);
            System.out.println();

            // 1.d. Конструктор	Matrix(Vector[]) – из массива векторов-строк.
            System.out.println("1.d. Конструктор Matrix(Vector[]) – из массива векторов-строк.");
            Vector[] vectors = new Vector[]{new Vector(3, new double[]{1, 2, 3}), new Vector(2, new double[]{3, 4})};
            Matrix matrix1d = new Matrix(vectors);
            System.out.println("     matrix1d : " + matrix1d);
            System.out.println();

            System.out.println("Итого, исходные матрицы:");
            System.out.println("     matrix1a: " + matrix1a);
            System.out.println("     matrix1b: " + matrix1b);
            System.out.println("     matrix1c: " + matrix1c);
            System.out.println("     matrix1d: " + matrix1d);
            System.out.println();

            // 2.b. Метод. Получение и задание вектора-строки по индексу.
            System.out.println("2.b. Метод. Получение и задание вектора-строки по индексу.");
            System.out.println("     Получение вектора-строки по индексу 2:");
            System.out.println("     Исходная матрица matrix1b: " + matrix1b);
            Vector vector2b = matrix1b.getRow(2);
            System.out.println("     vector2b : " + vector2b);
            System.out.println();

            System.out.println("     Задание вектора-строки по индексу 1:");
            Vector sourceVector = new Vector(new double[]{9, 10, 11});
            System.out.println("     Вектор-строка: " + sourceVector);
            System.out.println("     Исходная матрица matrix1a: " + matrix1a);
            matrix1a.setRow(1, sourceVector);
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

            // 2.f. Метод. Вычисление определителя.
            System.out.println("2.f. Метод. Вычисление определителя.");
        /*
        Почему в этой строке обязательно нужно что-то написать чтобы не было варнинга?
        Vector[] vectors2f = new Vector[]{
                new Vector(10, new double[]{0, 0, 3, 4, 5, 4, 7, 8, 9, 5}),
                new Vector(10, new double[]{4, 7, 8, 9, 5, 4, 7, 8, 9, 5}),
                new Vector(10, new double[]{0, 0, 3, 4, 5, 10, 11, 12, 5, 5}),
                new Vector(10, new double[]{13, 3, 4, 5, 10, 11, 14, 15, 0, 2}),
                new Vector(10, new double[]{13, 14, 8, 9, 5, 4, 7, 15, 0, 12}),
                new Vector(10, new double[]{0, 0, 3, 45, 5, 4, 12, 8, 9, 5}),
                new Vector(10, new double[]{4, 71, 8, 9, 5, 41, 7, 8, 9, 5}),
                new Vector(10, new double[]{0, 0, 3, 14, 5, 10, 11, 13, 5, 5}),
                new Vector(10, new double[]{13, 3, 0, 5, 10, 11, 1, 15, 0, 2}),
                new Vector(10, new double[]{13, 14, 8, 9, 0, 4, 7, 15, 0, 12})
        };
        */
            Vector[] vectors2f = new Vector[]{
                    new Vector(4, new double[]{2, -2, 1, 3}),
                    new Vector(4, new double[]{0, 1, 2, 9}),
                    new Vector(4, new double[]{2, 2, -1, 7}),
                    new Vector(4, new double[]{0, 2, 1, 6})
            };
            Matrix matrix2f = new Matrix(vectors2f);
            System.out.println("     Исходная матрица matrix2f: " + matrix2f);
            System.out.println("     Определитель матрицы matrix2f: " + matrix2f.getDeterminant());
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

            // 3.a. Метод. Сложение матриц.
            System.out.println("3.a. Метод. Сложение матриц.");
            System.out.println("     Исходная матрица matrix1d: " + matrix1d);
            System.out.println("     Исходная матрица matrix1e: " + matrix1e);
            System.out.println("     Результат: " + Matrix.getSum(matrix1d, matrix1e));
            System.out.println();

            // 3.b. Метод. Вычитание матриц.
            System.out.println("3.b. Метод. Вычитание матриц.");
            System.out.println("     Исходная матрица matrix1d: " + matrix1d);
            System.out.println("     Исходная матрица matrix1e: " + matrix1e);
            System.out.println("     Результат: " + Matrix.getDifference(matrix1d, matrix1e));
            System.out.println();

            // 3.c. Метод. Умножение матриц.
            System.out.println("3.c. Метод. Умножение матриц.");
            Vector[] vectors3c1 = new Vector[]{
                    new Vector(2, new double[]{2, 1}),
                    new Vector(2, new double[]{-3, 0}),
                    new Vector(2, new double[]{4, -1}),
                    new Vector(2, new double[]{3, -4})
            };
            Matrix matrix1 = new Matrix(vectors3c1);

            Vector[] vectors3c2 = new Vector[]{
                    new Vector(3, new double[]{5, -1, 6}),
                    new Vector(3, new double[]{-3, 0, 7})
            };
            Matrix matrix2 = new Matrix(vectors3c2);
            System.out.println("     Исходная матрица matrix1: " + matrix1);
            System.out.println("     Исходная матрица matrix2: " + matrix2);
            System.out.println("     Их произведение: " + Matrix.getProduct(matrix1, matrix2));
            System.out.println();

            // Проверим исключение:
            Vector[] vectors3c3 = new Vector[]{
                    new Vector(2, new double[]{5, -1}),
                    new Vector(2, new double[]{-3, 0})
            };

            Matrix matrix3 = new Matrix(vectors3c3);
            System.out.println(Matrix.getProduct(matrix1, matrix3));
        } catch (IllegalArgumentException | IndexOutOfBoundsException | UnsupportedOperationException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}