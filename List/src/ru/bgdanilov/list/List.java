package ru.bgdanilov.list;

import java.util.NoSuchElementException;

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

    // Подсчет размера. Для проверки.
    public int countLength() {
        int length = 0;

        for (ListItem<E> currentItem = head;
             currentItem != null;
             currentItem = currentItem.getNext()) {
            length++;
        }
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

        ListItem<E> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem.getData();
    }

    // Изменение с выдачей старого значения.
    public E setByIndex(int index, E data) {
        checkIndex(index, length);

        ListItem<E> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        E oldData = currentItem.getData();
        currentItem.setData(index, data);

        return oldData;
    }

    // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
    public E deleteByIndex(int index) {
        checkIndex(index, length);

        ListItem<E> currentItem = head;

        // Если нужно удалить нулевой элемент, то записываем в голову следующий за ним.
        if (index == 0) {
            head = currentItem.getNext();
        } else {
            ListItem<E> previousItem = currentItem;

            for (int i = 0; i < index; i++) {
                previousItem = currentItem;
                currentItem = currentItem.getNext();
            }

            // На данном этапе мы в поле next предыдущего элемента, записываем
            // поле next следующего за текущим элементом.
            // Таким образом, текущий элемент исключаем из цепочки.
            previousItem.setNext(currentItem.getNext());
        }

        length--;

        return currentItem.getData();
    }

    // 1.5. Вставка элемента в начало.
    public void addFirst(E data) {
        // Запоминаем ссылку на текущий элемент, чтобы сделать ее потом next.
        // Как это currentListItem не нужна?
        ListItem<E> currentListItem = head;
        head = new ListItem<>(data, currentListItem);
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
        ListItem<E> previousItem = head;

        for (int i = 0; i < index; i++) {
            previousItem = currentItem;
            currentItem = currentItem.getNext();
        }

        ListItem<E> newListItem = new ListItem<>(data);
        previousItem.setNext(newListItem);
        newListItem.setNext(currentItem);

        length++;
    }

    // 1.7. Удаление узла по значению, пусть выдает true, если элемент был удален.
    public boolean deleteByData(E data) {
        boolean isItemDeleted = false;

        // Начинаем с головы, пока не дойдем до конца.
        for (ListItem<E> currentItem = head, previousItem = head;
             currentItem != null;
             previousItem = currentItem,
                     currentItem = currentItem.getNext()) {
            // Если значение текущего элемента идентично искомому значению:
            if (currentItem.getData().equals(data)) {
                if (currentItem.equals(previousItem)) {
                    // setHead(currentItem.getNext());
                    head = currentItem.getNext();
                } else {
                    previousItem.setNext(currentItem.getNext());
                }

                isItemDeleted = true;
                length--;

                break;
            }
        }

        return isItemDeleted;
    }

    // 1.8. Удаление первого элемента, пусть выдает значение элемента.
    public E deleteFirst() {
        checkIndex(0, length);

        return this.deleteByIndex(0);
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
        List<E> newList = new List<>();

        for (ListItem<E> currentItem = head;
             currentItem != null;
             currentItem = currentItem.getNext()) {
            newList.add(currentItem.getData());
        }

        return newList;
    }

    // Добавление элемента в конец.
    public void add(E data) {
        addByIndex(length, data);
    }

    @Override
    public String toString() {
        // Текущий элемент.
        ListItem<E> currentListItem;
        StringBuilder sb = new StringBuilder();

        // Все справедливо если список не пустой.
        if (head != null) {
            sb.append("[");

            // Начинаем с головы и пока у текущего есть next.
            for (currentListItem = head;
                 currentListItem.getNext() != null;
                 currentListItem = currentListItem.getNext()) {
                sb.append(currentListItem.getData()).append(", ");
            }

            sb.append(currentListItem.getData()).append("]");
        } else {
            // TODO: Тут как-то коряво. Надо null заменить на что-то...
            return null;
        }

        return sb.toString();
    }

    // Проверка индекса на принадлежность допустимому диапазону.
    // private - доступ только внутри класса.
    // static - не нужно, т.к. метод из вне не нужно и нельзя вызывать.
    private void checkIndex(int index, int maxIndex) {
        if (index < 0 || index >= maxIndex) {
            throw new IllegalArgumentException("Index: " + index + ", Длина списка: " + length + ". Индекс выходит за пределы списка.");
            //throw new IndexOutOfBoundsException("Индекс [" + index + "] выходит за границы [0 - "+ (maxIndex - 1) + "] списка.");
        }
    }
}

/*  Описание класса:
    ===============================
    - Список - набор связанных объектов.
    - Связный список - это набор связанных объектов,
      где ссылка переносит нас от одного элемента к другому.
    - Односвязный список - это объект, содержащий ССЫЛКУ на первый элемент списка.
        Эту ссылку называют головой - head.
        Голова - это ссылка. Ссылка на первый элемент списка.
        next - это ссылка.
        next == null - последний элемент.

    1.9. Разворот списка за линейное время.
    - 1. Запоминаем адрес следующего за текущим элемента.
    - 2. Делаем чтобы текущий элемент ссылался на предыдущий. И назначаем его головой.
    - 3. Назначаем в качестве нового предыдущего элемента текущий.
    - 4. А новый текущий теперь будет элемент, бывший следующий у бывшего текущего, адрес которого мы запомнили в temp.
    - 5. Повторяем 2. Пока не закончится список.


    Вопросы.

    1. Пустой конструктор - почитать?
    2. getItemByIndex тоже Item лишнее?
    3. Как правильно оформить сообщение исключения?
    4. Где посмотреть стек вызовов?
    5. Почитать что такое NoSuchElementException. И, вообще, основные подвиды исключений.
    6. п. 15. IndexOutOfBoundsException - вроде подходит, но он прерывает программу - красный код.
    7. getFirst() и deleteFirst() оставить исключение или сделать проверку на пустой список с возвращением null?
 */