package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.LocationName;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.util.List;

public class MoveComponentController {
    @FXML MFXComboBox departmentMoveSelector;
    @FXML MFXComboBox newNodeSelector;
    @FXML MFXComboBox departmentOneSelector;
    @FXML MFXComboBox departmentTwoSelector;
    @FXML DatePicker moveDateSelector;
    @FXML Tab moveTab;
    @FXML Tab swapTab;
    @FXML MFXButton confirmButton;
    @FXML MFXButton resetButton;
    @FXML Text moveCountText;
    @FXML ListView currentMoveList;
    @FXML TableView futureMoveTable;
    @FXML TableColumn nodeIDCol;
    @FXML TableColumn nameCol;
    @FXML TableColumn dateCol;



    ObservableList<String> floorLocation = FXCollections.observableArrayList(SQLRepo.INSTANCE.getLongNamesFromLocationName(SQLRepo.INSTANCE.getLocationList()));;


    @FXML public void initialize(){
        
    }

    private void initiatingField(){
        floorLocation = FXCollections.observableArrayList(
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
        departmentMoveSelector
}
    private void swapDepartments(){

    }
}
