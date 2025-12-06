package smarthome.controller;

import smarthome.home.Home;
import smarthome.model.SmartDevice;
import smarthome.exceptions.DeviceNotFoundException;

public class TurnOnDeviceAction implements Action {
    private String deviceId;
    
    public TurnOnDeviceAction(String deviceId) {
        this.deviceId = deviceId;
    }
    
    @Override
    public void execute(Home home) {
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            if (!device.isOn()) {
                device.turnOn();
                System.out.println("[AUTOMATION] Turned on " + device.getDeviceName());
            }
        } catch (DeviceNotFoundException e) {
            System.out.println("[AUTOMATION ERROR] Device " + deviceId + " not found");
        }
    }
    
    @Override
    public String describe() {
        return "Turn ON device " + deviceId;
    }
}
