package sweeper_model;

public enum SweeperDifficultyLevel {
    EASY ("Легкий", 10, 10, 1),
    MEDIUM ("Средний", 15, 20, 20),
    HARD ("Тяжелый", 20, 30, 60);

    private final String label;
    private final int rows;
    private final int columns;
    private final int mines;

    SweeperDifficultyLevel(String label, int rows, int columns, int mines) {
        this.label = label;
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
    }

    public String getLabel() {
        return label;
    }
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMines() {
        return mines;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
