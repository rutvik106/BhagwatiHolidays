package gcm;

import android.content.Context;
import android.util.Log;

import com.rutvik.bhagwatiholidays.App;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import webservicehandler.PostHandler;


public final class GCMServerUtilities {

    private static final String TAG = App.APP_TAG + GCMServerUtilities.class.getSimpleName();


    //Register this account/device pair within the server.

    public static void register(final Context context, String name, String email, final String regId, PostHandler.ResponseCallback callback) {

        Log.i(TAG, "registering device (regId = " + regId + ")");

        String serverUrl = CommonUtilities.SERVER_URL;

        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("name", name);
        params.put("email", email);

        Log.i(TAG, "name: " + name + " email: " + email);


        new PostHandler(TAG, 3, 2000).doPostRequest(serverUrl, params,callback);


    }
}
