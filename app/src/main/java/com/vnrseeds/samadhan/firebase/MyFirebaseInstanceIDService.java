package com.vnrseeds.samadhan.firebase;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseInstanceIDService";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        //String firebaseToken = FirebaseMessaging.getInstance().getToken();

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();
                    storeToken(token);
                }
            });
    }


    private void storeToken(String token) {
        //we will save the token in sharedpreferences later
        SharedPreferences.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
