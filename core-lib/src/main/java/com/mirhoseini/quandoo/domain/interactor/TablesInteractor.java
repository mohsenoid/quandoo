package com.mirhoseini.quandoo.domain.interactor;

import rx.Observable;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface TablesInteractor {

    Observable<boolean[]> loadTables();

    void onDestroy();

}
