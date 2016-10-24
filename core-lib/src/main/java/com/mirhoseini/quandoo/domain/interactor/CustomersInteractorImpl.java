package com.mirhoseini.quandoo.domain.interactor;


import com.mirhoseini.quandoo.di.scope.Customer;
import com.mirhoseini.quandoo.domain.client.QuandooApi;
import com.mirhoseini.quandoo.domain.model.CustomerResponse;
import com.mirhoseini.quandoo.util.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Customer
public class CustomersInteractorImpl implements CustomersInteractor {

    private QuandooApi api;
    private SchedulerProvider scheduler;

    private ReplaySubject<CustomerResponse[]> customersSubject;
    private Subscription customersSubscription;

    @Inject
    public CustomersInteractorImpl(QuandooApi api, SchedulerProvider scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<CustomerResponse[]> loadCustomers() {
        if (customersSubscription == null || customersSubscription.isUnsubscribed()) {
            customersSubject = ReplaySubject.create();

            customersSubscription = api.getCustomers()
                    .subscribeOn(scheduler.backgroundThread())
                    .observeOn(scheduler.mainThread())
                    .subscribe(customersSubject);
        }

        return customersSubject.asObservable();
    }


    @Override
    public void onDestroy() {
        if (customersSubscription != null && !customersSubscription.isUnsubscribed())
            customersSubscription.unsubscribe();
    }

}
