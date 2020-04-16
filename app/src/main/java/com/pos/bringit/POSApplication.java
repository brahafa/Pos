package com.pos.bringit;

import android.app.Application;

import com.pos.bringit.utils.SharedPrefs;

public class POSApplication extends Application {

    private static POSApplication sInstance;

    public static POSApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefs.loadPrefs(this);
    }
}
