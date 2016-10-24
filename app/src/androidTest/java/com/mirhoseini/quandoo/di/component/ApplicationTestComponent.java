package com.mirhoseini.quandoo.di.component;

import com.mirhoseini.quandoo.di.module.AndroidModule;
import com.mirhoseini.quandoo.di.module.ApiModule;
import com.mirhoseini.quandoo.di.module.ApplicationModule;
import com.mirhoseini.quandoo.di.module.ClientModule;
import com.mirhoseini.quandoo.di.module.DatabaseModule;
import com.mirhoseini.quandoo.view.activity.MainActivityTest;

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
public interface ApplicationTestComponent extends ApplicationComponent {

    void inject(MainActivityTest activity);

}