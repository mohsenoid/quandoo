package com.mirhoseini.quandoo.Presentation;

import com.mirhoseini.quandoo.database.DatabaseHelper;
import com.mirhoseini.quandoo.database.mapper.Mapper;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.domain.interactor.CustomersInteractor;
import com.mirhoseini.quandoo.view.CustomerView;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class CustomerPresenterImpl implements CustomerPresenter {

    @Inject
    CustomersInteractor interactor;
    @Inject
    DatabaseHelper databaseHelper;

    private CustomerView view;
    private Subscription subscription = Subscriptions.empty();
    private ArrayList<CustomerModel> customers;

    @Inject
    public CustomerPresenterImpl() {
    }

    @Override
    public void setView(CustomerView view) {
        this.view = view;
    }

    @Override
    public void loadCustomersData(boolean isConnected) {
        if (null != view) {
            view.showProgress();
        }

        subscription = interactor.loadCustomers()
                // map API model to Database model
                .map(Mapper::mapCustomerResponsesToCustomers)
                // update Database
                .doOnNext(customers -> {
                    try {
                        // cache data on database
                        databaseHelper.addCustomers(customers);
                    } catch (SQLException e) {
                        if (null != view) {
                            view.hideProgress();
                            view.showError(e);
                        }
                    }
                })
                .subscribe(customers -> {
                            this.customers = customers;

                            if (null != view) {
                                view.hideProgress();
                                view.setCustomersData(customers);

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
    public void doSearch(String query) {
        if (null != view) {
            try {
                view.setCustomersData(new ArrayList<>(databaseHelper.searchCustomers(query)));
            } catch (SQLException e) {
                view.showError(e);
            }
        }
    }

    @Override
    public void clearFilter() {
        if (null != view)
            view.setCustomersData(customers);
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
