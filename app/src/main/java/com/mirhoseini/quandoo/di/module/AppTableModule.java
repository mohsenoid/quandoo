package com.mirhoseini.quandoo.di.module;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.mirhoseini.quandoo.di.scope.Table;
import com.mirhoseini.quandoo.util.AppConstants;
import com.mirhoseini.quandoo.util.GridSpacingItemDecoration;
import com.mirhoseini.quandoo.view.fragment.TablesFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Module
public class AppTableModule extends TableModule {

    private final Context context;
    private final TablesFragment.OnListFragmentInteractionListener listener;
    private final int columnCount;

    public AppTableModule(Context context, TablesFragment fragment, int columnCount) {
        super(fragment);

        this.context = context;
        this.columnCount = columnCount;

        if (context instanceof TablesFragment.OnListFragmentInteractionListener) {
            listener = (TablesFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Provides
    @Table
    public TablesFragment.OnListFragmentInteractionListener provideOnListFragmentInteractionListener() {
        return listener;
    }

    @Provides
    @Table
    public GridLayoutManager provideLayoutManager() {
        return new GridLayoutManager(context, columnCount);
    }

    @Provides
    @Table
    public GridSpacingItemDecoration provideGridSpacingItemDecoration() {
        return new GridSpacingItemDecoration(columnCount, AppConstants.RECYCLER_VIEW_ITEM_SPACE, true, 0);
    }

}
