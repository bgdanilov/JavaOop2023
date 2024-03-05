package ru.bgdanilov.array_list;

import java.util.*;


public class CustomArrayList<E> implements List<E> {
    private E[] items;
    private int size; // количество реальных элементов, не путать с вместимостью.
    private int modCount; // количество изменений.
    private static final int DEFAULT_CAPACITY = 10;

    // Конструктор по умолчанию.
    public CustomArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[DEFAULT_CAPACITY];
    }

    // Конструктор, принимающий capacity - вместимость.
    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость списка не может быть отрицательной.");
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    // 1. Возвращает количество элементов списка.
    @Override
    public int size() {
        return size;
    }

    // 2. Пустой ли список.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // 3. Содержится ли элемент в списке?
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    // 4. Итератор.
    public Iterator<E> iterator() {
        return new CustomListIterator();
    }

    // 5. Преобразует список в массив.
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    // 6. Возвращает из списка массив в переданный массив.
    // Если указанный массив имеет достаточную длину,
    // он заполняется элементами списка, а излишки длины - пустыми объектами null.
    // В противном случае создается массив с длинной исходного.
    @Override
    public <T> T[] toArray(T[] array) {
        if (size >= array.length) {
            //noinspection unchecked
            return Arrays.copyOf(items, size, (Class<? extends T[]>) array.getClass());
        }

        // array у нас T[], а мы ему назначаем E[] поэтому нужно приведение items к 'T'.
        // Почему в arraycopy (T[]) помечено как redundant?
        //noinspection unchecked
        System.arraycopy((T[]) items, 0, array, 0, size);
        array[size] = null;

        return array;
    }

    // 7. Добавление элемента в конец списка.
    @Override
    public boolean add(E item) {
        add(size, item);

        return true;
    }

    // 8. Удаляет первый попавшийся переданный элемент, если он есть в списке.
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != -1) {
            remove(index);
            return true;
        }

        return false;
    }

    // 9. Содержатся ли элементы переданного списка в нашем списке?
    // Возвращает true, если все элементы переданной коллекции содержатся в рассматриваемом списке.
    @Override
    public boolean containsAll(Collection<?> collection) {
        checkCollection(collection);

        for (Object item : collection) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }

    // 10. Добавляет все элементы из переданной коллекции в конец этого списка
    // в порядке их возврата Iterator переданной коллекции.
    // Поведение этой операции не определено, если указанная коллекция изменяется во время выполнения операции.
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        checkCollection(collection);
        addAll(size, collection);

        return true;
    }

    // 11. Вставляет все элементы из переданной коллекции в этот список, начиная с указанной позиции.
    // Перемещает элемент, находящийся в данный момент (если есть) и любые последующие элементы вправо
    // (увеличивает их индексы). Новые элементы будут отображаться в списке
    // в порядке их возврата итератором указанной коллекции.
    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        checkCollection(collection);
        checkIndex(index);

        int collectionSize = collection.size();

        if (collectionSize == 0) {
            return false;
        }

        if (items.length < size + collectionSize) {
            increaseCapacity(size + collectionSize);
        }

        // Расширяем список под вставку переданной коллекции в нужном месте.
        System.arraycopy(items, index, items, index + collectionSize, size - index);
        size += collectionSize;

        int i = index;

        for (E item : collection) {
            items[i] = item;
            i++;
        }

        modCount++;

        return true;
    }

    // 12. Удаляет из этого списка все его элементы, содержащиеся в переданной коллекции.
    // Выдает true, если список изменился в результате.
    @Override
    public boolean removeAll(Collection<?> collection) {
        checkCollection(collection);

        boolean isListChanged = false;

        for (int i = size - 1; i >= 0; i--) {
            if (collection.contains(items[i])) {
                remove(i);
                isListChanged = true;
                i++;
            }
        }

        return isListChanged;
    }

    // 13. Сохраняет только те элементы в этом списке, которые содержатся в переданной коллекции.
    // Другими словами, удаляет из этого списка все его элементы, которые не содержатся в переданной коллекции.
    @Override
    public boolean retainAll(Collection<?> collection) {
        checkCollection(collection);

        boolean isListChanged = false;

        for (int i = size - 1; i >= 0; i--) {
            if (!collection.contains(items[i])) {
                remove(i);
                isListChanged = true;
                i++;
            }
        }

        return isListChanged;
    }

    // 14. Удаляет все элементы из списка.
    @Override
    public void clear() {
        if (!isEmpty()) {
            Arrays.fill(items, null);
            modCount++;
            size = 0;
        }
    }

    // 15. Возвращает элемент списка по индексу.
    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    // 16. Замещает элемент по переданному индексу переданным элементом. Возвращает старое значение.
    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E replacedItem = items[index];
        items[index] = item;

        return replacedItem;
    }

    // 17. Вставить элемент по переданному индексу.
    @Override
    public void add(int index, E item) {
        checkIndex(index);
        increaseCapacity();

        // Скопировать последние size-i элементов исходного массива
        // в конечный массив (в него же), начиная с определенного индекса i + 1:
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;
        size++;
        modCount++;
    }

    // 18. Удалить элемент по переданному индексу.
    @Override
    public E remove(int index) {
        checkIndex(index);
        E removedItem = items[index];

        if (index < size - 1) {
            int copiedSize = size - index - 1;

            System.arraycopy(items, index + 1, items, index, copiedSize);
        }

        items[size - 1] = null;
        size--;
        modCount++;

        return removedItem;
    }

    // 19. Возвращает индекс первого вхождения переданного элемента или -1, если элемента в списке нет.
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    // 20. Возвращает индекс последнего вхождения переданного элемента или -1, если элемента в списке нет.
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    // Вложенный класс для итератора.
    private class CustomListIterator implements Iterator<E> {
        private int currentIndex = -1; // текущий индекс в списке.
        private final int initialModCount = modCount; // Исходное количество изменений.

        public boolean hasNext() {
            return currentIndex < size - 1;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Коллекция закончилась.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Зафиксированы изменения коллекции в процессе перебора элементов.");
            }

            currentIndex++;

            return items[currentIndex];
        }
    }

    // Не нужно делать.
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    // Не нужно делать.
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    // Не нужно делать.
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    // Возвращает вместимость списка.
    public int getCapacity() {
        return items.length;
    }

    private boolean isEnoughCapacity() {
        return size < items.length;
    }

    // Увеличить вместимость списка при необходимости.
    private void increaseCapacity() {
        if (items.length == 0) {
            items = Arrays.copyOf(items, 1);
        }

        if (!isEnoughCapacity()) {
            items = Arrays.copyOf(items, 2 * size);
        }
    }

    private void increaseCapacity(int capacityNumber) {
        if (capacityNumber > items.length) {
            items = Arrays.copyOf(items, capacityNumber);
        }
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }
    }

    @Override
    public String toString() {
        if (items.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder().append('[').append(items[0]);

        for (int i = 1; i < size; i++) {
            sb.append(',').append(' ').append(items[i]);
        }

        return sb.append(']').toString();
    }

    private void checkCollection(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция отсутствует.");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Индекс: (" + index + "), за пределами индексов списка: (0, " + size + ").");
        }
    }
}

/* Описание класса.
    1. Список методов.
    1.1.  size():int
    1.2.  isEmpty:boolean
    1.3.  contains(o Object):boolean
    1.4.  iterator():Iterator<E>
    1.5.  toArray():Object[]
    1.6.  toArray(a[T]):T[]
    1.7.  add(e:E):boolean
    1.8.  remove(o:Object):boolean
    1.9.  containsAll(o:Collection<?>):boolean
    1.11. addAll(o:Collection<?>):boolean
    1.11. addAll(index:int, o:Collection<?>):boolean
    1.12. removeAll(o:Collection<?>):boolean
    1.13. retainAll(o:Collection<?>):boolean
    1.14. clear():void
    1.15. get(index:int):E
    1.16. set(index:int, e:E):E
    1.17. add(index:int, e:E):void
    1.18. remove(index:int):E
    1.19. indexOf(o:Object):int
    1.20. lastIndexOf(o:Object):int

    getCapacity():int
    increaseCapacity():void
    increaseCapacity(int number):void
    trimToSize():void
    toString():String
    checkCollection(Collection<?> collection):void
    checkIndex(int index):void


    2. Длина нового массива рассчитывается так (3*n)/2+1,
    где n – это длина старого массива.
    Т.е. если старый массив был длиной 100 элементов, то новый будет 300/2+1 = 151.

    3. Справка по System.arraycopy:
    Из исходного массива (src),
    начиная с начальной позиции (srcPos),
    нужно скопировать данные в другой массив (dest),
    в такую-то позицию (destPos),
    в таком-то количестве (length).
 */