package com.mirhoseini.quandoo.view;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface DataLoaderBaseView extends BaseView {

    void showRetryMessage(Throwable throwable);

    void showError(Throwable throwable);

    void showProgress();

    void hideProgress();

}
