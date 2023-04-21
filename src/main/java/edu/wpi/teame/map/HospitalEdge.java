package edu.wpi.teame.map;

import edu.wpi.teame.entities.ORM;
import lombok.Getter;

import java.util.HashMap;

public class HospitalEdge implements ORM {
  @Getter String nodeOneID;
  @Getter String nodeTwoID;
  @Getter int edgeWeight;

  public HospitalEdge(String nodeOneID, String nodeTwoID) {
    this.nodeOneID = nodeOneID;
    this.nodeTwoID = nodeTwoID;
    this.edgeWeight = 1; // Default edge weight of 1
  }

  public HospitalEdge(String nodeOneID, String nodeTwoID, int weight) {
    this.nodeOneID = nodeOneID;
    this.nodeTwoID = nodeTwoID;
    this.edgeWeight = weight;
  }

  public String getTable(){
    return "Edge";
  }

  public void applyChanges(HashMap<String, String> changes){
    if(changes.containsKey("startNode")){
      this.nodeOneID = changes.get("nodeOneID");
    }
    if(changes.containsKey("endNode")){
      this.nodeTwoID = changes.get("nodeTwoID");
    }
    if(changes.containsKey("edgeWeight")){
      this.edgeWeight = Integer.parseInt(changes.get("edgeWeight"));
    }
  }
}
