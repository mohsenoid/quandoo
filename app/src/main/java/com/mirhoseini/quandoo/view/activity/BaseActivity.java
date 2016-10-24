package com.mirhoseini.quandoo.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mirhoseini.quandoo.QuandooApplication;
import com.mirhoseini.quandoo.di.component.ApplicationComponent;

/**
 * Created by Mohsen on 24/10/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies(QuandooApplication.getComponent());

        // can be used for general purpose in all Activities of Application
    }

    protected abstract void injectDependencies(ApplicationComponent component);

}
