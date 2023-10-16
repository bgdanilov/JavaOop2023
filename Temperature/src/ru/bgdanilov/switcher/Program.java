package ru.bgdanilov.switcher;

public class Program {
    public static void main(String[] args) {
        Switcher switcher = new Switcher();
        Lamp lamp = new Lamp();
        Radio radio = new Radio();

        // Осталось занести в поле нашего выключателя ссылку на объект класса,
        // реализующего интерфейс ElectricityConsumer.
        switcher.addElectricityListener(lamp);
        switcher.addElectricityListener(radio);

        switcher.addElectricityListener(new ElectricityConsumer() {
            @Override
            public void electricityOn() {
                System.out.println("Пожар!");
            }
        });

        switcher.addElectricityListener(() -> System.out.println("Fire!"));

        switcher.switchOn();
    }
}

/*
1. Как кнопке вызвать метод, про который она ничего не знает?
- в С для этого еть тип данных - указатель на функцию. Содержит адрес блока памяти с функцией.
Или нужно, чтобы метод одного класса сигнализировал методам других классов о произошедшем событии,
но ничего про них не зная.
 */
