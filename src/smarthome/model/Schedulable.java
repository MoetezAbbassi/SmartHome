package smarthome.model;

import java.time.LocalTime;

public interface Schedulable {
    void scheduleAction(LocalTime time, String action);
    String getSchedule();
}
