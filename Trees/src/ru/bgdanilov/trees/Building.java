package ru.bgdanilov.trees;

public record Building(String type, int roomsAmount) implements Comparable<Building> {
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