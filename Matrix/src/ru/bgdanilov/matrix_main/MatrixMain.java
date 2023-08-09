package ru.bgdanilov.matrix_main;

import ru.bgdanilov.matrix.Matrix;
import ru.bgdanilov.vector.Vector;

public class MatrixMain {
    public static void main(String[] args) {
        // 1.a. Конструктор - создание пустой матрицы размера n*m.
        Matrix matrix1 = new Matrix(2, 3);
        System.out.println(matrix1);

        // 1.c.	Конструктор - создание из Matrix(double[][]) – из двумерного массива.
        double[][] matrix2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(new Matrix(matrix2));

        // 1.d. Конструктор	Matrix(Vector[]) – из массива векторов-строк.
        Vector[] vectors = new Vector[]{new Vector(3), new Vector(3)};
    }
}