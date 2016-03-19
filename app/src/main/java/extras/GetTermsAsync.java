package extras;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.rutvik.bhagwatiholidays.App;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import webservicehandler.PostHandler;

/**
 * Created by rutvik on 19-03-2016 at 03:14 PM.
 */
public class GetTermsAsync extends AsyncTask<String, Void, Void> {

    private static final String TAG = App.APP_TAG + GetTermsAsync.class.getSimpleName();

    String resp = "";

    final AutoCompleteTextView actv;

    final Context context;

    final String url;

    public GetTermsAsync(AutoCompleteTextView actv, Context context, String url) {
        this.actv = actv;
        this.context = context;
        this.url = url;
    }

    @Override
    protected Void doInBackground(String... params) {


        Map<String, String> postParams = new HashMap<>();


        Log.i(TAG, "Do in background in getFromAsync");
        postParams.put("term", params[0]);
        new PostHandler(TAG, 2, 2000).doPostRequest(url,
                postParams,
                new PostHandler.ResponseCallback() {
                    @Override
                    public void response(int status, String response) {
                        Log.i(TAG, "GOT RESPONSE SUCCESSFULLY");
                        resp = response;
                    }
                });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (resp != "") {
            setAutocompleteTextViewAdapter(resp, actv);
        }
    }

    private void setAutocompleteTextViewAdapter(String response, AutoCompleteTextView view) {
        try {
            Log.i(TAG, "PARSING JSON");
            JSONArray array = new JSONArray(response);
            Log.i(TAG, "JSON ARRAY SIZE IN FROM: " + array.length());
            final String[] terms = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                Log.i(TAG, "LABEL IN FROM: " + array.getJSONObject(i).getString("label"));
                terms[i] = array.getJSONObject(i).getString("label");
            }
            Log.i(TAG, "SETTING ADAPTER NOW FOR FROM");
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_list_item_1, terms);
            view.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
