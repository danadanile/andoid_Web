package com.example.ex4.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.example.ex4.NEW_NOTIFICATION")) {
            // Send a broadcast intent with a custom action
            Intent broadcastIntent = new Intent("com.example.ex4.ACTION_NEW_NOTIFICATION");
            context.sendBroadcast(broadcastIntent);
        }
    }
}
