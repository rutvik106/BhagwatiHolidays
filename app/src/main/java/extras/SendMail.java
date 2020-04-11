package extras;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.rutvik.bhagwatiholidays.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import webservicehandler.PostHandler;

/**
 * Created by rutvik on 19-03-2016 at 04:05 PM.
 */
public class SendMail extends AsyncTask<Map<String, String>, Void, Void> {

    public enum Type {

        AIRTICKET("airticket"),
        VISA("visa"),
        HOLIDAY("holiday"),
        HOTEL("hotel"),
        BOOKING("booking");

        private final String name;

        private Type(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    final String email;

    private static final String TAG = App.APP_TAG + SendMail.class.getSimpleName();

    final Context context;

    private ProgressDialog pd;

    private boolean success = false;

    final MailCallbackListener listener;

    final String emailType;

    final App app;

    public SendMail(String email, Type emailType, Context context, MailCallbackListener listener,App app) {
        this.email = email;
        this.context = context;
        this.listener = listener;
        this.emailType = emailType.toString();
        this.app=app;
    }

    public interface MailCallbackListener {
        void mailSentSuccessfully();
    }

    @Override
    protected void onPreExecute() {
        pd = ProgressDialog.show(context, "Please Wait", "Sending inquiry...", true, false);
    }

    @Override
    protected Void doInBackground(final Map<String, String>... params) {

        try {

            JSONArray array = new JSONArray();

            String type="";

            Iterator iterator = params[0].entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                if(pair.getKey().equals("Type")){
                    type=pair.getValue().toString();
                }
                array.put(new JSONObject("{\"" + pair.getKey() + "\":" + "\"" + pair.getValue() + "\"}"));
                iterator.remove();
            }

            Log.d(TAG, "JSON-DATA: " + array);

            final Map<String, String> postParam = new HashMap<String, String>();
            postParam.put("data", array.toString());
            postParam.put("email", email);
            postParam.put("type", emailType);
            if(emailType.equals(Type.HOTEL.toString())){
                Log.i(App.APP_TAG,"ITS AN HOTEL INQUIRY MAIL WITH TYPE: "+type);
                postParam.put("hotel_type",type);
            }
            else if(emailType.equals(Type.HOLIDAY.toString())){
                Log.i(App.APP_TAG,"ITS AN HOLIDAY INQUIRY MAIL WITH TYPE: "+type);
                postParam.put("holiday_type",type);
            }
            new PostHandler(TAG, 2, 2000).doPostRequest("http://bhagwatiholidays.com/send-mail/send_mail.php", postParam, new PostHandler.ResponseCallback() {
                @Override
                public void response(int status, String response) {
                    if (status == HttpURLConnection.HTTP_OK) {
                        try {
                            JSONObject mailResponse = new JSONObject(response).getJSONObject("mail_response");
                            if (mailResponse.getString("status").equals("1")) {
                                success = true;
                            }
                        } catch (JSONException e) {
                            app.trackException(new Exception(TAG+e.toString()));
                            e.printStackTrace();
                        }
                    }
                }
            });

        } catch (JSONException e) {
            app.trackException(new Exception(TAG+e.toString()));
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
        if (success) {
            listener.mailSentSuccessfully();
        }
    }

}
