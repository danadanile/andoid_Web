import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.ex4.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    AppConstants.FCMConstants.CHANNEL_ID,
                    AppConstants.FCMConstants.CHANNEL_NAME,
                    importance
            );
            mChannel.setDescription(AppConstants.FCMConstants.CHANNEL_DESC);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        if (remoteMessage.getData() != null && !remoteMessage.getData().isEmpty()) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("message");
            Log.d("Notification", "From data object, notification title: " + title + " and body: " + body);
            sendNotification(title, body);
        }

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d("Notification", "From notification object, notification title: " + title + " and body: " + body);
            if (title != null && body != null) {
                sendNotification(title, body);
            }
        }
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(
                    AppConstants.FCMConstants.CHANNEL_ID,
                    AppConstants.FCMConstants.CHANNEL_NAME,
                    importance
            );
            channel.setDescription(AppConstants.FCMConstants.CHANNEL_DESC);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this, AppConstants.FCMConstants.CHANNEL_ID)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setChannelId(AppConstants.FCMConstants.CHANNEL_ID)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.push_icon);
            notificationBuilder.setColor(ContextCompat.getColor(this, R.color.pink_500));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d("NotificationFCM", "Token: " + token);
    }
}
