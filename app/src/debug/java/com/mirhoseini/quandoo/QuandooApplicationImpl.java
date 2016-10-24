package com.mirhoseini.quandoo;

import timber.log.Timber;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class QuandooApplicationImpl extends QuandooApplication {

    @Override
    public void initApplication() {

        // initialize Timber in debug version to log
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                //adding line number to logs
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

    }
}
