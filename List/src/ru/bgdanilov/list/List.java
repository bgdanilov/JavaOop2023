package ru.bgdanilov.list;

// Это класс односвязного списка.
public class List<E> {
    private ListItem<E> head; // первый элемент - голова списка.
    private int length; //  тут храним длину списка.

    public List() {}

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
        if (head == null) {
            throw new IllegalArgumentException("1.2. getFirstItem() head == null: у пустого списка не может быть первого элемента!");
        }

        return head.getData();
    }

    // 1.3. Получение/изменение значения по указанному индексу.
    // Получение.
    public E getByIndex(int index) {
        if (index < 0 || index > length - 1) {
            throw new IllegalArgumentException("1.3. getItemByIndex() index: индекс выходит за границы списка.");
        }

        ListItem<E> currentItem = head;
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem.getData();
    }

    // Изменение с выдачей старого значения.
    public E setByIndex(int index, E data) {
        if (index < 0 || index > length - 1) {
            throw new IllegalArgumentException("1.3. changeItemByIndex() index: индекс выходит за границы списка.");
        }

        ListItem<E> currentItem = head;
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        E oldListItem = currentItem.getData();
        currentItem.setData(index, data);

        return oldListItem;
    }

    // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
    public E deleteByIndex(int index) {
        if (index < 0 || index > length - 1) {
            throw new IllegalArgumentException("1.4. deleteItemByIndex() index: индекс выходит за границы списка.");
        }

        ListItem<E> currentItem = head;
        ListItem<E> previousItem = head;

        // Если нужно удалить нулевой элемент, то записываем в голову следующий за ним.
        if (index == 0) {
            //setHead(currentItem.getNext());
            head = currentItem.getNext();
        } else {
            for (int i = 0; i < index; i++) { //3
                previousItem = currentItem; // 3
                currentItem = currentItem.getNext();// 4
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
        ListItem<E> currentListItem = head;

        // В голову записываем новый созданный элемент.
        head = new ListItem<>(data);

        // Если список был не пуст, то в next новой головы записываем
        // бывший головной элемент.
        if (currentListItem != null) {
            // В головной элемент записываем ссылку на бывший текущий элемент.
            head.setNext(currentListItem);
        }

        length++;
    }

    // 1.6. Вставка элемента по индексу.
    public void addByIndex(int index, E data) {
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("1.6. addItemByIndex() index: индекс выходит за границы списка.");
        }

        // Индекс равен нулю. Значит в начало вставить.
        if (index == 0) {
            addFirst(data);
        } else {
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
        return this.deleteByIndex(0);
    }

    // 1.9. Разворот списка за линейное время.
    public void reverse() {
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
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
        // Создается элемент data = "One", next = null.
        ListItem<E> newListItem = new ListItem<>(data);

        // Если голова равна нулю, что происходит при первичном создании списка,
        // то созданный элемент - первый и голова - есть ссылка на него.
        if (head == null) {
            // в голову записываем ссылку на наш только что созданный элемент
            head = newListItem;
            length = 1;
            // В противном случае, если голова есть, то нужно дойти до конца списка
            // и вставить элемент туда.
        } else {
            ListItem<E> currentListItem = head; // ссылка на текущий элемент.
            // Пока у текущего следующий элемент есть..
            while (currentListItem.getNext() != null) {
                // ...текущий элемент равен следующему.
                currentListItem = currentListItem.getNext();
            }

            // Следующего элемента нет: записываем в next ссылку нового элемента.
            currentListItem.setNext(newListItem);
            length++;
        }
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
    - 4. А новый текущий теперь будет элемент, бывший следующий у у бывшего текущего,
    - 5. адрес которого мы запомнили в temp.
    - 6. Повторяем 2. Пока не закончится список.


    Вопросы.

    1. Пустой конструктор - почитать?
    2. getItemByIndex тоже Item лишнее?
 */