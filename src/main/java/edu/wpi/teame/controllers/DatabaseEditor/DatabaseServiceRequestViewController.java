package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
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
  @FXML TableView<FlowerRequestData> flowerTable;
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

  // table data for Office Supplies
  @FXML TableView<OfficeSuppliesData> officeSuppliesTable;
  @FXML TableColumn<OfficeSuppliesData, String> officeRecipientNameCol;
  @FXML TableColumn<OfficeSuppliesData, String> officeRoomCol;
  @FXML TableColumn<OfficeSuppliesData, String> officeDateCol;
  @FXML TableColumn<OfficeSuppliesData, String> officeTimeCol;
  @FXML TableColumn<OfficeSuppliesData, String> officeStaffCol;
  @FXML TableColumn<OfficeSuppliesData, String> officeSupplyTypeCol;
  @FXML TableColumn<OfficeSuppliesData, String> officeNumberOfSuppliesCol;
  @FXML TableColumn<OfficeSuppliesData, String> officeNotesCol;

  // table data for Conference Rooms
  @FXML TableView<ConferenceRequestData> conferenceRoomTable;
  @FXML TableColumn<ConferenceRequestData, String> conferenceNameCol;
  @FXML TableColumn<ConferenceRequestData, String> conferenceRoomCol;
  @FXML TableColumn<ConferenceRequestData, String> conferenceDateCol;
  @FXML TableColumn<ConferenceRequestData, String> conferenceTimeCol;
  @FXML TableColumn<ConferenceRequestData, String> conferenceStaffCol;
  @FXML TableColumn<ConferenceRequestData, String> conferenceRoomChangesCol;
  @FXML TableColumn<ConferenceRequestData, String> conferenceNumOfHoursCol;
  @FXML TableColumn<ConferenceRequestData, String> conferenceNotesCol;

  // table data for Furniture
  @FXML TableView<FurnitureRequestData> furnitureTable;
  @FXML TableColumn<FurnitureRequestData, String> furnitureNameCol;
  @FXML TableColumn<FurnitureRequestData, String> furnitureRoomCol;
  @FXML TableColumn<FurnitureRequestData, String> furnitureDateCol;
  @FXML TableColumn<FurnitureRequestData, String> furnitureTimeCol;
  @FXML TableColumn<FurnitureRequestData, String> furnitureStaffCol;
  @FXML TableColumn<FurnitureRequestData, String> furnitureTypeCol;
  @FXML TableColumn<FurnitureRequestData, String> furnitureNotesCol;

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

    flowerTable.setItems(FXCollections.observableArrayList(dC.getFlowerRequestsList()));
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

    // fill table for office supplies requests
    officeRecipientNameCol.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesData, String>("recipientName"));
    officeRoomCol.setCellValueFactory(new PropertyValueFactory<OfficeSuppliesData, String>("room"));
    officeDateCol.setCellValueFactory(new PropertyValueFactory<OfficeSuppliesData, String>("date"));
    officeTimeCol.setCellValueFactory(new PropertyValueFactory<OfficeSuppliesData, String>("time"));
    officeStaffCol.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesData, String>("staff"));
    officeSupplyTypeCol.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesData, String>("supplyType"));
    officeNumberOfSuppliesCol.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesData, String>("numberOfSupplies"));
    officeNotesCol.setCellValueFactory(
        new PropertyValueFactory<OfficeSuppliesData, String>("notes"));

    officeSuppliesTable.setItems(FXCollections.observableArrayList(dC.getOfficeSupplyList()));
    officeSuppliesTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                // displayMoveEdit(newSelection);
              }
            });
    officeSuppliesTable.setEditable(true);

    // fill table for conference room requests
    conferenceNameCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("recipientName"));
    conferenceRoomCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("room"));
    conferenceDateCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("date"));
    conferenceTimeCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("time"));
    conferenceStaffCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("staff"));
    conferenceRoomChangesCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("roomChanges"));
    conferenceNumOfHoursCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("numberOfHours"));
    conferenceNotesCol.setCellValueFactory(
        new PropertyValueFactory<ConferenceRequestData, String>("notes"));

    conferenceRoomTable.setItems(FXCollections.observableArrayList(dC.getConfList()));
    conferenceRoomTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                // displayMoveEdit(newSelection);
              }
            });
    conferenceRoomTable.setEditable(true);

    // fill table for furniture requests
    furnitureNameCol.setCellValueFactory(
        new PropertyValueFactory<FurnitureRequestData, String>("recipientName"));
    furnitureRoomCol.setCellValueFactory(
        new PropertyValueFactory<FurnitureRequestData, String>("room"));
    furnitureDateCol.setCellValueFactory(
        new PropertyValueFactory<FurnitureRequestData, String>("date"));
    furnitureTimeCol.setCellValueFactory(
        new PropertyValueFactory<FurnitureRequestData, String>("time"));
    furnitureStaffCol.setCellValueFactory(
        new PropertyValueFactory<FurnitureRequestData, String>("staff"));
    furnitureTypeCol.setCellValueFactory(
        new PropertyValueFactory<FurnitureRequestData, String>("furnitureType"));
    furnitureNotesCol.setCellValueFactory(
        new PropertyValueFactory<FurnitureRequestData, String>("notes"));

    furnitureTable.setItems(FXCollections.observableArrayList(dC.getFurnitureRequestsList()));
    furnitureTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                // displayMoveEdit(newSelection);
              }
            });
    furnitureTable.setEditable(true);
  }
}
