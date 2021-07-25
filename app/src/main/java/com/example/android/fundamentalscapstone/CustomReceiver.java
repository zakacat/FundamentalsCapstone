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

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private Activity mActivity;

    public CustomReceiver(Activity activity) {
        super();
        mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String toastMessage = "unknown intent action";
        int info = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

        switch (info) {
            case WifiManager.WIFI_STATE_UNKNOWN: {
                toastMessage = "Wifi is unknown";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                break;
            }
            case WifiManager.WIFI_STATE_DISABLED: {
                toastMessage = "Wifi is disconnected. Avoid recipe sharing.";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                break;
            }
            case WifiManager.WIFI_STATE_DISABLING: {
                toastMessage = "Wifi is disconnecting";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                break;
            }
            case WifiManager.WIFI_STATE_ENABLED: {
                toastMessage = "Wifi is connected. Share recipes without using data.";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                break;
            }
            case WifiManager.WIFI_STATE_ENABLING: {
                toastMessage = "Wifi is connecting";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                break;
            }
        }

        if (intent.getAction().equals(ACTION_CUSTOM_BROADCAST)) {
            toastMessage = "Zakacat\'s Recipe App is attempting to share a recipe.";
            Snackbar snackbar = Snackbar.make(context, mActivity.findViewById(R.id.activity_main), toastMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        //Display the toast.


    }


}
