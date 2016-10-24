package com.mirhoseini.quandoo.database.model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class CustomerDao extends BaseDaoImpl<CustomerModel, Integer> {

    public CustomerDao(Class<CustomerModel> dataClass) throws SQLException {
        super(dataClass);
    }

    public CustomerDao(ConnectionSource connectionSource, Class<CustomerModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CustomerDao(ConnectionSource connectionSource, DatabaseTableConfig<CustomerModel> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<CustomerModel> getCustomers() throws SQLException {
        return queryForAll();
    }

}
