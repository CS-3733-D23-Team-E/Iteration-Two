package edu.wpi.teame.Database;

import static java.lang.Integer.parseInt;

import edu.wpi.teame.entities.MealRequestData;
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

public class MealDAO<E> extends DAO<MealRequestData> {
  List<MealRequestData> mealRequestDataList;

  public MealDAO(Connection c) {
    activeConnection = c;
    table = "\"MealService\"";
  }

  @Override
  List<MealRequestData> get() {
    mealRequestDataList = new LinkedList<>();

    try {
      Statement stmt = activeConnection.createStatement();

      String sql = "SELECT * FROM \"MealService\";";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        MealRequestData data =
            new MealRequestData(
                rs.getInt("requestID"),
                rs.getString("name"),
                rs.getString("room"),
                rs.getString("deliveryDate"),
                rs.getString("deliveryTime"),
                rs.getString("staff"),
                rs.getString("mainCourse"),
                rs.getString("sideCourse"),
                rs.getString("drink"),
                rs.getString("allergies"),
                rs.getString("notes"),
                MealRequestData.Status.stringToStatus(rs.getString("status")));
        mealRequestDataList.add(data);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return mealRequestDataList;
  }

  @Override
  void update(MealRequestData obj, String attribute, String value) {
    int requestID = obj.getRequestID();

    String sqlUpdate =
        "UPDATE \"MealService\" "
            + "SET \""
            + attribute
            + "\" = '"
            + value
            + "' WHERE \"MealService\".\"requestID\" = '"
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
  void delete(MealRequestData obj) {
    int requestID = obj.getRequestID();
    String sqlDelete =
        "DELETE FROM \"MealService\" WHERE \"MealService\".\"requestID\" = " + requestID + ";";

    Statement stmt;
    try {
      stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlDelete);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  void add(MealRequestData obj) {
    obj.setRequestID(generateUniqueRequestID());
    int requestID = obj.getRequestID();
    String name = obj.getName();
    String room = obj.getRoom();
    String deliveryDate = obj.getDeliveryDate();
    MealRequestData.Status requestStatus = obj.getRequestStatus();
    String deliveryTime = obj.getDeliveryTime();
    String mainCourse = obj.getMainCourse();
    String sideCourse = obj.getSideCourse();
    String drink = obj.getDrink();
    String allergies = obj.getAllergies();
    String notes = obj.getNotes();
    String staff = obj.getAssignedStaff();

    String sqlAdd =
        "INSERT INTO \"MealService\" VALUES("
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
            + mainCourse
            + "','"
            + sideCourse
            + "','"
            + drink
            + "','"
            + allergies
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
      ResultSet rs = stmt.executeQuery("SELECT MAX(\"requestID\") FROM \"MealService\"");
      if (rs.next() && rs.getInt(1) > 0) {
        requestID = rs.getInt(1);
      }
      stmt.close();
      rs.close();
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
                + "','"
                + splitL1[9]
                + "','"
                + splitL1[10]
                + "','"
                + splitL1[11]
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
