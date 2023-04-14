package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.entities.Employee;
import org.junit.jupiter.api.Test;

public class EmployeeDAOTest {

  @Test
  public void testGetAddandDelete() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    int numEmployees = SQLRepo.INSTANCE.getEmployeeList().size();

    Employee Jamie = new Employee("Jamie Rapal", "JRapal", "password", Employee.Permission.ADMIN);
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

    Employee Jamie = new Employee("Jamie Rapal", "JRapal", "password", Employee.Permission.ADMIN);
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

    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.EMPLOYEE,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop",
        "Employee");
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.EMPLOYEE,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop\\Employee");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
