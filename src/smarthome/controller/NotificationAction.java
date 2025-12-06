package smarthome.controller;

import smarthome.home.Home;

public class NotificationAction implements Action {
    private String message;
    
    public NotificationAction(String message) {
        this.message = message;
    }
    
    @Override
    public void execute(Home home) {
        System.out.println("[NOTIFICATION] " + message);
    }
    
    @Override
    public String describe() {
        return "Send notification: " + message;
    }
}
