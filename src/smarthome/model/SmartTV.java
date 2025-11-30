package smarthome.model;

public class SmartTV extends SmartDevice implements Controllable, EnergyConsumer {
    private int volume;
    private int channel;
    private String energyMode;
    
    public SmartTV(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.volume = 50;
        this.channel = 1;
        this.energyMode = "NORMAL";
    }
    
    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println(deviceName + " turned ON - Channel " + channel);
    }
    
    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println(deviceName + " turned OFF");
    }
    
    @Override
    public String getStatus() {
        return String.format("%s - Channel: %d, Volume: %d, Power: %.2fW",
            toString(), channel, volume, getEnergyConsumption());
    }
    
    public void setVolume(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        this.volume = volume;
        System.out.println(deviceName + " volume set to " + volume);
    }
    
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println(deviceName + " switched to channel " + channel);
    }
    
    @Override
    public void executeCommand(String command) {
        String cmd = command.toLowerCase();
        if (cmd.equals("on")) {
            turnOn();
        } else if (cmd.equals("off")) {
            turnOff();
        } else if (cmd.startsWith("volume")) {
            String[] parts = cmd.split(" ");
            if (parts.length == 2) {
                setVolume(Integer.parseInt(parts[1]));
            }
        } else if (cmd.startsWith("channel")) {
            String[] parts = cmd.split(" ");
            if (parts.length == 2) {
                setChannel(Integer.parseInt(parts[1]));
            }
        }
    }
    
    @Override
    public boolean isControllable() {
        return true;
    }
    
    @Override
    public double getEnergyConsumption() {
        if (!isOn) return 0.0;
        double basePower = 120.0;
        if (energyMode.equals("ECO")) {
            basePower *= 0.8;
        }
        return basePower;
    }
    
    @Override
    public void setEnergyMode(String mode) {
        this.energyMode = mode;
        System.out.println(deviceName + " energy mode set to " + mode);
    }
}
