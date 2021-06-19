package com.example.android.fundamentalscapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    public static final String TAG = FeedbackActivity.class.getSimpleName();
    private TelephonyManager mTelephonyManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Create a telephony manager.
        mTelephonyManager = (TelephonyManager)
                getSystemService(TELEPHONY_SERVICE);

    }

    public void callZakacat(View view) {

        getSystemService(TELEPHONY_SERVICE);
        if (isTelephonyEnabled()) {
            Log.d(TAG, "Telephony is enabled");
            // ToDo: Check for phone permission.
            checkForPhonePermission();
            // ToDo: Register the PhoneStateListener.
        } else {
            Toast.makeText(this,
                    "TELEPHONY NOT ENABLED! ",
                    Toast.LENGTH_LONG).show();
            Log.d(TAG, "TELEPHONY NOT ENABLED! ");
            // Disable the call button
        }
        String myNumber = "12364890028";
        String formattedNumber = String.format("tel: %s", myNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(formattedNumber));

        //If an app to receive the intent can be found, carry-out action. If not,
        //then send a log message.

        if (callIntent.resolveActivity(getPackageManager()) != null) {
            if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            }
        } else {
            Log.e(TAG, "No apps found to carry-out call action.");
        }
    }

    private boolean isTelephonyEnabled() {
        if (mTelephonyManager != null) {
            if (mTelephonyManager.getSimState() ==
                    TelephonyManager.SIM_STATE_READY) {
                return true;
            }
        }
        return false;
    }

    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "PERMISSION NOT GRANTED!");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Permission already granted. Enable the call button.
            //Nothing happnens here. I ma not dealing with disabling
            // and enabling the buttons yet.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // Check if permission is granted or not for the request.
        // Check if permission is granted or not for the request.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.CALL_PHONE)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                } else {
                    // Permission denied.
                    Log.d(TAG, "Failure to obtain permission! You must change the permission in settings if you want to call!");
                    Toast.makeText(this,
                            "Failure to obtain permission! You must change the permission in settings if you want to call!",
                            Toast.LENGTH_LONG).show();
                    // Disable the call button. only if I want to.
                }
            }
        }
    }

        public void textZakacat (View view){
        }
    }