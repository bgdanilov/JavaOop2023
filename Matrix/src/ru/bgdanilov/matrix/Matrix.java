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

        // Создаем новый массив векторов с тем же количеством элементов.
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
        int size = 0;

        for (Vector vector : sourceVectors) {
            int temp = vector.getSize();

            if (temp > size) {
                size = temp;
            }
        }

        // Заполняем матрицу новыми векторами, созданными из исходных векторов.
        for (int i = 0; i < n; i++) {
            vectors[i] = new Vector(sourceVectors[i]);

            // Проверяем на совпадение с максимальным размером, при необходимости
            // дополняем нулями при помощи дополнения временного нового вектора размера size.
            if (vectors[i].getSize() < size) {
                vectors[i].add(new Vector(size));
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
        int n = getRowsAmount();

        if (index < 0 || index > n - 1) {
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
        int n = getRowsAmount();
        int m = getColumnsAmount();

        if (index < 0 || index > m - 1) {
            throw new IllegalArgumentException("Неверный индекс.");
        }

        Vector outputVector = new Vector(n);

        for (int i = 0; i < n; i++) {
            outputVector.setComponent(i, vectors[i].getComponent(index));
        }

        return outputVector;
    }

    // 2.d. Метод. Транспонирование матрицы.
    public void transpose() {
        int m = getColumnsAmount(); // 3;

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
        int n = getRowsAmount();
        int m = getColumnsAmount();

        if (n != m) {
            throw new IllegalArgumentException("Матрица должна быть квадратной!");
        }

        int swapCount = 0;

        for (int i = 0; i < m - 1; i++) { // цикл по столбцам.
            for (int j = i + 1; j < n; j++) { //  цикл по строкам.
                if (vectors[i].getComponent(i) == 0) {
                    // Меняем строки местами.
                    Vector tmp = getRow(j);  //  это новый вектор.
                    vectors[j] = getRow(i);
                    vectors[i] = tmp;
                    swapCount++;

                    continue;
                }

                double mull = -(vectors[j].getComponent(i)) / vectors[i].getComponent(i); // множитель для первой строки.
                Vector temp = getRow(i);
                temp.multiplyByScalar(mull);
                vectors[j].add(temp);
            }
        }

        double det = 1;

        for (int i = 0, j = 0; i < m && j < n; i++, j++) {
            det *= vectors[i].getComponent(j);
        }

        if (swapCount % 2 != 0) {
            return -det;
        }

        return det;
    }
    /*public double getDeterminant() {
        int n = getRowsAmount();
        int m = getColumnsAmount();

        if (n != m) {
            throw new IllegalArgumentException("Матрица должна быть квадратной!");
        }

        Matrix triangle = new Matrix(this.vectors);

        int swapCount = 0;

        for (int i = 0; i < m - 1; i++) { // цикл по столбцам.
            for (int j = i + 1; j < n; j++) { //  цикл по строкам.
                if (triangle.vectors[i].getComponent(i) == 0) {
                    // Меняем строки местами.
                    Vector tmp = triangle.getRow(j);  //  это новый вектор.
                    triangle.vectors[j] = triangle.getRow(i);
                    triangle.vectors[i] = tmp;
                    swapCount++;
                }

                double mull = -(triangle.vectors[j].getComponent(i)) / triangle.vectors[i].getComponent(i); // множитель для первой строки.
                Vector temp = triangle.getRow(i);
                temp.multiplyByScalar(mull);
                triangle.vectors[j].add(temp);
            }
        }

        double det = 1;

        for (int i = 0, j = 0; i < m && j < n; i++, j++) {
            det *= triangle.vectors[i].getComponent(j);
        }

        if (swapCount % 2 != 0) {
            return -det;
        }

        return det;
    }*/

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

        int n = getRowsAmount();

        for (int i = 0; i < n; i++) {
            this.vectors[i].add(matrix.getRow(i));
        }
    }

    // 2.j. Метод. Вычитание матриц.
    public void subtract(Matrix matrix) {
        if (this.getColumnsAmount() != matrix.getColumnsAmount()
                && this.getRowsAmount() != matrix.getRowsAmount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размрности!");
        }

        int n = getRowsAmount();

        for (int i = 0; i < n; i++) {
            this.vectors[i].subtract(matrix.getRow(i));
        }
    }
}

/*
    Описание класса:
    ===============================


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

    n - число строк.
    m - число колонок.
 */

/* TODO вопросы.
    1. Сделать проверку-добивку нулями в отдельную функцию.
    - сделано: improveSize.
        - удалено.
    2. Конструктор 1.d: проверить this.matrix = vectors;
    - это у нас просто ссылка копируется или новый объект создается как положено?
    - вообще, разобраться с ссылочными данными и как они копируются, создаются, где хранятся.
        - все верно, то была ссылка на vectors, а не создание нового объекта.
        - если бы vectors, то и наша новая матрица бы поменялась. Это неправильно.
    3. Метод 2.b. задание строки: нужно ли стобы влезала строка несовпадающей размерности?
    4. В самом классе. Поменять поле Vector[] matrix на vectors - это же массив векторов, а не матрица?
       2.f. Метод. Вычисление определителя. плохо звучит: triangle.matrix
 */