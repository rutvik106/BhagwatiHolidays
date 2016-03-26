package gcm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.rutvik.bhagwatiholidays.App;

import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import adapter.NavigationDrawerAdapter;
import webservicehandler.PostHandler;

public final class CommonUtilities {

    public static final String TAG = App.APP_TAG + CommonUtilities.class.getSimpleName();

    // give your server registration url here
    public static final String SERVER_URL = "http://bhagwatiholidays.com/bwt-gcm-server/app/templates/GCM/register.php";

    public static final String URL_UNSUBSCRIBE_GCM = "http://bhagwatiholidays.com/bwt-gcm-server/app/templates/GCM/unsubscribe.php";

    public static final String URL_SUBSCRIBE_GCM = "http://bhagwatiholidays.com/bwt-gcm-server/app/templates/GCM/subscribe.php";

    public static final String URL_FROM_TO = "http://www.bhagwatiholidays.com/admin/webservice/airport_name.php";

    public static final String URL_DESTINATIONS = "http://www.bhagwatiholidays.com/admin/webservice/destination_name.php";

    public static final String GPLUS_LINK = "https://plus.google.com/u/0/105330265050301843812";

    public static final String URL_FB = "https://www.facebook.com/bhagwatiholidays";

    public static final String URL_WEBSITE_PACKAGE = "http://bhagwatiholidays.com/package.php?id=";


    public static void disableNotification(final Context context, final SharedPreferences sp, final NavigationDrawerAdapter adapter) {
        new AsyncTask<Void, Void, Void>() {

            final String gcmToken = sp.getString("GCM_TOKEN", "");

            String resp="";

            @Override
            protected Void doInBackground(Void... params) {

                if (gcmToken != "") {

                    final Map<String, String> postParams = new HashMap<>();
                    postParams.put("regId", gcmToken);

                    new PostHandler(TAG, 1, 4000).doPostRequest(URL_UNSUBSCRIBE_GCM, postParams, new PostHandler.ResponseCallback() {
                        @Override
                        public void response(int status, String response) {

                            if (status == HttpURLConnection.HTTP_OK) {
                                resp=response;
                            }

                        }
                    });

                }

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {

                if (resp.equals("1")) {
                    Log.i(TAG,"RESPONSE IS EQUALS TO 1");
                    sp.edit().putBoolean("IS_NOTIFICATION_DISABLED", true).apply();
                    Toast.makeText(context, "Notifications disabled.", Toast.LENGTH_SHORT).show();
                    adapter.clearAndRefreshNavItems();
                }
                if (resp.equals("0")) {
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
                if (resp.equals("-1")) {
                    Toast.makeText(context, "No params.", Toast.LENGTH_SHORT).show();
                }

            }

        }.execute();
    }


    public static void enableNotification(final Context context, final SharedPreferences sp, final NavigationDrawerAdapter adapter) {

        new AsyncTask<Void, Void, Void>() {

            final String gcmToken=sp.getString("GCM_TOKEN", "");

            String resp="";

            @Override
            protected Void doInBackground(Void... params) {

                if (gcmToken != "") {

                    final Map<String, String> postParams = new HashMap<>();
                    postParams.put("regId", gcmToken);

                    new PostHandler(TAG, 1, 4000).doPostRequest(URL_SUBSCRIBE_GCM, postParams, new PostHandler.ResponseCallback() {
                        @Override
                        public void response(int status, String response) {

                            if (status == HttpURLConnection.HTTP_OK) {
                                resp=response;
                            }

                        }
                    });

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                    if (resp.equals("1")) {
                        sp.edit().putBoolean("IS_NOTIFICATION_DISABLED", false).apply();
                        Toast.makeText(context, "Notifications enabled.", Toast.LENGTH_SHORT).show();
                        adapter.clearAndRefreshNavItems();
                    }
                    if (resp.equals("0")) {
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                    if (resp.equals("-1")) {
                        Toast.makeText(context, "No params.", Toast.LENGTH_SHORT).show();
                    }

            }
        }.execute();
    }


    public static void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }

    }


    public static void createCalendarEntry(Context context, String title, String location, String description, GregorianCalendar calDate) {

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);

        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        context.startActivity(intent);
    }


    public static void showAlertDialog(final Context context, final String calendarTitle
            , final String calendarLocation, final String calendarDescription) {
        final Calendar calendar = Calendar.getInstance();

        Log.d(TAG, "In AlertDialog.......");
        new AlertDialog.Builder(context)
                .setTitle("Inquiry")
                .setMessage("Inquiry successfully submitted! We will contact you shortly!")
                .setPositiveButton("Set Reminder", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtilities.createCalendarEntry(context,
                                calendarTitle,
                                calendarLocation,
                                calendarDescription,
                                new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)));
                    }
                })
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


}
