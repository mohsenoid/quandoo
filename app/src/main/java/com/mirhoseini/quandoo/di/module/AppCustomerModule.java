package com.mirhoseini.quandoo.di.module;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.mirhoseini.quandoo.di.scope.Customer;
import com.mirhoseini.quandoo.util.AppConstants;
import com.mirhoseini.quandoo.util.GridSpacingItemDecoration;
import com.mirhoseini.quandoo.view.fragment.CustomersFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Module
public class AppCustomerModule extends CustomerModule {

    private final Context context;
    private final CustomersFragment.OnListFragmentInteractionListener listener;

    public AppCustomerModule(Context context, CustomersFragment fragment) {
        super(fragment);

        this.context = context;

        if (context instanceof CustomersFragment.OnListFragmentInteractionListener) {
            listener = (CustomersFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Provides
    @Customer
    public CustomersFragment.OnListFragmentInteractionListener provideOnListFragmentInteractionListener() {
        return listener;
    }

    @Provides
    @Customer
    public LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(context);
    }

    @Provides
    @Customer
    public GridSpacingItemDecoration provideGridSpacingItemDecoration() {
        return new GridSpacingItemDecoration(1, AppConstants.RECYCLER_VIEW_ITEM_SPACE, true, 0);
    }

}
