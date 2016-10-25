package com.mirhoseini.quandoo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mirhoseini.quandoo.database.DatabaseHelperImpl;

import java.sql.SQLException;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class ClearBookingAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "BookingAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Clear Booking Alarm is running...");

        try {
            new DatabaseHelperImpl(context).clearAllBookings();
            Log.d(TAG, "Clear Booking Done!");
        } catch (SQLException e) {
            Log.e(TAG, "Clear Booking Alarm Error!!!", e);
        }
    }

}