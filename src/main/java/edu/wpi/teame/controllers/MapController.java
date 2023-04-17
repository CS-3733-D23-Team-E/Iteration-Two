package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.LocationName;
import edu.wpi.teame.map.pathfinding.AbstractPathfinder;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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

    tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldTab, newTab) -> {
              if (!isPathDisplayed) {
                currentFloor = tabToFloor(newTab);
                resetComboboxes();
              }
            });

    refreshPathButton.setOnMouseClicked(
        event -> {
          currentFloor = tabToFloor(tabPane.getSelectionModel().selectedItemProperty().getValue());
          resetComboboxes();
          refreshPath();
        });

    currentLocationList.setOnAction(
        event -> {
          curLocFromComboBox = currentLocationList.getValue();
          destFromComboBox = destinationList.getValue();
          displayPath(curLocFromComboBox, destFromComboBox);
        });

    destinationList.setOnAction(
        event -> {
          curLocFromComboBox = currentLocationList.getValue();
          destFromComboBox = destinationList.getValue();
          displayPath(curLocFromComboBox, destFromComboBox);
        });

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

    // Make sure location list is initialized so that we can filter out the hallways
    SQLRepo.INSTANCE.getLocationList();

    resetComboboxes();
  }

  private void initializeMapUtilities() {
    mapUtilityLowerTwo = new MapUtilities(mapPaneLowerTwo);
    mapUtilityLowerOne = new MapUtilities(mapPaneLowerOne);
    mapUtilityOne = new MapUtilities(mapPaneOne);
    mapUtilityTwo = new MapUtilities(mapPaneTwo);
    mapUtilityThree = new MapUtilities(mapPaneThree);

    mapUtilityLowerTwo.setCircleStyle("-fx-fill: gold; -fx-stroke: black; -fx-stroke-width: 1");
    mapUtilityLowerOne.setCircleStyle("-fx-fill: cyan; -fx-stroke: black; -fx-stroke-width: 1");
    mapUtilityOne.setCircleStyle("-fx-fill: lime; -fx-stroke: black; -fx-stroke-width: 1");
    mapUtilityTwo.setCircleStyle("-fx-fill: hotpink; -fx-stroke: black; -fx-stroke-width: 1");
    mapUtilityThree.setCircleStyle("-fx-fill: orangered; -fx-stroke: black; -fx-stroke-width: 1");

    mapUtilityLowerTwo.setLineStyle("-fx-stroke: gold; -fx-stroke-width: 4");
    mapUtilityLowerOne.setLineStyle("-fx-stroke: cyan; -fx-stroke-width: 4");
    mapUtilityOne.setLineStyle("-fx-stroke: lime; -fx-stroke-width: 4");
    mapUtilityTwo.setLineStyle("-fx-stroke: hotpink; -fx-stroke-width: 4");
    mapUtilityThree.setLineStyle("-fx-stroke: orangered; -fx-stroke-width: 4");
  }

  public void resetComboboxes() {
    floorLocations =
        FXCollections.observableArrayList(
            SQLRepo.INSTANCE.getMoveList().stream()
                .filter(
                    (move) -> // Filter out hallways and long names with no corresponding
                        // LocationName
                        LocationName.allLocations.get(move.getLongName()) == null
                            ? false
                            : LocationName.allLocations.get(move.getLongName()).getNodeType()
                                    != LocationName.NodeType.HALL
                                && LocationName.allLocations.get(move.getLongName()).getNodeType()
                                    != LocationName.NodeType.STAI
                                && LocationName.allLocations.get(move.getLongName()).getNodeType()
                                    != LocationName.NodeType.ELEV
                                && LocationName.allLocations.get(move.getLongName()).getNodeType()
                                    != LocationName.NodeType.REST)
                .map((move) -> move.getLongName())
                .sorted() // Sort alphabetically
                .toList());
    currentLocationList.setItems(floorLocations);
    destinationList.setItems(floorLocations);
    currentLocationList.setValue("");
    destinationList.setValue("");
  }

  @FXML
  public void displayPath(String from, String to) {
    if (from == null || from.equals("") || to == null || to.equals("")) {
      return;
    }
    refreshPath();
    AbstractPathfinder pf = AbstractPathfinder.getInstance("A*");

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

      Line pathLine = currentMapUtility.drawStyledLine(x1, y1, x2, y2);
      Circle intermediateCircle = currentMapUtility.drawStyledCircle(x2, y2, 3);
      intermediateCircle.setViewOrder(-1);
      x1 = x2;
      y1 = y2;
    }

    // create circle to symbolize end
    Circle endingCircle = currentMapUtility.drawCircle(x1, y1, 8, BLACK);
    endingCircle.toFront();
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
