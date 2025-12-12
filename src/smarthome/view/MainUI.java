package smarthome.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import smarthome.home.*;
import smarthome.model.*;

public class MainUI extends Application {

    private Home home;

    @Override
    public void start(Stage stage) {

        // TEMPORARY — later connect to your backend logic
        home = createDemoHome();

        BorderPane root = new BorderPane();

        // Room list
        ListView<Room> roomList = new ListView<>();
        roomList.getItems().addAll(home.getAllRooms());

        // Device list
        ListView<SmartDevice> deviceList = new ListView<>();

        roomList.getSelectionModel().selectedItemProperty().addListener((obs, oldR, newR) -> {
            if (newR != null) {
                deviceList.getItems().setAll(newR.getDevices());
            }
        });

        // Controls
        VBox controls = new VBox(12);
        controls.setStyle("-fx-padding: 12;");

        Button onBtn = new Button("Turn ON");
        Button offBtn = new Button("Turn OFF");
        Label statusLabel = new Label("Select a device.");

        controls.getChildren().addAll(onBtn, offBtn, statusLabel);

        deviceList.getSelectionModel().selectedItemProperty().addListener((obs, o, d) -> {
            if (d != null) statusLabel.setText(d.getStatus());
        });

        onBtn.setOnAction(e -> {
            SmartDevice d = deviceList.getSelectionModel().getSelectedItem();
            if (d != null) {
                d.turnOn();
                statusLabel.setText(d.getStatus());
                deviceList.refresh();
            }
        });

        offBtn.setOnAction(e -> {
            SmartDevice d = deviceList.getSelectionModel().getSelectedItem();
            if (d != null) {
                d.turnOff();
                statusLabel.setText(d.getStatus());
                deviceList.refresh();
            }
        });

        root.setLeft(roomList);
        root.setCenter(deviceList);
        root.setRight(controls);

        stage.setScene(new Scene(root, 900, 500));
        stage.setTitle("Smart Home Dashboard");
        stage.show();
    }

    private Home createDemoHome() {
        Home h = new Home("My Home");

        Room living = new Room("Living Room");
        living.addDevice(new Light("L1", "Ceiling Light"));
        living.addDevice(new SmartDoorLock("DL1", "Front Door"));
        living.addDevice(new SmartWindowBlinds("WB1", "Main Window"));

        Room bedroom = new Room("Bedroom");
        bedroom.addDevice(new SmartWindowBlinds("WB2", "Bedroom Blinds"));

        h.addRoom(living);
        h.addRoom(bedroom);

        return h;
    }
}
