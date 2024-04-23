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

    // 1. Получение размера хеш-таблицы.
    @Override
    public int size() {
        return size;
    }

    // 2. Пуста ли хеш-таблица.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // 3. Есть ли элемент в хеш-таблице.
    @Override
    public boolean contains(Object object) {
        int index = getIndex(object);

        return !isEmpty() && lists[index] != null && lists[index].contains(object);
    }

    // 4. Итератор.
    @Override
    public Iterator<E> iterator() {
        return new CustomHashTableIterator();
    }

    // 5. Создать массив из значений хеш-таблицы.
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

    // 6. Возвращает из значений хеш-таблицы массив в переданный массив.
    // Если переданный массив имеет достаточную длину, он заполняется значениями хеш-таблицы.
    // При наличии излишка длины переданного массива, элементу полученного массива,
    // следующему за последним элементом хеш-таблицы присваивается null.
    // Если переданный массив короче размера хеш-таблицы, создается массив с длиной, равной размеру хеш-таблицы.
    @Override
    public <T> T[] toArray(T[] array) {
        // Получается, если переданный массив короче или равен по длине, то возвращаем просто хеш-таблицу как массив.
        if (size >= array.length) {
            //noinspection unchecked
            return (T[]) toArray();
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(toArray(), 0, array, 0, size);

        if (size < array.length) {
            array[size] = null;
        }

        return array;
    }

    // 7. Добавить элемент в хеш-таблицу.
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

    // 8. Удаление элемента из хеш-таблицы.
    @Override
    public boolean remove(Object object) {
        if (!isEmpty() && lists[getIndex(object)] != null) {
            modCount++;
            size--;

            return lists[getIndex(object)].remove(object);
        }

        return false;
    }

    // 9. Содержатся ли все элементы переданной коллекции в нашей хеш-таблице?
    // Возвращает true, если все элементы переданной коллекции содержатся в рассматриваемой хеш-таблице.
    @Override
    public boolean containsAll(Collection<?> collection) {
        checkCollectionNullPointerException(collection);

        for (Object item : collection) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }

    // 10. Добавляет все элементы из переданной коллекции в хеш-таблицу.
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        checkCollectionNullPointerException(collection);

        if (collection.isEmpty()) {
            return false;
        }

        for (E item : collection) {
            add(item);
        }

        return true;
    }

    // 11. Удаляет из хеш-таблицы все вхождения элементов, содержащиеся в указанной коллекции.
    @Override
    public boolean removeAll(Collection<?> collection) {
        checkCollectionNullPointerException(collection);

        if (collection.isEmpty()) {
            return false;
        }

        // Если сама хеш-таблица пустая.
        if (isEmpty()) {
            return false;
        }

        // Значит делаем removeAll() для каждого списка в хеш-таблице.
        boolean isChanged = false;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                int initialListSize = list.size();

                if (list.removeAll(collection)) {
                    isChanged = true;
                    size -= initialListSize - list.size();
                }
            }
        }

        if (isChanged) {
            modCount++;
        }

        return isChanged;
    }

    // 12. Сохраняет только те элементы в хеш-таблице, которые содержатся в переданной коллекции.
    // Другими словами, удаляет из хеш-таблицы все элементы, которые не содержатся в переданной коллекции.
    @Override
    public boolean retainAll(Collection<?> collection) {
        checkCollectionNullPointerException(collection);

        if (collection.isEmpty()) {
            clear();

            return true;
        }

        if (isEmpty()) {
            return false;
        }

        // Делаем retainAll() для каждого списка в хеш-таблице.
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

    // 13. Удаляем все элементы из хеш-таблицы.
    @Override
    public void clear() {
        if (!isEmpty()) {
            // Зря что-ли итератор делали.
            for (Object item : this) {
                remove(item);
            }
            /*
            А может лучше так?
            for (ArrayList<E> list : lists) {
                removeAll(list);
            }
            */

            modCount++;
            size = 0;
        }
    }

    // Вложенный класс для итератора.
    private class CustomHashTableIterator implements Iterator<E> {
        private int visitedItemsAmount; // количество пройденных элементов;
        private int arrayIndex; // текущий индекс списков в хеш-таблице;
        private int listIndex = -1; // текущий индекс элементов в списке;
        private final int initialModCount = modCount; // исходное количество изменений;

        @Override
        public boolean hasNext() {
            return visitedItemsAmount < size; // количество обработанных элементов не достигло их общего количества;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Хеш-таблица закончилась.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Зафиксированы изменения Хеш-таблицы в процессе перебора элементов.");
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

            visitedItemsAmount++;
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

    private static void checkCollectionNullPointerException(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция равна null.");
        }
    }
}