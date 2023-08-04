import java.util.Objects;

public class MyEquals {
    public void main(String[] args) {
        // 1. По умолчанию, в классе Object, метод
        // equals(Object o) просто проверяет равенство ссылок при помощи ==
        // 2. Многие стандартные классы, такие как String, переопределяют метод equals,
        // чтобы он сравнивал содержимое объектов
        // 3. Поэтому в своих классах, если мы хотим сравнивать их объекты,
        // нужно переопределить метод equals

        B b = new B(1);
        A a = new A(2, new B(1));


        System.out.println(a.equals(b));

    }

    public static class B {
        private final int y;

        public B (int y) {
            this.y = y;
        }
    }

    public static class A {
        private final int x;
        private final B y;

        public A (int x, B y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            // 1. Проверка что ссылка оббъекта o и текущего - одна и та же.
            // Т.е. он равен сам себе.
            if (o == this) {
                return true;
            }
            // 2. Проверяем на null.
            // 2.1. Если нам передали вместо объекта null, то false.
            // 2.2. Сравниваются классы текущего объекта и переданного o.
            // Классы сравниваются по сслыке потому, что у объектов одного класса,
            // объект "класс" - один и тот же. Если ссылки разнее, то это уже разные классы.
            // Есть такой класс класс. getClass() его выдает.
            // Проверить!!!
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            // 3.
            // 3.1 Приводим объект к типу A. Что это значит?
            A pair = (A) o;
            // 3.2. Сравниваем поля.
            // y может быть null. Надо учесть.
            // Для этого используем Objects.equals(y, a.y)
            // - сравнивает ссылки и сам учитывает null'ы:
            //   если первый переданный объект null, будет false/
            //   иначе, вызызывается equals.
            return this.x == pair.x && Objects.equals(this.y, pair.y);
        }
    }
}
