package edu.wpi.teame.controllers;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.map.LocationName;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONObject;

public class RoomRequestController {
  ObservableList<String> times =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");

  ObservableList<String> changes =
      FXCollections.observableArrayList(
          "Add chair",
          "Add 2 chairs",
          "Add 3 chairs",
          "Add a table",
          "Add a fan",
          "Add a whiteboard",
          "Add a projector and screen");

  @FXML TextField recipientName;
  @FXML SearchableComboBox<String> roomName;
  @FXML SearchableComboBox<String> bookingTime;
  @FXML SearchableComboBox<String> roomChanges;
  @FXML TextField numberOfHours;
  @FXML DatePicker bookingDate;
  @FXML TextField notes;
  @FXML MFXButton cancelButton;
  @FXML MFXButton resetButton;
  @FXML SearchableComboBox<String> assignedStaff;
  @FXML MFXButton submitButton;

  @FXML
  public void initialize() {
    // Add the items to the combo boxes
    Stream<LocationName> locationStream = LocationName.allLocations.values().stream();
    ObservableList<String> names =
        FXCollections.observableArrayList(
            locationStream
                .filter(
                    (locationName) -> {
                      return locationName.getNodeType() == LocationName.NodeType.CONF;
                    })
                .map(
                    (locationName) -> {
                      return locationName.getLongName();
                    })
                .sorted()
                .toList());
    roomName.setItems(names);
    bookingTime.setItems(times);
    roomChanges.setItems(changes);
    // Initialize the buttons

    submitButton.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    resetButton.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("deliveryTime", bookingTime.getValue());
    requestData.put("bookingDate", bookingDate.getValue());
    requestData.put("roomChanges", roomChanges.getValue());
    requestData.put("numberOfHours", numberOfHours.getText());
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomNumber", roomName.getValue());
    requestData.put("notes", notes.getText());

    // Create the service request data
    ServiceRequestData flowerRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.CONFERENCEROOM,
            requestData,
            ServiceRequestData.Status.PENDING,
            assignedStaff.getValue());

    // Return to the home screen
    Navigation.navigate(Screen.HOME);

    SQLRepo.INSTANCE.addServiceRequest(flowerRequestData);

    return flowerRequestData;
  }

  // Cancels the current service request
  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }

  // Clears the current service request fields
  public void clearForm() {
    bookingTime.setValue(null);
    recipientName.clear();
    roomName.setValue(null);
    notes.clear();
    assignedStaff.setValue(null);
    numberOfHours.clear();
    roomChanges.setValue(null);
    bookingDate.setValue(null);
  }
}
