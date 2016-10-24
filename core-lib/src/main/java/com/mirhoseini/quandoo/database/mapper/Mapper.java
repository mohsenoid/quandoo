package com.mirhoseini.quandoo.database.mapper;

import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.database.model.TableModel;
import com.mirhoseini.quandoo.domain.model.CustomerResponse;

import java.util.ArrayList;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class Mapper {

    public static ArrayList<CustomerModel> mapCustomerResponsesToCustomers(CustomerResponse[] customerResponses) {
        ArrayList<CustomerModel> customers = new ArrayList<>();

        for (CustomerResponse customerResponse : customerResponses) {
            CustomerModel customer = new CustomerModel();

            customer.setId(customerResponse.getId());
            customer.setFirstName(customerResponse.getFirstName());
            customer.setLastName(customerResponse.getLastName());

            customers.add(customer);
        }

        return customers;
    }

    public static ArrayList<TableModel> mapTableResponsesToTable(boolean[] tableResponses) {
        ArrayList<TableModel> tables = new ArrayList<>();

        for (int i = 0; i < tableResponses.length; i++) {
            TableModel table = new TableModel();

            table.setId(i);
            table.setNumber(i + 1);
            table.setReserved(!tableResponses[i]);

            tables.add(table);
        }

        return tables;
    }

}
