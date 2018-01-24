package com.example.longyuan.servicetest;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.longyuan.servicetest.service.boundservice.BindService;
import com.example.longyuan.servicetest.service.HelloService;
import com.example.longyuan.servicetest.service.intentservice.IntentTestService;
import com.example.longyuan.servicetest.service.boundservice.MessengerBindService;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Intent Service
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Intent Service

    // for receiving information from IntentTestService
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(IntentTestService.FILEPATH);
                int resultCode = bundle.getInt(IntentTestService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download done");
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download failed");
                }
            }
        }
    };




    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End Intent Service
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End Intent Service




    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Bound Service
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Bound Service

    BindService mService;
    boolean mBound = false;

    /** Messenger for communicating with the service. */
    Messenger mMessengerService = null;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance

         /*   // BindService
         BindService.LocalBinder binder = (BindService.LocalBinder) service;
            mService = binder.getService();*/

            // MessengerBindService
            mMessengerService = new Messenger(service);

            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Stop Bound Service
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Stop Bound Service



    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Common
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Common

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Common
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Common


    private TextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.status);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // for intent service
        registerReceiver(receiver, new IntentFilter(
                IntentTestService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        // for intent service
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(getApplicationContext(),TestActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_settings)
        {


        }

        return super.onOptionsItemSelected(item);
    }



    private boolean permissionCheck(){

        int permissionCheck = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck == PERMISSION_GRANTED){

           return true;
        }else {

            return false;
        }
    }





    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Intent Service
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Intent Service

    private void download(){

        Intent intent = new Intent(this, IntentTestService.class);
        intent.putExtra(IntentTestService.FILENAME, "index.html");
        intent.putExtra(IntentTestService.URL1,
                "http://www.vogella.com/index.html");

        startService(intent);

        textView.setText("Service started");
    }

    public void intentServiceTestDownload(View view) {

        if(permissionCheck()){
            download();
        }else {
            verifyStoragePermissions(this);
        }
    }

    public void intentServiceTestToast(View view) {


        Intent intent = new Intent(this, IntentTestService.class);

        startService(intent);

        textView.setText("Intent Service started");
    }



    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End Intent Service
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End Intent Service




    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Bound Service
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin Bound Service

    public void bindServiceMessager(View view){

        //Intent bindIntent = new Intent(this, BindService.class);

        Intent bindIntent = new Intent(this, MessengerBindService.class);
        bindService(bindIntent, mConnection, BIND_AUTO_CREATE);

    }

    public void unbindServiceMessager(View view){

        unbindService(mConnection);

    }

    public void bindServiceCalcu(View view){

        Intent bindIntent = new Intent(this, BindService.class);

        bindService(bindIntent, mConnection, BIND_AUTO_CREATE);

    }

    public void unbindServiceCalcu(View view){

        unbindService(mConnection);
    }

    public void sendMessage(View view){

        if (!mBound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, MessengerBindService.MSG_SAY_HELLO, 0, 0);
        try {
            mMessengerService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }



    public void getNumber(View view){

        if(mBound){
            textView.setText(String.valueOf(mService.getRandomNumber()));
        }


    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End Bound Service
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End Bound Service


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin  Service
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Begin  Service

    public void startService(View view){
        Intent intent = new Intent(this, HelloService.class);

        startService(intent);
    }

    public void stopService(View view){
        Intent intent = new Intent(this, HelloService.class);

        stopService(intent);
    }
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End  Service
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< End  Service


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    download();
                } else {
                    textView.setText("Service nooooooooooooooooo");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}