package com.pos.bringit;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.pos.bringit.utils.SharedPrefs;

public class POSApplication extends Application {

    private static POSApplication sInstance;
    public ConnectivityManager mConnectivityManager;


    public static POSApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        SharedPrefs.loadPrefs(this);

        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public boolean isNetworkAvailable() {
        return mConnectivityManager.getActiveNetworkInfo() != null
                && mConnectivityManager.getActiveNetworkInfo().isAvailable()
                && mConnectivityManager.getActiveNetworkInfo().isConnected();
    }
}
