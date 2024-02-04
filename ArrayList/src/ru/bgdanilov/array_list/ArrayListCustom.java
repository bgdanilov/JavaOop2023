package ru.bgdanilov.array_list;

import java.util.*;


public class ArrayListCustom<E> implements List<E> {
    E[] items;
    private int size; // количество реальных элементов, не путать с вместимостью.
    private int modCount = 0; // вместимость.

    // 1. Конструктор, принимающий capacity - вместимость.
    public ArrayListCustom() {
        size = 0;
        //noinspection unchecked
        items = (E[]) new Object[10];
    }

    public ArrayListCustom(int capacity) {
        size = 0;
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }


    // Метод. Возвращает вместимость списка.
    public int getCapacity() {
        return items.length;
    }

    // Увеличить вместимость списка при необходимости.
    public void ensureCapacity() {
        if (size >= getCapacity()) {
            items = Arrays.copyOf(items, (3 * size) / 2 + 1);
        }
    }

    // Вложенный класс для итератора.
    private class MyListIterator implements Iterator<E> {
        private int currentIndex = -1; // текущий индеск в списке.
        private int modCount = ArrayListCustom.this.modCount; // количество изменений.

        public boolean hasNext() {
            // TODO: next должен кидать исключение NoSuchElementException, если коллекция кончилась
            // TODO: next должен кидать исключение ConcurrentModificationException,
            //       если в коллекции добавились/удалились элементы за время обхода.
            return currentIndex + 1 < size;
        }

        public E next() {
            return items[currentIndex];
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{").append(items[0]);

        for (int i = 1; i < size(); i++) {
            sb.append(", ").append(items[i]);
        }

        return sb.append("}").toString();
    }

    // Метод. Возвращает количество элементов списка.
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
    @Override
    public boolean contains(Object o) {
        boolean result = false;

        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (items[i] == null) {
                    result = true;
                    break;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (items[i].equals(o)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    // 5. Итератор. Не надо делать?
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    // 6. Метод. Преобразует список в массив.
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    // 8. Метод. Добавление элемента в конец списка.
    @Override
    public boolean add(E e) {
        //Проверка на вместимость и увеличение при необходимости.
        ensureCapacity();
        items[size] = e;
        size++;
        modCount++;

        return true;
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
        // TODO: Добавить исключение?
        return items[i];
    }

    @Override
    public E set(int i, E e) {
        return null;
    }

    // Вставить элемент по указанному индексу.
//    src - the source array.
//    srcPos - starting position in the source array.
//    dest - the destination array.
//    destPos - starting position in the destination data.
//    length - the number of array elements to be copied.
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("add(int i, E e): i - неверный индекс элемента.");
        }

        getCapacity();
        // Скопировать последние size-i элементов исходного массива
        // в конечный массив (в него же), начиная с определенного индекса i + 1:
        System.arraycopy(items, i, items, i + 1, size - i);
        items[i] = e;
        size++;
        modCount++;
    }

    // Удалить элемент по указанному индексу.
    @Override
    public E remove(int i) {
        E oldItem;

        if (i < 0 || i > size) {
            throw new IllegalArgumentException("remove(int i, E e): i - неверный индекс элемента.");
        }

        oldItem = get(i);
        int copiedSize = size - 2;

        System.arraycopy(items, i + 1, items, i, copiedSize);
        size--;
        modCount++;


        return oldItem;
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

/* Описание класса.
    1. Длина нового массива рассчитывается так (3*n)/2+1,
    где n – это длина старого массива.
    Т.е. если старый массив был длиной 100 элементов, то новый будет 300/2+1 = 151.


 */
