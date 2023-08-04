package ru.bgdanilov.shapes_comparators;

import ru.bgdanilov.shapes.Shape;

import java.util.Comparator;

// Что значит <Shape> ?
public class ShapesAreaComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        return Double.compare(shape1.getArea(), shape2.getArea());
    }
}

