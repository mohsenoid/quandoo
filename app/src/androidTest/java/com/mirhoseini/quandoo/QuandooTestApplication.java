package com.mirhoseini.quandoo;

import com.mirhoseini.quandoo.di.component.ApplicationTestComponent;
import com.mirhoseini.quandoo.di.component.DaggerApplicationTestComponent;
import com.mirhoseini.quandoo.di.module.AndroidModule;
import com.mirhoseini.quandoo.di.module.ApiTestModule;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class QuandooTestApplication extends QuandooApplicationImpl {

    @Override
    public ApplicationTestComponent createComponent() {
        return DaggerApplicationTestComponent
                .builder()
                .androidModule(new AndroidModule(this))
                // replace Api Module with Mocked one
                .apiModule(new ApiTestModule())
                .build();
    }

}
