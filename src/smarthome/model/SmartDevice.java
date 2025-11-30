package smarthome.model;

public abstract class SmartDevice {
    protected String deviceId;
    protected String deviceName;
    protected boolean isOn;
    protected String location; 
    
    public SmartDevice(String deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.isOn = false;
    }
    
    public abstract void turnOn();
    public abstract void turnOff();
    public abstract String getStatus();
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public String getDeviceName() {
        return deviceName;
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getLocation() {
        return location;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s (ID: %s) - %s", 
            getClass().getSimpleName(), deviceName, deviceId, 
            isOn ? "ON" : "OFF");
    }

	public boolean isControllable() {
		return false;
	}

	public double getEnergyConsumption() {
		return 0;
	}
}
