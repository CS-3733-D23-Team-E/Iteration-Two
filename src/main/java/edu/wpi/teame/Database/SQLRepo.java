package edu.wpi.teame.Database;

import edu.wpi.teame.entities.*;
import edu.wpi.teame.map.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    OFFICE_SUPPLY,
    MEAL_REQUESTS,
    FLOWER_REQUESTS,
    CONFERENCE_ROOM;

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
        case OFFICE_SUPPLY:
          return "OfficeSupplies";
        case MEAL_REQUESTS:
          return "MealService";
        case FLOWER_REQUESTS:
          return "FlowerService";
        case CONFERENCE_ROOM:
          return "ConfRoomService";
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
  DatabaseUtility dbUtility;

  DAO<OfficeSuppliesData> officesupplyDAO;
  DAO<MealRequestData> mealDAO;
  DAO<FlowerRequestData> flowerDAO;
  DAO<ConferenceRequestData> conferenceDAO;

  public void connectToDatabase(String username, String password) {
    try {
      Class.forName("org.postgresql.Driver");
      activeConnection =
          DriverManager.getConnection(
              "jdbc:postgresql://database.cs.wpi.edu:5432/teamedb", username, password);

      nodeDAO = new NodeDAO(activeConnection);
      edgeDAO = new EdgeDAO(activeConnection);
      moveDAO = new MoveDAO(activeConnection);
      locationDAO = new LocationDAO(activeConnection);
      officesupplyDAO = new OfficeSuppliesDAO(activeConnection);
      mealDAO = new MealDAO(activeConnection);
      flowerDAO = new FlowerDAO(activeConnection);
      conferenceDAO = new ConferenceRoomDAO(activeConnection);
      serviceDAO = new ServiceDAO(activeConnection);
      dbUtility = new DatabaseUtility(activeConnection);

    } catch (SQLException e) {
      throw new RuntimeException("Your username or password is incorrect");
    } catch (ClassNotFoundException e) {
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
        case OFFICE_SUPPLY:
          this.officesupplyDAO.importFromCSV(filepath, "OfficeSupplies");
          break;
        case MEAL_REQUESTS:
          this.mealDAO.importFromCSV(filepath, "MealService");
          break;
        case CONFERENCE_ROOM:
          this.conferenceDAO.importFromCSV(filepath, "ConfRoomService");
          break;
        case FLOWER_REQUESTS:
          this.flowerDAO.importFromCSV(filepath, "FlowerService");
          break;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

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
        case OFFICE_SUPPLY:
          this.officesupplyDAO.exportToCSV(filepath, tableName);
          break;
        case MEAL_REQUESTS:
          this.mealDAO.exportToCSV(filepath, tableName);
          break;
        case CONFERENCE_ROOM:
          this.conferenceDAO.exportToCSV(filepath, tableName);
          break;
        case FLOWER_REQUESTS:
          this.flowerDAO.exportToCSV(filepath, tableName);
          break;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public List<ServiceRequestData> getServiceRequestList() {
    return this.serviceDAO.get();
  }

  public List<OfficeSuppliesData> getOfficeSupplyList(){
    return this.officesupplyDAO.get();
  }

  public List<MealRequestData> getMealRequestsList(){
    return this.mealDAO.get();
  }
  public List<ConferenceRequestData> getConfList(){
    return this.conferenceDAO.get();
  }
  public List<FlowerRequestData> getFlowerRequestsList(){
    return this.flowerDAO.get();
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

  public void updateServiceRequest(ServiceRequestData obj, String attribute, String value) {
    this.serviceDAO.update(obj, attribute, value);
  }

  public void updateOfficeSupply(OfficeSuppliesData obj, String attribute, String value){
    this.officesupplyDAO.update(obj, attribute, value);
  }

  public void updateMealRequest(MealRequestData obj, String attribute, String value){
    this.mealDAO.update(obj, attribute, value);
  }

  public void updateConfRoomRequest(ConferenceRequestData obj, String attribute, String value){
    this.conferenceDAO.update(obj, attribute, value);
  }

  public void updateFlowerRequest(FlowerRequestData obj, String attribute, String value){
    this.flowerDAO.update(obj, attribute, value);
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

  public void deleteServiceRequest(ServiceRequestData obj) {
    this.serviceDAO.delete(obj);
  }

  public void deleteOfficeSupplyRequest(OfficeSuppliesData obj) {this.officesupplyDAO.delete(obj);}
  public void deleteMealRequest(MealRequestData obj) {this.mealDAO.delete(obj);}
  public void deleteConfRoomRequest(ConferenceRequestData obj) {this.conferenceDAO.delete(obj);}
  public void deleteFlowerRequest(FlowerRequestData obj) {this.flowerDAO.delete(obj);}
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

  public void addServiceRequest(ServiceRequestData obj) {
    this.serviceDAO.add(obj);
  }

  public void addOfficeSupplyRequest(OfficeSuppliesData obj) {
    this.officesupplyDAO.add(obj);
  }
  public void addMealRequest(MealRequestData obj) {
    this.mealDAO.add(obj);
  }
  public void addConfRoomRequest(ConferenceRequestData obj) {
    this.conferenceDAO.add(obj);
  }
  public void addFlowerRequest(FlowerRequestData obj) {
    this.flowerDAO.add(obj);
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
}
