package webservicehandler;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class PostHandler {

    private String tag;

    private int maxAttempts = 5;

    private int backOff = 2000;

    public static interface ResponseCallback {
        public void response(int status, String response);
    }

    public PostHandler(String logtag, int maxAttempts, int backOff) {

        tag = logtag + " " + PostHandler.class.getSimpleName();
        this.maxAttempts = maxAttempts;
        this.backOff = backOff;

    }


    public void doPostRequest(String serverUrl, Map<String, String> params, ResponseCallback callback) {

        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= maxAttempts; i++) {

            Log.d(tag, "Attempt #" + i + " to register");

            try {


                post(serverUrl, params, callback);

                break;

            } catch (IOException e) {

                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(tag, "Failed to register on attempt " + i + ":" + e);

                if (i == maxAttempts) {
                    break;
                }
                try {

                    Log.d(tag, "Sleeping for " + backOff + " ms before retry");
                    Thread.sleep(backOff);
                } catch (InterruptedException e1) {

                    // Activity finished before we complete - exit.
                    Log.d(tag, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                }

                // increase backoff exponentially
                backOff *= 2;
            }
        }
    }


    private String constructBody(Map<String, String> params) {
        StringBuilder bodyBuilder = new StringBuilder();

        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

        // constructs the POST body using the parameters
        while (iterator.hasNext()) {

            Entry<String, String> param = iterator.next();

            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());

            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }

        }

        return bodyBuilder.toString();
    }


    //Issue a POST request to the server.

    private void post(String endpoint, Map<String, String> params, ResponseCallback callback)
            throws IOException {

        URL url;

        String body = "";

        String response = "";

        byte[] bytes;

        Log.i(tag, "URL: " + endpoint);

        try {

            url = new URL(endpoint);

        } catch (MalformedURLException e) {

            throw new IllegalArgumentException("invalid url: " + endpoint);

        }

        StringBuilder bodyBuilder = new StringBuilder();

        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

        // constructs the POST body using the parameters
        while (iterator.hasNext()) {

            Entry<String, String> param = iterator.next();

            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());

            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }

        }

        body = bodyBuilder.toString();

        Log.v(tag, "Posting: " + body + "' to " + url);

        bytes = body.getBytes();

        HttpURLConnection conn = null;

        try {

            Log.i(tag, "URL->" + url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setFixedLengthStreamingMode(bytes.length);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();

            // handle the response
            int status = conn.getResponseCode();

            Log.i(tag, "RESPONSE from server: " + status);

            Log.i(tag, "JSON Response Message: " + conn.getResponseMessage());

            if (status == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                Log.i(tag, "RESPONSE STRING: " + response);

                callback.response(status,response);

            } else {
                throw new IOException("Post failed with error code " + status);
            }


        } finally {

            if (conn != null) {

                conn.disconnect();

            }

        }
    }


}
