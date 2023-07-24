public class Line {
    private final double from;
    private final double to;
    private final double anotherLineLength;
    private final double something;

    public Line(double from, double to) {
        this.from = from;
        this.to = to;
        // Получается, в конструкторе можно так.
        this.anotherLineLength = to - from;
        this.something = 100;
    }

    public double getLength() {
        return to - from;
    }

    public double getAnotherLength() {
        return anotherLineLength + something;
    }
}
