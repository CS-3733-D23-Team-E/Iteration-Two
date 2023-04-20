package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.entities.Employee;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import org.junit.jupiter.api.Test;

public class EmployeeDAOTest {

  @Test
  public void testlogIn() throws RuntimeException {
    SQLRepo.INSTANCE.connectToDatabase("staff", "staff");
    SQLRepo.INSTANCE.exitDatabaseProgram();

    SQLRepo.INSTANCE.connectToDatabase("admin", "admin");
    SQLRepo.INSTANCE.exitDatabaseProgram();

    Employee failure = SQLRepo.INSTANCE.connectToDatabase("test", "fail");
    assertNull(failure);
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testGetAddandDelete() {
    SQLRepo.INSTANCE.connectToDatabase("staff", "staff");
    int numEmployees = SQLRepo.INSTANCE.getEmployeeList().size();

    Employee Jamie = new Employee("Jamie Rapal", "JRapal", "password", "ADMIN");
    SQLRepo.INSTANCE.addEmployee(Jamie);
    int numEmployees2 = SQLRepo.INSTANCE.getEmployeeList().size();
    assertEquals(numEmployees + 1, numEmployees2);

    SQLRepo.INSTANCE.deleteEmployee(Jamie);
    int numEmployees3 = SQLRepo.INSTANCE.getEmployeeList().size();
    assertEquals(numEmployees, numEmployees3);

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testUpdate() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    Employee Jamie = new Employee("Jamie Rapal", "JRapal", "password", "ADMIN");
    SQLRepo.INSTANCE.addEmployee(Jamie);
    SQLRepo.INSTANCE.updateEmployee(Jamie, "fullName", "Jamie R");
    Jamie.setFullName("Jamie R");

    SQLRepo.INSTANCE.updateEmployee(Jamie, "username", "JamieRapal");
    Jamie.setUsername("JamieRapal");

    SQLRepo.INSTANCE.updateEmployee(Jamie, "password", "pass");
    SQLRepo.INSTANCE.updateEmployee(Jamie, "permission", "STAFF");

    SQLRepo.INSTANCE.deleteEmployee(Jamie);

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testImportExport() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    FileSystemView view = FileSystemView.getFileSystemView();
    File file = view.getHomeDirectory();
    String desktopPath = file.getPath();

    SQLRepo.INSTANCE.exportToCSV(SQLRepo.Table.EMPLOYEE, desktopPath, "Employee");
    SQLRepo.INSTANCE.importFromCSV(SQLRepo.Table.EMPLOYEE, desktopPath + "\\Employee");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void addUsers() {
    Employee Kevin = new Employee("Kevin Siegall", "Kevin", "Kevin", "ADMIN");
    Employee Jamie = new Employee("Jamie Rapal", "Jamie", "Jamie", "ADMIN");
    Employee Joseph = new Employee("Joseph Thesmar", "Joseph", "Joseph", "STAFF");
    Employee Aarsh = new Employee("Aarsh Zadaphiya", "Aarsh", "Aarsh", "ADMIN");
    Employee Megan = new Employee("Megan Jacques", "Megan", "Megan", "ADMIN");
    Employee Braeden = new Employee("Braeden Swain", "Braeden", "Braeden", "staff");
    Employee Albert = new Employee("Albert Lewis", "Albert", "Albert", "ADMIN");
    Employee Diyar = new Employee("Diyar Aljabbari", "Diyar", "Diyar", "ADMIN");
    Employee Nick = new Employee("Nick Borrello", "Nick", "Nick", "ADMIN");
    Employee Mich = new Employee("Mich Toryu", "Mich", "Mich", "ADMIN");
    Employee Anthony = new Employee("Anthony Virone", "Anthony", "Anthony", "admin");
    Employee Ben = new Employee("Ben Kresge", "Ben", "Ben", "admin");

    SQLRepo.INSTANCE.connectToDatabase("admin", "admin");
    SQLRepo.INSTANCE.addEmployee(Kevin);
    SQLRepo.INSTANCE.addEmployee(Jamie);
    SQLRepo.INSTANCE.addEmployee(Joseph);
    SQLRepo.INSTANCE.addEmployee(Aarsh);
    SQLRepo.INSTANCE.addEmployee(Megan);
    SQLRepo.INSTANCE.addEmployee(Braeden);
    SQLRepo.INSTANCE.addEmployee(Albert);
    SQLRepo.INSTANCE.addEmployee(Diyar);
    SQLRepo.INSTANCE.addEmployee(Nick);
    SQLRepo.INSTANCE.addEmployee(Mich);
    SQLRepo.INSTANCE.addEmployee(Anthony);
    SQLRepo.INSTANCE.addEmployee(Ben);

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
