package com.mirhoseini.quandoo.database;

import com.mirhoseini.quandoo.database.model.BookingModel;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.database.model.TableModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface DatabaseHelper {

    int addBooking(TableModel table, CustomerModel customer) throws SQLException;

    int clearAllBookings() throws SQLException;

    List<CustomerModel> searchCustomers(String query) throws SQLException;

    List<CustomerModel> selectAllCustomers() throws SQLException;

    List<TableModel> selectAllTables() throws SQLException;

    List<BookingModel> selectAllBookings() throws SQLException;

    void addCustomers(ArrayList<CustomerModel> customers) throws SQLException;

    void addTables(ArrayList<TableModel> tables) throws SQLException;

}
