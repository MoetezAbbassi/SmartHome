package smarthome.model;

public class SmartDoorLock extends SmartDevice {

    private boolean locked;

    public SmartDoorLock(String id, String name) {
        super(id, name);
        this.locked = true; // default: door is locked
    }

    // Lock the door
    public void lock() {
        this.locked = true;
    }

    // Unlock the door
    public void unlock() {
        this.locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void turnOn() {
        // In a smart lock, "turn on" can mean locking the door
        this.locked = true;
        this.on = true;
    }

    @Override
    public void turnOff() {
        // "turn off" can mean unlocking the door
        this.locked = false;
        this.on = false;
    }

    @Override
    public String getStatus() {
        return "SmartDoorLock " + name + " is " +
               (locked ? "LOCKED" : "UNLOCKED");
    }
}

