package edu.wpi.teame.Database;

import edu.wpi.teame.entities.ConferenceRequestData;


import java.sql.Connection;
import java.util.List;

public class ConferenceRoomDAO<E> extends DAO<ConferenceRequestData>{
    List<ConferenceRequestData> officeSuppliesDataList;

    public ConferenceRoomDAO(Connection c) {
        activeConnection = c;
        table = "\"OfficeSupplies\"";
    }

    @Override
    List<ConferenceRequestData> get() {
        return null;
    }

    @Override
    void update(ConferenceRequestData obj, String attribute, String value) {

    }

    @Override
    void delete(ConferenceRequestData obj) {

    }

    @Override
    void add(ConferenceRequestData obj) {

    }

    @Override
    void importFromCSV(String filePath, String tableName) {

    }
}
