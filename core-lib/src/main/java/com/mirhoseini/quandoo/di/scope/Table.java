package com.mirhoseini.quandoo.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
}
