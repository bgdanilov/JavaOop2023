package ru.bgdanilov.switcher;

public class Lamp implements ElectricityConsumer {
    public void lightOn() {
        System.out.println("Лампа горит. ");

        // Теперь нужно в самой лампе реализовать этот интерфейс.
        // Сказать, да, лампа является электрическим потребителем: consumer'ом.
    }

    @Override
    public void electricityOn() {
        lightOn();
    }
}
