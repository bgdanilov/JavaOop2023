package ru.bgdanilov.vector_main;

import ru.bgdanilov.vector.Vector;

public class VectorMain {
    public static void main(String[] args) {
        try {
            // 1. Конструкторы.
            // -------------------------
            // 1.a. Создаем нулевой вектор размерностью 7.
            System.out.println("1.a. Создаем нулевой вектор размерностью 7.");
            Vector vector1 = new Vector(7);
            System.out.println("     Вектор 1: " + vector1);

            /* 1.c. Создаем вектор из значений массива. */
            System.out.println("1.c. Создаем вектор из значений массива.");
            Vector vector3 = new Vector(new double[]{1, 2, 3, 4, 5});
            System.out.println("     Вектор 3: " + vector3);

            // 1.b. Создаем вектор копированием из другого вектора.
            System.out.println("1.b. Создаем Вектор 2 копированием из другого Вектора 3.");
            Vector vector2 = new Vector(vector3);
            System.out.println("     Вектор 2: " + vector2);

            // 1.d. Создаем вектор и производим заполнение вектора значениями из массива.
            System.out.println("1.d. Создаем вектор размерностью 10 и производим заполнение значениями из массива.");
            Vector vector4 = new Vector(10, new double[]{1, 2, 3, 4, 5});
            System.out.println("     Вектор 4: " + vector4);
            System.out.println();

            // 2. Метод getSize() для получения размерности вектора.
            System.out.println("2. Метод getSize() для получения размерности вектора.");
            System.out.println("   Размер вектора 4: " + vector4.getSize());
            System.out.println();

            // 3. Реализовать метод toString() - проверено выше.
            System.out.println("3. Реализовать метод toString() - проверено выше.");
            System.out.println();

            // 4. Реализовать нестатические методы:
            // ---------------------------------------
            System.out.println("4. Реализовать нестатические методы:");
            // 4.a. Прибавление к вектору другого вектора.
            vector2.add(vector4);
            System.out.println("4.a. К Вектору 2 добавляем Вектор 4: " + vector2);

            // 4.b. Вычесть вектор из вектора.
            vector4.subtract(vector2);
            System.out.println("4.b. Из Вектора 4 вычитаем Вектор 2: " + vector4);

            // 4.c. Умножение вектора на скаляр.
            vector3.multiplyByScalar(10);
            System.out.println("4.c. Умножаем Вектор 3 на 10: " + vector3);

            // 4.d. Разворот вектора. Умножение на -1.
            vector3.reverse();
            System.out.println("4.d. Разворот Вектора 3: " + vector3);

            // 4.e. Получение длины вектора.
            System.out.printf("4.e. Получение длины Вектора 3: %.2f%n", vector3.getLength());

            // 4.f. Получение и установка компоненты вектора по индексу.
            // Создадим новый нулевой Вектор 5.
            Vector vector5 = new Vector(3);
            System.out.println("4.f. Устанавливаем компоненту 99 на место 1. Вектор 5: " + vector5);
            // Зададим компоненту.
            vector5.setComponent(1, 99);
            System.out.println("     В итоге Вектор 5: " + vector5);
            System.out.println("4.f. Получим компоненту Вектора 5 с индексом 1: " + vector5.getComponent(1));

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

            // 5. Реализовать статические методы:
            // --------------------------------------
            System.out.println();

            System.out.println("5. Реализовать статические методы:");
            // 5.a. Сложение двух векторов.
            // Это по-сути Конструктор "с".
            Vector vector6 = new Vector(Vector.getSum(vector3, vector5));
            // А можно так - вызываем статический метод просто из класса...
            Vector vector7 = Vector.getSum(vector3, vector5);
            // ... но почему не нужно слово "new" ?
            System.out.println("5.a. Результат сложения векторов 3 и 5 = Вектор 6: " + vector6);
            System.out.println("5.a. Тоже самое, только вызов метода не от объекта = Вектор 7: " + vector7);
            System.out.println("     Для справки vector3: " + vector3);
            System.out.println("     Для справки vector5: " + vector5);
            System.out.println();

            // 5.b. Вычитание двух векторов.
            Vector vector8 = Vector.getDifference(vector6, vector5);
            System.out.println("5.b. Результат разности векторов 6 и 5 = Вектор 8: " + vector8);
            System.out.println("     Для справки vector6: " + vector6);
            System.out.println("     Для справки vector5: " + vector5);
            System.out.println();

            // 5.c. Скалярное произведение двух векторов.
            double dotProduct = Vector.getDotProduct(vector1, vector5);
            System.out.printf("5.c. Результат скалярного произведения векторов 1 и 5 (должно быть 0) = %.2f%n", dotProduct);
            System.out.println("     Для справки vector1: " + vector1);
            System.out.println("     Для справки vector5: " + vector5);
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}