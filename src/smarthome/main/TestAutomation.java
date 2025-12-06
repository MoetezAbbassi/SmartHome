package smarthome.main;

import smarthome.model.*;
import smarthome.home.*;
import smarthome.controller.*;
import smarthome.exceptions.*;

public class TestAutomation {
    public static void main(String[] args) {
        try {
            System.out.println("=== Smart Home Automation Test ===\n");
            
            // Setup home
            Home home = new Home("Smart House");
            Room livingRoom = new Room("Living Room");
            Room bedroom = new Room("Bedroom");
            
            home.addRoom(livingRoom);
            home.addRoom(bedroom);
            
            // Create devices
            Light light1 = new Light("L001", "Living Room Light");
            Sensor motionSensor = new Sensor("S001", "Motion Sensor", "MOTION");
            Thermostat thermostat = new Thermostat("T001", "Main Thermostat");
            Light bedroomLight = new Light("L002", "Bedroom Light");
            
            livingRoom.addDevice(light1);
            livingRoom.addDevice(motionSensor);
            livingRoom.addDevice(thermostat);
            bedroom.addDevice(bedroomLight);
            
            motionSensor.turnOn();
            thermostat.turnOn();
            
            // Create Central Controller
            CentralController controller = new CentralController(home);
            
            // ========== RULE 1: Motion Detection ==========
            AutomationRule motionRule = new AutomationRule("R001", "Motion Activated Lighting");
            motionRule.addCondition(new SensorTriggeredCondition("S001"));
            motionRule.addAction(new TurnOnDeviceAction("L001"));
            motionRule.addAction(new NotificationAction("Motion detected! Light turned on."));
            controller.addRule(motionRule);
            
            // ========== RULE 2: Energy Saving ==========
            AutomationRule energyRule = new AutomationRule("R002", "Energy Saver");
            energyRule.addCondition(new EnergyThresholdCondition(2000, true)); // above 2000W
            energyRule.addAction(new SetEcoModeAction());
            energyRule.addAction(new NotificationAction("High energy usage! Switching to ECO mode."));
            controller.addRule(energyRule);
            
            // ========== RULE 3: Night Mode ==========
            AutomationRule nightRule = new AutomationRule("R003", "Night Mode");
            nightRule.addCondition(new TimeCondition(22, 0)); // 10:00 PM
            nightRule.addAction(new TurnOffDeviceAction("L001"));
            nightRule.addAction(new TurnOffDeviceAction("L002"));
            nightRule.addAction(new NotificationAction("Good night! Lights turned off."));
            controller.addRule(nightRule);
            
            // List all rules
            controller.listAllRules();
            
            // ========== TEST EXECUTION ==========
            System.out.println("\n=== Testing Rule Execution ===\n");
            
            // Test 1: Trigger motion sensor
            System.out.println("--- Test 1: Motion Detection ---");
            motionSensor.simulate("Motion detected at entrance");
            controller.executeRule("R001");
            
            System.out.println("\n--- Test 2: High Energy Usage ---");
            // Turn on multiple devices to increase energy
            thermostat.setTargetTemp(25);
            controller.executeRule("R002");
            
            System.out.println("\n--- Test 3: Execute All Rules ---");
            controller.executeAllRules();
            
            // Generate report
            controller.generateReport();
            
            // Test disabling a rule
            System.out.println("\n--- Disabling Motion Rule ---");
            controller.disableRule("R001");
            motionSensor.reset();
            motionSensor.simulate("Motion detected again");
            controller.executeRule("R001"); // Should not trigger
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
