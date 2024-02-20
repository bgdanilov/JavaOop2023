package ru.bgdanilov.array_list;

import java.util.*;


public class ArrayListCustom<E> implements List<E> {
    E[] items;
    private int size; // количество реальных элементов, не путать с вместимостью.
    private int modCount = 0; // количество изменений.

    // Конструктор по умолчанию.
    public ArrayListCustom() {
        size = 0;
        //noinspection unchecked
        items = (E[]) new Object[10];
        // items = (E[]) new Object[1];
    }

    // Конструктор, принимающий capacity - вместимость.
    public ArrayListCustom(int capacity) {
        size = 0;
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

    // 6. Возвращает из списка массив в указанный массив.
    // Если указанный массив имеет достаточную длину,
    // он заполняется элементами списка, а излишки длины - пустыми объектами null.
    // В противном случае создается массив с длинной исходного.
    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> T[] toArray(T[] ts) {
        if (ts.length < size) {
            return (T[]) toArray();
        }

        if (ts.length > size) {
            T[] resultArray = (T[]) toArray();
            Arrays.fill(ts, null);
            System.arraycopy(resultArray, 0, ts, 0, size);
        }

        return ts;
    }

    // 7. Добавление элемента в конец списка.
    @Override
    public boolean add(E e) {
        //Проверка на вместимость и увеличение при необходимости.
        if (getCapacity() - size <= 0) {
            ensureCapacity();
        }

        items[size] = e;
        size++;
        modCount++;

        return true;
    }

    // 8. Удаляет первый попавшийся указанный элемент, если он есть в списке.
    @Override
    public boolean remove(Object o) {
        int indexOfRemoved = indexOf(o);

        if (indexOfRemoved != -1) {
            remove(indexOfRemoved);
            return true;
        } else {
            return false;
        }
    }

    // 9. Содержатся ли элементы указанного списка в нашем списке?
    // Возвращает true, если все элементы указанного списка содержатся в рассматриваемом списке.
    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Указанный список пуст.");
        }

        boolean result = true;

        for (Object item : collection) {
            if (!contains(item)) {
                result = false;
                break;
            }
        }

        return result;
    }

    // 10. Добавляет все элементы из указанной коллекции в конец этого списка
    // в порядке их возврата Iterator указанной коллекции.
    // Поведение этой операции не определено, если указанная коллекция изменяется во время выполнения операции.
    // (Это означает, что поведение этого вызова не определено,
    // если указанной коллекцией является этот список, и этот список не является пустым.)
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException("Указанный список пуст.");
        }

        int collectionSize = collection.size();

        if (collectionSize == 0) {
            return false;
        }

        if (getCapacity() < size + collectionSize) {
            ensureCapacity(size + collectionSize);
        }

        for (E item : collection) {
            items[size] = item;
            size++;
            modCount++;
        }

        return true;
    }

    // 11. Вставляет все элементы из указанной коллекции в этот список, начиная с указанной позиции.
    // Перемещает элемент, находящийся в данный момент (если есть) и любые последующие элементы вправо
    // (увеличивает их индексы). Новые элементы будут отображаться в списке
    // в порядке их возврата итератором указанной коллекции.
    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException("Указанный список пуст.");
        }

        if (i < 0 || i > size) {
            throw new IllegalArgumentException("Индекс: (" + i + "), за пределами индексов списка.");
        }

        int collectionSize = collection.size();

        if (collectionSize == 0) {
            return false;
        }

        if (getCapacity() < size + collectionSize) {
            ensureCapacity(size + collectionSize);
        }

        // Расширяем список под вставку указанной коллекции в нужном месте.
        System.arraycopy(items, i, items, i + collectionSize, size - i);
        size = size + collectionSize;

        for (E item : collection) {
            items[i] = item;
            i++;
            modCount++;
        }

        return true;
    }

    // 12. Удаляет из этого списка все его элементы, содержащиеся в указанной коллекции.
    // Выдает true, если список изменился в результате.
    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Указанный список пуст.");
        }

        boolean result = false;

        for (int i = 0; i < size; i++) {
            if (collection.contains(items[i])) {
                remove(items[i]);
                result = true;
                i--;
            }
        }

        return result;
    }

    // 13. Сохраняет только те элементы в этом списке, которые содержатся в указанной коллекции.
    // Другими словами, удаляет из этого списка все его элементы, которые не содержатся в указанной коллекции.
    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Указанный список пуст.");
        }

        boolean result = false;

        for (int i = 0; i < size; i++) {
            if (!collection.contains(items[i])) {
                remove(items[i]);
                result = true;
                i--;
            }
        }

        return result;
    }

    // 14. Удаляет все элементы из списка.
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
            modCount++;
        }

        size = 0;
    }

    // 15. Возвращает элемент списка по индексу.
    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IllegalArgumentException("Индекс: (" + i + "), за пределами индексов списка.");
        }

        return items[i];
    }

    // 16. Замещает элемент по указанному индексу указанным элементом. Возвращает старое значение.
    @Override
    public E set(int i, E e) {
        if (i < 0 || i >= size) {
            throw new IllegalArgumentException("Индекс: (" + i + "), за пределами индексов списка.");
        }

        add(i, e);

        return remove(i + 1);
    }

    // 17. Вставить элемент по указанному индексу.
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("Индекс: (" + i + "), за пределами индексов списка.");
        }

        if (getCapacity() - size <= 0) {
            ensureCapacity();
        }

        // Скопировать последние size-i элементов исходного массива
        // в конечный массив (в него же), начиная с определенного индекса i + 1:
        System.arraycopy(items, i, items, i + 1, size - i);
        items[i] = e;
        size++;
        modCount++;
    }

    // 18. Удалить элемент по указанному индексу.
    @Override
    public E remove(int i) {
        E oldItem;

        if (i < 0 || i > size) {
            throw new IllegalArgumentException("Индекс: (" + i + "), за пределами индексов списка.");
        }

        oldItem = get(i);

        if (i <= size - 1) {
            int copiedSize = size - i - 1;

            System.arraycopy(items, i + 1, items, i, copiedSize);
            items[size - 1] = null;
            size--;
            modCount++;
        }

        return oldItem;
    }

    // 19. Возвращает индекс первого вхождения переданного элемента или -1, если элемента в списке нет.
    @Override
    public int indexOf(Object o) {
        int indexOf = -1;

        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (items[i] == null) {
                    indexOf = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(items[i])) {
                    indexOf = i;
                    break;
                }
            }
        }

        return indexOf;
    }

    // 20. Возвращает индекс последнего вхождения переданного элемента или -1, если элемента в списке нет.
    @Override
    public int lastIndexOf(Object o) {
        int lastIndexOf = -1;

        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (items[i] == null) {
                    lastIndexOf = i;
                    break;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(items[i])) {
                    lastIndexOf = i;
                    break;
                }
            }
        }

        return lastIndexOf;
    }

    // Вложенный класс для итератора.
    private class CustomListIterator implements Iterator<E> {
        private int currentIndex = -1; // текущий индекс в списке.
        private final int modCount = ArrayListCustom.this.modCount; // количество изменений.

        public boolean hasNext() {
            return currentIndex < size - 1;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Коллекция закончилась.");
            }
            if (modCount != ArrayListCustom.this.modCount) {
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
    public ListIterator<E> listIterator(int i) {
        return null;
    }

    // Не нужно делать.
    @Override
    public List<E> subList(int i, int i1) {
        return null;
    }

    // Возвращает вместимость списка.
    public int getCapacity() {
        return items.length;
    }

    // Увеличить вместимость списка при необходимости.
    public void ensureCapacity() {
        if (size >= getCapacity()) {
            items = Arrays.copyOf(items, (3 * size) / 2 + 1);
        }
    }

    public void ensureCapacity(int number) {
        items = Arrays.copyOf(items, number);
    }

    public void trimToSize() {
        if (getCapacity() > size) {
            items = Arrays.copyOf(items, size);
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
}

/* Описание класса.
    1. Длина нового массива рассчитывается так (3*n)/2+1,
    где n – это длина старого массива.
    Т.е. если старый массив был длиной 100 элементов, то новый будет 300/2+1 = 151.

    2. Справка по System.arraycopy:
    Из исходного массива (src),
    начиная с начальной позиции (srcPos),
    нужно скопировать данные в другой массив (dest),
    в такую-то позицию (destPos),
    в таком-то количестве (length).
 */