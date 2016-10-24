package com.mirhoseini.quandoo.domain.client;


import com.mirhoseini.quandoo.domain.model.CustomerResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface QuandooApi {

    // https://s3-eu-west-1.amazonaws.com/quandoo-assessment/customer-list.json
    @GET("customer-list.json")
    Observable<CustomerResponse[]> getCustomers();

    // https://s3-eu-west-1.amazonaws.com/quandoo-assessment/table-map.json
    @GET("table-map.json")
    Observable<boolean[]> getTables();

}
