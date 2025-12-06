package smarthome.controller;

import smarthome.home.Home;

public class EnergyThresholdCondition implements Condition {
    private double thresholdWatts;
    private boolean above; // true = above threshold, false = below
    
    public EnergyThresholdCondition(double thresholdWatts, boolean above) {
        this.thresholdWatts = thresholdWatts;
        this.above = above;
    }
    
    @Override
    public boolean evaluate(Home home) {
        double totalEnergy = home.getTotalEnergyConsumption();
        return above ? (totalEnergy > thresholdWatts) : (totalEnergy < thresholdWatts);
    }
    
    @Override
    public String describe() {
        return "Energy consumption is " + (above ? "above" : "below") + " " + thresholdWatts + "W";
    }
}
