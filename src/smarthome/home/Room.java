package smarthome.home;

import smarthome.model.SmartDevice;
import smarthome.exceptions.DeviceNotFoundException;
import smarthome.exceptions.InvalidOperationException;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomName;
    private ArrayList<SmartDevice> devices;
    private int maxDevices;
    
    public Room(String roomName) {
        this(roomName, 20);
    }
    
    public Room(String roomName, int maxDevices) {
        this.roomName = roomName;
        this.maxDevices = maxDevices;
        this.devices = new ArrayList<>();
    }
    
    // Add device to room
    public void addDevice(SmartDevice device) throws InvalidOperationException {
        if (devices.size() >= maxDevices) {
            throw new InvalidOperationException(
                "Room '" + roomName + "' has reached maximum capacity of " + maxDevices + " devices");
        }
        
        // Check for duplicate device ID
        for (SmartDevice d : devices) {
            if (d.getDeviceId().equals(device.getDeviceId())) {
                throw new InvalidOperationException(
                    "Device with ID '" + device.getDeviceId() + "' already exists in " + roomName);
            }
        }
        
        devices.add(device);
        device.setLocation(roomName);
        System.out.println("Added " + device.getDeviceName() + " to " + roomName);
    }
    
    // Remove device by ID
    public void removeDevice(String deviceId) throws DeviceNotFoundException {
        SmartDevice toRemove = null;
        
        for (SmartDevice device : devices) {
            if (device.getDeviceId().equals(deviceId)) {
                toRemove = device;
                break;
            }
        }
        
        if (toRemove == null) {
            throw new DeviceNotFoundException(
                "Device with ID '" + deviceId + "' not found in " + roomName);
        }
        
        devices.remove(toRemove);
        System.out.println("Removed " + toRemove.getDeviceName() + " from " + roomName);
    }
    
    // Find device by ID
    public SmartDevice findDeviceById(String deviceId) throws DeviceNotFoundException {
        for (SmartDevice device : devices) {
            if (device.getDeviceId().equals(deviceId)) {
                return device;
            }
        }
        throw new DeviceNotFoundException(
            "Device with ID '" + deviceId + "' not found in " + roomName);
    }
    
    // Find devices by type (e.g., "Light", "Thermostat")
    public List<SmartDevice> findDevicesByType(String type) {
        List<SmartDevice> result = new ArrayList<>();
        for (SmartDevice device : devices) {
            if (device.getClass().getSimpleName().equalsIgnoreCase(type)) {
                result.add(device);
            }
        }
        return result;
    }
    
    // Get all devices
    public List<SmartDevice> getAllDevices() {
        return new ArrayList<>(devices); // return copy to prevent external modification
    }
    
    // Turn off all devices in room
    public void turnOffAllDevices() {
        System.out.println("Turning off all devices in " + roomName);
        for (SmartDevice device : devices) {
            if (device.isOn()) {
                device.turnOff();
            }
        }
    }
    
    // Turn on all devices in room
    public void turnOnAllDevices() {
        System.out.println("Turning on all devices in " + roomName);
        for (SmartDevice device : devices) {
            if (!device.isOn()) {
                device.turnOn();
            }
        }
    }
    
    // Get room status
    public String getRoomStatus() {
        StringBuilder status = new StringBuilder();
        status.append("=== ").append(roomName).append(" ===\n");
        status.append("Total devices: ").append(devices.size()).append("\n");
        
        if (devices.isEmpty()) {
            status.append("No devices in this room.\n");
        } else {
            for (SmartDevice device : devices) {
                status.append("  - ").append(device.getStatus()).append("\n");
            }
        }
        return status.toString();
    }
    
    // Getters
    public String getRoomName() {
        return roomName;
    }
    
    public int getDeviceCount() {
        return devices.size();
    }
    
    @Override
    public String toString() {
        return roomName + " (" + devices.size() + " devices)";
    }
}
