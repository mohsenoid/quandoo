package com.mirhoseini.quandoo.Presentation;

import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.database.model.TableModel;
import com.mirhoseini.quandoo.view.TableView;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface TablePresenter extends BasePresenter<TableView> {

    void loadTablesData(boolean isConnected);

    void bookTable(TableModel table, CustomerModel customer);

    void doBooking(TableModel table, CustomerModel customer);

}
