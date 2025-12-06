package smarthome.controller;

import smarthome.home.Home;

public interface Condition {
    boolean evaluate(Home home);
    String describe();
}
