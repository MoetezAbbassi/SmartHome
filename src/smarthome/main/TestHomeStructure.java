package smarthome.main;

import smarthome.model.*;
import smarthome.home.*;
import smarthome.exceptions.*;

public class TestHomeStructure {
    public static void main(String[] args) {
        try {
            System.out.println("=== Creating Smart Home ===\n");
            
            // Create home
            Home myHome = new Home("My Smart Home");
            
            // Create rooms
            Room livingRoom = new Room("Living Room");
            Room bedroom = new Room("Bedroom");
            Room kitchen = new Room("Kitchen");
            
            // Add rooms to home
            myHome.addRoom(livingRoom);
            myHome.addRoom(bedroom);
            myHome.addRoom(kitchen);
            
            // Create devices
            Light light1 = new Light("L001", "Living Room Main Light");
            Light light2 = new Light("L002", "Living Room Reading Light");
            Thermostat thermostat = new Thermostat("T001", "Main Thermostat");
            SmartTV tv = new SmartTV("TV001", "Living Room TV");
            Sensor motionSensor = new Sensor("S001", "Front Door Sensor", "MOTION");
            Light bedroomLight = new Light("L003", "Bedroom Light");
            
            // Add devices to rooms
            livingRoom.addDevice(light1);
            livingRoom.addDevice(light2);
            livingRoom.addDevice(thermostat);
            livingRoom.addDevice(tv);
            livingRoom.addDevice(motionSensor);
            bedroom.addDevice(bedroomLight);
            
            // Turn on some devices
            light1.turnOn();
            light1.setBrightness(80);
            thermostat.turnOn();
            thermostat.setTargetTemp(22.5);
            tv.turnOn();
            tv.setVolume(25);
            
            // List all devices
            myHome.listAllDevices();
            
            System.out.println("\n=== Testing Search Functions ===");
            
            // Search by ID
            SmartDevice found = myHome.findDeviceById("T001");
            System.out.println("Found device: " + found.getStatus());
            
            // Search by type
            System.out.println("\nAll lights in home:");
            for (SmartDevice device : myHome.findDevicesByType("Light")) {
                System.out.println("  - " + device.getDeviceName());
            }
            
            System.out.println("\n" + myHome.getHomeSummary());
            
            // Test exception handling
            System.out.println("\n=== Testing Exceptions ===");
            
            // Try to add duplicate device
            try {
                Light duplicate = new Light("L001", "Duplicate Light");
                livingRoom.addDevice(duplicate);
            } catch (InvalidOperationException e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
            
            // Try to find non-existent device
            try {
                myHome.findDeviceById("XXXXX");
            } catch (DeviceNotFoundException e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
            
            // Try to remove device
            System.out.println("\n=== Removing Device ===");
            livingRoom.removeDevice("L002");
            System.out.println("Devices in living room: " + livingRoom.getDeviceCount());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
