package com.example.longyuan.servicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by loxu on 19/01/2018.
 */

public class HelloService extends Service {

    @Override
    public void onCreate() {
        // Only once
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Can be called many times
        Toast.makeText(this, "service starting + " + startId, Toast.LENGTH_SHORT).show();

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service Destroy", Toast.LENGTH_SHORT).show();
    }
}