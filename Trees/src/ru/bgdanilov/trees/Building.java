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
    // Почему compareTo красный?
    public int compareTo(Building otherBuilding) {
        return Integer.compare(roomsAmount, otherBuilding.roomsAmount);
        //return roomsAmount - otherBuilding.roomsAmount;
    }

    @Override
    public String toString() {
        return type + ": " + roomsAmount;
    }
}
