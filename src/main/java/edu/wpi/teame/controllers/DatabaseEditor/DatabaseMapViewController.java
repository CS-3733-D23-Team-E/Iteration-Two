package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.*;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class DatabaseMapViewController {

  @FXML AnchorPane lowerOneMapPane;
  @FXML AnchorPane lowerTwoMapPane;
  @FXML AnchorPane floorOneMapPane;
  @FXML AnchorPane floorTwoMapPane;
  @FXML AnchorPane floorThreeMapPane;

  @FXML Tab floorOneTab;
  @FXML Tab floorTwoTab;
  @FXML Tab floorThreeTab;
  @FXML Tab lowerLevelTwoTab;
  @FXML Tab lowerLevelOneTab;

  @FXML TabPane tabs;

  // Sidebar Elements

  @FXML Text editPageText;

  @FXML TextField xField;
  @FXML TextField yField;
  @FXML ComboBox<String> buildingSelector;
  @FXML MFXButton confirmButton;
  @FXML MFXButton deleteNodeButton;
  @FXML MFXButton cancelButton;

  @FXML MFXButton addEdgeButton;
  @FXML MFXButton removeEdgeButton;
  @FXML TableView<HospitalEdge> edgeView;
  @FXML TableColumn<HospitalEdge, String> edgeList;
  @FXML TextField addEdgeField;

  @FXML TextField newLongNameField;
  @FXML TextField newShortNameField;
  @FXML MFXButton addLocationButton;
  @FXML ComboBox<String> longNameSelector;

  @FXML ComboBox<LocationName.NodeType> nodeTypeChoice;

  @FXML ImageView mapImageLowerTwo; // Floor L2
  @FXML ImageView mapImageLowerOne; // Floor L1
  @FXML ImageView mapImageOne; // Floor 1
  @FXML ImageView mapImageTwo; // Floor 2
  @FXML ImageView mapImageThree; // Floor 3

  Floor currentFloor;
  MapUtilities mapUtilityLowerTwo = new MapUtilities(lowerTwoMapPane);
  MapUtilities mapUtilityLowerOne = new MapUtilities(lowerOneMapPane);
  MapUtilities mapUtilityOne = new MapUtilities(floorOneMapPane);
  MapUtilities mapUtilityTwo = new MapUtilities(floorTwoMapPane);
  MapUtilities mapUtilityThree = new MapUtilities(floorThreeMapPane);

  private Circle currentCircle;
  private Label currentLabel;

  ArrayList<HospitalEdge> edges = new ArrayList<>();
  ArrayList<HospitalEdge> addList = new ArrayList<>();
  ArrayList<HospitalEdge> deleteList = new ArrayList<>();

  @FXML
  public void initialize() {
    initializeMapUtilities();
    currentFloor = Floor.LOWER_TWO;
    //    mapUtil = new MapUtilities(lowerTwoMapPane);

    // Sidebar functions
    cancelButton.setOnAction(event -> cancel());
    confirmButton.setOnAction(event -> uploadChangesToDatabase());
    updateCombo(); // TODO: Change
    deleteNodeButton.setOnAction(event -> deleteNode());

    tabs.getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              currentFloor = tabToFloor(newValue);
              refreshMap();
            });

    // POPULATE COMBOBOXES
    buildingSelector.setItems(FXCollections.observableArrayList(HospitalNode.allBuildings()));
    nodeTypeChoice.setItems(
        FXCollections.observableArrayList(LocationName.NodeType.allNodeTypes()));

    edgeList.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeTwoID"));
  }

  private void cancel() {}

  private void deleteNode() {}

  public void loadFloorNodes() {
    List<HospitalNode> nodes = SQLRepo.INSTANCE.getNodesFromFloor(currentFloor);
    for (HospitalNode node : nodes) {
      String nodeTypeString =
          SQLRepo.INSTANCE.getNodeTypeFromNodeID(Integer.parseInt(node.getNodeID()));
      if (!nodeTypeString.equals("")) {
        LocationName.NodeType nodeType = LocationName.NodeType.stringToNodeType(nodeTypeString);
        if (nodeType == LocationName.NodeType.HALL) {
          continue;
        }
      }

      setupNode(node);
    }
  }

  public void initialLoadFloor(Floor floor) {
    List<HospitalNode> nodes = SQLRepo.INSTANCE.getNodesFromFloor(floor);
    for (HospitalNode node : nodes) {
      String nodeTypeString =
          SQLRepo.INSTANCE.getNodeTypeFromNodeID(Integer.parseInt(node.getNodeID()));
      if (!nodeTypeString.equals("")) {
        LocationName.NodeType nodeType = LocationName.NodeType.stringToNodeType(nodeTypeString);
        if (nodeType == LocationName.NodeType.HALL) {
          continue;
        }
      }

      setupNode(node);
    }
  }

  private void setupNode(HospitalNode node) {
    String nodeID = node.getNodeID();
    MapUtilities currentMapUtility = whichMapUtility(currentFloor);
    Circle nodeCircle = currentMapUtility.drawHospitalNode(node);
    Label nodeLabel = currentMapUtility.drawHospitalNodeLabel(node);

    nodeCircle.setOnMouseClicked(
        event -> {
          currentCircle = nodeCircle;
          currentLabel = nodeLabel;
          setEditMenuVisible(true);
        });

    nodeLabel.setOnMouseClicked(
        event -> {
          currentCircle = nodeCircle;
          currentLabel = nodeLabel;
          setEditMenuVisible(true);
        });
  }

  public void refreshMap() {
    MapUtilities currentMapUtility = whichMapUtility(currentFloor);

    currentMapUtility.removeAll();
    currentMapUtility = new MapUtilities(whichPane(currentFloor));
    loadFloorNodes();
  }

  private void setEditMenuVisible(boolean isVisible) {
    editPageText.setText("Edit Node");
  }

  private void updateEditMenu() {
    String nodeID = currentCircle.getId();
    HospitalNode hospitalNode = HospitalNode.allNodes.get(nodeID);
    edges =
        (ArrayList)
            SQLRepo.INSTANCE.getEdgeList().stream()
                .filter((edge) -> (edge.getNodeOneID().equals(nodeID)));
    addList.clear();
    deleteList.clear();

    String x = Integer.toString(hospitalNode.getXCoord());
    String y = Integer.toString(hospitalNode.getYCoord());
    longNameSelector.setValue(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(nodeID)));

    xField.setText(x);
    yField.setText(y);

    confirmButton.setOnAction(
        (event) -> {
          uploadChangesToDatabase();
        });

    edgeView.setItems(FXCollections.observableList(edges));
  }

  private void displayAddMenu() {

    // assign a new node id???? (or have a way to edit it)

    xField.setText("");
    yField.setText("");

    confirmButton.setOnAction(
        (event -> {
          addNodeToDatabase();
        }));
  }

  private void uploadChangesToDatabase() {
    String nodeID = currentCircle.getId();
    HospitalNode hospitalNode = HospitalNode.allNodes.get(nodeID);

    String newX = xField.getText();
    String newY = yField.getText();

    // update the database
    SQLRepo.INSTANCE.updateNode(hospitalNode, "xcoord", newX);
    SQLRepo.INSTANCE.updateNode(hospitalNode, "ycoord", newY);
  }

  private void addNodeToDatabase() {}

  private void updateCombo() {
    List<LocationName> locationNames = SQLRepo.INSTANCE.getLocationList();
    List<String> longNames = SQLRepo.INSTANCE.getLongNamesFromLocationName(locationNames);
    longNameSelector.setItems(FXCollections.observableList(longNames));
  }

  private void editFromNode(HospitalNode node) {
    SQLRepo.INSTANCE.updateNode(node, "xcoord", node.getXCoord() + "");
    System.out.println("theoretical new x coord: " + node.getXCoord());
    SQLRepo.INSTANCE.updateNode(node, "ycoord", node.getYCoord() + "");
    System.out.println("theoretical new y coord: " + node.getYCoord());

    //    // update the move name
    //    SQLRepo.INSTANCE.updateUsingNodeID(
    //        node.getNodeID(),
    //        SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())),
    //        "longName",
    //        longName);
    //    SQLRepo.INSTANCE.updateLocation(locationName, "shortName", locationName.getShortName());
  }

  public AnchorPane whichPane(Floor curFloor) {
    switch (curFloor) {
      case ONE:
        return floorOneMapPane;
      case TWO:
        return floorTwoMapPane;
      case THREE:
        return floorThreeMapPane;
      case LOWER_ONE:
        return lowerOneMapPane;
      case LOWER_TWO:
        return lowerTwoMapPane;
    }
    return floorOneMapPane;
  }

  //  private void updateCurrentNode(HospitalNode node, Circle circle, Label label) {
  //    if (currentNode != null && !currentNode.equals(node)) {
  //      refreshNode(currentNode, currentCircle, currentLabel);
  //      for (Node nodeCircle : mapUtil.filterShapes(Circle.class)) {
  //        Circle aCircle = ((Circle) nodeCircle);
  //        aCircle.setFill(Color.BLACK);
  //      }
  //    }
  //    circle.setFill(Color.RED);
  //    currentNode = node;
  //    currentCircle = circle;
  //    currentLabel = label;
  //  }

  /*
  private void updateNodeDatabase() {
    for (int i = 0; i < modifiedNodes.size(); i++) {
      editFromNode(modifiedNodes.get(i));
    }
    for (HospitalNode node : modifiedNodes) {
      editFromNode(node);
    }
  }

   */

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

  private void initializeMapUtilities() {
    mapUtilityLowerTwo = new MapUtilities(lowerTwoMapPane);
    mapUtilityLowerOne = new MapUtilities(lowerOneMapPane);
    mapUtilityOne = new MapUtilities(floorOneMapPane);
    mapUtilityTwo = new MapUtilities(floorTwoMapPane);
    mapUtilityThree = new MapUtilities(floorThreeMapPane);
  }

  private void initializeButtons() {
    addEdgeButton.setOnAction(
        (event -> {
          // if item is in edge list, remove from delete list
          // if item is not in edge list, add to add list

          // refresh the table
        }));

    removeEdgeButton.setOnAction(
        (event -> {
          // if item is in edge list, add to delete list
          // if item is not in the edge list, remove from add list

          // refresh the table
        }));
  }

  private void edgeUpdateDatabase() {
    // for (HospitalEdge edge : addList) {}
  }
}
