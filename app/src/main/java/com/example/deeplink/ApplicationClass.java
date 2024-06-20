package com.example.deeplink;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.onesignal.inAppMessages.IInAppMessageClickEvent;
import com.onesignal.inAppMessages.IInAppMessageClickListener;

import org.json.JSONException;
import org.json.JSONObject;


import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;

public class ApplicationClass extends Application {

    // Replace the below with your own ONESIGNAL_APP_ID
    private static final String ONESIGNAL_APP_ID = "49cb85de-aa93-4e1f-8a87-7fc8f09dc1df";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);


        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        OneSignal.getUser().addTag("key", "value");

        OneSignal.login("test");

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
            if (r.isSuccess()) {
                if (r.getData()) {
                    Log.e("appcode", "test");
                }
                else {
                    Log.e("appcode", "test");
                }
            }
            else {
                Log.e("appcode", "test");
            }
        }));

        /*Listen for a custom URL Scheme as opposed to an App Link*/
        OneSignal.getInAppMessages().addClickListener(new IInAppMessageClickListener() {
            @Override
            public void onClick(@Nullable IInAppMessageClickEvent event) {
                String link = event.getResult().getActionId();
                if(link != null){
                    Log.d("DeepLinkCheck", "URL Value: " + link);
                    Uri deepLinkValue = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, deepLinkValue);
                    // Add this flag to start the activity in a new task or if the activity is outside your app context
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        OneSignal.getNotifications().addClickListener(result ->
        {
            JSONObject addtlData = result.getNotification().getAdditionalData();
            if(addtlData != null && addtlData.has("url")){
                try {
                    String urlValue = addtlData.getString("url");
                    Log.d("DeepLinkCheck", "URL Value: " + urlValue);
                    Uri deepLinkValue = Uri.parse(urlValue);
                    Intent intent = new Intent(Intent.ACTION_VIEW, deepLinkValue);
                    // Add this flag to start the activity in a new task or if the activity is outside your app context
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
