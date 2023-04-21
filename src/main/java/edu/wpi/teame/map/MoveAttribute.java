package edu.wpi.teame.map;

public class MoveAttribute {
  @Getter int nodeID;
  @Getter String longName;
  @Getter String date;

  public MoveAttribute(int nodeID, String longName, String date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
