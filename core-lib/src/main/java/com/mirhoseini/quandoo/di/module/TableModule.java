package com.mirhoseini.quandoo.di.module;

import com.mirhoseini.quandoo.Presentation.TablePresenter;
import com.mirhoseini.quandoo.Presentation.TablePresenterImpl;
import com.mirhoseini.quandoo.di.scope.Table;
import com.mirhoseini.quandoo.domain.interactor.TablesInteractor;
import com.mirhoseini.quandoo.domain.interactor.TablesInteractorImpl;
import com.mirhoseini.quandoo.view.TableView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Module
public class TableModule {

    private TableView view;

    public TableModule(TableView view) {
        this.view = view;
    }

    @Provides
    public TableView provideView() {
        return view;
    }

    @Provides
    @Table
    public TablesInteractor provideInteractor(TablesInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @Table
    public TablePresenter providePresenter(TablePresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }

}
