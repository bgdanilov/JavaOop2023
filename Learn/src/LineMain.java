public class LineMain {
    public static void main(String[] args) {
        Line line = new Line(1, 4);

        System.out.println(line.getLength());

        System.out.println(line.getAnotherLength());

        line.printSomeVar();
        // Обращение к static переменной, минуя объект, сразу из класса.
        System.out.println(Line.someVar);
    }
}
