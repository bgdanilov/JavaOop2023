package ru.bgdanilov.array_list;

import java.util.*;


public class CustomArrayList<E> implements List<E> {
    private E[] items;
    private int size; // количество реальных элементов, не путать с вместимостью.
    private int modCount; // количество изменений.
    public static final int DEFAULT_CAPACITY = 10; // Почему не private?

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
    public <T> T[] toArray(T[] ts) {
        if (ts.length < size) {
            //noinspection unchecked
            return (T[]) toArray();
        }

        if (ts.length > size) {
            //noinspection unchecked
            T[] resultArray = (T[]) toArray();
            Arrays.fill(ts, null);
            System.arraycopy(resultArray, 0, ts, 0, size);
        }

        return ts;
    }

    // 7. Добавление элемента в конец списка.
    @Override
    public boolean add(E item) {
        //Проверка на вместимость и увеличение при необходимости.
        if (items.length - size <= 0) {
            ensureCapacity();
        }

        items[size] = item;
        size++;
        modCount++;

        return true;
    }

    // 8. Удаляет первый попавшийся переданный элемент, если он есть в списке.
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

    // 9. Содержатся ли элементы переданного списка в нашем списке?
    // Возвращает true, если все элементы переданной коллекции содержатся в рассматриваемом списке.
    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция пуста.");
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

    // 10. Добавляет все элементы из переданной коллекции в конец этого списка
    // в порядке их возврата Iterator переданной коллекции.
    // Поведение этой операции не определено, если указанная коллекция изменяется во время выполнения операции.
    // (Это означает, что поведение этого вызова не определено,
    // если переданной коллекцией является этот список, и этот список не является пустым.)
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция пуста.");
        }

        int collectionSize = collection.size();

        if (collectionSize == 0) {
            return false;
        }

        if (items.length < size + collectionSize) {
            ensureCapacity(size + collectionSize);
        }

        for (E item : collection) {
            items[size] = item;
            size++;
        }

        modCount++;
        return true;
    }

    // 11. Вставляет все элементы из переданной коллекции в этот список, начиная с указанной позиции.
    // Перемещает элемент, находящийся в данный момент (если есть) и любые последующие элементы вправо
    // (увеличивает их индексы). Новые элементы будут отображаться в списке
    // в порядке их возврата итератором указанной коллекции.
    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция пуста.");
        }

        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Индекс: (" + index + "), за пределами индексов списка: (0, " + size +").");
        }

        int collectionSize = collection.size();

        if (collectionSize == 0) {
            return false;
        }

        if (items.length < size + collectionSize) {
            ensureCapacity(size + collectionSize);
        }

        // Расширяем список под вставку переданной коллекции в нужном месте.
        System.arraycopy(items, index, items, index + collectionSize, size - index);
        size = size + collectionSize;

        for (E item : collection) {
            items[index] = item;
            index++;
        }

        modCount++;
        return true;
    }

    // 12. Удаляет из этого списка все его элементы, содержащиеся в переданной коллекции.
    // Выдает true, если список изменился в результате.
    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция пуста.");
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

    // 13. Сохраняет только те элементы в этом списке, которые содержатся в переданной коллекции.
    // Другими словами, удаляет из этого списка все его элементы, которые не содержатся в переданной коллекции.
    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция пуста.");
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
        }

        modCount++;
        size = 0;
    }

    // 15. Возвращает элемент списка по индексу.
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Индекс: (" + index + "), за пределами индексов списка: (0, " + size +").");
        }

        return items[index];
    }

    // 16. Замещает элемент по переданному индексу переданным элементом. Возвращает старое значение.
    @Override
    public E set(int index, E item) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Индекс: (" + index + "), за пределами индексов списка: (0, " + size +").");
        }

        add(index, item);

        return remove(index + 1);
    }

    // 17. Вставить элемент по переданному индексу.
    @Override
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Индекс: (" + index + "), за пределами индексов списка: (0, " + size +").");
        }

        if (items.length - size <= 0) {
            ensureCapacity();
        }

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
        E oldItem;

        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Индекс: (" + index + "), за пределами индексов списка: (0, " + size +").");
        }

        oldItem = get(index);

        if (index < size - 1) {
            int copiedSize = size - index - 1;

            System.arraycopy(items, index + 1, items, index, copiedSize);
        }

        items[size - 1] = null;
        size--;
        modCount++;

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
        private final int modCount = CustomArrayList.this.modCount; // количество изменений.

        public boolean hasNext() {
            return currentIndex < size - 1;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Коллекция закончилась.");
            }
            if (modCount != CustomArrayList.this.modCount) {
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

    // Увеличить вместимость списка при необходимости.
    public void ensureCapacity() {
        if (size >= items.length) {
            items = Arrays.copyOf(items, (3 * size) / 2 + 1);
        }

        modCount++;
    }

    public void ensureCapacity(int number) {
        items = Arrays.copyOf(items, number);
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }

        modCount++;
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
    ensureCapacity():void
    ensureCapacity(int number):void
    trimToSize():void
    toString():String


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