package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.map.Floor;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class DatabaseEditorController {
  @FXML MFXButton backButton;

  @FXML TabPane tabPane;
  @FXML Tab editMapTab;
  @FXML Tab moveTab;
  @FXML Tab editServiceRequestTab;
  @FXML Tab editDatabaseTab;

  @FXML DatabaseMapViewController mapViewController;

  @FXML
  DatabaseTableViewController tableViewController =
      new DatabaseTableViewController(); // I dont know why I have to do this but I do - Mich

  @FXML DatabaseServiceRequestViewController serviceRequestViewController;

  @FXML
  MoveComponentController moveComponentController =
      new MoveComponentController(); // I dont know why I have to do this but I do - Mich

  @FXML
  public void initialize() {

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldTab, newTab) -> {
              if (newTab == editMapTab) {
                // System.out.println(mapViewController);
                mapViewController.initialLoadFloor(Floor.LOWER_TWO);
                // mapViewController.initialize();
              } else if (newTab == editDatabaseTab) {
                // tableViewController.initialize();
                // System.out.println(tableViewController);
                tableViewController.initialize();
              } else if (newTab == editServiceRequestTab) {
                // System.out.println(serviceRequestViewController);
                serviceRequestViewController.initialize();
              } else {
                // move tab
                // System.out.println(moveComponentController);
                moveComponentController.initialize();
              }
            });
  }
}
