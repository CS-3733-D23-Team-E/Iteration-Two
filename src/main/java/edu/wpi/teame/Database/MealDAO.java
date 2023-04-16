package edu.wpi.teame.Database;

import edu.wpi.teame.entities.MealRequestData;
import edu.wpi.teame.entities.OfficeSuppliesData;
import edu.wpi.teame.map.MoveAttribute;

import java.sql.Connection;
import java.util.List;

public class MealDAO<E> extends DAO<MealRequestData>{
    List<MealRequestData> mealRequestDataList;

    public MealDAO(Connection c){
        activeConnection = c;
        table = "\"OfficeSupplies\"";
    }

    @Override
    List<MealRequestData> get() {
        return null;
    }

    @Override
    void update(MealRequestData obj, String attribute, String value) {

    }

    @Override
    void delete(MealRequestData obj) {

    }

    @Override
    void add(MealRequestData obj) {

    }

    @Override
    void importFromCSV(String filePath, String tableName) {

    }
}
