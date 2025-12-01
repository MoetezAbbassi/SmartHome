package com.smarthome.devices;

import com.smarthome.core.EnergyConsumer;

public class SmartTV extends SmartDevice implements EnergyConsumer {
    private int volume = 10;
    private String channel = "Home Screen";
    private double powerWhenOn = 120.0;
    private double totalEnergy = 0.0;

    public SmartTV(String id, String name) {
        super(id, name);
    }

    public void setVolume(int v) { this.volume = v; }
    public void setChannel(String c) { this.channel = c; }

    @Override
    public void turnOn() { this.on = true; }
    @Override
    public void turnOff() { this.on = false; }

    @Override
    public String getStatus() {
        return "TV " + name + " is " + (on ? "ON" : "OFF") +
               " (channel=" + channel + ", volume=" + volume + ")";
    }

    @Override
    public double getCurrentPowerUsage() {
        return on ? powerWhenOn : 0;
    }

    @Override
    public double getTotalEnergyConsumed() {
        return totalEnergy;
    }

    public void addEnergy(double e) {
        totalEnergy += e;
    }
}
