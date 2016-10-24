package com.mirhoseini.quandoo.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mohsen on 24/10/2016.
 */

@DatabaseTable(tableName = TableModel.TABLE_NAME_TABLE, daoClass = TableDao.class)
public class TableModel {

    public static final String TABLE_NAME_TABLE = "table";
    public static final String FIELD_TABLE_ID = "_id";
    public static final String FIELD_TABLE_NUMBER = "number";
    public static final String FIELD_TABLE_RESERVED = "is_reserved";


    @DatabaseField(id = true, columnName = FIELD_TABLE_ID)
    private int id;
    @DatabaseField(columnName = FIELD_TABLE_NUMBER)
    private int number;
    @DatabaseField(columnName = FIELD_TABLE_RESERVED)
    private boolean isReserved;

    public TableModel() {
    }

    public TableModel(int id, int number, boolean isReserved) {
        this.id = id;
        this.number = number;
        this.isReserved = isReserved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

}
