package com.mirhoseini.quandoo.database.model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class TableDao extends BaseDaoImpl<TableModel, Integer> {

    public TableDao(Class<TableModel> dataClass) throws SQLException {
        super(dataClass);
    }

    public TableDao(ConnectionSource connectionSource, Class<TableModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public TableDao(ConnectionSource connectionSource, DatabaseTableConfig<TableModel> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<TableModel> getTables() throws SQLException {
        return queryForAll();
    }

    public List<TableModel> getAvailableTables() throws SQLException {
        return queryForEq(TableModel.FIELD_TABLE_RESERVED, false);
    }

}
