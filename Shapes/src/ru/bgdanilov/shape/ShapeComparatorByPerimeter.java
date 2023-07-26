package ru.bgdanilov.shape;
import java.util.Comparator;
/*
Можно ли добавить в compare() третий аргумент Field, передающий
поле для сортировки: getArea() или getPerimeter() ?
Тогда можно было бы обойтись одним классом вроде ShapeComparatorByField.
 */
public class ShapeComparatorByPerimeter implements Comparator<Shape> {
    @Override
    public int compare(Shape object1, Shape object2) {
        return Double.compare(object2.getPerimeter(), object1.getPerimeter());
    }
    /*
    1. Можно менять местами object2 и object1
    в Double.compare(object2.getPerimeter(), object1.getPerimeter())
    - тогда изменится порядок (убывание или возрастание).
    2. Можно отсортировать в Main массив в обратном порядке:
    Arrays.sort(shapes, new ShapeComparatorByArea()).reversed()
     */
}