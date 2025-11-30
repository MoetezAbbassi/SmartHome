package smarthome.model;

public class Thermostat extends SmartDevice implements Controllable, EnergyConsumer, Schedulable {
    private double currentTemp;
    private double targetTemp;
    private String mode; // HEAT, COOL, AUTO, OFF
    private String energyMode;
    private String schedule;
    
    public Thermostat(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.currentTemp = 20.0;
        this.targetTemp = 22.0;
        this.mode = "OFF";
        this.energyMode = "NORMAL";
        this.schedule = "No schedule set";
    }
    
    @Override
    public void turnOn() {
        this.isOn = true;
        this.mode = "AUTO";
        System.out.println(deviceName + " turned ON in AUTO mode");
    }
    
    @Override
    public void turnOff() {
        this.isOn = false;
        this.mode = "OFF";
        System.out.println(deviceName + " turned OFF");
    }
    
    @Override
    public String getStatus() {
        return String.format("%s - Current: %.1f°C, Target: %.1f°C, Mode: %s, Power: %.2fW",
            toString(), currentTemp, targetTemp, mode, getEnergyConsumption());
    }
    
    public void setTargetTemp(double temp) {
        if (temp < 10 || temp > 35) {
            throw new IllegalArgumentException("Temperature must be between 10 and 35°C");
        }
        this.targetTemp = temp;
        System.out.println(deviceName + " target temperature set to " + temp + "°C");
    }
    
    public void setMode(String mode) {
        this.mode = mode.toUpperCase();
        this.isOn = !mode.equals("OFF");
        System.out.println(deviceName + " mode set to " + this.mode);
    }
    
    @Override
    public void executeCommand(String command) {
        if (command.toLowerCase().startsWith("set")) {
            String[] parts = command.split(" ");
            if (parts.length == 2) {
                try {
                    setTargetTemp(Double.parseDouble(parts[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid temperature value");
                }
            }
        } else {
            setMode(command);
        }
    }
    
    @Override
    public boolean isControllable() {
        return true;
    }
    
    @Override
    public double getEnergyConsumption() {
        if (!isOn) return 0.0;
        double basePower = 1500.0; // watts
        if (energyMode.equals("ECO")) {
            basePower *= 0.6;
        }
        return basePower;
    }
    
    @Override
    public void setEnergyMode(String mode) {
        this.energyMode = mode;
        System.out.println(deviceName + " energy mode set to " + mode);
    }
    
    @Override
    public void scheduleAction(java.time.LocalTime time, String action) {
        this.schedule = String.format("At %s: %s", time, action);
        System.out.println(deviceName + " scheduled: " + schedule);
    }
    
    @Override
    public String getSchedule() {
        return schedule;
    }
}
