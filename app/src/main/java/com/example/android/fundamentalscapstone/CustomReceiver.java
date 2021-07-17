package com.example.android.fundamentalscapstone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String toastMessage = "unknown intent action";
            int info = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

            switch (info){
                case WifiManager.WIFI_STATE_UNKNOWN:{
                    toastMessage = "Wifi is unknown";
                    break;
                }
                case WifiManager.WIFI_STATE_DISABLED:{
                    toastMessage = "Wifi is disconnected. Avoid recipe sharing.";
                    break;
                }
                case WifiManager.WIFI_STATE_DISABLING:{
                    toastMessage = "Wifi is disconnecting";
                    break;
                }
                case WifiManager.WIFI_STATE_ENABLED:{
                    toastMessage = "Wifi is connected. Share recipes without using data.";
                    break;
                }
                case WifiManager.WIFI_STATE_ENABLING:{
                    toastMessage = "Wifi is connecting";
                    break;
                }
            }
            //Display the toast.
//
//        Snackbar snackbar = Snackbar.make(context, ((Activity)context).findViewById(R.id.activity_main),toastMessage, Snackbar.LENGTH_LONG);
//        snackbar.show();
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

    }


}
