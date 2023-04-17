package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.entities.FurnitureRequestData;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FurnitureDAOTest {
  @Test
  public void testGetAddDelete() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    List<FurnitureRequestData> furniture = SQLRepo.INSTANCE.getFurnitureRequestsList();

    SQLRepo.INSTANCE.addFurnitureRequest(
        new FurnitureRequestData(
            1,
            "joseph",
            "Cafe",
            "4/17/2023",
            "3:12PM",
            "not jamie",
            "Bed",
            "for 2 hours",
            FurnitureRequestData.Status.DONE));

    List<FurnitureRequestData> furnitureRequestAdded = SQLRepo.INSTANCE.getFurnitureRequestsList();
    assertEquals(furnitureRequestAdded.size(), furniture.size() + 1);

    SQLRepo.INSTANCE.deleteFurnitureRequest(
        new FurnitureRequestData(
            1,
            "joseph",
            "Cafe",
            "4/17/2023",
            "3:12PM",
            "not jamie",
            "Bed",
            "for 2 hours",
            FurnitureRequestData.Status.DONE));
    List<FurnitureRequestData> furnitureRequestDeleted =
        SQLRepo.INSTANCE.getFurnitureRequestsList();
    assertEquals(furnitureRequestDeleted.size(), furniture.size());

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testUpdate() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    FurnitureRequestData furnitureRequest =
        new FurnitureRequestData(
            1,
            "joseph",
            "Cafe",
            "4/17/2023",
            "3:12PM",
            "not jamie",
            "Bed",
            "deliver with love",
            FurnitureRequestData.Status.DONE);

    SQLRepo.INSTANCE.addFurnitureRequest(furnitureRequest);
    SQLRepo.INSTANCE.updateFurnitureRequest(furnitureRequest, "status", "PENDING");
    SQLRepo.INSTANCE.deleteFurnitureRequest(furnitureRequest);

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testImportExport() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.FURNITURE_REQUESTS,
        "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733",
        "FurnitureExport");

    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.FURNITURE_REQUESTS,
        "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733\\FurnitureExport");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
