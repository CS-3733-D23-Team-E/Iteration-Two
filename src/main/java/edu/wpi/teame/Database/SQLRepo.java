package edu.wpi.teame.Database;

import edu.wpi.teame.Main;
import edu.wpi.teame.entities.Employee;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.map.*;
import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;

public enum SQLRepo {
  INSTANCE;

  public enum Table {
    LOCATION_NAME,
    MOVE,
    NODE,
    EDGE,
    SERVICE_REQUESTS,
    EMPLOYEE;

    public static String tableToString(Table tb) {
      switch (tb) {
        case LOCATION_NAME:
          return "LocationName";
        case MOVE:
          return "Move";
        case NODE:
          return "Node";
        case EDGE:
          return "Edge";
        case SERVICE_REQUESTS:
          return "ServiceRequests";
        case EMPLOYEE:
          return "Employee";
        default:
          throw new NoSuchElementException("No such Table found");
      }
    }
  }

  Connection activeConnection;
  DAO<HospitalNode> nodeDAO;
  DAO<HospitalEdge> edgeDAO;
  DAO<MoveAttribute> moveDAO;
  DAO<LocationName> locationDAO;
  DAO<ServiceRequestData> serviceDAO;
  EmployeeDAO employeeDAO;
  DatabaseUtility dbUtility;

  public Employee connectToDatabase(String username, String password) {
    try {
      Class.forName("org.postgresql.Driver");
      activeConnection =
          DriverManager.getConnection(
              "jdbc:postgresql://database.cs.wpi.edu:5432/teamedb", "teame", "teame50");
      employeeDAO = new EmployeeDAO(activeConnection);
      Employee loggedIn = employeeDAO.verifyLogIn(username, password);
      if (loggedIn == null) {
        return null;
      } else {
        nodeDAO = new NodeDAO(activeConnection);
        edgeDAO = new EdgeDAO(activeConnection);
        moveDAO = new MoveDAO(activeConnection);
        locationDAO = new LocationDAO(activeConnection);
        serviceDAO = new ServiceDAO(activeConnection);
        dbUtility = new DatabaseUtility(activeConnection);
        Employee.setCurrentEmployee(loggedIn);
        return loggedIn;
      }
    } catch (SQLException e) {
      exitDatabaseProgram();
      throw new RuntimeException("Your username or password is incorrect");
    } catch (ClassNotFoundException e) {
      exitDatabaseProgram();
      throw new RuntimeException("Sorry something went wrong please try again");
    }
  }

  public void exitDatabaseProgram() {
    try {
      activeConnection.close();
      System.out.println("Database Connection Closed");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  // DatabaseReset
  public void resetDatabase() {
    String node = Main.class.getResource("Data/NewData/Node.csv").getFile().replaceAll("%20", " ");
    String edge = Main.class.getResource("Data/NewData/Edge.csv").getFile().replaceAll("%20", " ");
    String move = Main.class.getResource("Data/NewData/Move.csv").getFile().replaceAll("%20", " ");
    String location =
        Main.class.getResource("Data/NewData/LocationName.csv").getFile().replaceAll("%20", " ");

    this.importFromCSV(Table.NODE, node);
    this.importFromCSV(Table.EDGE, edge);
    this.importFromCSV(Table.LOCATION_NAME, location);
    this.importFromCSV(Table.MOVE, move);
  }

  // ALL DATABASE UTILITY
  public int getNodeIDFromName(String longName) {
    return this.dbUtility.getNodeIDFromName(longName);
  }

  public String getNamefromNodeID(int nodeID) {
    return this.dbUtility.getNameFromNodeID(nodeID);
  }

  public void updateUsingNodeID(
      String nodeID, String oldLongName, String columnName, String value) {
    this.dbUtility.updateMoveWithoutObject(nodeID, oldLongName, columnName, value);
  }

  public String getShortNameFromNodeID(String nodeID) throws SQLException {
    return this.dbUtility.getShortNameFromNodeID(nodeID);
  }

  public String getNodeTypeFromNodeID(int nodeID) {
    return this.dbUtility.getNodeTypeFromNodeID(nodeID);
  }

  public List<HospitalNode> getNodesFromFloor(Floor fl) {
    return this.dbUtility.getNodesFromFloor(fl);
  }

  public List<MoveAttribute> getMoveAttributeFromFloor(Floor fl) {
    return this.dbUtility.getMoveAttributeFromFloor(fl);
  }

  public List<String> getLongNamesFromMove(List<MoveAttribute> mv) {
    return this.dbUtility.getLongNamesFromMove(mv);
  }

  public List<String> getLongNamesFromLocationName(List<LocationName> ln) {
    return this.dbUtility.getLongNamesFromLocationName(ln);
  }

  // ALL IMPORTS FOR DAOS
  public void importFromCSV(Table table, String filepath) {
    try {
      switch (table) {
        case MOVE:
          this.moveDAO.importFromCSV(filepath, "Move");
          break;
        case EDGE:
          this.edgeDAO.importFromCSV(filepath, "Edge");
          break;
        case NODE:
          this.nodeDAO.importFromCSV(filepath, "Node");
          break;
        case LOCATION_NAME:
          this.locationDAO.importFromCSV(filepath, "LocationName");
          break;
        case SERVICE_REQUESTS:
          this.serviceDAO.importFromCSV(filepath, "ServiceRequests");
          break;
        case EMPLOYEE:
          this.employeeDAO.importFromCSV(filepath, "Employee");
          break;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // ALL EXPORTS FOR DAOS
  public void exportToCSV(Table table, String filepath, String tableName) {
    try {
      switch (table) {
        case MOVE:
          this.moveDAO.exportToCSV(filepath, tableName);
          break;
        case EDGE:
          this.edgeDAO.exportToCSV(filepath, tableName);
          break;
        case NODE:
          this.nodeDAO.exportToCSV(filepath, tableName);
          break;
        case LOCATION_NAME:
          this.locationDAO.exportToCSV(filepath, tableName);
          break;
        case SERVICE_REQUESTS:
          this.serviceDAO.exportToCSV(filepath, tableName);
          break;
        case EMPLOYEE:
          this.employeeDAO.exportToCSV(filepath, tableName);
          break;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // ALL GETS FOR DAOS
  public List<ServiceRequestData> getServiceRequestList() {
    return this.serviceDAO.get();
  }

  public List<HospitalNode> getNodeList() {
    return this.nodeDAO.get();
  }

  public List<HospitalEdge> getEdgeList() {
    return this.edgeDAO.get();
  }

  public List<LocationName> getLocationList() {
    return this.locationDAO.get();
  }

  public List<MoveAttribute> getMoveList() {
    return this.moveDAO.get();
  }

  public List<Employee> getEmployeeList() {
    return this.employeeDAO.get();
  }

  // ALL UPDATES FOR DAOS
  public void updateServiceRequest(ServiceRequestData obj, String attribute, String value) {
    this.serviceDAO.update(obj, attribute, value);
  }

  public void updateNode(HospitalNode obj, String attribute, String value) {
    this.nodeDAO.update(obj, attribute, value);
  }

  public void updateEdge(HospitalEdge obj, String attribute, String value) {
    this.edgeDAO.update(obj, attribute, value);
  }

  public void updateMove(MoveAttribute obj, String attribute, String value) {
    this.moveDAO.update(obj, attribute, value);
  }

  public void updateLocation(LocationName obj, String attribute, String value) {
    this.locationDAO.update(obj, attribute, value);
  }

  public void updateEmployee(Employee obj, String attribute, String value) {
    this.employeeDAO.update(obj, attribute, value);
  }

  // ALL DELETES FOR DAOS
  public void deleteServiceRequest(ServiceRequestData obj) {
    this.serviceDAO.delete(obj);
  }

  public void deletenode(HospitalNode obj) {
    this.nodeDAO.delete(obj);
  }

  public void deleteEdge(HospitalEdge obj) {
    this.edgeDAO.delete(obj);
  }

  public void deleteLocation(LocationName obj) {
    this.locationDAO.delete(obj);
  }

  public void deleteMove(MoveAttribute obj) {
    this.moveDAO.delete(obj);
  }

  public void deleteEmployee(Employee obj) {
    this.employeeDAO.delete(obj);
  }

  // ALL ADDITIONS TO DAOS
  public void addServiceRequest(ServiceRequestData obj) {
    this.serviceDAO.add(obj);
  }

  public void addNode(HospitalNode obj) {
    this.nodeDAO.add(obj);
  }

  public void addEdge(HospitalEdge obj) {
    this.edgeDAO.add(obj);
  }

  public void addLocation(LocationName obj) {
    this.locationDAO.add(obj);
  }

  public void addMove(MoveAttribute obj) {
    this.moveDAO.add(obj);
  }

  public void addEmployee(Employee obj) {
    this.employeeDAO.add(obj);
  }
}
