package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ServiceRequestPageController {

  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarMaps;
  @FXML MFXButton menuBarDatabase;
  @FXML MFXButton menuBarBlank;
  @FXML MFXButton menuBarExit;
  @FXML MFXButton userButton;
  @FXML VBox menuBar;

  @FXML VBox logoutBox;
  @FXML MFXButton logoutButton;

  boolean menuVisibilty = false;
  boolean logoutVisible = false;

  @FXML
  public void initialize() {

    // Initially set the menu bar to invisible
    menuBarVisible(false);
    logoutPopup(false);

    // When the menu button is clicked, invert the value of menuVisibility and set the menu bar to
    // that value
    // (so each time the menu button is clicked it changes the visibility of menu bar back and
    // forth)
    menuButton.setOnMouseClicked(
        event -> {
          menuVisibilty = !menuVisibilty;
          menuBarVisible(menuVisibilty);
        });

    userButton.setOnMouseClicked(
        event -> {
          logoutVisible = !logoutVisible;
          logoutPopup(logoutVisible);
        });

    // Navigation controls for the button in the menu bar
    menuBarHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    menuBarServices.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.SERVICE_REQUESTS);
          menuVisibilty = !menuVisibilty;
        });
    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarMaps.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    menuBarDatabase.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_EDITOR));
    menuBarExit.setOnMouseClicked((event -> Platform.exit()));
    logoutButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));

    // makes the menu bar buttons get highlighted when the mouse hovers over them
    mouseSetupMenuBar(menuBarHome, true);
    mouseSetupMenuBar(menuBarServices, true);
    mouseSetupMenuBar(menuBarSignage, true);
    mouseSetupMenuBar(menuBarMaps, true);
    mouseSetupMenuBar(menuBarDatabase, true);
    mouseSetupMenuBar(menuBarExit, false);

    mouseSetup(logoutButton);
  }

  public void logoutPopup(boolean bool) {
    logoutBox.setVisible(bool);
  }

  public void menuBarVisible(boolean bool) {
    menuBarHome.setVisible(bool);
    menuBarServices.setVisible(bool);
    menuBarSignage.setVisible(bool);
    menuBarMaps.setVisible(bool);
    menuBarDatabase.setVisible(bool);
    menuBarBlank.setVisible(bool);
    menuBarExit.setVisible(bool);
    menuBar.setVisible(bool);
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #f1f1f1; -fx-alignment: center; -fx-border-color: #001A3C; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #001A3C; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }

  private void mouseSetupMenuBar(MFXButton btn, boolean isLeftAligned) {
    if (isLeftAligned) {
      btn.setOnMouseEntered(
          event -> {
            btn.setStyle(
                "-fx-background-color: #f1f1f1; -fx-alignment: baseline-left; -fx-border-color: #001A3C; -fx-border-width: 0; -fx-font-size: 18;");
            btn.setTextFill(Color.web("#192d5aff", 1.0));
          });
      btn.setOnMouseExited(
          event -> {
            btn.setStyle(
                "-fx-background-color: #001A3C; -fx-alignment: baseline-left;-fx-font-size: 18;");
            btn.setTextFill(WHITE);
          });
    } else {
      btn.setOnMouseEntered(
          event -> {
            btn.setStyle(
                "-fx-background-color: #f1f1f1; -fx-alignment: baseline-center; -fx-border-color: #001A3C; -fx-border-width: 0; -fx-font-size: 18;");
            btn.setTextFill(Color.web("#192d5aff", 1.0));
          });
      btn.setOnMouseExited(
          event -> {
            btn.setStyle(
                "-fx-background-color: #001A3C; -fx-alignment: baseline-center;-fx-font-size: 18;");
            btn.setTextFill(WHITE);
          });
    }
  }
}
