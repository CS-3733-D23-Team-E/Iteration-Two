package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.entities.FlowerRequestData;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FlowerDAOTest {
  @Test
  public void testGetAddDelete() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    List<FlowerRequestData> flower = SQLRepo.INSTANCE.getFlowerRequestsList();

    SQLRepo.INSTANCE.addServiceRequest(
        new FlowerRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "Joseph",
            "rose",
            "2",
            "false",
            "i love you babe",
            "no package",
            FlowerRequestData.Status.IN_PROGRESS));

    List<FlowerRequestData> flowerRequestAdded = SQLRepo.INSTANCE.getFlowerRequestsList();
    assertEquals(flowerRequestAdded.size(), flower.size() + 1);

    SQLRepo.INSTANCE.deleteServiceRequest(
        new FlowerRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "Joseph",
            "rose",
            "2",
            "false",
            "i love you babe",
            "no package",
            FlowerRequestData.Status.IN_PROGRESS));

    List<FlowerRequestData> flowerRequestDeleted = SQLRepo.INSTANCE.getFlowerRequestsList();
    assertEquals(flowerRequestDeleted.size(), flower.size());

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testUpdate() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    FlowerRequestData flowerRequest =
        new FlowerRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "Joseph",
            "rose",
            "2",
            "yes",
            "i love you babe",
            "no package",
            FlowerRequestData.Status.IN_PROGRESS);
    SQLRepo.INSTANCE.addServiceRequest(flowerRequest);
    SQLRepo.INSTANCE.updateServiceRequest(flowerRequest, "status", "DONE");
    SQLRepo.INSTANCE.deleteServiceRequest(flowerRequest);

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testImportExport() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.FLOWER_REQUESTS,
        "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733",
        "FlowerExport");

    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.FLOWER_REQUESTS,
        "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733\\FlowerExport");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
