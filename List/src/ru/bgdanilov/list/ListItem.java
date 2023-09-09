package ru.bgdanilov.list;

// Это класс элемента односвязного списка.
class ListItem<E> {
    private E data; // содержимое элемента.
    private ListItem<E> next; // ссылка на следующий за ним элемент.

    public ListItem(E data) {
        this.data = data;
    }

    public ListItem(E data, ListItem<E> next) {
        this.data = data;
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public ListItem<E> getNext() {
        return next;
    }

    public void setNext(ListItem<E> next) {
        this.next = next;
    }
}

/*
 *  Заметки.
 *  ===================
 *  1. Отсутствие модификатора у класса, метода или поля значит к нему модификатора по умолчанию.
 *  Класс, поле или метод виден всем классам в текущем пакете.
 */