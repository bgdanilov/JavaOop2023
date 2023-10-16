package ru.bgdanilov.switcher;

public class Radio implements ElectricityConsumer {
    public void playMusic() {
        System.out.println("Ради о работает.");
    }

    @Override
    public void electricityOn() {
        playMusic();
    }
}
