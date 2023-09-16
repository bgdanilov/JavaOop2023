package ru.bgdanilov.matrix;

import ru.bgdanilov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    // 1.a. Конструктор - создание пустой матрицы размера rowsAmount * columnsAmount.
    public Matrix(int rowsAmount, int columnsAmount) {
        if (rowsAmount <= 0 || columnsAmount <= 0) {
            throw new IllegalArgumentException("1.2. Matrix(int rowsAmount, int columnsAmount): rowsAmount, columnsAmount - размерность матрицы не может быть ноль на ноль и меньше.");
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
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; i++) {
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
        if (rows.length == 0) {
            throw new IllegalArgumentException("Количество строк: " + rows.length + ", матрицу размера 0 создать нельзя.");
        }
        // Создаем новый массив векторов (матрицу) с тем же количеством элементов.
        this.rows = new Vector[rows.length];
        // Ищем максимальный размер среди векторов массива - число столбцов будущей матрицы.
        int columnsAmount = 0;

        for (Vector row : rows) {
            if (row.getSize() > columnsAmount) {
                columnsAmount = row.getSize();
            }
        }

        // Заполняем матрицу новыми векторами, созданными из исходных векторов.
        for (int i = 0; i < rows.length; i++) {
            this.rows[i] = new Vector(rows[i]);

            // Проверяем на совпадение с максимальным размером, при необходимости
            // дополняем нулями при помощи дополнения временного нового вектора размера columnsAmount.
            if (this.rows[i].getSize() < columnsAmount) {
                this.rows[i].add(new Vector(columnsAmount));
            }
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
        if (index < 0 || index > getRowsAmount() - 1) {
            throw new IllegalArgumentException("2.b. Vector getRow(int index): index - неверный индекс.");
        }

        return new Vector(rows[index]);
    }

    public void setRow(Vector vector, int index) {
        int n = this.getRowsAmount();
        int m = this.getColumnsAmount();

        if (vector.getSize() > m || (index < 0 || index > n - 1)) {
            throw new IllegalArgumentException("2.b. setRow(Vector vector, int index): index - векторы не совпадают по размеру или неверный индекс.");
        }

        for (int i = 0; i < m; i++) {
            this.rows[index].setComponent(i, vector.getComponent(i));
        }
    }

    // 2.c. Метод. Получение вектора-столбца по индексу.
    public Vector getColumn(int index) {
        if (index < 0 || index > getColumnsAmount() - 1) {
            throw new IllegalArgumentException("2.c. getColumn(int index): index - неверный индекс.");
        }

        int n = getRowsAmount();
        Vector column = new Vector(n);

        for (int i = 0; i < n; i++) {
            column.setComponent(i, rows[i].getComponent(index));
        }

        return column;
    }

    // 2.d. Метод. Транспонирование матрицы.
    public void transpose() {
        int m = getColumnsAmount();

        Vector[] temp = new Vector[m];

        for (int i = 0; i < m; i++) {
            temp[i] = getColumn(i);
        }

        rows = temp;
    }

    // 2.e. Метод. Умножение на скаляр.
    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    // 2.f. Метод. Вычисление определителя.
    public double getDeterminant() {
        if (getRowsAmount() != getColumnsAmount()) {
            throw new IllegalArgumentException("2.f. getDeterminant(): матрица должна быть квадратной!");
        }

        Matrix triangleMatrix = new Matrix(rows);

        int matrixSize = getRowsAmount();
        int swapsCounter = 0;

        for (int i = 0; i < matrixSize - 1; i++) { // цикл по столбцам.
            for (int j = i + 1; j < matrixSize; j++) { //  цикл по строкам.
                // Если на диагонали ноль, то "опускаем" его вниз, меняя строку с нижеидущей.
                if (triangleMatrix.rows[i].getComponent(i) == 0) {
                    // Меняем строки местами и на следующую итерацию.
                    Vector temp = triangleMatrix.getRow(j);  //  это новый вектор.
                    triangleMatrix.rows[j] = triangleMatrix.getRow(i);
                    triangleMatrix.rows[i] = temp;
                    swapsCounter++;

                    continue;
                }

                // Добавляем к строке вышестоящую строку, умноженую на число,
                // чтобы получить ноль под элементом диагонали.
                double rowMultiplier = -(triangleMatrix.rows[j].getComponent(i)) / triangleMatrix.rows[i].getComponent(i);
                Vector temp = triangleMatrix.getRow(i);
                temp.multiplyByScalar(rowMultiplier);
                triangleMatrix.rows[j].add(temp);
            }
        }

        double determinant = 1;

        for (int i = 0; i < matrixSize; i++) {
            determinant *= triangleMatrix.rows[i].getComponent(i);
        }

        if (swapsCounter % 2 != 0) {
            return -determinant;
        }

        return determinant;
    }

    // 2.g.	Метод toString определить так,
    // чтобы результат получался в таком виде: {{1, 2}, {2, 3}}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        int n = rows.length;

        for (int i = 0; i < n - 1; i++) {
            sb.append(rows[i]).append(", ");
        }

        sb.append(rows[n - 1]).append("}");

        return sb.toString();
    }

    // 2.i. Метод. Сложение матриц.
    public void add(Matrix matrix) {
        if (this.getColumnsAmount() != matrix.getColumnsAmount()
                && this.getRowsAmount() != matrix.getRowsAmount()) {
            throw new IllegalArgumentException("2.i. add(Matrix matrix): матрицы должны быть одинаковой размрности!");
        }

        int matrixSize = getRowsAmount();

        for (int i = 0; i < matrixSize; i++) {
            this.rows[i].add(matrix.getRow(i));
        }
    }

    // 2.j. Метод. Вычитание матриц.
    public void subtract(Matrix matrix) {
        if (this.getColumnsAmount() != matrix.getColumnsAmount()
                && this.getRowsAmount() != matrix.getRowsAmount()) {
            throw new IllegalArgumentException("2.j. subtract(Matrix matrix): матрицы должны быть одинаковой размерности!");
        }

        int matrixSize = getRowsAmount();

        for (int i = 0; i < matrixSize; i++) {
            this.rows[i].subtract(matrix.getRow(i));
        }
    }

    // 3.a. Метод. Сложение матриц.
    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.add(matrix2);
        return resultMatrix;
    }

    // 3.b. Метод. Вычитание матриц.
    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.subtract(matrix2);
        return resultMatrix;
    }

    // 3.c. Метод. Умножение матриц.
    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("3.c. getMultiplication(Matrix matrix1, Matrix matrix2): число столбцов первой матрицы должно быть равно числу строк второй!");
        }
        int resultMatrixSize = matrix1.getRowsAmount();

        Matrix resultMatrix = new Matrix(resultMatrixSize, resultMatrixSize);

        // Скалярное произведение строк на столбцы.
        for (int i = 0; i < resultMatrixSize; i++) {
            for (int j = 0; j < resultMatrixSize; j++) {
                double resultMatrixItem = Vector.getDotProduct(matrix1.getRow(i), matrix2.getColumn(j));
                resultMatrix.rows[i].setComponent(j, resultMatrixItem);
            }
        }

        return resultMatrix;
    }
}

/*
    Описание класса:
    ===============================
    n - число строк.
    m - число колонок.

    1.d. Конструктор Matrix(Vector[]) – из массива векторов-строк.

    2.a. Метод. Получение размеров матрицы.
    - getColumnsAmount(). Будем считать, что в процессе создания матрицы не моут получится
      строки разной длины, поэтому длину берем из первой строки.

    2.d. Метод. Транспонирование матрицы.
    - столбцы исходной матрицы становятся строками результирующей.
    -  для получения транспонированной матрицы достаточно каждую строку исходной матрицы
    записать в виде столбца результирующей, соблюдая порядок следования элементов.

    2.g. Метод toString().
    - используется toString() из Vector для вывода внутренних векторов.

    2.f. Метод. Вычисление определителя.
    - используем метод Гаусса. Приводим матрицу к нижнему треугольному виду.
    - определитель не изменится, если к строке добавить строку, умноженную на число.
    - определитель - еть произведение элементов по диагонали.
    - при перемене строк, определитель меняет знак, поэтому считаем число замен.

    3.c. Метод. Умножение матриц.
    - матрицы можно умножить только если
    число столбцов первой равно числу строк второй или наоборот.
    - произведением двух матриц есть матрица,
    у которой столько строк, сколько их у левого сомножителя,
    и столько столбцов, сколько их у правого сомножителя.
    
    Вопросы:
    ===============================
    1. Изначально у меня импортировался встроенный класс Vector
       и я ломал голову, что методы не работают.
       Я попытался вручную прописать импорт своего Vector, но не получалось,
       IDE предложила добавить некую связь. Можно про это по подробнее, что за связь?
       Почему она нужна?

    2. Конструктор 1.d. А может не стоит тут проверять размерность передаваемого вектора?
       Мы же вектор через конструктор создаем, и размером ноль конструктор нам не даст сделать.
       Аналогично Конструктор 1.b.

    3. 2.f. Метод. Вычисление определителя.
       - что лучше: сделать две переменные n = getRowsAmount() и m = getColumnsAmount(),
       или вместо них сделать одну matrixSize т.к. матрица квадратная,
       а для проверки кватратности матрицы - один раз вызвать getRowsAmount() != getColumnsAmount(),
       как это сделано сейчас?

    4. 3.a. Метод. Сложение матриц.
       - почему нельзя так:
         return resultMatrix.add(matrix2); ?

    5. MatrixMain:
       - 2.f. Метод. Вычисление определителя. Как правильно закоментить, чтобы не было варнинга.



       1. 1.d. что выгоднее: новую переменную завести temp или два раза getRows() посчитать?
       2. 1.b. можно ли тут применить foreach? По двум аргументам одновременно.
 */