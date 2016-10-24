package com.mirhoseini.quandoo.Presentation;

import com.mirhoseini.quandoo.database.DatabaseHelper;
import com.mirhoseini.quandoo.database.mapper.Mapper;
import com.mirhoseini.quandoo.database.model.BookingModel;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.database.model.TableModel;
import com.mirhoseini.quandoo.domain.interactor.TablesInteractor;
import com.mirhoseini.quandoo.view.TableView;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class TablePresenterImpl implements TablePresenter {

    @Inject
    TablesInteractor interactor;
    @Inject
    DatabaseHelper databaseHelper;

    private TableView view;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public TablePresenterImpl() {
    }

    @Override
    public void setView(TableView view) {
        this.view = view;
    }


    @Override
    public void loadTablesData(boolean isConnected) {
        if (null != view) {
            view.showProgress();
        }

        subscription = interactor.loadTables()
                // map API model to Database model
                .map(Mapper::mapTableResponsesToTable)
                // update Database
                .doOnNext(tables -> {
                    try {
                        // cache data on database
                        databaseHelper.addTables(tables);
                    } catch (SQLException e) {
                        if (null != view) {
                            view.hideProgress();
                            view.showError(e);
                        }
                    }
                })
                .subscribe(tables -> {
                            ArrayList<BookingModel> bookings = null;
                            try {
                                // load booking data from database
                                bookings = new ArrayList<>(databaseHelper.selectAllBookings());
                            } catch (SQLException e) {
                                if (null != view) {
                                    view.hideProgress();
                                    view.showError(e);
                                }

                                return;
                            }

                            if (null != view) {
                                view.hideProgress();
                                view.setTablesData(tables, bookings);

                                if (!isConnected)
                                    view.showOfflineMessage();
                            }
                        },
                        // handle exceptions
                        throwable -> {
                            if (null != view) {
                                view.hideProgress();
                            }

                            if (isConnected) {
                                if (null != view) {
                                    view.showRetryMessage(null);
                                }
                            } else {
                                if (null != view) {
                                    view.showNoInternetMessage();
                                }
                            }
                        });
    }

    @Override
    public void bookTable(TableModel table, CustomerModel customer) {
        if (null != view) {
            view.showBookingDialog(table, customer);
        }
    }

    @Override
    public void doBooking(TableModel table, CustomerModel customer) {
        if (null != view) {
            try {
                databaseHelper.addBooking(table, customer);
            } catch (SQLException e) {
                view.showError(e);
            }

            view.finishBooking();
        }
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();

        interactor.onDestroy();

        view = null;
        interactor = null;
    }

}
