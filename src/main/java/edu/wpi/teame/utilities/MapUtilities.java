package edu.wpi.teame.utilities;

import edu.wpi.teame.Main;
import edu.wpi.teame.map.HospitalNode;
import java.awt.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javax.swing.*;

public class MapUtilities {
  private final int MAP_X = 5000;
  private final int MAP_Y = 3400;

  private final Pane pane;

  ObservableList<Node> currentNodes = FXCollections.observableArrayList();

  public MapUtilities(Pane pane) {
    this.pane = pane;
  }

  /**
   * draws a line given the starting and ending (x,y) coordinates and saves the line to a global
   * list of lines
   */
  public Line drawLine(int x1, int y1, int x2, int y2) {
    x1 = (int) convertX(x1);
    y1 = (int) convertY(y1);
    x2 = (int) convertX(x2);
    y2 = (int) convertY(y2);

    Line line = new Line(x1, y1, x2, y2);
    addShape(line);
    return line;
  }

  public Circle drawHospitalNode(HospitalNode hospitalNode) {

    int x = hospitalNode.getXCoord();
    int y = hospitalNode.getYCoord();

    // TODO: change color dependent on NodeType
    return drawCircle(x, y, 4);
  }

  public Circle drawRing(
      int x, int y, int radius, int thickness, Color innerColor, Color outerColor) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle innerCircle = new Circle(x, y, radius - thickness, innerColor);
    Circle outerCircle = new Circle(x, y, radius, outerColor);
    addShape(outerCircle);
    addShape(innerCircle);

    return outerCircle;
  }

  public void drawRing(int x, int y, int radius, int thickness) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle innerCircle = new Circle(x, y, radius - thickness, Color.WHITE);
    Circle outerCircle = new Circle(x, y, radius, Color.BLACK);
    addShape(innerCircle);
    addShape(outerCircle);
  }

  public Circle drawCircle(int x, int y, int radius, Color color) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle circle = new Circle(x, y, radius, color);
    addShape(circle);
    return circle;
  }

  public Circle drawCircle(int x, int y, int radius) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle circle = new Circle(x, y, radius, Color.BLACK);
    addShape(circle);
    return circle;
  }

  public Label createLabel(int x, int y, String text) {
    Label label = new Label(text);
    label.setLayoutX(convertX(x));
    label.setLayoutY(convertY(y));

    addShape(label);

    return label;
  }

  public Label createLabel(int x, int y, int xOffset, int yOffset, String text) {

    Label label = new Label(text);
    label.setLayoutX(convertX(x + xOffset));
    label.setLayoutY(convertY(y + yOffset));

    addShape(label);

    return label;
  }

  public double convertY(int yCoord) {
    return ImageCoordToPane(yCoord, MAP_Y, pane.getHeight());
  }

  public double convertX(int xCoord) {
    return ImageCoordToPane(xCoord, MAP_X, pane.getWidth());
  }

  /**
   * @param coord
   * @param mapWidth
   * @return
   */
  private double ImageCoordToPane(int coord, int mapWidth, double paneWidth) {
    return coord * (paneWidth / mapWidth);
  }

  public double PaneXToImageX(double coord) {
    double paneWidth = this.pane.getWidth();
    return coord * (MAP_X / paneWidth);
  }

  public double PaneYToImageY(double coord) {
    double paneWidth = this.pane.getHeight();
    return coord * (MAP_Y / paneWidth);
  }

  private double convertCoord(int coord, int mapWidth, double paneWidth) {
    return coord * (paneWidth / mapWidth);
  }

  /** @param node */
  private void addShape(Node node) {
    pane.getChildren().add(node);
    currentNodes.add(node);
  }

  public void removeNode(Node node) {
    pane.getChildren().remove(node);
  }

  public void removeAllByType(Class obj) {
    System.out.println(obj);
    System.out.println("remove1 :" + pane.getChildren());
    this.pane.getChildren().removeAll(filterShapes(obj));
    System.out.println("remove2 :" + pane.getChildren());
  }

  public void removeAll() {
    this.pane.getChildren().removeAll(currentNodes);
  }

  public ObservableList<Node> filterShapes(Class obj) {
    ObservableList<Node> result = currentNodes;
    result.removeIf(s -> (s.getClass() != obj));
    return result;
  }

  public ObservableList<Node> getCurrentNodes() {
    return currentNodes;
  }

  public void createPathLabels(VBox vbox, ArrayList<String> pathNames) {
    for (int i = 0; i < pathNames.size() - 1; i++) {

      String start = pathNames.get(i);
      String destination = pathNames.get(i + 1);

      // Start Label
      Label startLabel = new Label(start);
      startLabel.setFont(Font.font("SansSerif", 16));
      startLabel.setPrefWidth(125);
      startLabel.setAlignment(Pos.CENTER);
      startLabel.setWrapText(true);

      // Arrow Image
      ImageView arrowView = new ImageView();
      Image arrow = new Image(String.valueOf(Main.class.getResource("images/right-arrow.png")));
      arrowView.setImage(arrow);
      arrowView.setPreserveRatio(true);
      arrowView.setFitWidth(30);

      // Destination Label
      Label destinationLabel = new Label(destination);
      destinationLabel.setFont(Font.font("SansSerif", 16));
      destinationLabel.setPrefWidth(125);
      destinationLabel.setAlignment(Pos.CENTER);
      destinationLabel.setWrapText(true);

      // Drop Shadow
      DropShadow dropShadow = new DropShadow();
      dropShadow.setBlurType(BlurType.THREE_PASS_BOX);
      dropShadow.setWidth(21);
      dropShadow.setHeight(21);
      dropShadow.setRadius(4);
      dropShadow.setOffsetX(-4);
      dropShadow.setOffsetY(4);
      dropShadow.setSpread(0);
      dropShadow.setColor(new Color(0, 0, 0, 0.25));

      // Regions for spacing
      Region region1 = new Region();
      Region region2 = new Region();

      // HBox
      HBox hBox = new HBox();
      hBox.setBackground(
          new Background(
              new BackgroundFill(Color.web("#D9DAD7"), CornerRadii.EMPTY, Insets.EMPTY)));
      hBox.setPrefHeight(65);
      hBox.setEffect(dropShadow);
      hBox.setAlignment(Pos.CENTER);
      hBox.setSpacing(10);
      hBox.setHgrow(region1, Priority.ALWAYS);
      hBox.setHgrow(region2, Priority.ALWAYS);
      hBox.setPadding(new Insets(0, 10, 0, 10));
      hBox.getChildren().addAll(startLabel, region1, arrowView, region2, destinationLabel);

      // Add path label to VBox
      vbox.getChildren().add(hBox);
    }
  }
}
