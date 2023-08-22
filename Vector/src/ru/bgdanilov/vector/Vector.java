package ru.bgdanilov.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    // 1.a. Конструктор - создание пустого вектора размерностью size.
    public Vector(int size) {
        // Если size < 0 - бросаем исключение.
        if (size <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть больше нуля!");
        }

        // Код, выполняемый в отсутствии исключения.
        components = new double[size];
    }

    // 1.b. Конструктор - копирование из другого вектора.
    public Vector(Vector vector) {
        int size = vector.getSize(); // Размер вектора-источника.
        components = Arrays.copyOf(vector.components, size);
    }

    // 1.c. Конструктор - заполнение вектора значениями из массива.
    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть больше нуля!");
        }

        this.components = components;
    }

    // 1.d. Конструктор - заполнение вектора значениями из массива.
    // Если длина массива меньше size, то считать что в остальных компонентах 0.
    public Vector(int size, double[] array) {
        if (size <= 0 || array.length == 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть больше нуля!");
        }

        components = Arrays.copyOf(array, size);
    }

    // 2. Метод. Получение размерности вектора - по-сути длину массива компонент.
    public int getSize() {
        return components.length;
    }

    // Нужно в Matrix
    public double[] getComponents() {
        return components;
    }

    // 3. Метод. toString(), чтобы выдавал информацию о векторе в  формате { значения компонент через запятую }
    @Override
    public String toString() {
        int size = this.getSize();
        StringBuilder sb = new StringBuilder().append("{").append(components[0]);

        for (int i = 1; i < size; i++) {
            sb.append(", ").append(components[i]);
        }

        return sb.append("}").toString();
    }

    // 4.a. Добавить вектор к вектору.
    public void addVector(Vector additionalVector) {
        int sourceVectorSize = getSize();
        int additionalVectorSize = additionalVector.getSize();

        if (additionalVectorSize > sourceVectorSize) {
            this.components = Arrays.copyOf(additionalVector.components, additionalVectorSize);
        }

        for (int i = 0; i < additionalVectorSize; i++) {
            this.components[i] += additionalVector.components[i];
        }
    }

    // 4.b. Вычесть вектор из вектора.
    public void subtractVector(Vector deductedVector) {
        int deductedVectorSize = deductedVector.getSize();
        int sourceVectorSize = getSize();

        // Добавочный вектор дополним нулями до размерности исходного.
        if (deductedVectorSize > sourceVectorSize) {
            components = Arrays.copyOf(deductedVector.components, deductedVectorSize);
        }

        for (int i = 0; i < deductedVectorSize; i++) {
            components[i] -= deductedVector.components[i];
        }
    }

    // 4.c. Умножение вектора на скаляр.
    public void multiplyByScalar(double scalar) {
        int vectorSize = getSize();

        for (int i = 0; i < vectorSize; i++) {
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

        return Arrays.equals(components, vector.getComponents());
/*
        if (getSize() != vector.getSize()) {
            return false;
        }

        int size = getSize();

        for (int i = 0; i < size; i++) {
            if (components[i] != vector.components[i]) {
                return false;
            }
        }
*/
    }

    // Берем готовый метод получения Хэш массива.
    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }

    // 5.a. Сложение двух векторов.
    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);
        resultVector.addVector(vector2);

        return resultVector;
    }

    // 5.b. Вычитание двух векторов.
    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);
        resultVector.subtractVector(vector2);

        return resultVector;
    }

    // 5.c. Скалярное произведение двух векторов.
    public static double getDotProduct(Vector vector1, Vector vector2) {
        int smallerSize = Math.min(vector1.getSize(), vector2.getSize());
        double dotProduct = 0;

        for (int i = 0; i < smallerSize; i++) {
            dotProduct += vector1.components[i] * vector2.components[i];
        }

        return dotProduct;
    }
}

/*  Описание класса:
    ===============================
    - У вектора есть единственное поле - массив его компонент.
    - Конструкторы - функции с перегрузкой.
    - Здесь мы только бросаем исключения.Обработки нет.
      Обработка должна быть в main. Т.е. тут мы бросаем, а в маин ловим.
      Но блок try-catch по условию не требуется.

    1.d. Конструктор - заполнение вектора значениями из массива.
     - Если компоненты не поместились (длина меньше длины существующего вектора), то лишние значения игнорируются.
     - Если длина массива больше длины вектора, ячейки заполняются нулями.

    4.a. Метод "Добавить вектор к вектору".
    - К компонентам исходного вектора прибавить соответствующие компоненты добавляемого вектора.
    - Мы движемся в цикле по добавочному вектору, потому, что его компоненты добавляем к компонентам исходника.
    - Поэтому, если добавочный по размеру превышает исходник, то его компоненты все не влезут.
    - Нужно дополнить исходник до размеров добавочного.
    - А если наоборот, то меньшее всегда влезет в большее.
    - К компонентам исходника, которые за пределами размерности добавочного вектора, просто ничего не прибавится,
      что равносзначно прибавлению нуля. Поэтому дополнять нулями добавочный вектор до размеров исходного необязательно.
            source:     [1, 2, 3]     или  [1, 2, 3, 4]
          + additional: [1, 2, 3, 4]  или  [1, 2, 3]
          = source:     [2, 4, 6, 4]       [2, 4, 6, 4]

    4.b. Метод "Вычесть вектор из вектора".
    - Аналогично методу "Добавить вектор к вектору".

    5.c. Метод "Скалярное произведение двух векторов".
    - Скалярным произведением двух векторов называется сумма произведений соответствующих компонент векторов.
            vector1:    [1, 2, 3, 4]          или  [1, 2, 3]
          + vector2:    [1, 2, 3]             или  [1, 2, 3, 4]
          = dotProduct:  1 + 4 + 9 + (4 * 0)        1 + 4 + 9
          Если число итераций цикла будет равно большей размерности из двух вектров,
          то надо дополнять меньший вектор нулями.
          а если идти итерировать по меньшей размерности, то просто четверку ни на что не умножаем,
          и нулями заполнять не нужно.
          Можно так же, наверное, поступить в 4.a. ?
 */

/* TODO
    1. Проверить возможность реализации 4.a как в 5.c : через Math.min - нет.
    2. Как отказаться от использования переопределенной toString ?
        -- никак.
    3. Что происходит с массивами?
    4. 5.a. Сложение двух векторов.
    5. В чем разница: ?
        Vector vectorG = new Vector(Vector.additionVectors(vectorC, vectorF));
        Vector vectorH = Vector.additionVectors(vectorC, vectorF);
 */