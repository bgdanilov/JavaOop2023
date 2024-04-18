package ru.bgdanilov.hashtable;

import java.util.*;

public class CustomHashTable<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 20;

    private final ArrayList<E>[] lists; // массив параметризованных списков.
    private int size;
    private int modCount;

    public CustomHashTable() {
        //noinspection unchecked
        lists = new ArrayList[DEFAULT_CAPACITY];
    }

    public CustomHashTable(int capacity) {
        //noinspection unchecked
        lists = new ArrayList[capacity];
    }

    // 1. Получение размера таблицы.
    @Override
    public int size() {
        return size;
    }

    // 2. Пуста ли таблица.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // 3. Есть ли элемент в таблице.
    @Override
    public boolean contains(Object object) {
        int index = getIndex(object);

        return lists[index] != null;
    }

    // 4. Итератор.
    @Override
    public Iterator<E> iterator() {
        return new CustomHashTableIterator();
    }

    // 5. Создать массив из значений таблицы.
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size]; // массив любых объектов.
        // Чтобы сработал наш Итератор, нужно применить foreach.
        int i = 0;

        for (Object item : this) {
            array[i] = item;
            i++;
        }

        return array;
    }

    // 6. Возвращает из значений таблицы массив в переданный массив.
    // Если переданный массив имеет достаточную длину, он заполняется значениями таблицы.
    // При наличии излишка длины переданного массива, элементу полученного массива,
    // следующему за последним элементом таблицы присваивается null.
    // Если переданный массив короче размера таблицы, создается массив с длиной, равной размеру таблицы.
    @Override
    public <T> T[] toArray(T[] array) {
        if (size > array.length) {
            //noinspection unchecked
            return Arrays.copyOf(lists, size, (Class<? extends T[]>) array.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(toArray(), 0, array, 0, size);

        if (size < array.length) {
            array[size] = null;
        }

        return array;
    }

    // 7. Добавить элемент в таблицу.
    @Override
    public boolean add(E item) {
        int index = getIndex(item);

        // Создаем по индексу-хэшу объект-список, если его еще там нет.
        if (lists[index] == null) {
            lists[index] = new ArrayList<>();
        }

        lists[index].add(item);
        modCount++;
        size++;

        return true;
    }

    // 8. Удаление элемента из таблицы.
    @Override
    public boolean remove(Object object) {
        boolean isChanged = false;

        if (contains(object)) {
            isChanged = lists[getIndex(object)].remove(object);
            modCount++;
            size--;
        }

        return isChanged;
    }

    // 9. Содержатся ли все элементы переданной коллекции в нашей таблице?
    // Возвращает true, если все элементы переданной коллекции содержатся в рассматриваемой таблице.
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

    // 10. Добавляет все элементы из переданной коллекции в таблицу.
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        checkCollection(collection);

        if (collection.isEmpty()) {
            return false;
        }

        for (E item : collection) {
            add(item);
        }

        return true;
    }

    // 11. Удаляет из таблицы все вхождения элементов, содержащиеся в указанной коллекции.
    @Override
    public boolean removeAll(Collection<?> collection) {
        // Если сама таблица пустая.
        if (this.isEmpty()) {
            return false;
        }

        checkCollection(collection);

        if (collection.isEmpty()) {
            return false;
        }

        // Значит делаем removeAll() для каждого списка в таблице.
        boolean isChanged = false;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                int initialListSize = list.size();

                if (list.removeAll(collection)) {
                    isChanged = true;
                    size -= initialListSize - list.size();
                    modCount++;
                }
            }
        }

        return isChanged;
    }

    // 12. Сохраняет только те элементы в таблице, которые содержатся в переданной коллекции.
    // Другими словами, удаляет из таблицы все элементы, которые не содержатся в переданной коллекции.
    @Override
    public boolean retainAll(Collection<?> collection) {
        if (this.isEmpty()) {
            return false;
        }

        checkCollection(collection);

        if (collection.isEmpty()) {
            clear();

            return true;
        }

        // Делаем retainAll() для каждого списка в таблице.
        boolean isChanged = false;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                int initialListSize = list.size();

                if (list.retainAll(collection)) {
                    isChanged = true;
                    size -= initialListSize - list.size();
                    modCount++;
                }
            }
        }

        return isChanged;
    }

    // 13. Удаляем все элементы из таблицы.
    @Override
    public void clear() {
        if (!isEmpty()) {
            Arrays.fill(lists, null);

            modCount++;
            size = 0;
        }
    }

    // Вложенный класс для итератора.
    private class CustomHashTableIterator implements Iterator<E> {
        private int passedItemsAmount; // количество пройденных элементов.
        private int arrayIndex; // текущий индекс списков в таблице.
        private int listIndex = -1; // текущий индекс элементов в списке.
        private final int initialModCount = modCount; // исходное количество изменений.

        @Override
        public boolean hasNext() {
            return passedItemsAmount < size; // количество обработанных элементов не достигло их общего количества.
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Таблица закончилась.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Зафиксированы изменения Таблицы в процессе перебора элементов.");
            }

            for (; arrayIndex < lists.length; arrayIndex++) {
                if (lists[arrayIndex] == null) {
                    continue;
                }

                if (listIndex < lists[arrayIndex].size() - 1) {
                    listIndex++;
                    break;
                }

                listIndex = -1;
            }

            passedItemsAmount++;
            return lists[arrayIndex].get(listIndex);
        }
    }

    // Получение индекса для вставки по нему объекта. Индекс - хэш-код.
    private int getIndex(Object object) {
        if (object == null) {
            return 0;
        }

        return Math.abs(object.hashCode() % lists.length);
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        int index = 0;

        for (ArrayList<E> list : lists) {
            sb.append(index).append('-').append(list).append(", ");

            index++;
        }

        sb.setLength(sb.length() - 2);
        sb.append(']');

        return sb.toString();
    }

    private static void checkCollection(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция равна null.");
        }
    }
}