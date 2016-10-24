package com.mirhoseini.quandoo.view;


import com.mirhoseini.quandoo.database.model.BookingModel;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.database.model.TableModel;

import java.util.ArrayList;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface TableView extends DataLoaderBaseView {

    void setTablesData(ArrayList<TableModel> tables, ArrayList<BookingModel> bookings);

    void showBookingDialog(TableModel table, CustomerModel customer);

    void finishBooking();

}
