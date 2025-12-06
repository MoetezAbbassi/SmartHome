package smarthome.controller;

import smarthome.home.Home;

public interface Action {
    void execute(Home home);
    String describe();
}
