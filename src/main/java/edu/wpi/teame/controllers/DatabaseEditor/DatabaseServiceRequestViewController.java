package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.MealRequestData;
import edu.wpi.teame.map.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DatabaseServiceRequestViewController {

  @FXML MFXButton addMealButton;

  // Tabs
  @FXML TabPane serviceTableTabs;
  @FXML Tab mealTab;
  @FXML Tab flowerTab;
  @FXML Tab officeSuppliesTab;
  @FXML Tab conferenceRoomTab;
  @FXML Tab furnitureTab;

  // fields for Meals
  @FXML HBox mealAddZone;
  @FXML MFXTextField mealRecipientNameField;
  @FXML MFXTextField mealRoomField;
  @FXML MFXTextField mealDateField;
  @FXML MFXTextField mealTimeField;
  @FXML MFXTextField mealStaffField;
  @FXML MFXTextField mealMainCourseField;
  @FXML MFXTextField mealSideCourseField;
  @FXML MFXTextField mealDrinkField;
  @FXML MFXTextField mealAllergiesField;
  @FXML MFXTextField mealNotesField;

  // table data for Meals
  @FXML TableView<MealRequestData> mealTable;
  @FXML TableColumn<MealRequestData, String> mealRecipientNameCol;
  @FXML TableColumn<MealRequestData, String> mealRoomCol;
  @FXML TableColumn<MealRequestData, String> mealDateCol;
  @FXML TableColumn<MealRequestData, String> mealTimeCol;
  @FXML TableColumn<MealRequestData, String> mealStaffCol;
  @FXML TableColumn<MealRequestData, String> mealMainCourseCol;
  @FXML TableColumn<MealRequestData, String> mealSideCourseCol;
  @FXML TableColumn<MealRequestData, String> mealDrinkCol;
  @FXML TableColumn<MealRequestData, String> mealAllergiesCol;
  @FXML TableColumn<MealRequestData, String> mealNotesCol;

  //////////////////////////////////////////
  @FXML VBox editMealZone;
  @FXML MFXTextField editMealRecipientNameField;
  @FXML ComboBox<String> editRoomField;
  @FXML MFXTextField editNodeYField;
  @FXML ComboBox<Floor> editNodeFloorChoice;
  @FXML ComboBox<String> editNodeBuildingChoice;

  @FXML
  public void initialize() {

    SQLRepo dC = SQLRepo.INSTANCE;

    mealRecipientNameCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("recipientName"));
    mealRoomCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("room"));
    mealDateCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("date"));
    mealTimeCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("time"));
    mealStaffCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("staff"));
    mealMainCourseCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("mainCourse"));
    mealSideCourseCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("sideCourse"));
    mealDrinkCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("drink"));
    mealAllergiesCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("allergies"));
    mealNotesCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("notes"));

    mealTable.setItems(FXCollections.observableArrayList(dC.getMeal()));
      mealTable
              .getSelectionModel()
              .selectedItemProperty()
              .addListener(
                      (obs, oldSelection, newSelection) -> {
                          if (newSelection != null) {
                              displayMoveEdit(newSelection);
                          }
                      });
      mealTable.setEditable(true);

    /*    Stream<LocationName> locationStream = LocationName.allLocations.values().stream();
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
            editRoomField.setItems(names);
    */
  }
}
