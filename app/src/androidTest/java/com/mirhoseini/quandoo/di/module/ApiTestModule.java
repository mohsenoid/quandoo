package com.mirhoseini.quandoo.di.module;

import com.mirhoseini.quandoo.domain.client.QuandooApi;

import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class ApiTestModule extends ApiModule {

    @Override
    public QuandooApi provideQuandooApiService(Retrofit retrofit) {
        return mock(QuandooApi.class);
    }

}
