package ru.bgdanilov.list;

// Это класс односвязного списка.
public class List<T> {
    private ListItem<T> head; // первый элемент - голова списка.
    private int count = 0; //  Тут храним длину списка.

    public List() {
        this.head = null; // значит список пустой.
    }

    // 1.1. Получение размера списка.
    public int getCount() {
        return count;
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
        if (index >= count) {
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
    public T change2ItemByIndex (int index, T data) {
        if (index >= count) {
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

    public void addItem(T data) {
        // Создается элемент data = "One", next = null.
        ListItem<T> newListItem = new ListItem<>(data);

        // Если голова равна нулю, что происходит при первичном создании списка,
        // то созданный элемент - первый и голова - есть ссылка на него.
        if (this.head == null) {
            // в голову записываем ссылку на наш только что созданный элемент
            this.head = newListItem;
            this.count = 1;
            // В противном случае, если голова есть, то нужно дойти до конца списка
            // и вставть элемент туда.
        } else {
            ListItem<T> currentListItem = this.head; // ссылка на текущий элемент.
            // Пока у текущего следующий элемент есть..
            while (currentListItem.getNext() != null) {
                // ...текущий элемент равен следующему.
                currentListItem = currentListItem.getNext();
            }

            // Следующего элемента нет: записываем в next ссылку нового элемента.
            currentListItem.setNext(newListItem);
            this.count++;
        }
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
                 currentListItem = currentListItem.getNext())
            {
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