package ru.bgdanilov.list;

// Это класс односвязного списка.
public class List<T> {
    private ListItem<T> head; // первый элемент - голова списка.
    private int length = 0; //  тут храним длину списка.

    public List() {
        head = null; // значит список пустой.
    }

    public void setHead(ListItem<T> head) {
        this.head = head;
    }

    // 1.1. Получение размера списка.
    public int getLength() {
        return length;
    }

    // Подсчет размера. Для проверки.
    public int countLength() {
        int length = 0;

        for (ListItem<T> currentItem = head;
             currentItem != null;
             currentItem = currentItem.getNext()) {
            length++;
        }
        return length;
    }

    // 1.2. Получение значения первого элемента.
    public T getFirstItem() {
        if (head == null) {
            throw new IllegalArgumentException("У пустого списка не может быть первого элемента!");
        }

        return head.getData();
    }

    // 1.3. Получение/изменение значения по указанному индексу.
    // Получение.
    public T getItemByIndex(int index) {
        if (index < 0 || index > length - 1) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        ListItem<T> currentItem = head;
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem.getData();
    }

    // Изменение с выдачей старого значения.
    public T changeItemByIndex(int index, T data) {
        if (index < 0 || index > length - 1) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        ListItem<T> currentItem = head;
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        T oldListItem = currentItem.getData();
        currentItem.setData(index, data);

        return oldListItem;
    }

    // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
    public T deleteItemByIndex(int index) {
        if (index < 0 || index > length - 1) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        ListItem<T> currentItem = head;
        ListItem<T> previousItem = head;

        // Если нужно удалить нулевой элемент, то записываем в голову следующий за ним.
        if (index == 0) {
            setHead(currentItem.getNext());
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
    public void addFirst(T data) {
        ListItem<T> currentListItem = head;

        // В голову записываем новый созданный элемент.
        head = new ListItem<T>(data);

        // Если список был не пуст, то в next новой головы записываем
        // бывший головной элемент.
        if (currentListItem != null) {
            // В головной элемент записываем ссылку на бывший текущий элемент.
            head.setNext(currentListItem);
        }

        length++;
    }

    // 1.6. Вставка элемента по индексу.
    public void addItemByIndex(int index, T data) {
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        // Индекс равен нулю. Значит в начало вставить.
        if (index == 0) {
            addFirst(data);
        } else {
            ListItem<T> currentItem = head;
            ListItem<T> previousItem = head;

            for (int i = 0; i < index; i++) {
                previousItem = currentItem;
                currentItem = currentItem.getNext();
            }

            ListItem<T> newListItem = new ListItem<>(data);
            previousItem.setNext(newListItem);
            newListItem.setNext(currentItem);

            length++;
        }
    }

    // 1.7. Удаление узла по значению, пусть выдает true, если элемент был удален.
    public boolean deleteByData(T data) {
        boolean isItemDeleted = false;

        // Начинаем с головы, пока не дойдем до конца.
        for (ListItem<T> currentItem = head, previousItem = head;
             currentItem != null;
             previousItem = currentItem,
                     currentItem = currentItem.getNext()) {
            // Если значение текущего элемента идентично искомому значению:
            if (currentItem.getData().equals(data)) {
                if (currentItem.equals(previousItem)) {
                    setHead(currentItem.getNext());
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
    public T deteteFirst() {
        return this.deleteItemByIndex(0);
    }

    // 1.9. Разворот списка за линейное время.
    public void reverse() {
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
        ListItem<T> currentItem = head; // адрес элемента в памяти.
        ListItem<T> previousItem = null;

        for (ListItem<T> temp = currentItem.getNext();
             temp != null;
             previousItem = currentItem, currentItem = temp, temp = temp.getNext()) {
            currentItem.setNext(previousItem);
        }

        currentItem.setNext(previousItem);
        head = currentItem;
    }

    // 1.10. Копирование списка.
    public List<T> copyList() {
        List<T> newList = new List<>();

        for (ListItem<T> currentItem = head;
             currentItem != null;
             currentItem = currentItem.getNext()) {
            newList.addItem(currentItem.getData());
        }

        return newList;
    }

    // Добавление элемента в конец.
    public void addItem(T data) {
        // Создается элемент data = "One", next = null.
        ListItem<T> newListItem = new ListItem<>(data);

        // Если голова равна нулю, что происходит при первичном создании списка,
        // то созданный элемент - первый и голова - есть ссылка на него.
        if (head == null) {
            // в голову записываем ссылку на наш только что созданный элемент
            head = newListItem;
            length = 1;
            // В противном случае, если голова есть, то нужно дойти до конца списка
            // и вставить элемент туда.
        } else {
            ListItem<T> currentListItem = head; // ссылка на текущий элемент.
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
        ListItem<T> currentListItem;
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
    - 5. адрес которого мы замомнили в temp.
    - 6. Повторяем 2. Пока не закончится список.
 */