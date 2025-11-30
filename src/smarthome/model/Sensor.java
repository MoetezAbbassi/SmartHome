package smarthome.model;

public class Sensor extends SmartDevice {
    private String sensorType; // MOTION, TEMPERATURE, SMOKE, etc.
    private boolean triggered;
    private String lastReading;
    
    public Sensor(String deviceId, String deviceName, String sensorType) {
        super(deviceId, deviceName);
        this.sensorType = sensorType;
        this.triggered = false;
        this.lastReading = "No data";
    }
    
    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println(deviceName + " (" + sensorType + " sensor) activated");
    }
    
    @Override
    public void turnOff() {
        this.isOn = false;
        this.triggered = false;
        System.out.println(deviceName + " deactivated");
    }
    
    @Override
    public String getStatus() {
        return String.format("%s - Type: %s, Triggered: %s, Reading: %s",
            toString(), sensorType, triggered ? "YES" : "NO", lastReading);
    }
    
    public void simulate(String reading) {
        if (!isOn) {
            System.out.println(deviceName + " is off, cannot detect");
            return;
        }
        this.lastReading = reading;
        this.triggered = true;
        System.out.println(deviceName + " detected: " + reading);
    }
    
    public boolean isTriggered() {
        return triggered;
    }
    
    public String getSensorType() {
        return sensorType;
    }
    
    public void reset() {
        this.triggered = false;
        System.out.println(deviceName + " reset");
    }
}
