package com.thimble.customer.base;

import android.app.Application;

/**
 * Created by pasari on 25/11/18.
 */

public class AppClass extends Application {


    private static AppClass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppClass getInstance() {
        return instance;
    }



}
