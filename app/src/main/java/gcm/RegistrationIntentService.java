package gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

import java.net.HttpURLConnection;

import webservicehandler.PostHandler;


/**
 * Created by ACER on 01-Mar-16.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = App.APP_TAG + RegistrationIntentService.class.getSimpleName();

    SharedPreferences sharedPreferences;

    public RegistrationIntentService() {
        super("RegistrationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPreferences.getBoolean("SENT_TOKEN_TO_SERVER", false) == false) {

            Log.i(TAG,"DEVICE NOT REGISTERED FOR GCM (SENDING TOKEN TO GCM SERVER NOW...)");

            try {

                final String name = intent.getStringExtra("name");
                final String email = intent.getStringExtra("email");

                InstanceID instanceID = InstanceID.getInstance(this);
                final String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                Log.i(TAG, "GCM registration token: " + token);

                GCMServerUtilities.register(this, name, email, token,new PostHandler.ResponseCallback() {
                    @Override
                    public void response(int status, String response) {
                        if (status == HttpURLConnection.HTTP_OK) {
                            Log.i(TAG, "STATUS: " + status + " RESPONSE: " + response);

                            sharedPreferences.edit().putBoolean("SENT_TOKEN_TO_SERVER", true).apply();

                            Log.i(TAG,"GCM REGISTRATION COMPLETED SUCCESSFULLY");
                        }
                    }
                });



            } catch (Exception e) {
                Log.i(TAG, "FAILED TO COMPLETE TOKEN REFRESH");

                sharedPreferences.edit().putBoolean("SENT_TOKEN_TO_SERVER", false).apply();
            }

        }
        else
        {
            Log.i(TAG,"DEVICE ALREADY REGISTERED FOR GCM");
        }

        Intent registrationComplete = new Intent("REGISTRATION_COMPLETE");
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }


}
