package com.smarthome.devices;

import com.smarthome.core.EnergyConsumer;

public class Light extends SmartDevice implements EnergyConsumer {
    private int brightness;
    private double totalEnergy = 0.0;
    private double powerWhenOn = 10.0;

    public Light(String id, String name) {
        super(id, name);
        this.brightness = 100;
    }

    @Override
    public void turnOn() { this.on = true; }

    @Override
    public void turnOff() { this.on = false; }

    public void setBrightness(int brightness) {
        this.brightness = Math.max(0, Math.min(100, brightness));
    }

    @Override
    public String getStatus() {
        return "Light " + name + " is " + (on ? "ON" : "OFF") +
               " (brightness=" + brightness + "%)";
    }

    @Override
    public double getCurrentPowerUsage() {
        return on ? powerWhenOn * (brightness / 100.0) : 0;
    }

    @Override
    public double getTotalEnergyConsumed() {
        return totalEnergy;
    }

    public void addEnergy(double e) {
        totalEnergy += e;
    }
}
