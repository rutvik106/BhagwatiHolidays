package extras;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.rutvik.bhagwatiholidays.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import webservicehandler.PostHandler;

/**
 * Created by ACER on 08-Feb-16.
 */
public class AppVerification {

    private static final String TAG = App.APP_TAG + AppVerification.class.getSimpleName();

    private static ProgressDialog progressDialog;

    Activity activity;

    final String packageName;

    final SharedPreferences sharedPreferences;

    public AppVerification(Activity activity, String packageName) {
        this.activity = activity;
        this.packageName = packageName;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        verify();
    }

    private void verify() {

        new AsyncTask<Void, Void, Void>() {

            boolean allow;

            boolean success = false;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(activity, "Please Wait", "Getting things ready...", true, false);
            }

            @Override
            protected Void doInBackground(Void... voids) {


                HashMap<String, String> postParams = new HashMap<String, String>();
                postParams.put("package_name", packageName);

                new PostHandler(TAG, 2, 2000).doPostRequest("http://rutvik.comlu.com/sts.php", postParams, new PostHandler.ResponseCallback() {
                    @Override
                    public void response(int status, String response) {
                        if (status == HttpURLConnection.HTTP_OK) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                allow = new ValidationResponse(obj.getJSONObject("response")).isAllowed();
                                sharedPreferences.edit().putBoolean("ALLOW", allow).apply();
                                success = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                success = false;
                            }
                        } else {
                            success = false;
                        }
                    }
                });


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                if (!success) {
                    allow = sharedPreferences.getBoolean("ALLOW", false);
                }
                if (allow) {
                    return;
                }
                Toast.makeText(activity, "App verification failed", Toast.LENGTH_SHORT).show();
                activity.finish();

            }
        }.execute();

    }


    class ValidationResponse {

        String packageName, allow;

        ValidationResponse(JSONObject obj) throws JSONException {
            packageName = obj.getString("package_name");
            allow = obj.getString("allow");
            Log.i("STS", "package: " + packageName + " allow: " + allow);
        }

        public boolean isAllowed() {
            Log.i("STS", AppVerification.this.packageName + " == " + packageName);
            if (AppVerification.this.packageName.equals(packageName) && allow.equals("1")) {
                Log.i("STS", "app verification passed");
                return true;
            }
            return false;
        }

    }


}
