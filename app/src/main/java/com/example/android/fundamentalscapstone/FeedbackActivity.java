package com.example.android.fundamentalscapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    public static final String TAG = FeedbackActivity.class.getSimpleName();
    private TelephonyManager mTelephonyManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) ==
                    PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            }
        } else {
            Log.e(TAG, "No apps found to carry-out call action.");
            Toast.makeText(this, "No apps found to carry-out sms action.", Toast.LENGTH_LONG).show();
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
            //Nothing happens here. I am not dealing with disabling
            // and enabling the buttons yet.
        }
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission not granted!");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            //Nothing happens here. I am not dealing with disabling
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
            if (permissions[0].equalsIgnoreCase
                    (Manifest.permission.SEND_SMS)
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Enable sms button.
            } else {
                // Permission denied.
                Log.d(TAG, "Failure to obtain permission! You must change the permission in settings if you want to text!");
                Toast.makeText(this,
                        "Failure to obtain permission! You must change the permission in settings if you want to text!",
                        Toast.LENGTH_LONG).show();
                // Disable the sms button. Only if I want to.
            }
        }
    }


    public void textZakacat(View view) {
        String myNumber = "12364890028";
        String formattedNumber = String.format("smsto: %s", myNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(formattedNumber));
        // Find the sms_message view.
        EditText smsEditText = findViewById(R.id.feedback_input_text_edit);
        // Get the text of the SMS message.
        String sms = smsEditText.getText().toString();
        // Create the intent.
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        // Set the data for the intent as the phone number.
        smsIntent.setData(Uri.parse(formattedNumber));
        // Add the message (sms) with the key ("sms_body").
        smsIntent.putExtra("sms_body", sms);
        // Set the service center address if needed, otherwise null.
        String scAddress = null;
        // Set pending intents to broadcast
        // when message sent and when delivered, or set to null.
        PendingIntent sentIntent = null, deliveryIntent = null;
        //I can get time stamps for this???
        //Is that what these are for?
        Spinner iterationsSpinner = findViewById(R.id.feedback_text_iterations_spinner);
        int iterations = 1;
        switch (iterationsSpinner.getSelectedItem().toString()) {

            case "How many times to send the text?": {
                iterations = 1;
                break;
            }

            case "1": {
                iterations = 1;
                break;
            }

            case "10": {
                iterations = 10;
                break;
            }

            case "20": {
                iterations = 20;
                break;
            }

            case "30": {
                iterations = 30;
                break;
            }

            case "40": {
                iterations = 40;
                break;
            }

            case "50": {
                iterations = 50;
                break;
            }

            case "60": {
                iterations = 60;
                break;
            }

            case "70": {
                iterations = 70;
                break;
            }

            case "80": {
                iterations = 80;
                break;
            }

            case "90": {
                iterations = 90;
                break;
            }

            case "100": {
                iterations = 100;
                break;
            }

            default: {
                iterations = 1;
            }

        }


        checkForSmsPermission();

        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // Use SmsManager.
                SmsManager smsManager = SmsManager.getDefault();

                for (int i = 0; i < iterations; i++) {
                    smsManager.sendTextMessage
                            (myNumber, scAddress, sms,
                                    sentIntent, deliveryIntent);
                }
            }
        } else {
            Log.e(TAG, "No apps found to carry-out sms action.");
            Toast.makeText(this, "No apps found to carry-out sms action.", Toast.LENGTH_LONG).show();
        }

    }
}