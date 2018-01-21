package com.example.longyuan.servicetest.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.longyuan.servicetest.service.intentservice.IntentTestService;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LONGYUAN on 2018/1/20.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String string = bundle.getString(IntentTestService.FILEPATH);
            int resultCode = bundle.getInt(IntentTestService.RESULT);
            if (resultCode == RESULT_OK) {
                Toast.makeText(context,
                        "Download complete. Download URI: " + string,
                        Toast.LENGTH_LONG).show();
                //textView.setText("Download done");
            } else {
                Toast.makeText(context, "Download failed",
                        Toast.LENGTH_LONG).show();
               // textView.setText("Download failed");
            }
        }
    }
}
