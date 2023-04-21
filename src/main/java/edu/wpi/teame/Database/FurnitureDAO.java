package edu.wpi.teame.Database;

import static java.lang.Integer.parseInt;

import edu.wpi.teame.entities.FurnitureRequestData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FurnitureDAO<E> extends DAO<FurnitureRequestData> {
  List<FurnitureRequestData> furnitureRequestDataList;

  public FurnitureDAO(Connection c) {
    activeConnection = c;
    table = "\"FurnitureService\"";
  }

  @Override
  List<FurnitureRequestData> get() {
    furnitureRequestDataList = new LinkedList<>();

    try {
      Statement stmt = activeConnection.createStatement();

      String sql = "SELECT * FROM \"FurnitureService\";";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        furnitureRequestDataList.add(
            new FurnitureRequestData(
                rs.getInt("requestID"),
                rs.getString("name"),
                rs.getString("room"),
                rs.getString("deliveryDate"),
                rs.getString("deliveryTime"),
                rs.getString("staff"),
                rs.getString("furnitureType"),
                rs.getString("notes"),
                FurnitureRequestData.Status.stringToStatus(rs.getString("status"))));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return furnitureRequestDataList;
  }

  @Override
  void update(FurnitureRequestData obj, String attribute, String value) {
    int requestID = obj.getRequestID();

    String sqlUpdate =
        "UPDATE \"FurnitureService\" "
            + "SET \""
            + attribute
            + "\" = '"
            + value
            + "' WHERE \"requestID\" = '"
            + requestID
            + "';";

    try {
      Statement stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlUpdate);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(
          "Exception: Set a valid column name for attribute, quantity is an integer");
    }
  }

  @Override
  void delete(FurnitureRequestData obj) {
    int requestID = obj.getRequestID();
    String sqlDelete =
        "DELETE FROM \"FurnitureService\" WHERE \"requestID\" = '" + requestID + "';";

    Statement stmt;
    try {
      stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlDelete);
      stmt.close();
    } catch (SQLException e) {
      System.out.println("error deleting");
    }
  }

  @Override
  void add(FurnitureRequestData obj) {
    obj.setRequestID(generateUniqueRequestID());
    int requestID = obj.getRequestID();
    String name = obj.getName();
    String room = obj.getRoom();
    String deliveryDate = obj.getDeliveryDate();
    FurnitureRequestData.Status requestStatus = obj.getRequestStatus();
    String deliveryTime = obj.getDeliveryTime();
    String furnitureType = obj.getFurnitureType();
    String notes = obj.getNotes();
    String staff = obj.getAssignedStaff();

    String sqlAdd =
        "INSERT INTO \"FurnitureService\" VALUES("
            + requestID
            + ",'"
            + name
            + "','"
            + room
            + "','"
            + deliveryDate
            + "','"
            + deliveryTime
            + "','"
            + staff
            + "','"
            + furnitureType
            + "','"
            + notes
            + "','"
            + requestStatus
            + "');";

    Statement stmt;
    try {
      stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlAdd);
    } catch (SQLException e) {
      System.out.println("error adding");
    }
  }

  private int generateUniqueRequestID() {
    int requestID = 0;
    try {
      Statement stmt = activeConnection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT MAX(\"requestID\") FROM \"FurnitureService\"");
      if (rs.next()) {
        requestID = rs.getInt(1);
      }
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return requestID + 1;
  }

  @Override
  void importFromCSV(String filePath, String tableName) {
    try {
      BufferedReader ireader = new BufferedReader(new FileReader(filePath));
      String line;
      List<String> rows = new ArrayList<>();
      while ((line = ireader.readLine()) != null) {
        rows.add(line);
      }
      rows.remove(0);
      ireader.close();
      Statement stmt = activeConnection.createStatement();

      String sqlDelete = "DELETE FROM \"" + tableName + "\";";
      stmt.execute(sqlDelete);

      for (String l1 : rows) {
        String[] splitL1 = l1.split(",");
        String sql =
            "INSERT INTO "
                + "\""
                + tableName
                + "\""
                + " VALUES ("
                + parseInt(splitL1[0])
                + ",'"
                + splitL1[1]
                + "','"
                + splitL1[2]
                + "','"
                + splitL1[3]
                + "','"
                + splitL1[4]
                + "','"
                + splitL1[5]
                + "','"
                + splitL1[6]
                + "','"
                + splitL1[7]
                + "','"
                + splitL1[8]
                + "'); ";
        stmt.execute(sql);
      }

      System.out.println(
          "Imported " + (rows.size()) + " rows from " + filePath + " to " + tableName);

    } catch (IOException | SQLException e) {
      System.err.println("Error importing from " + filePath + " to " + tableName);
      e.printStackTrace();
    }
  }
}
