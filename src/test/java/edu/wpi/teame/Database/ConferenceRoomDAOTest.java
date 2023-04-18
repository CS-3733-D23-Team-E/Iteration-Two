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

    SQLRepo.INSTANCE.addConfRoomRequest(
        new ConferenceRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "not jamie",
            "Conference Room L1",
            "for 2 hours",
            ConferenceRequestData.Status.DONE));

    List<ConferenceRequestData> conferenceRequestAdded = SQLRepo.INSTANCE.getConfList();
    assertEquals(conferenceRequestAdded.size(), conference.size() + 1);

    SQLRepo.INSTANCE.deleteConfRoomRequest(
        new ConferenceRequestData(
            1,
            "joseph",
            "Cafe",
            "2023-04-07",
            "3:12PM",
            "not jamie",
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
            "not jamie",
            "Conference Room L1",
            "for 2 hours",
            ConferenceRequestData.Status.DONE);

    SQLRepo.INSTANCE.addConfRoomRequest(conferenceRequest);
    SQLRepo.INSTANCE.updateConfRoomRequest(conferenceRequest, "status", "PENDING");
    // SQLRepo.INSTANCE.deleteConfRoomRequest(conferenceRequest);

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
