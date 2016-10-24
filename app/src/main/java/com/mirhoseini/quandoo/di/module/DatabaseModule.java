package com.mirhoseini.quandoo.di.module;

import com.mirhoseini.quandoo.database.DatabaseHelper;
import com.mirhoseini.quandoo.database.DatabaseHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 24/10/2016.
 */

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelperService(DatabaseHelperImpl databaseHelper) {
        return databaseHelper;
    }

}
