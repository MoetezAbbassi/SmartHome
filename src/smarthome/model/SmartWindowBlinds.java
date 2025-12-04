package smarthome.model;

public class SmartWindowBlinds extends SmartDevice {

    private int openness; // 0 = closed, 100 = fully open

    public SmartWindowBlinds(String id, String name) {
        super(id, name);
        this.openness = 0; // default: blinds closed
    }

    // Set blinds openness level (0–100)
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
        // Turning "on" opens the blinds fully
        this.openness = 100;
        this.on = true;
    }

    @Override
    public void turnOff() {
        // Turning "off" closes the blinds
        this.openness = 0;
        this.on = false;
    }

    @Override
    public String getStatus() {
        return "SmartWindowBlinds " + name + " are " +
               (openness == 0 ? "CLOSED" :
                openness == 100 ? "FULLY OPEN" :
                "OPEN (" + openness + "%)");
    }
}

