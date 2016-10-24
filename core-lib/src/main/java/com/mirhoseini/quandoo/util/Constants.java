package com.mirhoseini.quandoo.util;

/**
 * Created by Mohsen on 24/10/2016.
 */

public abstract class Constants {

    public static final String BASE_URL = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/";

    public static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final int CACHE_MAX_AGE = 2; // 2 min
    public static final int CACHE_MAX_STALE = 7; // 7 day

}
