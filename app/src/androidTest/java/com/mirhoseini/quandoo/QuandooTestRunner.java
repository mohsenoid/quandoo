package com.mirhoseini.quandoo;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class QuandooTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, QuandooTestApplication.class.getName(), context);
    }
}
