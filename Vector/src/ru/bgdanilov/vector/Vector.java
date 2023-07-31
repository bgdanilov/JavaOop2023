package ru.bgdanilov.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    // 1.a. Конструктор - создание пустого вектора размерностью n.
    public Vector(int size) {
        try {
            // Если n < 0 - бросаем исключение.
            if (size <= 0) {
                throw new IllegalArgumentException();
            }
            // Код, выполняемый в отсутствии исключения.
            this.components = new double[size];
        }
        // Обрабатываем исключение.
        catch (IllegalArgumentException e) {
            System.out.println("n must be > 0");
        }
    }

    // 1.b. Конструктор - копирование из другого вектора.
    public Vector(Vector vector) {
        int size = vector.getSize(); // Размер вектора-источника.
        this.components = Arrays.copyOf(vector.components, size);
    }

    // 1.c. Конструктор - заполнение вектора значениями из массива.
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

    // 1.d. Конструктор - заполнение вектора значениями из массива.
    // Если длина массива меньше n, то считать что в остальных компонентах 0.
    public Vector(int size, double[] array) {
        try {
            if (size <= 0 || array.length == 0) {
                throw new IllegalArgumentException();
            }

            this.components = Arrays.copyOf(array, size);
        } catch (IllegalArgumentException e) {
            System.out.println("n must be > 0");
        }
    }

    // 2. Метод. Получение размерности вектора - по-сути длину массива компонент.
    public int getSize() {
        return this.components.length;
    }

    // 3. Метод. toString(), чтобы выдавал информацию о векторе в  формате { значения компонент через запятую }
    @Override
    public String toString() {
        int size = this.getSize();
        StringBuilder sb = new StringBuilder().append("{ ").append(this.components[0]);

        for (int i = 1; i < size; i++) {
            sb.append(", ").append(components[i]);
        }

        return sb.append(" }").toString();
    }

    // 4.a. Добавить вектор к вектору.
    public void addVector(Vector additionalVector) {
        int sourceVectorSize = this.getSize();
        int additionalVectorSize = additionalVector.getSize();

        if (additionalVectorSize > sourceVectorSize) {
            this.components = Arrays.copyOf(additionalVector.components, additionalVectorSize);
        }

        for (int i = 0; i < sourceVectorSize; i++) {
            this.components[i] += additionalVector.components[i];
        }
    }

    // 4.b. Вычесть вектор из вектора.
    public void subtractVector(Vector deductedVector) {
        int deductedVectorSize = deductedVector.getSize();
        int sourceVectorSize = this.getSize();

        // Добавочный вектор дополним нулями до размерности исходного.
        if (deductedVectorSize < sourceVectorSize) {
            deductedVector.components = Arrays.copyOf(deductedVector.components, sourceVectorSize);
        }

        for (int i = 0; i < sourceVectorSize; i++) {
            this.components[i] -= deductedVector.components[i];
        }
    }

    // 4.c. Умножение вектора на скаляр.
    public void multiplicationByScalar(double scalar) {
        int vectorSize = this.getSize();

        for (int i = 0; i < vectorSize; i++) {
            this.components[i] *= scalar;
        }
    }

    // 4.d. Разворот вектора. Умножение на -1.
    public void reverseVector() {
        this.multiplicationByScalar(-1);
    }

    // 4.e. Получение длины веектора.
    public double getLength() {
        int size = this.getSize();
        double sqrtExpression = 0;

        for (int i = 0; i < size; i++) {
            sqrtExpression += Math.pow(this.components[i], 2);
        }

        return Math.sqrt(sqrtExpression);
    }

    // 4.f. Получение и установка компоненты вектора по индексу.
    public double getComponent(int index) {
        return this.components[index];
    }

    public void setComponent(int index, double component) {
        this.components[index] = component;
    }

    // 5.a. Сложение двух векторов.
    public static Vector additionVectors(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);
        resultVector.addVector(vector2);

        return resultVector;
    }

    // 5.b. Вычитание двух векторов.
    public static Vector subtractionVectors(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);
        resultVector.subtractVector(vector2);

        return resultVector;
    }

    // 5.c. Скалярное произведение двух векторов.
    public static double dotProductVectors(Vector vector1, Vector vector2) {
        int size = Math.min(vector1.getSize(), vector2.getSize());
        double dotProduct = 0;

        for (int i = 0; i < size; i++) {
            dotProduct += vector1.components[i] * vector2.components[i];
        }

        return dotProduct;
    }

    // Метод. Задание компонент существующего вектора вручную.
    public void setComponents(double[] components) {
        this.components = components;
    }
}

/*  Описание класса:
    ===============================
    - У вектора есть единственное поле - массив его компонент.
    - Конструкторы - функции с перегрузкой.

    1.d. Конструктор - заполнение вектора значениями из массива.
     - Если компоненты не поместились (длина меньше длины существующего вектора), то лишние значения игнорируются.
     - Если длина массива больше длины вектора, ячейки заполняются нулями.

    4.a. Метод "Добавить вектор к вектору".
    - Значит к компонентам исходного вектора прибавить соответствующие компоненты добавляемого вектора.
    - Мы движемся в цикле по добавочному вектору, потому, что его компоненты добавляем  к компонентам исходника.
    - Поэтому, если добавочный по размеру превышает исходник, то его компоненты все не влезут.
    - Нужно дополнить исходник до размеров добавочного.
    - А если наоборот, то меньшее всегда влезет в большее.
    - К компонентам исходника, которые за пределами размерности бобавочного вектора, просто ничего не прибавится,
      что равносзначно прибавлению нуля. Поэтому дополнять нулями добавочный вектор до размеров исходного необязательно.
            source:     [1, 2, 3]     или  [1, 2, 3, 4]
          + additional: [1, 2, 3, 4]  или  [1, 2, 3]
          = source:     [2, 4, 6, 4]       [2, 4, 6, 4]

    4.b. Метод "Вычесть вектор из вектора".
    - Аналогично методу "Добавить вектор к вектору".

    5.c. Метод "Скалярное произведение двух векторов".
    - Скалярным произведением двух векторов называется сумма произведений соответствующих компонент векторов.
            vector1:    [1, 2, 3, 4]         или  [1, 2, 3]
          + vector2:    [1, 2, 3]            или  [1, 2, 3, 4]
          = dotProduct:  1 + 4 + 9 + 4 * 0         1 + 4 + 9
          Если число итераций цикла будет равно большей размерности из двух вектров,
          то надо дополнять меньший вектор нулями.
          а если идти итерировать по меньшей размерности, то просто четверку ни на что не умножаем,
          и нулями заполнять не нужно.
          Можно так же, наверное, поступить в 4.a. ?
 */

/* TODO
    1. Проверить возможность реализации 4.a как в 5.c : через Math.min .
 */