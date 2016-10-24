package com.mirhoseini.quandoo.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mohsen on 24/10/2016.
 */

@DatabaseTable(tableName = BookingModel.TABLE_NAME_BOOKING, daoClass = BookingDao.class)
public class BookingModel {

    public static final String TABLE_NAME_BOOKING = "booking";
    public static final String FIELD_BOOKING_TABLE = "table";
    public static final String FIELD_BOOKING_CUSTOMER = "customer";


    @DatabaseField(columnName = FIELD_BOOKING_TABLE, foreign = true)
    private TableModel table;
    @DatabaseField(columnName = FIELD_BOOKING_CUSTOMER, foreign = true)
    private CustomerModel customer;

    public BookingModel() {
    }

    public BookingModel(TableModel table, CustomerModel customer) {
        this.table = table;
        this.customer = customer;
    }

    public TableModel getTable() {
        return table;
    }

    public void setTable(TableModel table) {
        this.table = table;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

}
