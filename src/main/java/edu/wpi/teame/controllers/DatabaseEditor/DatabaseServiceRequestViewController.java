package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.controllers.FlowerRequestController;
import edu.wpi.teame.entities.FlowerRequestData;
import edu.wpi.teame.entities.MealRequestData;
import edu.wpi.teame.map.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

  // table data for Flowers
  @FXML TableView<FlowerRequestController> flowerTable;
  @FXML TableColumn<FlowerRequestData, String> flowerRecipientNameCol;
  @FXML TableColumn<FlowerRequestData, String> flowerRoomCol;
  @FXML TableColumn<FlowerRequestData, String> flowerDateCol;
  @FXML TableColumn<FlowerRequestData, String> flowerTimeCol;
  @FXML TableColumn<FlowerRequestData, String> flowerStaffCol;
  @FXML TableColumn<FlowerRequestData, String> flowerFlowerChoiceCol;
  @FXML TableColumn<FlowerRequestData, String> flowerNumberOfFlowersCol;
  @FXML TableColumn<FlowerRequestData, String> flowerIncludeACardCol;
  @FXML TableColumn<FlowerRequestData, String> flowerCardMessageCol;
  @FXML TableColumn<FlowerRequestData, String> flowerNotesCol;

  //////////////////////////////////////////
  @FXML VBox editMealZone;

  @FXML
  public void initialize() {

    SQLRepo dC = SQLRepo.INSTANCE;

    // fill table for meal requests
    mealRecipientNameCol.setCellValueFactory(
        new PropertyValueFactory<MealRequestData, String>("recipientName"));
    mealRoomCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("room"));
    mealDateCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("date"));
    mealTimeCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("time"));
    mealStaffCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("staff"));
    mealMainCourseCol.setCellValueFactory(
        new PropertyValueFactory<MealRequestData, String>("mainCourse"));
    mealSideCourseCol.setCellValueFactory(
        new PropertyValueFactory<MealRequestData, String>("sideCourse"));
    mealDrinkCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("drink"));
    mealAllergiesCol.setCellValueFactory(
        new PropertyValueFactory<MealRequestData, String>("allergies"));
    mealNotesCol.setCellValueFactory(new PropertyValueFactory<MealRequestData, String>("notes"));

    mealTable.setItems(FXCollections.observableArrayList(dC.getMealRequestsList()));
    mealTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                // displayMoveEdit(newSelection);
              }
            });
    mealTable.setEditable(true);

    // fill table for flower requests
    flowerRecipientNameCol.setCellValueFactory(
        new PropertyValueFactory<FlowerRequestData, String>("recipientName"));
    flowerRoomCol.setCellValueFactory(new PropertyValueFactory<FlowerRequestData, String>("room"));
    flowerDateCol.setCellValueFactory(new PropertyValueFactory<FlowerRequestData, String>("date"));
    flowerTimeCol.setCellValueFactory(new PropertyValueFactory<FlowerRequestData, String>("time"));
    flowerStaffCol.setCellValueFactory(
        new PropertyValueFactory<FlowerRequestData, String>("staff"));
    flowerFlowerChoiceCol.setCellValueFactory(
        new PropertyValueFactory<FlowerRequestData, String>("flowerChoice"));
    flowerNumberOfFlowersCol.setCellValueFactory(
        new PropertyValueFactory<FlowerRequestData, String>("numberOfFlowers"));
    flowerIncludeACardCol.setCellValueFactory(
        new PropertyValueFactory<FlowerRequestData, String>("includeACard"));
    flowerCardMessageCol.setCellValueFactory(
        new PropertyValueFactory<FlowerRequestData, String>("cardMessage"));
    flowerNotesCol.setCellValueFactory(
        new PropertyValueFactory<FlowerRequestData, String>("notes"));

    flowerTable.setItems(FXCollections.observableArrayList());
    flowerTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                // displayMoveEdit(newSelection);
              }
            });
    flowerTable.setEditable(true);
  }
}
