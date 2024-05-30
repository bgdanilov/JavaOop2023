package ru.bgdanilov.list;

import java.util.NoSuchElementException;
import java.util.Objects;

// Это класс односвязного списка.
public class List<E> {
    private ListItem<E> head; // первый элемент - голова списка.
    private int length; //  тут храним длину списка.

    // 1.1. Получение размера списка.
    public int getLength() {
        return length;
    }

    // 1.2. Получение значения первого элемента.
    public E getFirst() {
        checkListIsEmpty();
        return head.getData();
    }

    // 1.3. Получение/изменение значения по указанному индексу.
    // Получение.
    public E get(int index) {
        checkIndex(index, length - 1);
        return getItemByIndex(index).getData();
    }

    // Изменение с выдачей старого значения.
    public E set(int index, E data) {
        checkIndex(index, length - 1);

        ListItem<E> item = getItemByIndex(index);

        E oldData = item.getData();
        item.setData(data);

        return oldData;
    }

    // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
    public E deleteByIndex(int index) {
        checkIndex(index, length - 1);

        if (index == 0) {
            return deleteFirst();
        }

        ListItem<E> previousItem = getItemByIndex(index - 1);
        ListItem<E> currentItem = previousItem.getNext();

        previousItem.setNext(currentItem.getNext());
        length--;

        return currentItem.getData();
    }

    // 1.5. Вставка элемента в начало.
    public void addFirst(E data) {
        head = new ListItem<>(data, head);
        length++;
    }

    // 1.6. Вставка элемента по индексу.
    public void addByIndex(int index, E data) {
        checkIndex(index, length);

        // Индекс равен нулю. Значит в начало вставить.
        if (index == 0) {
            addFirst(data);
            return;
        }

        ListItem<E> previousItem = getItemByIndex(index - 1);
        previousItem.setNext(new ListItem<>(data, previousItem.getNext()));

        length++;
    }

    // 1.7. Удаление узла по значению, пусть выдает true, если элемент был удален.
    public boolean deleteByData(E data) {
        for (ListItem<E> currentItem = head, previousItem = null;
             currentItem != null;
             previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (Objects.equals(currentItem.getData(), data)) {
                if (previousItem == null) {
                    head = head.getNext();
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
        checkListIsEmpty();

        E headData = head.getData();
        head = head.getNext();
        length--;

        return headData;
    }

    // 1.9. Разворот списка за линейное время.
    public void reverse() {
        if (head == null) { // список пустой.
            return;
        }

        ListItem<E> currentItem = head; // адрес элемента в памяти.
        ListItem<E> previousItem = null;

        for (ListItem<E> nextItem = currentItem.getNext();
             nextItem != null;
             previousItem = currentItem, currentItem = nextItem, nextItem = nextItem.getNext()) {
            currentItem.setNext(previousItem);
        }

        currentItem.setNext(previousItem);
        head = currentItem;
    }

    // 1.10. Копирование списка.
    public List<E> copy() {
        if (head == null) {
            return new List<>();
        }

        List<E> copiedList = new List<>(); // создаем пустой скопированный лист - заготовка;
        copiedList.length = length; // задаем ему длину, равную исходнику;

        ListItem<E> copiedCurrentItem = new ListItem<>(head.getData()); // текущий скопированный элемент - голова исходника;
        copiedList.head = copiedCurrentItem; // записываем в голову скопированного списка;

        // Перебираем элементы исходного списка.
        for (ListItem<E> currentItem = head.getNext(); currentItem != null; currentItem = currentItem.getNext()) {
            ListItem<E> copiedListNextItem = new ListItem<>(currentItem.getData());
            copiedCurrentItem.setNext(copiedListNextItem);
            copiedCurrentItem = copiedListNextItem;
        }

        return copiedList;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("["); // строка-аргумент менее затратна, чем .append();

        for (ListItem<E> item = head; item != null; item = item.getNext()) {
            sb.append(item.getData()).append(", ");
        }

        int sbLength = sb.length();
        sb.replace(sbLength - 2, sbLength, "]");

        return sb.toString();
    }

    // Добавление элемента в конец.
    public void add(E data) {
        addByIndex(length, data);
    }

    // Итерация до заданного по индексу элемента.
    private ListItem<E> getItemByIndex(int index) {
        ListItem<E> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem;
    }

    // Проверка индекса на принадлежность допустимому диапазону.
    private void checkIndex(int index, int maxIndex) {
        if (index < 0 || index > maxIndex) {
            String indexesRange = (maxIndex == -1) ? "список пуст" : "0 - " + maxIndex;
            throw new IndexOutOfBoundsException("Индекс [" + index + "] выходит за пределы индексов [" + indexesRange + "] списка.");
        }
    }

    private void checkListIsEmpty() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст!");
        }
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