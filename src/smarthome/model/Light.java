package smarthome.model;

public class Light extends SmartDevice implements EnergyConsumer, Controllable {
    private int brightness;
    private String energyMode;
    private static final double BASE_POWER = 10.0;
    
    public Light(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.brightness = 0;
        this.energyMode = "NORMAL";
    }
    
    @Override
    public void turnOn() {
        this.isOn = true;
        this.brightness = 100;
        System.out.println(deviceName + " turned ON with brightness " + brightness);
    }
    
    @Override
    public void turnOff() {
        this.isOn = false;
        this.brightness = 0;
        System.out.println(deviceName + " turned OFF");
    }
    
    @Override
    public String getStatus() {
        return String.format("%s - Brightness: %d%%, Energy Mode: %s, Power: %.2fW",
            toString(), brightness, energyMode, getEnergyConsumption());
    }
    
    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 100) {
            throw new IllegalArgumentException("Brightness must be between 0 and 100");
        }
        this.brightness = brightness;
        if (brightness > 0) {
            this.isOn = true;
        }
        System.out.println(deviceName + " brightness set to " + brightness + "%");
    }
    
    public int getBrightness() {
        return brightness;
    }
    
    @Override
    public void executeCommand(String command) {
        switch (command.toLowerCase()) {
            case "on":
                turnOn();
                break;
            case "off":
                turnOff();
                break;
            case "dim":
                setBrightness(30);
                break;
            case "bright":
                setBrightness(100);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }
    
    @Override
    public boolean isControllable() {
        return true;
    }
    
    @Override
    public double getEnergyConsumption() {
        if (!isOn) return 0.0;
        double consumption = BASE_POWER * (brightness / 100.0);
        if (energyMode.equals("ECO")) {
            consumption *= 0.7;
        }
        return consumption;
    }
    
    @Override
    public void setEnergyMode(String mode) {
        this.energyMode = mode;
        System.out.println(deviceName + " energy mode set to " + mode);
    }
}
