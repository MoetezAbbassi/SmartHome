package smarthome.model;

public interface EnergyConsumer {
    double getEnergyConsumption(); // in watts
    void setEnergyMode(String mode); // e.g., "ECO", "NORMAL", "HIGH"
	boolean isControllable();
}
