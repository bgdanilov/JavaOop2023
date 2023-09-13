package ru.bgdanilov.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    // 1.a. Конструктор - создание пустого вектора размерностью size.
    public Vector(int size) {
        checkSize(size);
        components = new double[size];
    }

    // 1.b. Конструктор - копирование из другого вектора.
    public Vector(Vector vector) {
        // vector.components.length - размер вектора-источника.
        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    // 1.c. Конструктор - заполнение вектора значениями из массива.
    public Vector(double[] components) {
        this.components = Arrays.copyOf(components, components.length);
    }

    // 1.d. Конструктор - заполнение вектора значениями из массива.
    // Если длина массива меньше size, то считать что в остальных компонентах 0.
    public Vector(int size, double[] array) {
        checkSize(size);
        components = Arrays.copyOf(array, size);
    }

    // 2. Метод. Получение размерности вектора - по-сути длина массива компонент.
    public int getSize() {
        return components.length;
    }

    // 3. Метод. toString(), чтобы выдавал информацию о векторе в формате {значения компонент через запятую}
    @Override
    public String toString() {
        if (components.length == 0) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder().append('{');
        int maxIndex = components.length - 1;

        for (int i = 0; i < maxIndex; i++) {
            sb.append(components[i]).append(", ");
        }

        sb.append(components[maxIndex]).append('}');

        return sb.toString();
    }

    // 4.a. Добавить вектор к вектору.
    public void add(Vector vector) {
        if (vector.components.length > components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; i++) {
            components[i] += vector.components[i];
        }
    }

    // 4.b. Вычесть вектор из вектора.
    public void subtract(Vector vector) {
        // Исходный вектор дополним нулями до размерности добавочного.
        // Если добавочный больше.
        if (vector.components.length > components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; i++) {
            components[i] -= vector.components[i];
        }
    }

    // 4.c. Умножение вектора на скаляр.
    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= scalar;
        }
    }

    // 4.d. Разворот вектора. Умножение на -1.
    public void reverse() {
        multiplyByScalar(-1);
    }

    // 4.e. Получение длины вектора.
    public double getLength() {
        double sum = 0;

        for (double component : components) {
            sum += component * component;
        }

        return Math.sqrt(sum);
    }

    // 4.f. Получение и установка компоненты вектора по индексу.
    public double getComponent(int index) {
        return components[index];
    }

    public void setComponent(int index, double component) {
        components[index] = component;
    }

    // 4.g. Переопределение equals() и hashCode().
    @Override
    public boolean equals(Object object) {
        // Передан ли сам объект.
        if (this == object) {
            return true;
        }

        // Проверка на пустой объект, на отличный от Vector класс.
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        // Если все-таки это объект класса Vector,
        // нужно проверить длину и равенство компонент,
        // Для доступа к полям-компонентам, нужно привести Object к Vector.
        Vector vector = (Vector) object;

        return Arrays.equals(components, vector.components);
    }

    // Берем готовый метод получения Хэш массива.
    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }

    // 5.a. Сложение двух векторов.
    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);
        resultVector.add(vector2);

        return resultVector;
    }

    // 5.b. Вычитание двух векторов.
    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);
        resultVector.subtract(vector2);

        return resultVector;
    }

    // 5.c. Скалярное произведение двух векторов.
    public static double getDotProduct(Vector vector1, Vector vector2) {
        int minSize = Math.min(vector1.components.length, vector2.components.length);
        double dotProduct = 0;

        for (int i = 0; i < minSize; i++) {
            dotProduct += vector1.components[i] * vector2.components[i];
        }

        return dotProduct;
    }

    // Проверка размерности вектора.
    private void checkSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size: " + size + ", Отрицательная размерность вектора недопустима.");
        }
    }
}

/*  Заметки.
 *  ===================
 *  - У вектора есть единственное поле - массив его компонент.
 *    - Конструкторы - функции с перегрузкой.
 *
 *    1.d. Конструктор - заполнение вектора значениями из массива.
 *     - Если компоненты не поместились (длина меньше длины существующего вектора), то лишние значения игнорируются.
 *     - Если длина массива больше длины вектора, ячейки заполняются нулями.
 *
 *   4.a. Метод "Добавить вектор к вектору".
 *    - К компонентам исходного вектора прибавить соответствующие компоненты добавляемого вектора.
 *    - Мы движемся в цикле по добавочному вектору, потому, что его компоненты добавляем к компонентам исходника.
 *    - Поэтому, если добавочный по размеру превышает исходник, то его компоненты все не влезут.
 *    - Нужно дополнить исходник до размеров добавочного.
 *    - А если наоборот, то меньшее всегда влезет в большее.
 *    - К компонентам исходника, которые за пределами размерности добавочного вектора, просто ничего не прибавится,
 *      что равнозначно прибавлению нуля. Поэтому дополнять нулями добавочный вектор до размеров исходного необязательно.
 *            source:     [1, 2, 3]     или  [1, 2, 3, 4]
 *          + additional: [1, 2, 3, 4]  или  [1, 2, 3]
 *          = source:     [2, 4, 6, 4]       [2, 4, 6, 4]
 *
 *    4.b. Метод "Вычесть вектор из вектора".
 *    - Аналогично методу "Добавить вектор к вектору".
 *
 *    5.c. Метод "Скалярное произведение двух векторов".
 *    - Скалярным произведением двух векторов называется сумма произведений соответствующих компонент векторов.
 *            vector1:    [1, 2, 3, 4]          или  [1, 2, 3]
 *          + vector2:    [1, 2, 3]             или  [1, 2, 3, 4]
 *          = dotProduct:  1 + 4 + 9 + (4 * 0)        1 + 4 + 9
 *          Если число итераций цикла будет равно большей размерности из двух векторов,
 *          то надо дополнять меньший вектор нулями.
 *          А если идти итерировать по меньшей размерности, то просто четверку ни на что не умножаем,
 *          и нулями заполнять не нужно.
 */