package ru.bgdanilov.list;

// Это класс односвязного списка.
public class List<T> {
    private ListItem<T> head; // первый элемент - голова списка.
    private int size = 0; //  Тут храним длину списка.

    public List() {
        this.head = null; // значит список пустой.
    }

    public void setHead(ListItem<T> head) {
        this.head = head;
    }

    // 1.1. Получение размера списка.
    public int getSize() {
        return size;
    }

    // Подсчет размера. Для проверки.
    public int countSize() {
        int size = 0;

        for (ListItem<T> currentListItem = this.head;
             currentListItem != null;
             currentListItem = currentListItem.getNext()) {
            size++;
        }
        return size;
    }

    // 1.2. Получение значения первого элемента.
    public T getFirstItem() {
        if (head == null) {
            throw new IllegalArgumentException("У пустого списка не может быть первого элемента!");
        }
        return this.head.getData();
    }

    // 1.3. Получение/изменение значения по указанному индексу.
    // Получение.
    public T getItemByIndex(int index) {
        if (index >= size) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        ListItem<T> currentListItem = this.head;
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
        for (int i = 0; i < index; i++) {
            currentListItem = currentListItem.getNext();
        }

        return currentListItem.getData();
    }

    // Изменение с выдачей старого значения.
    public T change2ItemByIndex(int index, T data) {
        if (index >= size) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        ListItem<T> currentListItem = this.head;
        // TODO: Добавить исключение для пустого списка. Или вообще запретить создвать пустые списки.
        for (int i = 0; i < index; i++) {
            currentListItem = currentListItem.getNext();
        }

        T oldListItem = currentListItem.getData();
        currentListItem.setData(index, data);

        return oldListItem;
    }

    // 1.4. Удаление элемента по индексу, пусть выдает значение элемента.
    public T deleteByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        ListItem<T> currentListItem = this.head;

        for (int i = 0; i < index; i++) {
            currentListItem = currentListItem.getNext();
        }

        T oldListItem = currentListItem.getData();
        // Записать в текущий data и next из последующего, если они есть.
        currentListItem.setData((currentListItem.getNext()).getData());
        currentListItem.setNext((currentListItem.getNext()).getNext());

        size--;

        return oldListItem;
    }

    // 1.5. Вставка элемента в начало.
    public void addFirst(T data) {
        ListItem<T> currentListItem = this.head;

        // В голову записываем новый созданный элемент.
        this.head = new ListItem<T>(data);

        // Если список был не пуст, то в next новой головы записываем
        // бывший головной элемент.
        if (currentListItem != null) {
            // В головной элемент записываем ссылку на бывший текущий элемент.
            this.head.setNext(currentListItem);
        }

        size++;
    }

    // 1.6. Вставка элемента по индексу.
    public void addItemByIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new IllegalArgumentException("Индекс выходит за границы списка.");
        }

        // Индекс равен нулю. Значит в начало вставить.
        if (index == 0) {
            this.addFirst(data);
        } else {
            ListItem<T> currentListItem = this.head;
            ListItem<T> previousListItem = null;

            for (int i = 0; i < index; i++) {
                previousListItem = currentListItem;
                currentListItem = currentListItem.getNext();
            }

            ListItem<T> newListItem = new ListItem<>(data);
            previousListItem.setNext(newListItem);
            newListItem.setNext(currentListItem);

            size++;
        }
    }

    // 1.7. Удаление узла по значению, пусть выдает true, если элемент был удален.
    public boolean deleteByData(T data) {
        boolean isItemDeleted = false;

        for (ListItem<T> currentItem = head, previousItem = head;
             currentItem != null;
             previousItem = currentItem,
             currentItem = currentItem.getNext()) {
            // Если значение текущего элемента идентично искомому значению:
            if (currentItem.getData().equals(data)) {
                previousItem.setNext(currentItem.getNext());

                isItemDeleted = true;
                size--;
            }
        }

        return isItemDeleted;
    }


    // 1.8. Удаление первого элемента, пусть выдает значение элемента.
    public T deteteFirst() {
        return this.deleteByIndex(0);
    }


    public void addItem(T data) {
        // Создается элемент data = "One", next = null.
        ListItem<T> newListItem = new ListItem<>(data);

        // Если голова равна нулю, что происходит при первичном создании списка,
        // то созданный элемент - первый и голова - есть ссылка на него.
        if (this.head == null) {
            // в голову записываем ссылку на наш только что созданный элемент
            this.head = newListItem;
            this.size = 1;
            // В противном случае, если голова есть, то нужно дойти до конца списка
            // и вставить элемент туда.
        } else {
            ListItem<T> currentListItem = this.head; // ссылка на текущий элемент.
            // Пока у текущего следующий элемент есть..
            while (currentListItem.getNext() != null) {
                // ...текущий элемент равен следующему.
                currentListItem = currentListItem.getNext();
            }

            // Следующего элемента нет: записываем в next ссылку нового элемента.
            currentListItem.setNext(newListItem);
            this.size++;
        }
    }

    // 1.8. Разворот списка за линейное время.
    // Цикл работает с тремя переменными,
    // которым перед началом присваивается значение предыдущего, текущего и следующего узла.
    // (В этот момент значение предыдущего узла, естественно, пустое.)
    // Цикл начинается с проверки того, что следующий узел – не пустой,
    // и, если это так, выполняется тело цикла.
    // В цикле не происходит никакой магии:
    // у текущего узла полю, ссылающемуся на следующий элемент,
    // присваивается ссылка на предыдущий
    // (на первой итерации значение ссылки, соответственно, обнуляется,
    // что соответствует положению дел в последнем узле).
    // Ну и дальше переменным соответствующим предыдущему, текущему и следующему узлу присваиваются новые значения.
    // После выхода из цикла текущему (т.е. вообще последнему итерируемому)
    // узлу присваивается новое значение ссылки на следующий узел, т.к. выход из цикла происходит как раз в момент,
    // когда последний узел в списке становится текущим.

    /**
     *   prev    curr     next
     *   null    1[2] --> 2[3] --> 3[4] --> 4[null]
     *           1[n]
     *           prev     curr     next
     *           1[n] --> 2[3] --> 3[4] --> 4[null]
     *
     *
     */
    public void reverse() {
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

    @Override
    public String toString() {
        // Текущий элемент.
        ListItem<T> currentListItem;
        StringBuilder sb = new StringBuilder();

        // Все справедливо если список не пустой.
        if (this.head != null) {
            sb.append("[");

            // Начинаем с головы и пока у текущего есть next.
            for (currentListItem = this.head;
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
// Список - набор связанных объектов.
// Связный список - это набор связанных объектов,
// где ссылка переносит нас от одного элемента к другому.

// Односвязный список - это объект, содержащий ССЫЛКУ на первый элемент списка.

// Эту ссылку называют головой - head.
// Голова - это ссылка. Ссылка на первый элемент списка.
// next - это ссылка.
// next == null - последний элемент.

// Элемент по-умолчанию: data = "One", next = null.
// Список по умолчанию:  head == null.