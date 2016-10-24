package com.mirhoseini.quandoo;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mirhoseini.quandoo.di.component.ApplicationComponent;
import com.mirhoseini.quandoo.di.component.DaggerApplicationComponent;
import com.mirhoseini.quandoo.di.module.AndroidModule;
import com.mirhoseini.quandoo.service.ClearBookingAlarmReceiver;
import com.mirhoseini.quandoo.util.AppConstants;

import java.util.Calendar;

/**
 * Created by Mohsen on 24/10/2016.
 */

public abstract class QuandooApplication extends Application {

    private static final int ALARM_REQUEST_CODE = 0;

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        component = createComponent();

        setupClearAlarm();
    }

    private void setupClearAlarm() {
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, ClearBookingAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + AppConstants.CLEAR_TIME_INTERVAL);

        // set new Alarm
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AppConstants.CLEAR_TIME_INTERVAL, pendingIntent);
    }

    public ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public abstract void initApplication();

}
