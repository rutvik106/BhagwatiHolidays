package liveapi;

import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by rutvik on 31-08-2016 at 11:25 PM.
 */

public class LiveAPI
{


    public static String authenticate()
    {
        return executePost(Authentication.URL_AUTHENTICATION, Authentication.getJSON());
    }


    private static String executePost(String targetURL, String urlParameters)
    {
        HttpURLConnection connection = null;

        try
        {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setRequestProperty("Accept-Encoding", "gzip");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();

            is = new GZIPInputStream(is);

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null)
            {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }


}
