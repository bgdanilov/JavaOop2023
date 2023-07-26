package ru.bgdanilov.shape;
import java.util.Comparator;

// Что значит <Shape> ?
public class ShapeComparatorByArea implements Comparator<Shape> {
    @Override
    public int compare(Shape object1, Shape object2) {
        return Double.compare(object2.getArea(), object1.getArea());
    }
}

