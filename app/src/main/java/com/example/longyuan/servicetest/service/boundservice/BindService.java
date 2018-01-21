package com.example.longyuan.servicetest.service.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by LONGYUAN on 2018/1/19.
 */

public class BindService extends Service {


    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();


    /** method for clients */
    public int getRandomNumber() {
        return 5;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {

        BindService getService() {
            // Return this instance of LocalService so clients can call public methods
            return BindService.this;
        }
    }
}
