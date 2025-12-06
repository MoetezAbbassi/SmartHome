package smarthome.controller;

import smarthome.home.Home;
import smarthome.model.SmartDevice;
import smarthome.exceptions.DeviceNotFoundException;

public class DeviceStateCondition implements Condition {
    private String deviceId;
    private boolean expectedState; // true = ON, false = OFF
    
    public DeviceStateCondition(String deviceId, boolean expectedState) {
        this.deviceId = deviceId;
        this.expectedState = expectedState;
    }
    
    @Override
    public boolean evaluate(Home home) {
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            return device.isOn() == expectedState;
        } catch (DeviceNotFoundException e) {
            System.out.println("Warning: Device " + deviceId + " not found");
            return false;
        }
    }
    
    @Override
    public String describe() {
        return "Device " + deviceId + " is " + (expectedState ? "ON" : "OFF");
    }
}
