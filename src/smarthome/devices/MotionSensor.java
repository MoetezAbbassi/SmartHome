package com.smarthome.devices;

public class MotionSensor extends SmartDevice {

    public MotionSensor(String id, String name) {
        super(id, name);
        this.on = true; // sensors are always on
    }

    @Override
    public void turnOn() { this.on = true; }

    @Override
    public void turnOff() { this.on = false; }

    public boolean detectMotion() {
        return on; // simple simulation
    }

    @Override
    public String getStatus() {
        return "MotionSensor " + name + " is " + (on ? "ON" : "OFF");
    }
}
