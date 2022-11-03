package com.emmanuelmyapplicationnotication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class OPEN_ACTIVITY_1  extends BroadcastReceiver {
    private final String TAG = "FirebaseDataReceiver";
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "OPEN_ACTIVITY_1");
        Bundle demo = intent.getExtras();
        //Bundle dataBundle = intent.getBundleExtra("data");
        Log.d(TAG,  demo.getString("datos","Fallo"));
    }
}
