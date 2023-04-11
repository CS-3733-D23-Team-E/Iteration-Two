package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ServiceRequestPageController {

  @FXML MFXButton returnButton;
  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarMaps;
  @FXML MFXButton menuBarDatabase;
  @FXML MFXButton menuBarExit;
  @FXML VBox menuBar;
  boolean menuVisibilty = false;

  @FXML
  public void initialize() {

    // Initially set the menuVBox to invisible
    menuBar.setVisible(false);

    returnButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    // When the menu button is clicked, invert the value of menuVisibility and set the menuVBox to
    // that value
    // (so each time the menu button is clicked it changes the visibility of menuVBox back and
    // forth)
    menuButton.setOnMouseClicked(
        event -> {
          menuVisibilty = !menuVisibilty;
          menuBar.setVisible(menuVisibilty);
        });

    // Navigation controls for the button in the menuVBox
    menuBarHome.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.HOME);
          menuVisibilty = !menuVisibilty;
        });
    menuBarServices.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    menuBarMaps.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    menuBarDatabase.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_VIEW));
    menuBarExit.setOnMouseClicked((event -> Platform.exit()));
  }
}

  //  public void menuDropDownVisibility(boolean bool) {
  //    menuBarSignage.setVisible(bool);
  //    menuBarServices.setVisible(bool);
  //    menuBarHome.setVisible(bool);
  //    menuBarMap.setVisible(bool);
  //    menuBarDatabase.setVisible(bool);
  //    menuBarExit.setVisible(bool);
  //  }
  //
  //  public void showMenuButtonsWhenHovered(MFXButton button) {
  //    button.setOnMouseEntered(
  //        event -> {
  //          menuDropDownVisibility(true);
  //        });
  //    button.setOnMouseExited(
  //        event -> {
  //          menuDropDownVisibility(false);
  //        });
  //  }
