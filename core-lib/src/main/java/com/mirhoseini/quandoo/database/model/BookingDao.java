package com.mirhoseini.quandoo.database.model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class BookingDao extends BaseDaoImpl<BookingModel, Integer> {

    public BookingDao(Class<BookingModel> dataClass) throws SQLException {
        super(dataClass);
    }

    public BookingDao(ConnectionSource connectionSource, Class<BookingModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public BookingDao(ConnectionSource connectionSource, DatabaseTableConfig<BookingModel> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<BookingModel> getBookings() throws SQLException {
        return queryForAll();
    }

}
