package com.mirhoseini.quandoo.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class CustomerResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("customerFirstName")
    private String firstName;
    @SerializedName("customerLastName")
    private String lastName;

    public CustomerResponse() {
    }

    public CustomerResponse(int id, String firstName, String lastName) {
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

}
