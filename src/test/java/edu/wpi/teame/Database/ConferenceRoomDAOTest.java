package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.entities.ConferenceRequestData;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ConferenceRoomDAOTest {
  @Test
  public void testGetAddDelete() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    List<ConferenceRequestData> conference = SQLRepo.INSTANCE.getConfList();

    SQLRepo.INSTANCE.addServiceRequest(
        new ConferenceRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "Joseph",
            "Conference Room L1",
            "for 2 hours",
            ConferenceRequestData.Status.DONE));

    List<ConferenceRequestData> conferenceRequestAdded = SQLRepo.INSTANCE.getConfList();
    assertEquals(conferenceRequestAdded.size(), conference.size() + 1);

    SQLRepo.INSTANCE.addServiceRequest(
        new ConferenceRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "Joseph",
            "Conference Room L1",
            "for 2 hours",
            ConferenceRequestData.Status.DONE));
    List<ConferenceRequestData> conferenceRequestDeleted = SQLRepo.INSTANCE.getConfList();
    assertEquals(conferenceRequestDeleted.size(), conference.size());

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testUpdate() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    ConferenceRequestData conferenceRequest =
        new ConferenceRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "Joseph",
            "Conference Room L1",
            "for 2 hours",
            ConferenceRequestData.Status.DONE);

    SQLRepo.INSTANCE.addServiceRequest(conferenceRequest);
    SQLRepo.INSTANCE.updateServiceRequest(conferenceRequest, "status", "PENDING");
    SQLRepo.INSTANCE.deleteServiceRequest(conferenceRequest);

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testImportExport() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.CONFERENCE_ROOM,
        "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733",
        "ConfExport");

    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.CONFERENCE_ROOM, "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733\\ConfExport");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
