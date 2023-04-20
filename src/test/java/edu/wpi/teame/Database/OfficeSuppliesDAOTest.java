package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.entities.OfficeSuppliesData;
import java.util.List;
import org.junit.jupiter.api.Test;

public class OfficeSuppliesDAOTest {
  @Test
  public void testGetAddDelete() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    List<OfficeSuppliesData> officeSupply = SQLRepo.INSTANCE.getOfficeSupplyList();

    SQLRepo.INSTANCE.addOfficeSupplyRequest(
        new OfficeSuppliesData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "not jamie",
            "rulers",
            "2",
            "fast",
            OfficeSuppliesData.Status.PENDING));

    List<OfficeSuppliesData> officeSupplyRequestAdded = SQLRepo.INSTANCE.getOfficeSupplyList();
    assertEquals(officeSupplyRequestAdded.size(), officeSupply.size() + 1);

    SQLRepo.INSTANCE.deleteOfficeSupplyRequest(
        new OfficeSuppliesData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "not jamie",
            "rulers",
            "2",
            "fast",
            OfficeSuppliesData.Status.PENDING));
    List<OfficeSuppliesData> officeSupplyRequestDeleted = SQLRepo.INSTANCE.getOfficeSupplyList();
    assertEquals(officeSupplyRequestDeleted.size(), officeSupply.size());

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testUpdate() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    OfficeSuppliesData officeSupplyRequest =
        new OfficeSuppliesData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "not jamie",
            "rulers",
            "2",
            "fast",
            OfficeSuppliesData.Status.PENDING);

    SQLRepo.INSTANCE.addOfficeSupplyRequest(officeSupplyRequest);
    SQLRepo.INSTANCE.updateOfficeSupply(officeSupplyRequest, "status", "DONE");
    SQLRepo.INSTANCE.deleteOfficeSupplyRequest(officeSupplyRequest);

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testImportExport() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.OFFICE_SUPPLY,
        "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733",
        "OfficeExport");

    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.OFFICE_SUPPLY, "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733\\OfficeExport");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
