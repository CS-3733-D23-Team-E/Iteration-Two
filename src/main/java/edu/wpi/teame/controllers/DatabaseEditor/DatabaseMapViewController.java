package edu.wpi.teame.controllers.DatabaseEditor;

import static edu.wpi.teame.map.HospitalNode.allNodes;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.*;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
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
  @FXML TableColumn<HospitalEdge, String> edgeColumn;
  @FXML ComboBox<String> addEdgeField;

  @FXML TextField newLongNameField;
  @FXML TextField newShortNameField;
  @FXML MFXButton addLocationButton;
  @FXML MFXButton removeLocationButton;

  @FXML ComboBox<LocationName.NodeType> nodeTypeChoice;

  @FXML ComboBox<String> longNameSelector;

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

  List<HospitalEdge> edges = new LinkedList<>();
  List<HospitalEdge> addList = new LinkedList<>();
  List<HospitalEdge> deleteList = new LinkedList<>();

  List<HospitalEdge> workingList = new LinkedList<>();

  HospitalNode curNode;

  @FXML
  public void initialize() {
    //    System.out.println("Initializing databasemapviewcontroller!");
    initializeMapUtilities();
    currentFloor = Floor.LOWER_TWO;
    //    mapUtil = new MapUtilities(lowerTwoMapPane);

    sidebar.setVisible(true);
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

    edgeColumn.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeTwoID"));

    displayAddMenu();
    initializeButtons();
  }

  private void cancel() {
    //    System.out.println(currentCircle);
    //    System.out.println(currentLabel);
    if (currentCircle != null) {
      currentCircle.setRadius(4);
      currentLabel.setVisible(false);
    }
    currentCircle = null;
    currentLabel = null;
    displayAddMenu();
    // workingList.clear();
    // turn circle back to normal

    //    System.out.println(currentCircle);

  }

  private void deleteNode() {
    SQLRepo.INSTANCE.deletenode(curNode);
    displayAddMenu();
    refreshMap();
  }

  public void loadFloorNodes() {
    List<HospitalNode> nodes = SQLRepo.INSTANCE.getNodesFromFloor(currentFloor);
    for (HospitalNode node : nodes) {
      setupNode(node);
    }
  }

  public void initialLoadFloor(Floor floor) {
    List<HospitalNode> nodes = SQLRepo.INSTANCE.getNodesFromFloor(floor);
    for (HospitalNode node : nodes) {
      setupNode(node);
    }
  }

  private void setupNode(HospitalNode node) {
    String nodeID = node.getNodeID();
    MapUtilities currentMapUtility = whichMapUtility(currentFloor);
    Circle nodeCircle = currentMapUtility.drawHospitalNode(node);
    Label nodeLabel = currentMapUtility.drawHospitalNodeLabel(node);
    nodeLabel.setVisible(false);

    nodeCircle.setOnMouseClicked(
        event -> {
          if (currentCircle != null && currentLabel != null) {
            currentCircle.setRadius(4);
            currentLabel.setVisible(false);
          }
          currentCircle = nodeCircle;
          currentCircle.setRadius(7);
          currentLabel = nodeLabel;
          currentLabel.setVisible(true);
          setEditMenuVisible(true);
          updateEditMenu();
        });

    //    nodeLabel.setOnMouseClicked(
    //        event -> {
    //          if (currentCircle != null) {
    //            currentCircle.setFill(BLACK);
    //          }
    //          currentCircle = nodeCircle;
    //          currentCircle.setFill(RED);
    //          currentLabel = nodeLabel;
    //          setEditMenuVisible(true);
    //          updateEditMenu();
    //        });
  }

  public void refreshMap() {
    MapUtilities currentMapUtility = whichMapUtility(currentFloor);
    currentMapUtility.removeAll();
    loadFloorNodes();
  }

  private void setEditMenuVisible(boolean isVisible) {
    if (isVisible) {
      editPageText.setText("Edit Node");
    } else {
      editPageText.setText("Add Node");
    }
  }

  // APPEARS WHEN YOU CLICK ON A NODE
  private void updateEditMenu() {
    String nodeID = currentCircle.getId();
    editPageText.setText("Edit Node: ID = " + nodeID);

    curNode = allNodes.get(nodeID);
    edges =
        SQLRepo.INSTANCE.getEdgeList().stream()
            .filter((edge) -> (edge.getNodeOneID().equals(nodeID)))
            .toList();

    workingList = new LinkedList<>();

    for (HospitalEdge edge : edges) {
      workingList.add(edge);
      // System.out.println("item added to working list!");
    }
    //    workingList = FXCollections.observableList(edges);

    addList = new LinkedList<>();
    deleteList = new LinkedList<>();

    String x = Integer.toString(curNode.getXCoord());
    String y = Integer.toString(curNode.getYCoord());
    longNameSelector.setValue(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(nodeID)));

    buildingSelector.setValue(curNode.getBuilding());

    xField.setText(x);
    yField.setText(y);

    confirmButton.setOnAction(
        (event) -> {
          uploadChangesToDatabase();
        });

    edgeView.setItems(FXCollections.observableList(workingList));

    deleteNodeButton.setVisible(true);
  }

  // APPEARS WHEN YOU CLICK OFF A NODE/CANCEL (DEFAULT)
  private void displayAddMenu() {
    setEditMenuVisible(false);

    String nodeID = makeNewNodeID();
    currentCircle = new Circle();
    currentCircle.setId(nodeID);
    // System.out.println(nodeID);
    editPageText.setText("Add Node: ID = " + nodeID);

    // System.out.println("making sure we are here");

    // clear all items
    xField.setText("");
    yField.setText("");
    edges = new LinkedList<>();
    addList = new LinkedList<>();
    workingList = new LinkedList<>();
    deleteList = new LinkedList<>();
    longNameSelector.setValue(null);
    buildingSelector.setValue(null);
    // edgeView.getItems().clear();
    deleteNodeButton.setVisible(false);

    edgeView.setItems(FXCollections.observableList(workingList));

    confirmButton.setOnAction(
        (event -> {
          addNodeToDatabase();
        }));
  }

  private void addLocationName() {
    if (newLongNameField.getText() != "" && newShortNameField.getText() != "") {
      LocationName addedLN =
          new LocationName(
              newLongNameField.getText(), newShortNameField.getText(), nodeTypeChoice.getValue());
      longNameSelector.getItems().add(addedLN.getLongName());
      SQLRepo.INSTANCE.addLocation(addedLN);
      newShortNameField.setText("");
      newLongNameField.setText("");
      nodeTypeChoice.setValue(null);
    } else {
      // nothing
    }
  }

  private void removeLocation() {
    LocationName toBeDeleted =
        new LocationName(longNameSelector.getValue(), "", LocationName.NodeType.INFO);
    SQLRepo.INSTANCE.deleteLocation(toBeDeleted);
    longNameSelector.getItems().remove(longNameSelector.getValue());
  }

  private void uploadChangesToDatabase() {
    String nodeID = currentCircle.getId();
    HospitalNode hospitalNode = allNodes.get(nodeID);

    String newX = xField.getText();
    String newY = yField.getText();

    // update the database
    SQLRepo.INSTANCE.updateNode(hospitalNode, "xcoord", newX);
    SQLRepo.INSTANCE.updateNode(hospitalNode, "ycoord", newY);
    edgeUpdateDatabase();

    // EDIT THE MOVE
    SQLRepo.INSTANCE.updateUsingNodeID(
        nodeID,
        SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(nodeID)),
        "longName",
        longNameSelector.getValue());

    // RELOAD THE DATABASE
    refreshMap();

    // CLOSE THE MENU
    displayAddMenu();
  }

  private void addNodeToDatabase() {
    String id = makeNewNodeID();
    // add respective node
    HospitalNode node =
        new HospitalNode(
            id,
            Integer.parseInt(xField.getText()),
            Integer.parseInt(yField.getText()),
            currentFloor,
            buildingSelector.getValue());
    SQLRepo.INSTANCE.addNode(node);
    // add respective move
    MoveAttribute move =
        new MoveAttribute(id, longNameSelector.getValue(), LocalDate.now().toString());
    SQLRepo.INSTANCE.addMove(move);
    // add respective edges
    edgeUpdateDatabase();
    refreshMap();
  }

  private void updateCombo() {
    // POPULATE COMBOBOXES

    List<LocationName> locationNames = SQLRepo.INSTANCE.getLocationList();
    List<String> longNames = SQLRepo.INSTANCE.getLongNamesFromLocationName(locationNames);
    longNameSelector.setItems(FXCollections.observableList(longNames));

    buildingSelector.setItems(FXCollections.observableArrayList(HospitalNode.allBuildings()));

    nodeTypeChoice.setItems(
        FXCollections.observableArrayList(LocationName.NodeType.allNodeTypes()));

    addEdgeField.setItems(FXCollections.observableList(allNodes.keySet().stream().toList()));
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
          if (edges.contains(addEdgeField.getValue())) {
            deleteList.remove(addEdgeField.getValue());
          } else { // if item is not in edge list, add to add list
            addList.add(new HospitalEdge(currentCircle.getId(), addEdgeField.getValue()));
          }
          workingList.add(new HospitalEdge(currentCircle.getId(), addEdgeField.getValue()));
          // System.out.println("item added to working list!");
          // refresh the table
          refreshEdgeTable();
        }));

    removeEdgeButton.setOnAction(
        (event -> {
          // if item is in edge list, add to delete list
          if (edges.contains(edgeView.getSelectionModel().getSelectedItem())) {
            deleteList.add(edgeView.getSelectionModel().getSelectedItem());
            // System.out.println("added to delete list!");
          } else { // if item is not in the edge list, remove from add list
            addList.remove(edgeView.getSelectionModel().getSelectedItem());
          }
          workingList.remove(edgeView.getSelectionModel().getSelectedItem());
          refreshEdgeTable();
        }));
    removeLocationButton.setOnAction(
        event -> {
          removeLocation();
        });
    addLocationButton.setOnAction(event -> addLocationName());
  }

  private void refreshEdgeTable() {
    // edgeView.getItems().clear();
    edgeView.setItems(FXCollections.observableList(workingList));
  }

  private void edgeUpdateDatabase() {
    for (HospitalEdge edgeAddition : addList) {
      SQLRepo.INSTANCE.addEdge(edgeAddition);
    }
    for (HospitalEdge edgeDeletion : deleteList) {
      SQLRepo.INSTANCE.deleteEdge(edgeDeletion);
    }
  }

  private String makeNewNodeID() {
    System.out.println(
        allNodes.keySet().stream()
            .sorted(
                new Comparator<>() {
                  @Override
                  public int compare(String str1, String str2) {
                    return Integer.parseInt((String) str1) - Integer.parseInt((String) str2);
                  }
                })
            .toList());
    return (Integer.parseInt(
                allNodes.keySet().stream()
                    .sorted(
                        new Comparator<>() {
                          @Override
                          public int compare(String str1, String str2) {
                            return Integer.parseInt((String) str1)
                                - Integer.parseInt((String) str2);
                          }
                        })
                    .toList()
                    .get(allNodes.size() - 1))
            + 5)
        + "";
  }
}
