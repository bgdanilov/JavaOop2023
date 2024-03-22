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
        return new CustomHashTableIterator();
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

    // 6. Возвращает из значений таблицы массив в переданный массив.
    // Если переданный массив имеет достаточную длину, он заполняется значениями таблицы.
    // При наличии излишка длины переданного массива, элементу полученного массива,
    // следующему за последним элементом таблицы присваивается null.
    // Если переданный массив короче размера таблицы, создается массив с длиной, равной размеру таблицы.
    @Override
    public <T> T[] toArray(T[] array) {
        if (size > array.length) {
            //noinspection unchecked
            return Arrays.copyOf(hashTable, size, (Class<? extends T[]>) array.getClass());
        }

        // array у нас T[], а мы ему назначаем E[] поэтому нужно приведение items к 'T'.
        // Почему в arraycopy (T[]) помечено как redundant?
        System.arraycopy(toArray(), 0, array, 0, size);
        array[size] = null;

        return array;
    }

    // 7. Добавить элемент в таблицу.
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

    // 9. Содержатся ли элементы переданной коллекции в нашей таблице?
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

        boolean isChanged = false;

        for (E item : collection) {

            if (add(item)) {
                isChanged = true;
            }
        }

        return isChanged;
    }

    // 11. Удаляет из таблицы все ее элементы, содержащиеся в указанной коллекции.
    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return true;
        }

        boolean isChanged = false;

        // Обходим указанную коллекцию.
        // Тут итератор подходит, т.к. указанную коллекцию мы не меняем.
        for (Object item : collection) {
            isChanged = remove(item);
        }

        return isChanged;
    }

    // 12. Сохраняет только те элементы в таблице, которые содержатся в переданной коллекции.
    // Другими словами, удаляет из таблицы все элементы, которые не содержатся в переданной коллекции.
    @Override
    public boolean retainAll(Collection<?> collection) {
        checkCollection(collection);

        boolean isChanged = false;

        // Тут наш итератор не подходит!
        // Делаем retainAll() для каждого списка в таблице.
        for (ArrayList<E> list : hashTable) {
            if (list != null) {
                if (list.retainAll(collection)) {
                    isChanged = true;

                    modCount++;
                    size -= list.size();
                }
            }
        }

        return isChanged;
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

            for (; currentHashIndex < hashTable.length; currentHashIndex++) {
                if (hashTable[currentHashIndex] == null) {
                    continue;
                }

                if (currentListIndex < hashTable[currentHashIndex].size() - 1) {
                    currentListIndex++;
                    break;
                } else {
                    currentListIndex = -1;
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
        sb.append("[");

        int index = 0;

        for (ArrayList<E> list : hashTable) {
            if (list != null) {
                sb.append(index).append(list).append(", ");
            } else {
                sb.append(index).append("[], ");
            }

            index++;
        }

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }

    private static void checkCollection(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция равна null.");
        }
    }
}