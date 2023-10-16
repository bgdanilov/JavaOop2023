package ru.bgdanilov.switcher;

import java.util.ArrayList;
import java.util.List;

public class Switcher {
    // Поле типа интерфейса.
    // - это поле может содержать ссылку на объект любого класса, реализующего данный интерфейс.
    private List<ElectricityConsumer> listeners = new ArrayList<>();

    public void addElectricityListener(ElectricityConsumer listener) {
        listeners.add(listener);
    }

    public void removeElectricityListener(ElectricityConsumer listener) {
        listeners.remove(listener);
    }

    public void switchOn() {
        System.out.println("Выключатель включили.");

        // Проверим, не пустое ли поле consumer. Вызовем метод этого объекта.
        //if (consumer != null) {
        //    consumer.electricityOn();
        // }
        for (ElectricityConsumer item : listeners) {
            item.electricityOn();
        }
    }
}
