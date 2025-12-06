package smarthome.controller;

import smarthome.home.Home;
import smarthome.model.SmartDevice;
import smarthome.model.Sensor;
import smarthome.exceptions.DeviceNotFoundException;

public class SensorTriggeredCondition implements Condition {
    private String sensorId;
    
    public SensorTriggeredCondition(String sensorId) {
        this.sensorId = sensorId;
    }
    
    @Override
    public boolean evaluate(Home home) {
        try {
            SmartDevice device = home.findDeviceById(sensorId);
            if (device instanceof Sensor) {
                return ((Sensor) device).isTriggered();
            }
            return false;
        } catch (DeviceNotFoundException e) {
            System.out.println("Warning: Sensor " + sensorId + " not found");
            return false;
        }
    }
    
    @Override
    public String describe() {
        return "Sensor " + sensorId + " is triggered";
    }
}
