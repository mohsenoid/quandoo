package com.mirhoseini.quandoo.di.component;

import com.mirhoseini.quandoo.di.module.AndroidModule;
import com.mirhoseini.quandoo.di.module.ApiModule;
import com.mirhoseini.quandoo.di.module.AppCustomerModule;
import com.mirhoseini.quandoo.di.module.AppTableModule;
import com.mirhoseini.quandoo.di.module.ApplicationModule;
import com.mirhoseini.quandoo.di.module.ClientModule;
import com.mirhoseini.quandoo.di.module.DatabaseModule;
import com.mirhoseini.quandoo.view.activity.MainActivity;
import com.mirhoseini.quandoo.view.activity.TablesActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        DatabaseModule.class,
        ClientModule.class
})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    void inject(TablesActivity tablesActivity);

    TableSubComponent plus(AppTableModule module);

    CustomerSubComponent plus(AppCustomerModule module);

}