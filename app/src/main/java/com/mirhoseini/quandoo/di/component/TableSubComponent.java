package com.mirhoseini.quandoo.di.component;

import com.mirhoseini.quandoo.di.module.AppTableModule;
import com.mirhoseini.quandoo.di.scope.Table;
import com.mirhoseini.quandoo.view.fragment.TablesFragment;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Table
@Subcomponent(modules = {
        AppTableModule.class
})
public interface TableSubComponent {

    void inject(TablesFragment fragment);

}
