package com.mirhoseini.quandoo.util;

import rx.Scheduler;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
