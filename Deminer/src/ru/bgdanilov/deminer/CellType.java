package ru.bgdanilov.deminer;

public enum CellType {
    EMPTY("e", 0), MINE("m", 0), DIGIT("d",1);

    private final String name;
    public final int number;

    CellType(String name, int number) {
        this.number = number;
        this.name = name;
    }

    public int getNumber(){
        return this.number;
    }

    public String getName(){
        return this.name;
    }
}
