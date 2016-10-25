package com.mirhoseini.quandoo.domain.interactor;

import com.mirhoseini.quandoo.domain.client.QuandooApi;
import com.mirhoseini.quandoo.domain.model.CustomerResponse;
import com.mirhoseini.quandoo.util.SchedulerProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class CustomersInteractorTest {

    CustomersInteractor interactor;
    QuandooApi api;
    SchedulerProvider scheduler;
    CustomerResponse[] expectedResult;

    @Before
    public void setup() {
        api = mock(QuandooApi.class);
        scheduler = mock(SchedulerProvider.class);

        // Set up the stub we want to return in the mock
        expectedResult = new CustomerResponse[1];
        expectedResult[0] = new CustomerResponse(1, "Mohsen", "Mirhoseini Argi");

        // mock scheduler to run immediately
        when(scheduler.mainThread())
                .thenReturn(Schedulers.immediate());
        when(scheduler.backgroundThread())
                .thenReturn(Schedulers.immediate());

        // mock api result with expected result
        when(api.getCustomers())
                .thenReturn(Observable.just(expectedResult));

        // create a real new interactor using mocked api and scheduler
        interactor = new CustomersInteractorImpl(api, scheduler);
    }

    @Test
    public void testLoadCustomers() throws Exception {
        TestSubscriber<CustomerResponse[]> testSubscriber = new TestSubscriber<>();

        // call interactor with some random params
        interactor.loadCustomers()
                .subscribe(testSubscriber);

        // it must return the expectedResult with no error
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }
}
