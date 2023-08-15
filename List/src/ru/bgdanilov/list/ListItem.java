package ru.bgdanilov.list;
// Это класс элемента односвязного списка.
public class ListItem<T> {
    protected T data; // содержимое элемента.
    protected ListItem<T> next; // ссылка на следующий за ним элемент.

    public ListItem (T data) {
        this.data = data;
        this.next = null;
    }
}
