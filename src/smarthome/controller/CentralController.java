package smarthome.controller;

import smarthome.home.Home;
import smarthome.home.Room;
import smarthome.model.SmartDevice;
import smarthome.model.Sensor;
import smarthome.exceptions.DeviceNotFoundException;
import smarthome.exceptions.InvalidOperationException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class CentralController {

    private final Home home;

    private final List<ScheduleEntry> schedules = new ArrayList<>();

    private final List<AutomationEventListener> automationListeners = new ArrayList<>();

    public CentralController(Home home) {
        this.home = Objects.requireNonNull(home, "home must not be null");
    }


    public void turnDeviceOn(String deviceId) {
        try {
            SmartDevice d = home.findDeviceById(deviceId);
            d.turnOn();
        } catch (DeviceNotFoundException e) {
            System.out.println("[Controller] turnDeviceOn: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Controller] turnDeviceOn: Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void turnDeviceOff(String deviceId) {
        try {
            SmartDevice d = home.findDeviceById(deviceId);
            d.turnOff();
        } catch (DeviceNotFoundException e) {
            System.out.println("[Controller] turnDeviceOff: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Controller] turnDeviceOff: Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SmartDevice findDevice(String deviceId) {
        try {
            return home.findDeviceById(deviceId);
        } catch (DeviceNotFoundException e) {
            return null;
        }
    }

    public void listAllDevices() {
        home.listAllDevices();
    }

    public void turnOffAllDevices() {
        System.out.println("[Controller] Turning off all devices...");
        home.turnOffAllDevices();
    }

    public void turnOnAllDevices() {
        System.out.println("[Controller] Turning on all devices...");
        home.turnOnAllDevices();
    }

    public void turnOffAllInRoom(String roomName) {
        try {
            Room r = home.getRoom(roomName);
            r.turnOffAllDevices();
        } catch (DeviceNotFoundException e) {
            System.out.println("[Controller] turnOffAllInRoom: " + e.getMessage());
        }
    }

    public void turnOnAllInRoom(String roomName) {
        try {
            Room r = home.getRoom(roomName);
            r.turnOnAllDevices();
        } catch (DeviceNotFoundException e) {
            System.out.println("[Controller] turnOnAllInRoom: " + e.getMessage());
        }
    }

    public double getTotalEnergyConsumption() {
        return home.getTotalEnergyConsumption();
    }

    public void addSchedule(LocalTime time, String deviceId, String action) {
        schedules.add(new ScheduleEntry(time, deviceId, action));
        System.out.println("[Controller] Schedule added: " + time + " -> " + action + " (" + deviceId + ")");
    }

    public void executeDueSchedules(LocalTime now) {
        if (now == null) now = LocalTime.now();
        Iterator<ScheduleEntry> it = schedules.iterator();
        while (it.hasNext()) {
            ScheduleEntry s = it.next();
            if (!s.executed && !s.time.isAfter(now)) {
                System.out.println("[Controller] Executing scheduled action: " + s);
                performScheduledAction(s);
                s.executed = true;
                it.remove();
            }
        }
    }

    private void performScheduledAction(ScheduleEntry s) {
        switch (s.action.toLowerCase()) {
            case "turn_on":
            case "on":
                turnDeviceOn(s.deviceId);
                break;
            case "turn_off":
            case "off":
                turnDeviceOff(s.deviceId);
                break;
            default:
                // try to send raw command to device if controllable
                SmartDevice d = findDevice(s.deviceId);
                if (d instanceof smarthome.model.Controllable) {
                    try {
                        ((smarthome.model.Controllable) d).executeCommand(s.action);
                    } catch (Exception ex) {
                        System.out.println("[Controller] performScheduledAction: failed to execute command: " + ex.getMessage());
                    }
                } else {
                    System.out.println("[Controller] performScheduledAction: unknown action and device not controllable.");
                }
        }
    }
    
    public void handleSensorEvent(Sensor sensor, String reading) {
        System.out.println("[Controller] Sensor event from " + sensor.getDeviceName() + " : " + reading);

        for (AutomationEventListener listener : automationListeners) {
            try {
                listener.onSensorEvent(sensor, reading, this);
            } catch (Exception ex) {
                System.out.println("[Controller] automation listener error: " + ex.getMessage());
            }
        }
    }

    public void registerAutomationListener(AutomationEventListener listener) {
        if (listener != null && !automationListeners.contains(listener)) {
            automationListeners.add(listener);
        }
    }

    public void unregisterAutomationListener(AutomationEventListener listener) {
        automationListeners.remove(listener);
    }

    public void printHomeSummary() {
        System.out.println(home.getHomeSummary());
    }

    private static class ScheduleEntry {
        final LocalTime time;
        final String deviceId;
        final String action;
        boolean executed = false;

        ScheduleEntry(LocalTime time, String deviceId, String action) {
            this.time = time;
            this.deviceId = deviceId;
            this.action = action;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s -> %s", time, deviceId, action);
        }
    }


    public interface AutomationEventListener {

        void onSensorEvent(Sensor sensor, String reading, CentralController ctrl);
    }
}
