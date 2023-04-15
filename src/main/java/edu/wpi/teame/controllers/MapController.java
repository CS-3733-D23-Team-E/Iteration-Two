package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.pathfinding.AStarPathfinder;
import edu.wpi.teame.utilities.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.SearchableComboBox;

public class MapController {
  @FXML AnchorPane mapPaneOne;
  @FXML AnchorPane mapPaneTwo;
  @FXML AnchorPane mapPaneThree;
  @FXML AnchorPane mapPaneLowerOne;
  @FXML AnchorPane mapPaneLowerTwo;

  @FXML TabPane tabPane;
  @FXML Tab floorOneTab;
  @FXML Tab floorTwoTab;
  @FXML Tab floorThreeTab;
  @FXML Tab lowerLevelTwoTab;
  @FXML Tab lowerLevelOneTab;
  @FXML SearchableComboBox<String> currentLocationList;
  @FXML SearchableComboBox<String> destinationList;
  @FXML private Label pathLabel;
  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarMaps;
  @FXML MFXButton menuBarDatabase;
  @FXML MFXButton menuBarBlank;
  @FXML MFXButton menuBarExit;

  @FXML MFXButton refreshPathButton;
  @FXML VBox menuBar;

  @FXML ImageView mapImageLowerTwo; // Floor L2
  @FXML ImageView mapImageLowerOne; // Floor L1
  @FXML ImageView mapImageOne; // Floor 1
  @FXML ImageView mapImageTwo; // Floor 2
  @FXML ImageView mapImageThree; // Floor 3

  boolean isPathDisplayed = false;

  Floor currentFloor = Floor.LOWER_TWO;
  String curLocFromComboBox;
  String destFromComboBox;
  MapUtilities mapUtilityLowerTwo = new MapUtilities(mapPaneLowerTwo);
  MapUtilities mapUtilityLowerOne = new MapUtilities(mapPaneLowerOne);
  MapUtilities mapUtilityOne = new MapUtilities(mapPaneOne);
  MapUtilities mapUtilityTwo = new MapUtilities(mapPaneTwo);
  MapUtilities mapUtilityThree = new MapUtilities(mapPaneThree);

  ObservableList<String> floorLocations =
      FXCollections.observableArrayList(
          SQLRepo.INSTANCE.getLongNamesFromMove(
              SQLRepo.INSTANCE.getMoveAttributeFromFloor(currentFloor)));

  @FXML
  public void initialize() {
    initializeMapUtilities();
    //    lowerLevelTwoTab.setOnSelectionChanged(
    //        event -> {
    //          currentFloor = Floor.LOWER_TWO;
    //          if (!pathIsBeingDisplayed()) resetComboboxes(currentFloor);
    //        });
    //    lowerLevelOneTab.setOnSelectionChanged(
    //        event -> {
    //          currentFloor = Floor.LOWER_ONE;
    //          if (!pathIsBeingDisplayed()) resetComboboxes(currentFloor);
    //        });
    //    floorOneTab.setOnSelectionChanged(
    //        event -> {
    //          currentFloor = Floor.ONE;
    //          if (!pathIsBeingDisplayed()) resetComboboxes(currentFloor);
    //        });
    //    floorTwoTab.setOnSelectionChanged(
    //        event -> {
    //          currentFloor = Floor.TWO;
    //          if (!pathIsBeingDisplayed()) resetComboboxes(currentFloor);
    //        });
    //    floorThreeTab.setOnSelectionChanged(
    //        event -> {
    //          currentFloor = Floor.THREE;
    //          if (!pathIsBeingDisplayed()) resetComboboxes(currentFloor);
    //        });

    tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldTab, newTab) -> {
              if (!isPathDisplayed) {
                resetComboboxes(tabToFloor(newTab));
              }
              currentFloor = tabToFloor(newTab);
            });

    refreshPathButton.setOnMouseClicked(
        event -> {
          resetComboboxes(currentFloor);
          refreshPath();
        });

    currentLocationList.setOnAction(
        event -> {
          curLocFromComboBox = currentLocationList.getValue();
          destFromComboBox = destinationList.getValue();
          displayPath(curLocFromComboBox, destFromComboBox, currentFloor);
        });

    destinationList.setOnAction(
        event -> {
          curLocFromComboBox = currentLocationList.getValue();
          destFromComboBox = destinationList.getValue();
          displayPath(curLocFromComboBox, destFromComboBox, currentFloor);
        });

    //    mapImageLowerOne.setImage(
    //        new Image(String.valueOf(Main.class.getResource("maps/00_thelowerlevel1.png"))));
    //    mapImageLowerTwo.setImage(
    //        new Image(String.valueOf(Main.class.getResource("maps/00_thelowerlevel2.png"))));
    //    mapImageOne.setImage(
    //        new Image(String.valueOf(Main.class.getResource("maps/01_thefirstfloor.png"))));
    //    mapImageTwo.setImage(
    //        new Image(String.valueOf(Main.class.getResource("maps/02_thesecondfloor.png"))));
    //    mapImageThree.setImage(
    //        new Image(String.valueOf(Main.class.getResource("maps/03_thethirdfloor.png"))));

    // Initially set the menu bar to invisible
    menuBar.setVisible(false);

    // When the menu button is clicked, invert the value of menuVisibility and set the menu bar to
    // that value
    // (so each time the menu button is clicked it changes the visibility of menu bar back and
    // forth)
    menuButton.setOnMouseClicked(
        event -> {
          menuBar.setVisible(!menuBar.isVisible());
        });

    // Navigation controls for the button in the menu bar
    menuBarHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    menuBarServices.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.SERVICE_REQUESTS);
          menuBar.setVisible(!menuBar.isVisible());
        });
    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarMaps.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    menuBarDatabase.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_DATA_EDITOR));
    menuBarExit.setOnMouseClicked((event -> Platform.exit()));

    // makes the buttons get highlighted when the mouse hovers over them
    mouseSetup(menuBarHome);
    mouseSetup(menuBarServices);
    mouseSetup(menuBarSignage);
    mouseSetup(menuBarMaps);
    mouseSetup(menuBarDatabase);
    mouseSetup(menuBarExit);

    resetComboboxes(currentFloor);
  }

  private void initializeMapUtilities() {
    mapUtilityLowerTwo = new MapUtilities(mapPaneLowerTwo);
    mapUtilityLowerOne = new MapUtilities(mapPaneLowerOne);
    mapUtilityOne = new MapUtilities(mapPaneOne);
    mapUtilityTwo = new MapUtilities(mapPaneTwo);
    mapUtilityThree = new MapUtilities(mapPaneThree);
    mapUtilityLowerTwo.setLineStyle("-fx-stroke: gold; -fx-stroke-width: 3");
    mapUtilityLowerOne.setLineStyle("-fx-stroke: cyan; -fx-stroke-width: 3");
    mapUtilityOne.setLineStyle("-fx-stroke: lime; -fx-stroke-width: 3");
    mapUtilityTwo.setLineStyle("-fx-stroke: hotpink; -fx-stroke-width: 3");
    mapUtilityThree.setLineStyle("-fx-stroke: orangered; -fx-stroke-width: 3");
  }

  public void resetComboboxes(Floor floor) {
    floorLocations =
        FXCollections.observableArrayList(
            SQLRepo.INSTANCE.getLongNamesFromMove(
                SQLRepo.INSTANCE.getMoveAttributeFromFloor(floor)));
    currentLocationList.setItems(floorLocations);
    destinationList.setItems(floorLocations);
    currentLocationList.setValue("");
    destinationList.setValue("");
  }

  @FXML
  public void displayPath(String from, String to, Floor whichFloor) {
    if (from == null || from.equals("") || to == null || to.equals("")) {
      return;
    }
    refreshPath();
    AStarPathfinder pf = new AStarPathfinder();

    String toNodeID = SQLRepo.INSTANCE.getNodeIDFromName(to) + "";
    String fromNodeID = SQLRepo.INSTANCE.getNodeIDFromName(from) + "";

    List<HospitalNode> path =
        pf.findPath(HospitalNode.allNodes.get(fromNodeID), HospitalNode.allNodes.get(toNodeID));
    if (path == null) {
      System.out.println("Path does not exist");
      return;
    }
    ArrayList<String> pathNames = new ArrayList<>();
    for (HospitalNode node : path) {
      pathNames.add(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));
    }
    pathLabel.setText(pathNames.toString());
    drawPath(path);
    isPathDisplayed = true;
  }

  /**
   * draws the path with lines connecting each node on the map
   *
   * @param path
   */
  private void drawPath(List<HospitalNode> path) {
    MapUtilities currentMapUtility = whichMapUtility(currentFloor);
    // create circle to symbolize start
    int x1 = path.get(0).getXCoord();
    int y1 = path.get(0).getYCoord();
    currentMapUtility.drawRing(x1, y1, 8, 2, WHITE, BLACK);
    currentMapUtility.createLabel(x1, y1, 5, 5, "Current Location");
    // draw the lines between each node
    int x2, y2;
    for (int i = 1; i < path.size(); i++) {
      HospitalNode node = path.get(i);
      x2 = node.getXCoord();
      y2 = node.getYCoord();

      Floor newFloor = node.getFloor();
      if (newFloor != currentFloor) {
        Floor oldFloor = currentFloor;
        currentMapUtility.createLabel(x2, y2, "Went to Floor: " + newFloor.toString());
        currentFloor = newFloor;
        currentMapUtility = whichMapUtility(currentFloor);
        currentMapUtility.createLabel(x2, y2, "Came from Floor: " + oldFloor.toString());
      }

      currentMapUtility.drawLine(x1, y1, x2, y2);

      x1 = x2;
      y1 = y2;
    }

    // create circle to symbolize end
    currentMapUtility.drawCircle(x1, y1, 8, BLACK);
    currentMapUtility.createLabel(x1, y1, 5, 5, "Destination");
  }

  /** removes all the lines in the currentLines list */
  public void refreshPath() {
    pathLabel.setText("");

    mapUtilityLowerTwo.removeAll();
    mapUtilityLowerOne.removeAll();
    mapUtilityOne.removeAll();
    mapUtilityTwo.removeAll();
    mapUtilityThree.removeAll();

    isPathDisplayed = false;
  }

  public MapUtilities whichMapUtility(Floor currFloor) {
    switch (currFloor) {
      case LOWER_TWO:
        return mapUtilityLowerTwo;
      case LOWER_ONE:
        return mapUtilityLowerOne;
      case ONE:
        return mapUtilityOne;
      case TWO:
        return mapUtilityTwo;
      case THREE:
        return mapUtilityThree;
    }
    return mapUtilityLowerOne;
  }

  public Floor tabToFloor(Tab tab) {
    if (tab == lowerLevelTwoTab) {
      return Floor.LOWER_TWO;
    }
    if (tab == lowerLevelOneTab) {
      return Floor.LOWER_ONE;
    }
    if (tab == floorOneTab) {
      return Floor.ONE;
    }
    if (tab == floorTwoTab) {
      return Floor.TWO;
    }
    if (tab == floorThreeTab) {
      return Floor.THREE;
    }
    return Floor.ONE;
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #192d5a; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }
}
