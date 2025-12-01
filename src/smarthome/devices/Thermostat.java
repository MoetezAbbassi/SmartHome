package com.smarthome.devices;

import com.smarthome.core.EnergyConsumer;

public class Thermostat extends SmartDevice implements EnergyConsumer {
    private double setTemperature;
    private boolean heatingOn;
    private double powerWhenHeating = 1500.0;
    private double totalEnergy = 0.0;

    public Thermostat(String id, String name, double temp) {
        super(id, name);
        this.setTemperature = temp;
    }

    public void setTemperature(double temp) {
        this.setTemperature = temp;
    }

    @Override
    public void turnOn() {
        this.heatingOn = true;
        this.on = true;
    }

    @Override
    public void turnOff() {
        this.heatingOn = false;
        this.on = false;
    }

    @Override
    public String getStatus() {
        return "Thermostat " + name +
               " set to " + setTemperature + "°C, heating " +
               (heatingOn ? "ON" : "OFF");
    }

    @Override
    public double getCurrentPowerUsage() {
        return heatingOn ? powerWhenHeating : 0;
    }

    @Override
    public double getTotalEnergyConsumed() {
        return totalEnergy;
    }

    public void addEnergy(double e) {
        totalEnergy += e;
    }
}
