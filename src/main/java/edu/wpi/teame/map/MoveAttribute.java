package edu.wpi.teame.map;

import lombok.Getter;

public class MoveAttribute {
  @Getter int nodeID;
  @Getter String longName;
  @Getter String date;

  public MoveAttribute(int nodeID, String longName, String date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }

  public int getNodeID() {
    return nodeID;
  }

  public String getLongName() {
    return longName;
  }

  public String getDate() {
    return date;
  }
}
