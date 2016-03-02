package gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.OffersActivity;
import com.rutvik.bhagwatiholidays.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ACER on 01-Mar-16.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = App.APP_TAG + MyInstanceIDListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message=data.getString("message");
        Log.i(TAG,"FROM: "+from);
        Log.i(TAG,"MESSAGE: "+message);

        try{
            JSONObject obj=new JSONObject(message).getJSONObject("push_message");
            Log.i(TAG,"TITLE: "+obj.getString("title"));
            Log.i(TAG,"MESSAGE: "+obj.getString("message"));
            Log.i(TAG,"TYPE: "+obj.getString("type"));

            sendNotification(obj.getString("title"),obj.getString("message"),obj.getString("type"));

        }catch(JSONException e){
            Log.i(TAG,"JSON EXCEPTION: "+e.getMessage());
            sendNotification(message);
        }




    }



    private void sendNotification(String message) {
        Intent intent = new Intent(this, OffersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Bhagwati Holidays")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification(String title,String message,String type) {
        Intent intent = new Intent(this, OffersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
