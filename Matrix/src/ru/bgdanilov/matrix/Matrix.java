package ru.bgdanilov.matrix;

import ru.bgdanilov.vector.Vector;

public class Matrix {
    private Vector[] vectors; // строки.

    // 1.a. Конструктор - создание пустой матрицы размера n*m.
    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Размерность матрицы не может быть ноль на ноль и меньше.");
        }

        // Создаем строки матрицы - массив объектов Vector c n-элементами.
        vectors = new Vector[n];

        // Теперь нужно в каждый элемент массива matrix, поместить вектор с m-элементами.
        for (int i = 0; i < n; i++) {
            // Используем создание конструктор Vector 1.a.
            vectors[i] = new Vector(m);
        }
    }

    // 1.b. Конструктор копирования - Matrix(Matrix).
    public Matrix(Matrix sourceMatrix) {
        // Получаем количество строк (векторов) в исходной матрице.
        int n = sourceMatrix.getRowsAmount();

        // Создаем новый массив векторов с тем же количеством векторов.
        vectors = new Vector[n];

        // Заполняем созданный массив новыми векторами,
        // созданными из векторов матрицы переданного объекта sourceMatrix.
        for (int i = 0; i < n; i++) {
            vectors[i] = new Vector(sourceMatrix.vectors[i]);
        }
    }

    // 1.c.	Конструктор - создание из Matrix(double[][]) – из двумерного массива.
    public Matrix(double[][] sourceArray) {
        int n = sourceArray.length; // Число строк.
        int m = 0;                  // Число столбцов - размерность векторов.

        // Ищем максимальный размер подмассива в переданном массиве.
        for (double[] subArrays : sourceArray) {
            int temp = subArrays.length;

            if (temp == 0) {
                throw new IllegalArgumentException("Размер подмассива должен быть больше нуля!");
            }

            if (temp > m) {
                m = temp;
            }
        }

        // Создаем новый массив векторов с тем же количеством элементов.
        vectors = new Vector[n];

        for (int i = 0; i < n; i++) {
            // Используем 1.d. Конструктор - заполнение вектора значениями из массива.
            vectors[i] = new Vector(m, sourceArray[i]);
        }
    }

    // 1.d. Конструктор	Matrix(Vector[]) – из массива векторов-строк.
    public Matrix(Vector[] sourceVectors) {
        int n = sourceVectors.length;  // Количество векторов в массиве.
        // Создаем новый массив векторов (матрицу) с тем же количеством элементов.
        vectors = new Vector[n];
        // Ищем максимальный размер среди векторов массива.
        int m = 0;

        for (Vector vector : sourceVectors) {
            int temp = vector.getSize();

            if (temp > m) {
                m = temp;
            }
        }

        // Заполняем матрицу новыми векторами, созданными из исходных векторов.
        for (int i = 0; i < n; i++) {
            vectors[i] = new Vector(sourceVectors[i]);

            // Проверяем на совпадение с максимальным размером, при необходимости
            // дополняем нулями при помощи дополнения временного нового вектора размера m.
            if (vectors[i].getSize() < m) {
                vectors[i].add(new Vector(m));
            }
        }
    }

    // 2.a. Метод. Получение размеров матрицы.
    public int getRowsAmount() {
        return vectors.length; // n
    }

    public int getColumnsAmount() {
        return vectors[0].getSize(); // m
    }

    // 2.b. Метод. Получение и задание вектора-строки по индексу.
    public Vector getRow(int index) {
        if (index < 0 || index > getRowsAmount() - 1) {
            throw new IllegalArgumentException("Неверный индекс.");
        }

        return new Vector(vectors[index]);
    }

    public void setRow(Vector sourceVector, int index) {
        int n = this.getRowsAmount();
        int m = this.getColumnsAmount();

        // index считается от 0, а n - от 1.
        if (sourceVector.getSize() > m || (index < 0 || index > n - 1)) {
            throw new IllegalArgumentException("Векторы не совпадают по размеру или неверный индекс.");
        }

        for (int i = 0; i < m; i++) {
            this.vectors[index].setComponent(i, sourceVector.getComponent(i));
        }
    }

    // 2.c. Метод. Получение вектора-столбца по индексу.
    public Vector getColumn(int index) {
        if (index < 0 || index > getColumnsAmount() - 1) {
            throw new IllegalArgumentException("Неверный индекс.");
        }

        int n = getRowsAmount();
        Vector outputVector = new Vector(n);

        for (int i = 0; i < n; i++) {
            outputVector.setComponent(i, vectors[i].getComponent(index));
        }

        return outputVector;
    }

    // 2.d. Метод. Транспонирование матрицы.
    public void transpose() {
        int m = getColumnsAmount();

        Vector[] temp = new Vector[m];

        for (int i = 0; i < m; i++) {
            temp[i] = getColumn(i);
        }

        vectors = temp;
    }

    // 2.e. Метод. Умножение на скаляр.
    public void multiplyByScalar(double scalar) {
        for (Vector row : vectors) {
            row.multiplyByScalar(scalar);
        }
    }

    // 2.f. Метод. Вычисление определителя.
    public double getDeterminant() {
        if (getRowsAmount() != getColumnsAmount()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной!");
        }

        Matrix triangleMatrix = new Matrix(vectors);

        int matrixSize = getRowsAmount();
        int swapsCounter = 0;

        for (int i = 0; i < matrixSize - 1; i++) { // цикл по столбцам.
            for (int j = i + 1; j < matrixSize; j++) { //  цикл по строкам.
                // Если на диагонали ноль, то "опускаем" его вниз, меняя строку с нижеидущей.
                if (triangleMatrix.vectors[i].getComponent(i) == 0) {
                    // Меняем строки местами и на следующую итерацию.
                    Vector temp = triangleMatrix.getRow(j);  //  это новый вектор.
                    triangleMatrix.vectors[j] = triangleMatrix.getRow(i);
                    triangleMatrix.vectors[i] = temp;
                    swapsCounter++;

                    continue;
                }

                // Добавляем к строке вышестоящую строку, умноженую на число,
                // чтобы получить ноль под элементом диагонали.
                double rowMultiplier = -(triangleMatrix.vectors[j].getComponent(i)) / triangleMatrix.vectors[i].getComponent(i);
                Vector temp = triangleMatrix.getRow(i);
                temp.multiplyByScalar(rowMultiplier);
                triangleMatrix.vectors[j].add(temp);
            }
        }

        double determinant = 1;

        for (int i = 0; i < matrixSize; i++) {
            determinant *= triangleMatrix.vectors[i].getComponent(i);
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
        int n = vectors.length;

        for (int i = 0; i < n - 1; i++) {
            sb.append(vectors[i]).append(", ");
        }

        sb.append(vectors[n - 1]).append("}");

        return sb.toString();
    }

    // 2.i. Метод. Сложение матриц.
    public void add(Matrix matrix) {
        if (this.getColumnsAmount() != matrix.getColumnsAmount()
                && this.getRowsAmount() != matrix.getRowsAmount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размрности!");
        }

        int matrixSize = getRowsAmount();

        for (int i = 0; i < matrixSize; i++) {
            this.vectors[i].add(matrix.getRow(i));
        }
    }

    // 2.j. Метод. Вычитание матриц.
    public void subtract(Matrix matrix) {
        if (this.getColumnsAmount() != matrix.getColumnsAmount()
                && this.getRowsAmount() != matrix.getRowsAmount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!");
        }

        int matrixSize = getRowsAmount();

        for (int i = 0; i < matrixSize; i++) {
            this.vectors[i].subtract(matrix.getRow(i));
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
            throw new IllegalArgumentException("Число столбцов первой матрицы должно быть равно числу строк второй!");
        }
        int resultMatrixSize = matrix1.getRowsAmount();

        Matrix resultMatrix = new Matrix(resultMatrixSize, resultMatrixSize);

        // Скалярное произведение строк на столбцы.
        for (int i = 0; i < resultMatrixSize; i++) {
            Vector row = new Vector(matrix1.getRow(i)); // берем строку матрицы 1.
            Vector resultRow = new Vector(resultMatrixSize); // создаем пустую строку для результата.
            for (int j = 0; j < resultMatrixSize; j++) {
                Vector column = new Vector(matrix2.getColumn(j)); // берем столбец матрицы 2.
                // Заполняем результирующую строку покомпонентно.
                resultRow.setComponent(j, Vector.getDotProduct(row, column));
            }

            // Заполняем результирующими строками результирующую матрицу.
            resultMatrix.setRow(resultRow, i);
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
    число столбцов первой равно числу строк второй.
    - произведением двух матриц есть матрица,
    у которой столько строк, сколько их у левого сомножителя,
    и столько столбцов, сколько их у правого сомножителя.
    
    Вопросы:
    ===============================
    1. Изначально у меня импортировался встроенный класс Vector
       и я ломал голову, что методы не работают.
       Я попыталься вручную прописать импорт своего Vector, но не получалось,
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
       - почему нельзя так: return resultMatrix.add(matrix2); ?
 */