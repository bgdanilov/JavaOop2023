package ru.bgdanilov.trees;

public class Building implements Comparable<Building> {
    private final String type;
    private int roomsAmount;

    public Building(String type, int roomsAmount) {
        this.type = type;
        this.roomsAmount = roomsAmount;
    }

    public String getType() {
        return type;
    }

    public int getRoomsAmount() {
        return roomsAmount;
    }

    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }

    @Override
    public int compareTo(Building otherBuilding) {
        return Integer.compare(roomsAmount, otherBuilding.roomsAmount);
    }

    @Override
    public String toString() {
        return type + ": " + roomsAmount;
    }

    @Override
    public boolean equals(Object object) {
        // Ссылаемся на тот же самый объект (ссылки равны).
        if (this == object) {
            return true;
        }

        // Ссылка пустая или ведет к объекту с классом, отличным от сравниваемого.
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        // Приводим Object к Building для сравнения полей.
        Building building = (Building) object;
        return roomsAmount == building.roomsAmount;
    }
}