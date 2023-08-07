// Приведение ссылочных типов.
public class TypeCasting {
    public static void main(String[] args) {
        // Прямое, восходящее преобраование от подкласса к суперклассу.
        // Восходящее, расширяющиеся.
        Object box = new Box(10);
        Box heavyBox = new HeavyBox(10, 20);

        // Преобразуем объект heavyBox к простой коробке. Зачем?
        Box box1 = (Box)heavyBox;
    }
}

class Box {
    private double size;

    public Box(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}

class HeavyBox extends Box {
    private double weight;
    public HeavyBox(double size, double weight) {
        super(size);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
