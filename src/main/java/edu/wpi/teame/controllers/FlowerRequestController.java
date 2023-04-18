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

public class FlowerRequestController implements IRequestController {

  ObservableList<String> flowerChoices =
      FXCollections.observableArrayList("Tiger Lilies", "Roses", "Tulips", "Daisies");

  ObservableList<String> flowerNum =
      FXCollections.observableArrayList(
          "1", "2", "Small Bouquet (6)", "Medium Bouquet (8)", "Large Bouquet (12)");

  ObservableList<String> yesNo = FXCollections.observableArrayList("yes", "no");

  ObservableList<String> deliveryTimes =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");

  @FXML MFXButton submitButton;
  @FXML TextField recipientName;
  @FXML SearchableComboBox roomName;
  @FXML DatePicker deliveryDate;
  @FXML SearchableComboBox<String> deliveryTime;
  @FXML SearchableComboBox<String> flowerChoice;
  @FXML SearchableComboBox<String> numOfFlowers;
  @FXML SearchableComboBox<String> cardQuestion;
  @FXML TextField cardMessage;
  @FXML TextField notes;
  @FXML SearchableComboBox<String> assignedStaff;
  @FXML MFXButton cancelButton;
  @FXML MFXButton resetButton;

  @FXML
  public void initialize() {
    Stream<LocationName> locationStream = LocationName.allLocations.values().stream();
    ObservableList<String> names =
        FXCollections.observableArrayList(
            locationStream
                .filter(
                    (locationName) -> {
                      return locationName.getNodeType() != LocationName.NodeType.HALL
                          && locationName.getNodeType() != LocationName.NodeType.STAI
                          && locationName.getNodeType() != LocationName.NodeType.REST
                          && locationName.getNodeType() != LocationName.NodeType.ELEV;
                    })
                .map(
                    (locationName) -> {
                      return locationName.getLongName();
                    })
                .sorted()
                .toList());
    roomName.setItems(names);
    // Add the items to the combo boxes
    flowerChoice.setItems(flowerChoices);
    numOfFlowers.setItems(flowerNum);
    deliveryTime.setItems(deliveryTimes);
    cardQuestion.setItems(yesNo);
    // Initialize the buttons
    submitButton.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    resetButton.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("flowerChoice", flowerChoice.getValue());
    requestData.put("numOfFlowers", numOfFlowers.getValue());
    requestData.put("deliveryDate", deliveryDate.getValue());
    requestData.put("deliveryTime", deliveryTime.getValue());
    requestData.put("cardQuestion", cardQuestion.getValue());
    requestData.put("cardMessage", cardMessage.getText());
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomName", roomName.getValue());
    requestData.put("notes", notes.getText());

    // Create the service request data
    ServiceRequestData flowerRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.FLOWERDELIVERY,
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
    flowerChoice.setValue(null);
    numOfFlowers.setValue(null);
    deliveryDate.setValue(null);
    deliveryTime.setValue(null);
    roomName.setValue(null);
    cardQuestion.setValue(null);
    cardMessage.clear();
    recipientName.clear();
    notes.clear();
    assignedStaff.setValue(null);
  }
}
