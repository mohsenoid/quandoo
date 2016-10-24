package com.mirhoseini.quandoo.view.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mirhoseini.quandoo.QuandooApplication;
import com.mirhoseini.quandoo.di.component.ApplicationComponent;

/**
 * Created by Mohsen on 24/10/2016.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        injectDependencies(QuandooApplication.getComponent(), context);

        // can be used for general purpose in all Fragments of Application
    }

    protected abstract void injectDependencies(ApplicationComponent component, Context context);

}