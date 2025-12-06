package smarthome.controller;

import smarthome.home.Home;
import smarthome.model.SmartDevice;
import smarthome.model.EnergyConsumer;

public class SetEcoModeAction implements Action {
    
    @Override
    public void execute(Home home) {
        int count = 0;
        for (SmartDevice device : home.getAllDevices()) {
            if (device instanceof EnergyConsumer) {
                ((EnergyConsumer) device).setEnergyMode("ECO");
                count++;
            }
        }
        System.out.println("[AUTOMATION] Set " + count + " devices to ECO mode");
    }
    
    @Override
    public String describe() {
        return "Set all devices to ECO mode";
    }
}
