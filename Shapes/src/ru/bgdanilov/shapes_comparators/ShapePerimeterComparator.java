package ru.bgdanilov.shapes_comparators;

import ru.bgdanilov.shapes.Shape;

import java.util.Comparator;

public class ShapePerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        return Double.compare(shape1.getPerimeter(), shape2.getPerimeter());
    }
}

/*
 * Заметки.
 * ===================
 * 1. Можно менять местами object2 и object1
 * в Double.compare(object2.getPerimeter(), object1.getPerimeter())
 * тогда изменится порядок (убывание или возрастание).
 * <p>
 * 2. Можно отсортировать в Main массив в обратном порядке:
 * Arrays.sort(shapes, new ShapeComparatorByArea()).reversed()
 */