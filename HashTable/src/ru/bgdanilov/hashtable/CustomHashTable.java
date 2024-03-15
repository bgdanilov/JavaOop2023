package ru.bgdanilov.hashtable;

import java.util.*;

public class CustomHashTable<E> implements Collection<E> {
    ArrayList<E>[] hashTable; // массив параметризованных списков.
    private static final int DEFAULT_CAPACITY = 20;
    private int size;
    private int modCount;

    public CustomHashTable() {
        //noinspection unchecked
        hashTable = new ArrayList[DEFAULT_CAPACITY];
    }

    public CustomHashTable(int capacity) {
        //noinspection unchecked
        hashTable = new ArrayList[capacity];
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
    // TODO: на null - дает исключение!
    public boolean contains(Object o) {
        int index = calcIndex(o);

        if (hashTable[index] == null) {
            return false;
        } else {
            for (E item : hashTable[index]) {
                if (item.equals(o)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 4. Итератор.
    @Override
    public Iterator<E> iterator() {
        CustomHashTableIterator iterator;
        return iterator = new CustomHashTableIterator();
    }

    // 5. Создать массив из значений таблицы.
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size]; // массив любых объектов.
        // Чтобы сработал наш Итератор, нужно применить foreach.
        int index = 0;

        for (Object item : this) {
            array[index] = item;
            index++;
        }

        return array;
    }

    // 6.
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    // 7. Добавить объект в таблицу.
    @Override
    public boolean add(E e) {
        if (contains(e)) {
            return false;
        }

        int index = calcIndex(e);

        // Создаем по индексу-хэшу объект-список, если его еще там нет.
        if (hashTable[index] == null) {
            hashTable[index] = new ArrayList<>();
        }

        hashTable[index].add(e);
        modCount++;
        size++;

        return true;
    }

    // 8. Удаление элемента из таблицы.
    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        modCount++;
        size--;

        return hashTable[calcIndex(o)].remove(o);
    }

    // 9.
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    // 10.
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    // 11.
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    // 12.
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    // 13. Удаляем все элементы из таблицы.
    @Override
    public void clear() {
        if (!isEmpty()) {
            Arrays.fill(hashTable, null);

            modCount++;
            size = 0;
        }
    }

    // Вложенный класс для итератора.
    private class CustomHashTableIterator implements Iterator<E> {
        private int itemsCount; // накопительный счетчик обработанных элементов.
        private int currentHashIndex; // текущий индекс списков в таблице.
        private int currentListIndex = -1; // текущий индекс элементов в списке.
        private final int initialModCount = modCount; // исходное количество изменений.

        public int getItemsCount() {
            return itemsCount;
        }

        @Override
        public boolean hasNext() {
            return itemsCount < size; // количество обработанных элементов не достигло их общего количества.
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Таблица закончилась.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Зафиксированы изменения Таблицы в процессе перебора элементов.");
            }

            for (; currentHashIndex < hashTable.length - 1; currentHashIndex++) {
                if (hashTable[currentHashIndex] == null) {
                    continue;
                }

                if (currentListIndex < hashTable[currentHashIndex].size() - 1) {
                    currentListIndex++;
                }
            }

            itemsCount++;
            return hashTable[currentHashIndex].get(currentListIndex);
        }
    }


    // Получение индекса для вставки по нему объекта. Индекс - хэш-код.
    private int calcIndex(Object object) {
        return Math.abs(object.hashCode() % hashTable.length);
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");

        int index = 0;

        for (ArrayList<E> arrayList : hashTable) {
            if (arrayList != null) {
                sb.append(index).append(arrayList).append(", ");
            } else {
                sb.append(index).append("[], ");
            }

            index++;
        }

        sb.setLength(sb.length() - 2);
        sb.append(" ]");

        return sb.toString();
    }
}