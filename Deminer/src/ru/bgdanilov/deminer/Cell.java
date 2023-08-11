package ru.bgdanilov.deminer;

public class Cell {
    private final int rowNumber;
    private final int colNumber;
    private String name;
    private boolean isHidden;
    private boolean isMarked;
    public CellType type;

    public Cell(int rowNumber, int colNumber, String name, boolean isHidden, boolean isMarked, CellType type) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.name = name;
        this.isHidden = isHidden;
        this.isMarked = isMarked;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType (CellType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public int getColNumber() {
        return colNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}