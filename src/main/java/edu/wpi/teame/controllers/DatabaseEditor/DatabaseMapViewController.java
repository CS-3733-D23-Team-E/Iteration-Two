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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class DatabaseMapViewController {

  @FXML AnchorPane mapPaneLowerTwo;
  @FXML AnchorPane mapPaneLowerOne;
  @FXML AnchorPane mapPaneOne;
  @FXML AnchorPane mapPaneTwo;
  @FXML AnchorPane mapPaneThree;

  @FXML Tab floorOneTab;
  @FXML Tab floorTwoTab;
  @FXML Tab floorThreeTab;
  @FXML Tab lowerLevelTwoTab;
  @FXML Tab lowerLevelOneTab;

  @FXML TabPane tabPane;

  // Sidebar Elements
  @FXML VBox sidebar;

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
  MapUtilities mapUtilityLowerTwo = new MapUtilities(mapPaneLowerTwo);
  MapUtilities mapUtilityLowerOne = new MapUtilities(mapPaneLowerOne);
  MapUtilities mapUtilityOne = new MapUtilities(mapPaneOne);
  MapUtilities mapUtilityTwo = new MapUtilities(mapPaneTwo);
  MapUtilities mapUtilityThree = new MapUtilities(mapPaneThree);

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

    sidebar.setVisible(false);
    // Sidebar functions
    cancelButton.setOnAction(event -> cancel());
    confirmButton.setOnAction(event -> uploadChangesToDatabase());
    updateCombo(); // TODO: Change
    deleteNodeButton.setOnAction(event -> deleteNode());

    tabPane
        .getSelectionModel()
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
    List<HospitalNode> floorNodes = SQLRepo.INSTANCE.getNodesFromFloor(currentFloor);
    List<HospitalEdge> floorEdges =
        SQLRepo.INSTANCE.getEdgeList().stream()
            .filter(
                edge -> HospitalNode.allNodes.get(edge.getNodeOneID()).getFloor() == currentFloor)
            .toList();

    for (HospitalNode node : floorNodes) {
      setupNode(node);
    }

    for (HospitalEdge edge : floorEdges) {

      System.out.println("ONE: " + HospitalNode.allNodes.get(edge.getNodeOneID()));
      System.out.println("TWO: " + HospitalNode.allNodes.get(edge.getNodeTwoID()));

      whichMapUtility(currentFloor)
          .drawEdge(
              HospitalNode.allNodes.get(edge.getNodeOneID()),
              HospitalNode.allNodes.get(edge.getNodeTwoID()));
    }
  }

  public void initialLoadFloor(Floor floor) {
    currentFloor = floor;
    loadFloorNodes();
  }

  private ArrayList<Line> drawEdges(HospitalNode node) {
    MapUtilities currentMapUtility = whichMapUtility(currentFloor);
    ArrayList<Line> listOfEdges = new ArrayList<>();
    //
    //    HospitalNode realHospitalNode = HospitalNode.allNodes.get(node.getNodeID());

    for (HospitalNode neighbor : node.getNeighbors()) {
      Line line = currentMapUtility.drawEdge(node, neighbor);
      listOfEdges.add(line);
    }

    return listOfEdges;
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
        return mapPaneOne;
      case TWO:
        return mapPaneTwo;
      case THREE:
        return mapPaneThree;
      case LOWER_ONE:
        return mapPaneLowerOne;
      case LOWER_TWO:
        return mapPaneLowerTwo;
    }
    return mapPaneOne;
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
    mapUtilityLowerTwo = new MapUtilities(mapPaneLowerTwo);
    mapUtilityLowerOne = new MapUtilities(mapPaneLowerOne);
    mapUtilityOne = new MapUtilities(mapPaneOne);
    mapUtilityTwo = new MapUtilities(mapPaneTwo);
    mapUtilityThree = new MapUtilities(mapPaneThree);
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
