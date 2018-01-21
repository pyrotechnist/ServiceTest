package com.example.longyuan.servicetest.service.intentservice;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by LONGYUAN on 2018/1/18.
 */

public class IntentTestService extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static final String URL1 = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.vogella.android.service.receiver";

    // post task to ui thread
    private Handler mHandler;

    public IntentTestService() {
        super("IntentTestService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {
        String urlPath = intent.getStringExtra(URL1);
        String fileName = intent.getStringExtra(FILENAME);

        if(urlPath != null && urlPath!= "")
        {
            download(urlPath,fileName);
        }else {

            displayToast();
        }

    }

    private void displayToast() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(IntentTestService.this, "Hello Toast from intent service!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onDestroy() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(IntentTestService.this, "intent service stop!", Toast.LENGTH_LONG).show();
            }
        });
        super.onDestroy();

    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }


    private void download(String urlPath,String fileName){

        File output = new File(Environment.getExternalStorageDirectory(),
                fileName);
        if (output.exists()) {
            output.delete();
        }

        InputStream stream = null;
        FileOutputStream fos = null;
        try {

            URL url = new URL(urlPath);
            stream = url.openConnection().getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            fos = new FileOutputStream(output.getPath());
            int next = -1;
            while ((next = reader.read()) != -1) {
                fos.write(next);
            }
            // successfully finished
            result = Activity.RESULT_OK;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        publishResults(output.getAbsolutePath(), result);
    }
}