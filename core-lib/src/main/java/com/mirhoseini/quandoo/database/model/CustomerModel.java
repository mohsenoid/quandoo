package com.mirhoseini.quandoo.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Mohsen on 24/10/2016.
 */

@DatabaseTable(tableName = CustomerModel.TABLE_NAME_CUSTOMER, daoClass = CustomerDao.class)
public class CustomerModel implements Serializable {

    public static final String TABLE_NAME_CUSTOMER = "customer";
    public static final String FIELD_CUSTOMER_ID = "_id";
    public static final String FIELD_CUSTOMER_FIRST_NAME = "first_name";
    public static final String FIELD_CUSTOMER_LAST_NAME = "last_name";


    @DatabaseField(id = true, columnName = FIELD_CUSTOMER_ID)
    private int id;
    @DatabaseField(columnName = FIELD_CUSTOMER_FIRST_NAME)
    private String firstName;
    @DatabaseField(columnName = FIELD_CUSTOMER_LAST_NAME)
    private String lastName;

    public CustomerModel() {
    }

    public CustomerModel(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}
