package ru.bgdanilov.list;
// Это класс односвязного списка.

// Список - набор связанных объектов.
// Связный список - это набор связанных объектов,
// где ссылка переносит нас от одного элемента к другому.

// Односвязный список - это объект, содержащий ССЫЛКУ на первый элемент списка.
// Эту ссылку называют головой - head.
public class List<T> {
    private ListItem<T> head; // первый элемент - голова списка.
    private int count = 0; //  Тут храним длину списка.

    public List() {
        this.head = null; // значит список пустой.
    }

    public int getCount() {
        return count;
    }

    // Голова - это ссылка. Ссылка на первый элемент списка.
    // next - это ссылка.
    // next == null - последний элемент.

    // Элемент по-умолчанию: data = "One", next = null.
    // Список по умолчанию:  head == null.

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
            while (currentListItem.next != null) {
                // ...текущий элемент равен следующему.
                currentListItem = currentListItem.next;
            }

            // Следующего элемента нет: записываем в next ссылку нового элемента.
            currentListItem.next = newListItem;
            this.count++;
        }
    }

    @Override
    public String toString() {
        // Текущий элемент - голова, первый.
        ListItem<T> currentListItem = this.head;
        StringBuilder sb = new StringBuilder();

        if (this.head != null) {
            sb.append("[");
            sb.append(currentListItem.data);

            // Пока у текущего есть next.
            while (currentListItem.next != null) {
                currentListItem = currentListItem.next;
                sb.append(", ").append(currentListItem.data);
            }

            sb.append("]");
        } else {
            return null;
        }

        return sb.toString();
    }
}