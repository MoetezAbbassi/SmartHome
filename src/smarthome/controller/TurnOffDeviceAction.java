package smarthome.controller;

import smarthome.home.Home;
import smarthome.model.SmartDevice;
import smarthome.exceptions.DeviceNotFoundException;

public class TurnOffDeviceAction implements Action {
    private String deviceId;
    
    public TurnOffDeviceAction(String deviceId) {
        this.deviceId = deviceId;
    }
    
    @Override
    public void execute(Home home) {
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            if (device.isOn()) {
                device.turnOff();
                System.out.println("[AUTOMATION] Turned off " + device.getDeviceName());
            }
        } catch (DeviceNotFoundException e) {
            System.out.println("[AUTOMATION ERROR] Device " + deviceId + " not found");
        }
    }
    
    @Override
    public String describe() {
        return "Turn OFF device " + deviceId;
    }
}
