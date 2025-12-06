package smarthome.controller;

import smarthome.home.Home;
import java.time.LocalTime;

public class TimeCondition implements Condition {
    private LocalTime targetTime;
    private int toleranceMinutes; // ±tolerance
    
    public TimeCondition(LocalTime targetTime, int toleranceMinutes) {
        this.targetTime = targetTime;
        this.toleranceMinutes = toleranceMinutes;
    }
    
    public TimeCondition(int hour, int minute) {
        this(LocalTime.of(hour, minute), 1);
    }
    
    @Override
    public boolean evaluate(Home home) {
        LocalTime now = LocalTime.now();
        long minutesDiff = Math.abs(now.toSecondOfDay() - targetTime.toSecondOfDay()) / 60;
        return minutesDiff <= toleranceMinutes;
    }
    
    @Override
    public String describe() {
        return "Time is " + targetTime + " (±" + toleranceMinutes + " min)";
    }
}
