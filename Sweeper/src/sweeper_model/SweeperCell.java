package sweeper_model;

public class SweeperCell {
    protected final int row;
    protected final int column;
    protected char status = 'C'; // O - opened, C -closed, F - flag.
    protected int adjacentMinesAmount;
    protected boolean isMine;

    public SweeperCell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public char getStatus() {
        return status;
    }

    public boolean getIsMine() {
        return isMine;
    }

    public int getAdjacentMinesAmount() {
        return adjacentMinesAmount;
    }
}