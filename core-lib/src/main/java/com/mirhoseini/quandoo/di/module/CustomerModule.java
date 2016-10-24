package com.mirhoseini.quandoo.di.module;

import com.mirhoseini.quandoo.Presentation.CustomerPresenter;
import com.mirhoseini.quandoo.Presentation.CustomerPresenterImpl;
import com.mirhoseini.quandoo.di.scope.Customer;
import com.mirhoseini.quandoo.domain.interactor.CustomersInteractor;
import com.mirhoseini.quandoo.domain.interactor.CustomersInteractorImpl;
import com.mirhoseini.quandoo.view.CustomerView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Module
public class CustomerModule {

    private CustomerView view;

    public CustomerModule(CustomerView view) {
        this.view = view;
    }

    @Provides
    public CustomerView provideView() {
        return view;
    }

    @Provides
    @Customer
    public CustomersInteractor provideInteractor(CustomersInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @Customer
    public CustomerPresenter providePresenter(CustomerPresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }

}
