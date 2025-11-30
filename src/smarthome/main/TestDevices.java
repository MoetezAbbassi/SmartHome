package smarthome.main;

import smarthome.model.*;

public class TestDevices {
    public static void main(String[] args) {
        System.out.println("=== Testing Smart Devices ===\n");
        
        // Create devices
        Light livingRoomLight = new Light("L001", "Living Room Light");
        Thermostat mainThermostat = new Thermostat("T001", "Main Thermostat");
        SmartTV bedroomTV = new SmartTV("TV001", "Bedroom TV");
        Sensor motionSensor = new Sensor("S001", "Front Door Sensor", "MOTION");
        
        // Test Light
        livingRoomLight.turnOn();
        livingRoomLight.setBrightness(75);
        livingRoomLight.setEnergyMode("ECO");
        System.out.println(livingRoomLight.getStatus());
        System.out.println();
        
        // Test Thermostat
        mainThermostat.turnOn();
        mainThermostat.setTargetTemp(24.0);
        mainThermostat.scheduleAction(java.time.LocalTime.of(6, 0), "HEAT to 22°C");
        System.out.println(mainThermostat.getStatus());
        System.out.println();
        
        // Test TV
        bedroomTV.turnOn();
        bedroomTV.setChannel(5);
        bedroomTV.setVolume(30);
        System.out.println(bedroomTV.getStatus());
        System.out.println();
        
        // Test Sensor
        motionSensor.turnOn();
        motionSensor.simulate("Motion detected at entrance");
        System.out.println(motionSensor.getStatus());
        
        System.out.println("\n=== Polymorphism Test ===");
        SmartDevice[] devices = {livingRoomLight, mainThermostat, bedroomTV, motionSensor};
        for (SmartDevice device : devices) {
            System.out.println(device.getStatus());
        }
    }
}
