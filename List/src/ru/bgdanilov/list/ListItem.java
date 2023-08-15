package ru.bgdanilov.list;
// Это класс элемента односвязного списка.
public class ListItem<T> {
    private T data; // содержимое элемента.
    private ListItem<T> next; // ссылка на следующий за ним элемент.

    public ListItem (T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return this.data;
    }

    public void setData (int index, T data) {
        this.data = data;
    }

    public ListItem<T> getNext() {
        return next;
    }

    public void setNext(ListItem<T> next) {
        this.next = next;
    }
}
