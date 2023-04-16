package edu.wpi.teame.Database;

import edu.wpi.teame.entities.OfficeSuppliesData;
import org.json.JSONObject;

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


public class OfficeSuppliesDAO<E> extends DAO<OfficeSuppliesData>{
    List<OfficeSuppliesData> officeSuppliesDataList;

    public OfficeSuppliesDAO(Connection c){
        activeConnection = c;
        table = "\"OfficeSupplies\"";
    }

    @Override
    List<OfficeSuppliesData> get(){
        officeSuppliesDataList = new LinkedList<>();

        try {
            Statement stmt = activeConnection.createStatement();

            String sql = "SELECT * FROM \"OfficeSupplies\";";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                officeSuppliesDataList.add(new OfficeSuppliesData(rs.getString("name"), rs.getString("room"), OfficeSuppliesData.Status.stringToStatus(rs.getString("status")), rs.getString("deliverytime"), rs.getString("officesupply"), rs.getInt("quantity"), rs.getString("staff") ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return officeSuppliesDataList;
    }

    @Override
    void update(OfficeSuppliesData obj, String attribute, String value){
        String name = obj.getName();
        String staff = obj.getAssignedStaff();

        String sqlUpdate =
                "UPDATE \"OfficeSupplies\" "
                        + "SET \""
                        + attribute
                        + "\" = '"
                        + value
                        + "' WHERE \"name\" = '"
                        + name
                        + "' AND \"staff\" = '"
                        + staff
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
    void delete(OfficeSuppliesData obj) {
        String name = obj.getName();
        String staff = obj.getAssignedStaff();
        String sqlDelete =
                "DELETE FROM \"OfficeSupplies\" WHERE \"name\" = '"
                        + name
                        + "' AND \"staff\" = '"
                        + staff
                        + "';";

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
    void add(OfficeSuppliesData obj) {
        String name = obj.getName();
        String room = obj.getRoom();
        int quantity = obj.getQuantity();
        OfficeSuppliesData.Status requestStatus = obj.getRequestStatus();
        String deliveryTime = obj.getDeliveryTime();
        String officeSupply = obj.getOfficeSupply();
        String staff = obj.getAssignedStaff();

        String sqlAdd =
                "INSERT INTO \"OfficeSupplies\" VALUES('" + name + "','" + room + "'," + quantity + ",'"+ requestStatus + "','"+ deliveryTime + "','" + officeSupply + "','" + staff +"');";

        Statement stmt;
        try {
            stmt = activeConnection.createStatement();
            stmt.executeUpdate(sqlAdd);
        } catch (SQLException e) {
            System.out.println("error adding");
        }

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
                                + " VALUES ('"
                                + splitL1[0]
                                + "','"
                                + splitL1[1]
                                + "',"
                                + splitL1[2]
                                +  ",'"
                                + splitL1[3]
                                + "','"
                                + splitL1[4]
                                + "','"
                                + splitL1[5]
                                + "','"
                                + splitL1[6]
                                + "','"
                                +splitL1[7]
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
