package ru.bgdanilov.list;

// Это класс односвязного списка.
public class List<E> {
    private ListItem<E> head; // первый элемент - голова списка.
    private int length; //  тут храним длину списка.

    public List() {
    }

    // 1.1. Получение размера списка.
    public int getLength() {
        return length;
    }

    // 1.2. Получение значения первого элемента.
    public E getFirst() {
        checkIndex(0, length);
        return head.getData();
    }

    // 1.3. Получение/изменение значения по указанному индексу.
    // Получение.
    public E getByIndex(int index) {
        checkIndex(index, length);
        return getItemByIndex(index).getData();
    }

    // Изменение с выдачей старого значения.
    public E setByIndex(int index, E data) {
        checkIndex(index, length);

        ListItem<E> currentItem = getItemByIndex(index);

        E oldData = currentItem.getData();
        currentItem.setData(index, data);

        return oldData;
    }

    // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
    public E deleteByIndex(int index) {
        checkIndex(index, length);

        ListItem<E> currentItem = head;

        if (index == 0) {
            deleteFirst();
        } else {
            ListItem<E> previousItem = currentItem;

            for (int i = 0; i < index; i++) {
                previousItem = currentItem;
                currentItem = currentItem.getNext();
            }

            previousItem.setNext(currentItem.getNext());

            length--;
        }

        return currentItem.getData();
    }

    // 1.5. Вставка элемента в начало.
    public void addFirst(E data) {
        head = new ListItem<>(data, head);

        length++;
    }

    // 1.6. Вставка элемента по индексу.
    public void addByIndex(int index, E data) {
        checkIndex(index, length + 1);

        // Индекс равен нулю. Значит в начало вставить.
        if (index == 0) {
            addFirst(data);

            return;
        }

        ListItem<E> currentItem = head;
        ListItem<E> previousItem = currentItem; // это тоже одинаковое присваивание?

        for (int i = 0; i < index; i++) {
            previousItem = currentItem;
            currentItem = currentItem.getNext();
        }

        previousItem.setNext(new ListItem<>(data, currentItem));

        length++;
    }

    // 1.7. Удаление узла по значению, пусть выдает true, если элемент был удален.
    public boolean deleteByData(E data) {
        for (ListItem<E> currentItem = head, previousItem = null;
             currentItem != null;
             previousItem = currentItem, currentItem = currentItem.getNext()) {

            if (currentItem.getData() == data) {
                if (previousItem == null) {
                    head = currentItem.getNext();
                } else {
                    previousItem.setNext(currentItem.getNext());
                }

                length--;

                return true;
            }
        }

        return false;
    }

    // 1.8. Удаление первого элемента, пусть выдает значение элемента.
    public E deleteFirst() {
        checkIndex(0, length);

        ListItem<E> currentItem = head;
        head = currentItem.getNext();
        length--;

        return currentItem.getData();
    }

    // 1.9. Разворот списка за линейное время.
    public void reverse() {
        if (head == null) { // список пустой.
            return;
        }

        ListItem<E> currentItem = head; // адрес элемента в памяти.
        ListItem<E> previousItem = null;

        for (ListItem<E> nextCurrent = currentItem.getNext();
             nextCurrent != null;
             previousItem = currentItem, currentItem = nextCurrent, nextCurrent = nextCurrent.getNext()) {
            currentItem.setNext(previousItem);
        }

        currentItem.setNext(previousItem);
        head = currentItem;
    }

    // 1.10. Копирование списка.
    public List<E> copy() {
        // создали новый пустой список.
        List<E> copiedList = new List<>();
        copiedList.length = length;

        ListItem<E> copiedCurrentItem = new ListItem<>(head.getData());
        copiedList.head = copiedCurrentItem;

        for (ListItem<E> nextItem = head.getNext(); nextItem != null; nextItem = nextItem.getNext()) {
            ListItem<E> copiedNextItem = new ListItem<>(nextItem.getData());
            copiedCurrentItem.setNext(copiedNextItem);
            copiedCurrentItem = copiedNextItem;
        }

        return copiedList;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        ListItem<E> currentItem;
        StringBuilder sb = new StringBuilder().append('[');

        for (currentItem = head; currentItem.getNext() != null; currentItem = currentItem.getNext()) {
            sb.append(currentItem.getData()).append(", ");
        }

        sb.append(currentItem.getData()).append(']');

        return sb.toString();
    }

    // Добавление элемента в конец.
    public void add(E data) {
        addByIndex(length, data);
    }

    // Проверка индекса на принадлежность допустимому диапазону.
    // Private - доступ только внутри класса.
    // Static - не нужно, т.к. метод из вне не нужно и нельзя вызывать.
    private void checkIndex(int index, int maxIndex) {
        if (index < 0 || index >= maxIndex) {
            throw new IllegalArgumentException("Index: " + index + ", Длина списка: " + length + ". Индекс выходит за пределы списка.");
            //throw new IndexOutOfBoundsException("Индекс [" + index + "] выходит за границы [0 - "+ (maxIndex - 1) + "] списка.");
        }
    }

    // Итерация до заданного по индексу элемента.
    private ListItem<E> getItemByIndex(int index) {
        ListItem<E> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem;
    }
}

/*  Заметки.
 *  ===================
 *  1. Список - набор связанных объектов.
 *  2. Связный список - это набор связанных объектов,
 *     где ссылка переносит нас от одного элемента к другому.
 *  3. Односвязный список - это объект, содержащий ССЫЛКУ на первый элемент списка.
 *       Эту ссылку называют головой - head.
 *       Голова - это ссылка. Ссылка на первый элемент списка.
 *       Next - это ссылка.
 *       Next == null - последний элемент.
 *
 *  4. reverse() - разворот списка за линейное время.
 *   - Запоминаем адрес следующего за текущим элемента.
 *   - Делаем чтобы текущий элемент ссылался на предыдущий. И назначаем его головой.
 *   - Назначаем в качестве нового предыдущего элемента текущий.
 *   - А новый текущий теперь будет элемент, бывший следующий у бывшего текущего, адрес которого мы запомнили в temp.
 *   - Повторяем 2. Пока не закончится список.
 */