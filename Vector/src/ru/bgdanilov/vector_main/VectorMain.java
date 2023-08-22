package ru.bgdanilov.vector_main;

import ru.bgdanilov.vector.Vector;

public class VectorMain {
    public static void main(String[] args) {
        // 1. Конструкторы.
        // -------------------------
        // 1.a. Создаем нулевой вектор размерностью 10.
        Vector vectorA = new Vector(10);
        System.out.println("Вектор A: " + vectorA);

        // 1.c. Создаем вектор из значений массива.
        Vector vectorC = new Vector(new double[]{1, 2, 3, 4, 5});
        System.out.println("Вектор C: " + vectorC);

        // 1.b. Создаем вектор копированием из другого вектора.
        Vector vectorB = new Vector(vectorC);
        System.out.println("Вектор B: " + vectorB);

        // 1.d. Создаем вектор и производим заполнение вектора значениями из массива.
        Vector vectorD = new Vector(10, new double[]{1, 2, 3, 4, 5});
        System.out.println("Вектор D: " + vectorD.toString());

        // 2. Метод getSize() для получения размерности вектора
        System.out.println("Размер вектора D: " + vectorD.getSize());

        // 3. Реализовать метод toString() - проверено выше.
        System.out.println();

        // 4. Реализовать  нестатические методы:
        // ---------------------------------------
        System.out.println("4. Реализовать нестатические методы:");
        // 4.a. Прибавление к вектору другого вектора.
        vectorB.addVector(vectorD);
        System.out.println("4.a. К Вектору B добавляем Вектор D: " + vectorB);

        // 4.b. Вычесть вектор из вектора.
        vectorD.subtractVector(vectorB);
        System.out.println("4.b. Из Вектора D вычитаем Вектор B: " + vectorD);

        // 4.c. Умножение вектора на скаляр.
        vectorC.multiplyByScalar(10);
        System.out.println("4.c. Умножаем Вектор C на 10: " + vectorC);

        // 4.d. Разворот вектора. Умножение на -1.
        vectorC.reverse();
        System.out.println("4.d. Разворот Вектора C: " + vectorC);

        // 4.e. Получение длины вектора.
        System.out.printf("4.e. Получение длины Вектора C: %.2f%n", vectorC.getLength());

        // 4.f. Получение и установка компоненты вектора по индексу.
        // Создадим новый нулевой Вектор F.
        Vector vectorF = new Vector(3);
        // Зададим компоненту.
        vectorF.setComponent(1, 99);
        System.out.println("4.f. Устанавливаем компоненту 99 на место 1. Вектор F: " + vectorF);
        System.out.println("4.f. Установленная компонента Вектора F с индекском 1: " + vectorF.getComponent(1));

        // 4.g. Переопределение equals() и hashCode().
        System.out.println("4.g. Равен ли Вектор {1, 2, 3} Вектору {1, 2, 3} ?: "
                + new Vector(3, new double[]{1, 2, 3}).equals(new Vector(3, new double[]{1, 2, 3}))
        );
        System.out.println("4.g. Равен ли Вектор {0, 2, 3} Вектору {1, 2, 3} ?: "
                + new Vector(3, new double[]{0, 2, 3}).equals(new Vector(3, new double[]{1, 2, 3}))
        );
        System.out.println("4.g. Равен ли Вектор {2, 3} Вектору {1, 2, 3} ?: "
                + new Vector(2, new double[]{2, 3}).equals(new Vector(2, new double[]{1, 2, 3}))
        );

        // 4.g. Переопределение hashCode().
        System.out.println("4.g. Хэш вектора {1, 2, 3} равен: "
                + new Vector(3, new double[]{1, 2, 3}).hashCode());
        System.out.println("4.g. Хэш еще одного вектора {1, 2, 3} равен: "
                + new Vector(3, new double[]{1, 2, 3}).hashCode());


        // 5. Реализовать  статические методы:
        // --------------------------------------
        System.out.println();
        System.out.println("5. Реализовать статические методы:");
        // 5.a. Сложение двух векторов.

        // Это по-сути Конструктор "с".
        Vector vectorG = new Vector(Vector.getSum(vectorC, vectorF));
        // А можно так - вызываем статический метод просто из класса...
        Vector vectorH = Vector.getSum(vectorC, vectorF);
        // ... но почему не нужно слово  "new" ?
        System.out.println("5.a. Результат сложения векторов C и F = Вектор G: " + vectorG);

        // 5.b. Вычитание двух векторов.
        Vector vectorI = Vector.getDifference(vectorG, vectorF);
        System.out.println("5.b. Результат разности векторов G и F = Вектор I: " + vectorI);

        // 5.c. Скалярное произведение двух векторов.
        double dotProduct = Vector.getDotProduct(vectorA, vectorF);
        System.out.printf("5.c. Результат скалярного произведения веторов A и F (должно быть 0) = %.2f%n", dotProduct);
    }
}