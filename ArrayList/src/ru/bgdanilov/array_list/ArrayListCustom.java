package ru.bgdanilov.array_list;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ArrayListCustom<E> implements List<E> {
    E[] items;
    private int size; // количество реальных элементов, не путать с вместимостью.
    private int capacity = 10;

    // 1. Конструктор, принимающий capacity - вместимость.
    public ArrayListCustom() {
        size = 0;
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayListCustom(int capacity) {
        size = 0;
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");

        for (int i = 0; i < capacity; i++) {
            if (items[i] != null) {
                sb.append(items[i]).append(", ");
            }
        }

        return sb.append("}").toString();
    }

    // 2. Метод. Возвращает количество элементов списка.
    @Override
    public int size() {
        return size;
    }

    // 3. Метод. Пустой ли список.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // 4. Метод. Содержится ли элемент в списке?
    // TODO: Пока непонятно.
    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    // 5. Не надо делать по условию.
    public Iterator<E> iterator() {
        return null;
    }

    // 6. Метод. Преобразует список в массив.
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int i) {
        return null;
    }

    @Override
    public E set(int i, E e) {
        return null;
    }

    @Override
    public void add(int i, E e) {
        items[i] = e;
    }

    @Override
    public E remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return null;
    }

    @Override
    public List<E> subList(int i, int i1) {
        return null;
    }
}
