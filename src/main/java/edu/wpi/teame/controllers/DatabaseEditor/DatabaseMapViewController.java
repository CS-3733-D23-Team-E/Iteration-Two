package edu.wpi.teame.controllers.DatabaseEditor;

import static edu.wpi.teame.map.HospitalNode.allNodes;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.*;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXButton;
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

  LinkedList<HospitalEdge> edges = new LinkedList<>();
  LinkedList<HospitalEdge> addList = new LinkedList<>();
  LinkedList<HospitalEdge> deleteList = new LinkedList<>();

  HospitalNode curNode;

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

    edgeColumn.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeTwoID"));
  }

  private void cancel() {}

  private void deleteNode() {}

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
    curNode = allNodes.get(nodeID);
    edges =
        (LinkedList)
            SQLRepo.INSTANCE.getEdgeList().stream()
                .filter((edge) -> (edge.getNodeOneID().equals(nodeID)));
    addList.clear();
    deleteList.clear();

    String x = Integer.toString(curNode.getXCoord());
    String y = Integer.toString(curNode.getYCoord());
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
    HospitalNode hospitalNode = allNodes.get(nodeID);

    String newX = xField.getText();
    String newY = yField.getText();

    // update the database
    SQLRepo.INSTANCE.updateNode(hospitalNode, "xcoord", newX);
    SQLRepo.INSTANCE.updateNode(hospitalNode, "ycoord", newY);
    edgeUpdateDatabase();
  }

  private void addNodeToDatabase() {}

  private void updateCombo() {
    // POPULATE COMBOBOXES

    List<LocationName> locationNames = SQLRepo.INSTANCE.getLocationList();
    List<String> longNames = SQLRepo.INSTANCE.getLongNamesFromLocationName(locationNames);
    longNameSelector.setItems(FXCollections.observableList(longNames));

    buildingSelector.setItems(FXCollections.observableArrayList(HospitalNode.allBuildings()));

    nodeTypeChoice.setItems(
        FXCollections.observableArrayList(LocationName.NodeType.allNodeTypes()));

    // addEdgeField.setItems(FXCollections.observableList((List) allNodes.keySet()));
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
          // refresh the table
          refreshEdgeTable();
        }));

    removeEdgeButton.setOnAction(
        (event -> {
          // if item is in edge list, add to delete list
          if (edges.contains(edgeView.getSelectionModel().getSelectedItem())) {
            deleteList.add(edgeView.getSelectionModel().getSelectedItem());
          } else { // if item is not in the edge list, remove from add list
            addList.remove(edgeView.getSelectionModel().getSelectedItem());
          }
          // refresh the table
          refreshEdgeTable();
        }));
  }

  private void refreshEdgeTable() {
    edgeView.getItems().clear();
    edgeView.setItems(FXCollections.observableArrayList(edges));
  }

  private void edgeUpdateDatabase() {
    for (HospitalEdge edgeAddition : addList) {
      SQLRepo.INSTANCE.addEdge(edgeAddition);
    }
    for (HospitalEdge edgeDeletion : deleteList) {
      SQLRepo.INSTANCE.deleteEdge(edgeDeletion);
    }
  }

  private String getNewNodeID() {
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
                    .get(allNodes.size() - 1)
                + 5))
        + "";
  }
}
