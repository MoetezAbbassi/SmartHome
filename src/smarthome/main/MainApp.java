package smarthome.main;

import smarthome.model.*;
import smarthome.home.*;
import smarthome.exceptions.*;
import java.util.Scanner;
import java.util.List;

public class MainApp {
    private static Home home;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Smart Home Automation Simulator     ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        System.out.print("Enter your home name: ");
        String homeName = scanner.nextLine();
        home = new Home(homeName);
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    manageRooms();
                    break;
                case 2:
                    manageDevices();
                    break;
                case 3:
                    controlDevices();
                    break;
                case 4:
                    viewStatus();
                    break;
                case 5:
                    searchDevices();
                    break;
                case 6:
                    energyManagement();
                    break;
                case 0:
                    System.out.println("Shutting down Smart Home System...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Manage Rooms");
        System.out.println("2. Manage Devices");
        System.out.println("3. Control Devices");
        System.out.println("4. View Status");
        System.out.println("5. Search Devices");
        System.out.println("6. Energy Management");
        System.out.println("0. Exit");
        System.out.println("================================");
    }
    
    // ========== ROOM MANAGEMENT ==========
    private static void manageRooms() {
        System.out.println("\n--- Room Management ---");
        System.out.println("1. Add Room");
        System.out.println("2. Remove Room");
        System.out.println("3. List All Rooms");
        System.out.println("0. Back");
        
        int choice = getIntInput("Choice: ");
        
        switch (choice) {
            case 1:
                addRoom();
                break;
            case 2:
                removeRoom();
                break;
            case 3:
                listRooms();
                break;
        }
    }
    
    private static void addRoom() {
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        
        try {
            Room room = new Room(roomName);
            home.addRoom(room);
            System.out.println("✓ Room added successfully!");
        } catch (InvalidOperationException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void removeRoom() {
        System.out.print("Enter room name to remove: ");
        String roomName = scanner.nextLine();
        
        try {
            home.removeRoom(roomName);
            System.out.println("✓ Room removed successfully!");
        } catch (DeviceNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void listRooms() {
        List<Room> rooms = home.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms in the home.");
            return;
        }
        
        System.out.println("\n--- Rooms ---");
        for (Room room : rooms) {
            System.out.println("  • " + room);
        }
    }
    
    // ========== DEVICE MANAGEMENT ==========
    private static void manageDevices() {
        System.out.println("\n--- Device Management ---");
        System.out.println("1. Add Device");
        System.out.println("2. Remove Device");
        System.out.println("3. List All Devices");
        System.out.println("0. Back");
        
        int choice = getIntInput("Choice: ");
        
        switch (choice) {
            case 1:
                addDevice();
                break;
            case 2:
                removeDevice();
                break;
            case 3:
                home.listAllDevices();
                break;
        }
    }
    
    private static void addDevice() {
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        
        try {
            Room room = home.getRoom(roomName);
            
            System.out.println("\nSelect device type:");
            System.out.println("1. Light");
            System.out.println("2. Thermostat");
            System.out.println("3. Smart TV");
            System.out.println("4. Sensor");
            
            int type = getIntInput("Device type: ");
            
            System.out.print("Enter device ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter device name: ");
            String name = scanner.nextLine();
            
            SmartDevice device = null;
            
            switch (type) {
                case 1:
                    device = new Light(id, name);
                    break;
                case 2:
                    device = new Thermostat(id, name);
                    break;
                case 3:
                    device = new SmartTV(id, name);
                    break;
                case 4:
                    System.out.print("Enter sensor type (MOTION/TEMPERATURE/SMOKE): ");
                    String sensorType = scanner.nextLine();
                    device = new Sensor(id, name, sensorType);
                    break;
                default:
                    System.out.println("Invalid device type.");
                    return;
            }
            
            room.addDevice(device);
            System.out.println("✓ Device added successfully!");
            
        } catch (DeviceNotFoundException | InvalidOperationException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void removeDevice() {
        System.out.print("Enter device ID to remove: ");
        String deviceId = scanner.nextLine();
        
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            String roomName = device.getLocation();
            Room room = home.getRoom(roomName);
            room.removeDevice(deviceId);
            System.out.println("✓ Device removed successfully!");
        } catch (DeviceNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    // ========== DEVICE CONTROL ==========
    private static void controlDevices() {
        System.out.println("\n--- Device Control ---");
        System.out.println("1. Turn On Device");
        System.out.println("2. Turn Off Device");
        System.out.println("3. Control Specific Device");
        System.out.println("4. Turn On All Devices");
        System.out.println("5. Turn Off All Devices");
        System.out.println("0. Back");
        
        int choice = getIntInput("Choice: ");
        
        switch (choice) {
            case 1:
                turnOnDevice();
                break;
            case 2:
                turnOffDevice();
                break;
            case 3:
                controlSpecificDevice();
                break;
            case 4:
                home.turnOnAllDevices();
                System.out.println("✓ All devices turned on!");
                break;
            case 5:
                home.turnOffAllDevices();
                System.out.println("✓ All devices turned off!");
                break;
        }
    }
    
    private static void turnOnDevice() {
        System.out.print("Enter device ID: ");
        String deviceId = scanner.nextLine();
        
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            device.turnOn();
            System.out.println("✓ Device turned on!");
        } catch (DeviceNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void turnOffDevice() {
        System.out.print("Enter device ID: ");
        String deviceId = scanner.nextLine();
        
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            device.turnOff();
            System.out.println("✓ Device turned off!");
        } catch (DeviceNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void controlSpecificDevice() {
        System.out.print("Enter device ID: ");
        String deviceId = scanner.nextLine();
        
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            
            if (device instanceof Light) {
                controlLight((Light) device);
            } else if (device instanceof Thermostat) {
                controlThermostat((Thermostat) device);
            } else if (device instanceof SmartTV) {
                controlTV((SmartTV) device);
            } else if (device instanceof Sensor) {
                controlSensor((Sensor) device);
            }
            
        } catch (DeviceNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void controlLight(Light light) {
        System.out.println("\n--- Light Control ---");
        System.out.println("1. Set Brightness");
        System.out.println("2. Set Energy Mode");
        int choice = getIntInput("Choice: ");
        
        if (choice == 1) {
            int brightness = getIntInput("Enter brightness (0-100): ");
            light.setBrightness(brightness);
        } else if (choice == 2) {
            System.out.print("Enter mode (ECO/NORMAL/HIGH): ");
            String mode = scanner.nextLine();
            light.setEnergyMode(mode);
        }
    }
    
    private static void controlThermostat(Thermostat thermostat) {
        System.out.println("\n--- Thermostat Control ---");
        System.out.println("1. Set Target Temperature");
        System.out.println("2. Set Mode");
        int choice = getIntInput("Choice: ");
        
        if (choice == 1) {
            System.out.print("Enter temperature (10-35°C): ");
            double temp = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            thermostat.setTargetTemp(temp);
        } else if (choice == 2) {
            System.out.print("Enter mode (HEAT/COOL/AUTO/OFF): ");
            String mode = scanner.nextLine();
            thermostat.setMode(mode);
        }
    }
    
    private static void controlTV(SmartTV tv) {
        System.out.println("\n--- TV Control ---");
        System.out.println("1. Set Volume");
        System.out.println("2. Set Channel");
        int choice = getIntInput("Choice: ");
        
        if (choice == 1) {
            int volume = getIntInput("Enter volume (0-100): ");
            tv.setVolume(volume);
        } else if (choice == 2) {
            int channel = getIntInput("Enter channel: ");
            tv.setChannel(channel);
        }
    }
    
    private static void controlSensor(Sensor sensor) {
        System.out.println("\n--- Sensor Control ---");
        System.out.println("1. Simulate Detection");
        System.out.println("2. Reset Sensor");
        int choice = getIntInput("Choice: ");
        
        if (choice == 1) {
            System.out.print("Enter detection data: ");
            String data = scanner.nextLine();
            sensor.simulate(data);
        } else if (choice == 2) {
            sensor.reset();
        }
    }
    
    // ========== VIEW STATUS ==========
    private static void viewStatus() {
        System.out.println("\n--- Status View ---");
        System.out.println("1. Home Summary");
        System.out.println("2. All Devices Details");
        System.out.println("3. Specific Device Status");
        System.out.println("0. Back");
        
        int choice = getIntInput("Choice: ");
        
        switch (choice) {
            case 1:
                System.out.println("\n" + home.getHomeSummary());
                break;
            case 2:
                home.listAllDevices();
                break;
            case 3:
                viewDeviceStatus();
                break;
        }
    }
    
    private static void viewDeviceStatus() {
        System.out.print("Enter device ID: ");
        String deviceId = scanner.nextLine();
        
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            System.out.println("\n" + device.getStatus());
        } catch (DeviceNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    // ========== SEARCH ==========
    private static void searchDevices() {
        System.out.println("\n--- Search Devices ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Type");
        System.out.println("0. Back");
        
        int choice = getIntInput("Choice: ");
        
        switch (choice) {
            case 1:
                searchById();
                break;
            case 2:
                searchByType();
                break;
        }
    }
    
    private static void searchById() {
        System.out.print("Enter device ID: ");
        String deviceId = scanner.nextLine();
        
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            System.out.println("\nFound: " + device.getStatus());
        } catch (DeviceNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void searchByType() {
        System.out.print("Enter device type (Light/Thermostat/SmartTV/Sensor): ");
        String type = scanner.nextLine();
        
        List<SmartDevice> devices = home.findDevicesByType(type);
        
        if (devices.isEmpty()) {
            System.out.println("No devices of type '" + type + "' found.");
            return;
        }
        
        System.out.println("\nFound " + devices.size() + " device(s):");
        for (SmartDevice device : devices) {
            System.out.println("  • " + device.getDeviceName() + " (" + device.getDeviceId() + ")");
        }
    }
    
    // ========== ENERGY MANAGEMENT ==========
    private static void energyManagement() {
        System.out.println("\n--- Energy Management ---");
        System.out.println("1. View Total Energy Consumption");
        System.out.println("2. Set All Devices to ECO Mode");
        System.out.println("3. View Energy by Device");
        System.out.println("0. Back");
        
        int choice = getIntInput("Choice: ");
        
        switch (choice) {
            case 1:
                System.out.printf("\nTotal Energy Consumption: %.2f W\n", 
                    home.getTotalEnergyConsumption());
                break;
            case 2:
                setAllToEcoMode();
                break;
            case 3:
                viewEnergyByDevice();
                break;
        }
    }
    
    private static void setAllToEcoMode() {
        for (SmartDevice device : home.getAllDevices()) {
            if (device instanceof EnergyConsumer) {
                ((EnergyConsumer) device).setEnergyMode("ECO");
            }
        }
        System.out.println("✓ All devices set to ECO mode!");
    }
    
    private static void viewEnergyByDevice() {
        System.out.println("\n--- Energy Consumption by Device ---");
        for (SmartDevice device : home.getAllDevices()) {
            if (device instanceof EnergyConsumer) {
                double consumption = ((EnergyConsumer) device).getEnergyConsumption();
                System.out.printf("  • %s: %.2f W\n", device.getDeviceName(), consumption);
            }
        }
    }
    
    // ========== HELPER METHODS ==========
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear buffer
            }
        }
    }
}
