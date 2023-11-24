package sweeper_model;

public class SweeperCell {
    private final int row;
    private final int column;
    private SweeperCellStatus status;
    private int adjacentMinesAmount;
    private boolean isMine;

    public SweeperCell(int row, int column) {
        this.row = row;
        this.column = column;
        status = SweeperCellStatus.CLOSED;
    }

    public  int getRow() {
        return row;
    }

    public  int getColumn() {
        return column;
    }

    public SweeperCellStatus getStatus() {
        return status;
    }

    public void setStatus(SweeperCellStatus status) {
        this.status = status;
    }

    public int getAdjacentMinesAmount() {
        return adjacentMinesAmount;
    }

    public void setAdjacentMinesAmount(int adjacentMinesAmount) {
        this.adjacentMinesAmount = adjacentMinesAmount;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }
}