package edu.wpi.teame;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.LocationName;

public class Main {

  public static void main(String[] args) {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.getNodeList();
    HospitalNode.processEdgeList(SQLRepo.INSTANCE.getEdgeList());
    SQLRepo.INSTANCE.getLocationList();
    System.out.println(LocationName.allLocations.keySet());
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
