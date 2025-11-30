package smarthome.model;

public interface Controllable {
    void executeCommand(String command);
    boolean isControllable();
}
