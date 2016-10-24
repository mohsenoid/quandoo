package com.mirhoseini.quandoo.view;

import com.mirhoseini.quandoo.database.model.CustomerModel;

import java.util.ArrayList;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface CustomerView extends DataLoaderBaseView {

    void setCustomersData(ArrayList<CustomerModel> customers);

}
