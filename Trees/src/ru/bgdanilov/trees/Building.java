package ru.bgdanilov.trees;

public class Building implements Comparable<Building> {
    private int roomsAmount;

    public Building(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }
    public int getRoomsAmount() {
        return roomsAmount;
    }

    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }
    @Override
    // Почему compareTo красный?
    public int compareTo(Building otherBuilding) {
        // return this.roomsAmount.compareTo(otherBuilding.getRoomsAmount()); // снимите комментарий.
        return 0;
    }
}
