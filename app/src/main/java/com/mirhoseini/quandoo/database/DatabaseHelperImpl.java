package com.mirhoseini.quandoo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.BookingModel;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.database.model.TableModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class DatabaseHelperImpl extends OrmLiteSqliteOpenHelper implements DatabaseHelper {

    private static final String DATABASE_NAME = "quandoo.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<CustomerModel, Integer> customerDao;
    private Dao<TableModel, Integer> tableDao;
    private Dao<BookingModel, Integer> bookingDao;

    @Inject
    public DatabaseHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, CustomerModel.class);
            TableUtils.createTable(connectionSource, TableModel.class);
            TableUtils.createTable(connectionSource, BookingModel.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, BookingModel.class, true);
            TableUtils.dropTable(connectionSource, TableModel.class, true);
            TableUtils.dropTable(connectionSource, CustomerModel.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public Dao<CustomerModel, Integer> getCustomerDao() throws SQLException {
        if (customerDao == null) {
            customerDao = getDao(CustomerModel.class);
        }
        return customerDao;
    }

    public Dao<TableModel, Integer> getTableDao() throws SQLException {
        if (tableDao == null) {
            tableDao = getDao(TableModel.class);
        }
        return tableDao;
    }

    public Dao<BookingModel, Integer> getBookingDao() throws SQLException {
        if (bookingDao == null) {
            bookingDao = getDao(BookingModel.class);
        }
        return bookingDao;
    }

    @Override
    public int addBooking(TableModel table, CustomerModel customer) throws SQLException {
        return getBookingDao().create(new BookingModel(table, customer));
    }

    @Override
    public int clearAllBookings() throws SQLException {
        return TableUtils.clearTable(connectionSource, BookingModel.class);
    }

    @Override
    public List<CustomerModel> searchCustomers(String query) throws SQLException {
        return getCustomerDao().query(getCustomerDao().queryBuilder()
                .where()
                .like(CustomerModel.FIELD_CUSTOMER_FIRST_NAME, "%" + query + "%")
                .or()
                .like(CustomerModel.FIELD_CUSTOMER_LAST_NAME, "%" + query + "%")
                .prepare());
    }

    @Override
    public List<CustomerModel> selectAllCustomers() throws SQLException {
        return getCustomerDao().queryForAll();
    }

    @Override
    public List<TableModel> selectAllTables() throws SQLException {
        return getTableDao().queryForAll();
    }

    @Override
    public List<BookingModel> selectAllBookings() throws SQLException {
        return getBookingDao().queryForAll();
    }

    @Override
    public void addCustomers(ArrayList<CustomerModel> customers) throws SQLException {
        for (CustomerModel customer : customers) {
            getCustomerDao().createOrUpdate(customer);
        }
    }

    @Override
    public void addTables(ArrayList<TableModel> tables) throws SQLException {
        for (TableModel table : tables) {
            getTableDao().createOrUpdate(table);
        }
    }
}
