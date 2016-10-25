package com.mirhoseini.quandoo.domain.interactor;


import com.mirhoseini.quandoo.di.scope.Table;
import com.mirhoseini.quandoo.domain.client.QuandooApi;
import com.mirhoseini.quandoo.util.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Table
public class TablesInteractorImpl implements TablesInteractor {

    private final QuandooApi api;
    private final SchedulerProvider scheduler;

    private ReplaySubject<boolean[]> tablesSubject;
    private Subscription tablesSubscription;

    @Inject
    public TablesInteractorImpl(QuandooApi api, SchedulerProvider scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<boolean[]> loadTables() {
        if (tablesSubscription == null || tablesSubscription.isUnsubscribed()) {
            tablesSubject = ReplaySubject.create();

            tablesSubscription = api.getTables()
                    .subscribeOn(scheduler.backgroundThread())
                    .observeOn(scheduler.mainThread())
                    .subscribe(tablesSubject);
        }

        return tablesSubject.asObservable();
    }


    @Override
    public void onDestroy() {
        if (tablesSubscription != null && !tablesSubscription.isUnsubscribed())
            tablesSubscription.unsubscribe();
    }

}
