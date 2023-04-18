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
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONObject;

public class OfficeSuppliesController implements IRequestController {

  @FXML MFXButton returnButtonOfficeSuppliesRequest;
  @FXML MFXButton submitButton;
  @FXML MFXButton cancelButton;
  @FXML MFXButton resetButton;
  @FXML TextField recipientName;
  @FXML SearchableComboBox<String> roomName;
  @FXML TextField notes;
  @FXML SearchableComboBox<String> deliveryTime;
  @FXML SearchableComboBox<String> supplyType;
  @FXML TextField quantityOfSupplies;
  @FXML SearchableComboBox<String> assignedStaff;

  ObservableList<String> deliveryTimes =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");
  ObservableList<String> officeSupplies =
      FXCollections.observableArrayList(
          "pencils", "pens", "white-out", "tape", "ruler", "hole puncher", "sharpener", "charger");

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
    deliveryTime.setItems(deliveryTimes);
    supplyType.setItems(officeSupplies);
    submitButton.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    resetButton.setOnMouseClicked(event -> clearForm());
  }

  private void clearForm() {
    recipientName.clear();
    roomName.setValue(null);
    notes.clear();
    deliveryTime.setValue(null);
    supplyType.setValue(null);
    quantityOfSupplies.clear();
    assignedStaff.setValue(null);
  }

  @Override
  public ServiceRequestData sendRequest() {
    JSONObject requestData = new JSONObject();
    requestData.put("staffName", recipientName.getText());
    requestData.put("officeName", roomName.getValue());
    requestData.put("deliveryTime", deliveryTime.getValue());
    requestData.put("supplyType", supplyType.getValue());
    requestData.put("numOfSupplies", quantityOfSupplies.getText());
    requestData.put("notes", notes.getText());

    ServiceRequestData officeSuppliesRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.OFFICESUPPLIESDELIVERY,
            requestData,
            ServiceRequestData.Status.PENDING,
            assignedStaff.getValue());

    Navigation.navigate(Screen.HOME);

    SQLRepo.INSTANCE.addServiceRequest(officeSuppliesRequestData);
    return officeSuppliesRequestData;
  }

  @Override
  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }
}
