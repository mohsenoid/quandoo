package com.mirhoseini.quandoo.Presentation;


import com.mirhoseini.quandoo.view.CustomerView;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface CustomerPresenter extends BasePresenter<CustomerView> {

    void loadCustomersData(boolean isConnected);

    void doSearch(String query);

    void clearFilter();

}
