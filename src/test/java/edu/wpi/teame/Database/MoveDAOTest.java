package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.map.MoveAttribute;
import java.io.File;
import java.util.List;
import javax.swing.filechooser.FileSystemView;
import org.junit.jupiter.api.Test;

public class MoveDAOTest {

  @Test
  public void testUpdateList() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.getMoveList();

    // add update
    SQLRepo.INSTANCE.updateMove(
        new MoveAttribute(1200, "Hall 3 Level 1", "2023-01-01"), "date", "2023-01-02");

    // reset update
    SQLRepo.INSTANCE.updateMove(
        new MoveAttribute(1200, "Hall 3 Level 1", "2023-01-02"), "date", "2023-01-01");
  }

  @Test
  public void testAddAndDeleteMove() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<MoveAttribute> moveAttributes = SQLRepo.INSTANCE.getMoveList();

    int lengthList = moveAttributes.size();

    SQLRepo.INSTANCE.addMove(new MoveAttribute(2535, "HallNode", "2023-01-01"));

    moveAttributes = SQLRepo.INSTANCE.getMoveList();

    assertTrue(moveAttributes.size() == lengthList + 1);

    SQLRepo.INSTANCE.deleteMove(new MoveAttribute(2535, "HallNode", "2023-01-01"));

    moveAttributes = SQLRepo.INSTANCE.getMoveList();
    System.out.println(moveAttributes.size() + " " + lengthList);

    assertTrue(moveAttributes.size() == lengthList);
  }

  @Test
  public void exportImportMove() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    FileSystemView view = FileSystemView.getFileSystemView();
    File file = view.getHomeDirectory();
    String desktopPath = file.getPath();

    SQLRepo.INSTANCE.exportToCSV(SQLRepo.Table.MOVE, desktopPath, "MoveExport");

    SQLRepo.INSTANCE.importFromCSV(SQLRepo.Table.MOVE, desktopPath + "\\MoveExport");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
