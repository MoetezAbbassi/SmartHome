package smarthome.model;

public class SmartDoorLock extends SmartDevice {

    private boolean locked;

    public SmartDoorLock(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.locked = true; // default: locked
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void turnOn() {
        this.locked = true;   // locking the door
        this.isOn = true;
    }

    @Override
    public void turnOff() {
        this.locked = false; // unlocking the door
        this.isOn = false;
    }

    @Override
    public String getStatus() {
        return String.format("Door '%s' is %s", 
            deviceName,
            locked ? "LOCKED" : "UNLOCKED"
        );
    }

    @Override
    public boolean isControllable() {
        return true; // this device can be controlled
    }
}
