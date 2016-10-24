package com.mirhoseini.quandoo.di.component;

import com.mirhoseini.quandoo.di.module.AppCustomerModule;
import com.mirhoseini.quandoo.di.scope.Customer;
import com.mirhoseini.quandoo.view.fragment.CustomersFragment;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Customer
@Subcomponent(modules = {
        AppCustomerModule.class
})
public interface CustomerSubComponent {

    void inject(CustomersFragment fragment);

}
