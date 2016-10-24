package com.mirhoseini.quandoo.Presentation;

/**
 * Created by Mohsen on 24/10/2016.
 */

public interface BasePresenter<T> {

    void setView(T view);

    void destroy();

}
