package smarthome.controller;

import smarthome.home.Home;
import smarthome.model.SmartDevice;  // ADD THIS IMPORT
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CentralController {
    private Home home;
    private Map<String, AutomationRule> automationRules;
    private boolean autoExecutionEnabled;
    
    public CentralController(Home home) {
        this.home = home;
        this.automationRules = new HashMap<>();
        this.autoExecutionEnabled = true;
    }
    
    // ========== RULE MANAGEMENT ==========
    
    public void addRule(AutomationRule rule) {
        automationRules.put(rule.getRuleId(), rule);
        System.out.println("Added automation rule: " + rule.getRuleName());
    }
    
    public void removeRule(String ruleId) {
        AutomationRule removed = automationRules.remove(ruleId);
        if (removed != null) {
            System.out.println("Removed automation rule: " + removed.getRuleName());
        } else {
            System.out.println("Rule not found: " + ruleId);
        }
    }
    
    public AutomationRule getRule(String ruleId) {
        return automationRules.get(ruleId);
    }
    
    public List<AutomationRule> getAllRules() {
        return new ArrayList<>(automationRules.values());
    }
    
    public void enableRule(String ruleId) {
        AutomationRule rule = automationRules.get(ruleId);
        if (rule != null) {
            rule.setEnabled(true);
            System.out.println("Enabled rule: " + rule.getRuleName());
        }
    }
    
    public void disableRule(String ruleId) {
        AutomationRule rule = automationRules.get(ruleId);
        if (rule != null) {
            rule.setEnabled(false);
            System.out.println("Disabled rule: " + rule.getRuleName());
        }
    }
    
    // ========== RULE EXECUTION ==========
    
    public void executeAllRules() {
        if (!autoExecutionEnabled) {
            return;
        }
        
        for (AutomationRule rule : automationRules.values()) {
            rule.execute(home);
        }
    }
    
    public void executeRule(String ruleId) {
        AutomationRule rule = automationRules.get(ruleId);
        if (rule != null) {
            rule.execute(home);
        } else {
            System.out.println("Rule not found: " + ruleId);
        }
    }
    
    public void setAutoExecutionEnabled(boolean enabled) {
        this.autoExecutionEnabled = enabled;
        System.out.println("Auto-execution " + (enabled ? "enabled" : "disabled"));
    }
    
    // ========== QUICK ACTIONS (FIXED) ==========
    
    public void emergencyShutdown() {
        System.out.println("[EMERGENCY] Shutting down all devices...");
        home.turnOffAllDevices();
        System.out.println("[EMERGENCY] All devices shut down");
    }
    
    public void morningRoutine() {
        System.out.println("[ROUTINE] Starting morning routine...");
        List<SmartDevice> lights = home.findDevicesByType("Light");
        for (SmartDevice device : lights) {
            if (!device.isOn()) {
                device.turnOn();
            }
        }
    }
    
    public void nightRoutine() {
        System.out.println("[ROUTINE] Starting night routine...");
        home.turnOffAllDevices();
    }
    
    // ========== MONITORING ==========
    
    public void generateReport() {
        System.out.println("\n========== SMART HOME REPORT ==========");
        System.out.println(home.getHomeSummary());
        System.out.println("\nAutomation Rules: " + automationRules.size());
        for (AutomationRule rule : automationRules.values()) {
            System.out.println("  • " + rule);
        }
        System.out.println("========================================\n");
    }
    
    public void listAllRules() {
        System.out.println("\n========== AUTOMATION RULES ==========");
        if (automationRules.isEmpty()) {
            System.out.println("No automation rules configured.");
        } else {
            for (AutomationRule rule : automationRules.values()) {
                System.out.println("\n" + rule.describe());
            }
        }
        System.out.println("======================================\n");
    }
    
    public Home getHome() {
        return home;
    }
}
