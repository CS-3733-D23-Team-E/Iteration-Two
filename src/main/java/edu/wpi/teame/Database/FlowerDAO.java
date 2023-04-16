package edu.wpi.teame.Database;

import edu.wpi.teame.entities.FlowerRequestData;

import java.sql.Connection;
import java.util.List;

public class FlowerDAO<E> extends DAO<FlowerRequestData>{
    List<FlowerRequestData> flowerRequestDataList;

    public FlowerDAO(Connection c){
        activeConnection = c;
        table = "\"OfficeSupplies\"";
    }

    @Override
    List<FlowerRequestData> get() {
        return null;
    }

    @Override
    void update(FlowerRequestData obj, String attribute, String value) {

    }

    @Override
    void delete(FlowerRequestData obj) {

    }

    @Override
    void add(FlowerRequestData obj) {

    }

    @Override
    void importFromCSV(String filePath, String tableName) {

    }
}
