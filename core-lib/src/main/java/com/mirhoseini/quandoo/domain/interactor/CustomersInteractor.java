package com.mirhoseini.quandoo.domain.interactor;

import com.mirhoseini.quandoo.domain.model.CustomerResponse;

import rx.Observable;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface CustomersInteractor {

    Observable<CustomerResponse[]> loadCustomers();

    void onDestroy();

}
