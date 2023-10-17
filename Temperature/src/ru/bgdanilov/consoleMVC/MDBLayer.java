package ru.bgdanilov.consoleMVC;

public class MDBLayer implements MModelLayer {
    @Override
    public MTemperature getMTemperature() {
        return new MTemperature(22, "C");
    }
}
