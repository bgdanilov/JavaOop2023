package ru.bgdanilov.matrix;

import ru.bgdanilov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    // 1.a. Конструктор - создание пустой матрицы размера rowsAmount * columnsAmount.
    public Matrix(int rowsAmount, int columnsAmount) {
        if (rowsAmount <= 0) {
            throw new IllegalArgumentException("Количество строк: " + rowsAmount + ", нельзя создать матрицу с нулевым или отрицательным количеством строк.");
        }

        if (columnsAmount <= 0) {
            throw new IllegalArgumentException("Количество столбцов: " + columnsAmount + ", нельзя создать матрицу с нулевым или отрицательным количеством столбцов.");
        }

        // Создаем строки матрицы - массив объектов Vector.
        rows = new Vector[rowsAmount];

        // Теперь нужно в каждый элемент массива matrix, поместить вектор с columnsAmount-элементами.
        for (int i = 0; i < rowsAmount; i++) {
            // Используем создание конструктор Vector 1.a.
            rows[i] = new Vector(columnsAmount);
        }
    }

    // 1.b. Конструктор копирования - Matrix(Matrix).
    public Matrix(Matrix matrix) {
        int rowsAmount = matrix.rows.length;

        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < rowsAmount; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    // 1.c.	Конструктор - создание из Matrix(double[][]) – из двумерного массива.
    public Matrix(double[][] array) {
        int rowsAmount = array.length;
        int columnsAmount = 0;

        // Ищем максимальный размер среди векторов массива - число столбцов будущей матрицы.
        for (double[] row : array) {
            int maxColumnAmount = row.length;

            if (maxColumnAmount > columnsAmount) {
                columnsAmount = maxColumnAmount;
            }
        }

        if (columnsAmount == 0) {
            throw new IllegalArgumentException("Количество столбцов: " + columnsAmount + ", матрицу размера 0 создать нельзя.");
        }

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; i++) {
            // Используем 1.d. Конструктор - заполнение вектора значениями из массива.
            rows[i] = new Vector(columnsAmount, array[i]);
        }
    }

    // 1.d. Конструктор	Matrix(Vector[]) – из массива векторов-строк.
    public Matrix(Vector[] rows) {
        int rowsAmount = rows.length;

        if (rowsAmount == 0) {
            throw new IllegalArgumentException("Количество строк: " + rowsAmount + ", матрицу размера 0 создать нельзя.");
        }

        // Создаем новый массив векторов (матрицу).
        this.rows = new Vector[rowsAmount];
        // Ищем максимальный размер среди векторов массива - число столбцов будущей матрицы.
        int columnsAmount = 0;

        for (Vector row : rows) {
            if (row.getSize() > columnsAmount) {
                columnsAmount = row.getSize();
            }
        }

        // Заполняем матрицу новыми векторами, созданными из исходных векторов.
        for (int i = 0; i < rowsAmount; i++) {
            this.rows[i] = new Vector(columnsAmount);
            this.rows[i].add(rows[i]);
        }
    }

    // 2.a. Метод. Получение размеров матрицы.
    public int getRowsAmount() {
        return rows.length;
    }

    public int getColumnsAmount() {
        return rows[0].getSize();
    }

    // 2.b. Метод. Получение и задание вектора-строки по индексу.
    public Vector getRow(int index) {
        int rowsAmount = rows.length;

        if (index < 0 || index >= rowsAmount) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", за пределами индексов строк [0, " + (rowsAmount - 1) + "] матрицы.");
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        int rowsAmount = rows.length;
        int columnsAmount = getColumnsAmount();

        if (index < 0 || index >= rowsAmount) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", за пределами индексов строк [0, " + (rowsAmount - 1) + "] матрицы.");
        }

        int vectorSize = vector.getSize();

        if (vectorSize > columnsAmount) {
            throw new IllegalArgumentException("Размер вектора: " + vectorSize + ", превышает число столбцов [" + columnsAmount + "] матрицы.");
        }

        for (int i = 0; i < columnsAmount; i++) {
            this.rows[index].setComponent(i, vector.getComponent(i));
        }
    }

    // 2.c. Метод. Получение вектора-столбца по индексу.
    public Vector getColumn(int index) {
        int columnsAmount = getColumnsAmount();

        if (index < 0 || index >= columnsAmount) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", за пределами индексов столбцов [0, " + (columnsAmount - 1) + "] матрицы.");
        }

        int rowsAmount = rows.length;
        Vector column = new Vector(rowsAmount);

        for (int i = 0; i < rowsAmount; i++) {
            column.setComponent(i, rows[i].getComponent(index));
        }

        return column;
    }

    // 2.d. Метод. Транспонирование матрицы.
    public void transpose() {
        int columnsAmount = getColumnsAmount();

        Vector[] columns = new Vector[columnsAmount];

        for (int i = 0; i < columnsAmount; i++) {
            columns[i] = getColumn(i);
        }

        rows = columns;
    }

    // 2.e. Метод. Умножение на скаляр.
    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    // 2.f. Метод. Вычисление определителя.
    public double getDeterminant() {
        if (rows.length != getColumnsAmount()) {
            throw new UnsupportedOperationException("Число строк: " + rows.length + ", число столбцов: " + getColumnsAmount() + ". Матрица должна быть квадратной.");
        }

        Matrix triangleMatrix = new Matrix(rows);

        int matrixSize = getRowsAmount();
        int swapsAmount = 0;
        final double EPSILON = 1.0e-10;

        for (int i = 0; i < matrixSize - 1; i++) { // Цикл по столбцам.
            for (int j = i + 1; j < matrixSize; j++) { // Цикл по строкам.
                // Если на диагонали ноль, то "опускаем" его вниз, меняя строку с ниже-идущей.
                if (Math.abs(triangleMatrix.rows[i].getComponent(i)) <= EPSILON) {
                    // Меняем строки местами и на следующую итерацию.
                    Vector currentRow = triangleMatrix.getRow(i);
                    triangleMatrix.rows[i] = triangleMatrix.getRow(j);
                    triangleMatrix.rows[j] = currentRow;

                    swapsAmount++;

                    continue;
                }

                // Добавляем к текущей строке ниже-идущую строку, умноженную на число,
                // чтобы получить ноль под элементом диагонали.
                double rowMultiplier = -(triangleMatrix.rows[j].getComponent(i)) / triangleMatrix.rows[i].getComponent(i);
                Vector currentRow = triangleMatrix.getRow(i);
                currentRow.multiplyByScalar(rowMultiplier);
                triangleMatrix.rows[j].add(currentRow);
            }
        }

        double determinant = 1;

        for (int i = 0; i < matrixSize; i++) {
            determinant *= triangleMatrix.rows[i].getComponent(i);
        }

        if (swapsAmount % 2 != 0) {
            return -determinant;
        }

        return determinant;
    }

    // 2.g.	Метод toString определить так,
    // чтобы результат получался в таком виде: {{1, 2}, {2, 3}}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append('{' );

        for (int i = 0; i < rows.length - 1; i++) {
            sb.append(rows[i]).append(", ");
        }

        sb.append(rows[rows.length - 1]).append('}' );

        return sb.toString();
    }

    // 2.h.	Метод. Умножение матрицы на вектор.
    public Vector multiplyByVector(Vector column) {
        if (getColumnsAmount() != column.getSize()) {
            throw new UnsupportedOperationException("Число столбцов матрицы: "
                    + getColumnsAmount()
                    + ", число строк вектора-столбца: " + column.getSize()
                    + " должны быть равны.");
        }

        Vector resultColumn = new Vector(rows.length);

        for (int i = 0; i < resultColumn.getSize(); i++) {
            resultColumn.setComponent(i, Vector.getDotProduct(rows[i], column));
        }

        return resultColumn;
    }

    // 2.i. Метод. Сложение матриц.
    public void add(Matrix matrix) {
        checkMatricesSizes(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    // 2.j. Метод. Вычитание матриц.
    public void subtract(Matrix matrix) {
        checkMatricesSizes(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    // 3.a. Метод. Сложение матриц.
    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        checkMatricesSizes(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.add(matrix2);
        return resultMatrix;
    }

    // 3.b. Метод. Вычитание матриц.
    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        checkMatricesSizes(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.subtract(matrix2);
        return resultMatrix;
    }

    private static void checkMatricesSizes(Matrix matrix1, Matrix matrix2) {
        // Вот тут яркий пример. Что выгоднее: объявить переменные rowsAmount и columnsAmount или напрямую считать?
        // Или сделать как в 3.c ниже?
        if (matrix1.getColumnsAmount() != matrix2.getColumnsAmount()
                || matrix1.rows.length != matrix2.rows.length) {
            throw new IllegalArgumentException("Матрица1 (строк: " + matrix1.rows.length + ", столбцов: " + matrix1.getColumnsAmount() + ")"
                    + " и Матрица2 (строк: " + matrix2.rows.length + ", столбцов: " + matrix2.getColumnsAmount()
                    + ") не совпадают по размеру.");
        }
    }

    // 3.c. Метод. Умножение матриц.
    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        int matrix1ColumnsAmount = matrix1.getColumnsAmount();
        int matrix2RowsAmount = matrix2.rows.length;

        if (matrix1ColumnsAmount != matrix2RowsAmount) {
            throw new IllegalArgumentException("Число столбцов ("
                    + matrix1ColumnsAmount + ") первой матрицы должно равняться числу строк ("
                    + matrix2RowsAmount + ") второй матрицы.");
        }

        int resultRowsAmount = matrix1.rows.length;
        int resultColumnsAmount = matrix2.getColumnsAmount();

        Matrix resultMatrix = new Matrix(resultRowsAmount, resultColumnsAmount);

        // Скалярное произведение строк на столбцы.
        for (int i = 0; i < resultRowsAmount; i++) {
            for (int j = 0; j < resultColumnsAmount; j++) {
                resultMatrix.rows[i].setComponent(j, Vector.getDotProduct(matrix1.rows[i], matrix2.getColumn(j)));
            }
        }

        return resultMatrix;
    }
}

/*  Заметки.
 *  ===================
 *    1.d. Конструктор Matrix(Vector[]) – из массива векторов-строк.
 *
 *    2.a. Метод. Получение размеров матрицы.
 *    - getColumnsAmount(). Будем считать, что в процессе создания матрицы не могут получиться
 *      строки разной длины, поэтому длину берем из первой строки.
 *
 *    2.d. Метод. Транспонирование матрицы.
 *    - столбцы исходной матрицы становятся строками результирующей.
 *    - для получения транспонированной матрицы достаточно каждую строку исходной матрицы
 *    записать в виде столбца результирующей, соблюдая порядок следования элементов.
 *
 *    2.g. Метод toString().
 *    - используется toString() из Vector для вывода внутренних векторов.
 *
 *    2.h. Метод. Умножение матрицы на вектор.
 *    - подразумевается под вектором - вектор-столбец. Умножение на вектор-строку не рассматривается.
 *
 *    2.f. Метод. Вычисление определителя.
 *    - используем метод Гаусса. Приводим матрицу к нижнему треугольному виду.
 *    - определитель не изменится, если к строке добавить строку, умноженную на число.
 *    - определитель - есть произведение элементов по диагонали.
 *    - при перемене строк, определитель меняет знак, поэтому считаем число замен.
 *
 *    3.c. Метод. Умножение матриц.
 *    - матрицы можно умножить только если
 *    число столбцов первой равно числу строк второй или наоборот.
 *    - произведением двух матриц есть матрица,
 *    у которой столько строк, сколько их у левого сомножителя,
 *    и столько столбцов, сколько их у правого сомножителя.
 */