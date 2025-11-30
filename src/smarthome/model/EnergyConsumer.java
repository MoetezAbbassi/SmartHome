package smarthome.model;

public interface EnergyConsumer {
    double getEnergyConsumption(); 
    void setEnergyMode(String mode);
	boolean isControllable();
}
