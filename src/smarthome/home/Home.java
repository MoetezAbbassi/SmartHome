package smarthome.home;

import smarthome.model.SmartDevice;
import smarthome.model.EnergyConsumer;
import smarthome.exceptions.DeviceNotFoundException;
import smarthome.exceptions.InvalidOperationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home {
    private String homeName;
    private HashMap<String, Room> rooms; // roomName -> Room object
    
    public Home(String homeName) {
        this.homeName = homeName;
        this.rooms = new HashMap<>();
    }
    
    // Add a room to the home
    public void addRoom(Room room) throws InvalidOperationException {
        if (rooms.containsKey(room.getRoomName())) {
            throw new InvalidOperationException(
                "Room '" + room.getRoomName() + "' already exists in the home");
        }
        rooms.put(room.getRoomName(), room);
        System.out.println("Added room: " + room.getRoomName());
    }
    
    // Remove a room
    public void removeRoom(String roomName) throws DeviceNotFoundException {
        if (!rooms.containsKey(roomName)) {
            throw new DeviceNotFoundException("Room '" + roomName + "' not found");
        }
        rooms.remove(roomName);
        System.out.println("Removed room: " + roomName);
    }
    
    // Get room by name
    public Room getRoom(String roomName) throws DeviceNotFoundException {
        if (!rooms.containsKey(roomName)) {
            throw new DeviceNotFoundException("Room '" + roomName + "' not found");
        }
        return rooms.get(roomName);
    }
    
    // Get all rooms
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
    
    // Find device by ID across all rooms
    public SmartDevice findDeviceById(String deviceId) throws DeviceNotFoundException {
        for (Room room : rooms.values()) {
            try {
                return room.findDeviceById(deviceId);
            } catch (DeviceNotFoundException e) {
                // Continue searching in other rooms
            }
        }
        throw new DeviceNotFoundException(
            "Device with ID '" + deviceId + "' not found in any room");
    }
    
    // Find all devices of a specific type across all rooms
    public List<SmartDevice> findDevicesByType(String type) {
        List<SmartDevice> result = new ArrayList<>();
        for (Room room : rooms.values()) {
            result.addAll(room.findDevicesByType(type));
        }
        return result;
    }
    
    // Get all devices in the entire home
    public List<SmartDevice> getAllDevices() {
        List<SmartDevice> allDevices = new ArrayList<>();
        for (Room room : rooms.values()) {
            allDevices.addAll(room.getAllDevices());
        }
        return allDevices;
    }
    
    // Turn off all devices in the home
    public void turnOffAllDevices() {
        System.out.println("Turning off all devices in " + homeName);
        for (Room room : rooms.values()) {
            room.turnOffAllDevices();
        }
    }
    
    // Turn on all devices in the home
    public void turnOnAllDevices() {
        System.out.println("Turning on all devices in " + homeName);
        for (Room room : rooms.values()) {
            room.turnOnAllDevices();
        }
    }
    
    // Calculate total energy consumption
    public double getTotalEnergyConsumption() {
        double total = 0.0;
        for (SmartDevice device : getAllDevices()) {
            if (device instanceof EnergyConsumer) {
                total += ((EnergyConsumer) device).getEnergyConsumption();
            }
        }
        return total;
    }
    
    // List all devices and their statuses
    public void listAllDevices() {
        System.out.println("\n=== " + homeName + " - All Devices ===");
        if (rooms.isEmpty()) {
            System.out.println("No rooms in the home.");
            return;
        }
        
        for (Room room : rooms.values()) {
            System.out.println(room.getRoomStatus());
        }
        
        System.out.printf("Total Energy Consumption: %.2f W\n", getTotalEnergyConsumption());
    }
    
    // Get home summary
    public String getHomeSummary() {
        int totalRooms = rooms.size();
        int totalDevices = getAllDevices().size();
        int devicesOn = 0;
        
        for (SmartDevice device : getAllDevices()) {
            if (device.isOn()) {
                devicesOn++;
            }
        }
        
        return String.format("%s: %d rooms, %d devices (%d on, %d off), %.2f W total consumption",
            homeName, totalRooms, totalDevices, devicesOn, 
            totalDevices - devicesOn, getTotalEnergyConsumption());
    }
    
    // Getters
    public String getHomeName() {
        return homeName;
    }
    
    public int getRoomCount() {
        return rooms.size();
    }
    
    @Override
    public String toString() {
        return getHomeSummary();
    }
}
