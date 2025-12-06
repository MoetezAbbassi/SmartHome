package smarthome.model;

public class SmartWindowBlinds extends SmartDevice {

    private int openness; // 0 = closed, 100 = fully open

    public SmartWindowBlinds(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.openness = 0; // default: closed
    }

    public void setOpenness(int value) {
        if (value < 0) value = 0;
        if (value > 100) value = 100;
        this.openness = value;
    }

    public int getOpenness() {
        return openness;
    }

    @Override
    public void turnOn() {
        this.openness = 100; // open blinds fully
        this.isOn = true;
    }

    @Override
    public void turnOff() {
        this.openness = 0; // close blinds
        this.isOn = false;
    }

    @Override
    public String getStatus() {
        return String.format("Blinds '%s' are %s (%d%% open)",
            deviceName,
            openness == 0 ? "CLOSED" : (openness == 100 ? "FULLY OPEN" : "PARTIALLY OPEN"),
            openness
        );
    }

    @Override
    public boolean isControllable() {
        return true;
    }
}
