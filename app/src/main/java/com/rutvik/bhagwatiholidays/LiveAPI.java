package com.rutvik.bhagwatiholidays;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import extras.Log;
import liveapimodels.ApiConstants;
import liveapimodels.Authentication;
import liveapimodels.flightsearchresult.Results;
import webservicehandler.PostHandler;

/**
 * Created by rutvik on 05-09-2016 at 12:11 PM.
 */

public class LiveAPI
{

    private static final String TAG = App.APP_TAG + LiveAPI.class.getSimpleName();


    public static class SearchFlights extends AsyncTask<Void, Void, liveapimodels.flightsearchresult.FlightSearchResult>
    {

        liveapimodels.flightsearchresult.FlightSearchResult flightSearchResult;

        final Map<String, String> flightParam;
        final String authToken;

        public SearchFlights(String authToken, Map<String, String> flightParam)
        {
            this.flightParam = flightParam;
            this.authToken = authToken;
        }

        @Override
        protected final liveapimodels.flightsearchresult.FlightSearchResult doInBackground(Void... voids)
        {


            if (!authToken.isEmpty())
            {
                final Map<String, String> postParam = new HashMap<>();
                postParam.put("method", "search_flights");
                postParam.put("token_id", authToken);
                postParam.putAll(flightParam);
                /**postParam2.put("origin", "AMD");
                 postParam2.put("destination", "DEL");
                 postParam2.put("flight_cabin_class", ApiConstants.CabinClass.ECONOMY);
                 postParam2.put("departure_time", "2016-09-04");
                 postParam2.put("arrival_time", "2016-09-04");
                 postParam2.put("adult_count", "1");
                 postParam2.put("child_count", "0");
                 postParam2.put("infant_count", "0");
                 postParam2.put("journey_type", ApiConstants.JourneyType.ONE_WAY);*/

                new PostHandler(TAG, 3, 3000).doPostRequest(ApiConstants.URL, postParam, new PostHandler.ResponseCallback()
                {
                    @Override
                    public void response(int status, String response)
                    {
                        if (status == HttpURLConnection.HTTP_OK)
                        {

                            try
                            {
                                final ObjectMapper objectMapper = new ObjectMapper();
                                flightSearchResult = objectMapper.readValue(response, liveapimodels.flightsearchresult.FlightSearchResult.class);

                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

            return flightSearchResult;
        }

        /**@Override
        protected void onPostExecute(liveapimodels.flightsearchresult.FlightSearchResult aVoid)
        {
            Log.i(TAG, flightSearchResult.toString());
            Results[][] r = flightSearchResult.getResponse().getResults();
            android.util.Log.i(TAG, "RESULT[][] length: " + r.length);
            for (int i = 0; i < r[0].length; i++)
            {
                Log.i(TAG, r[0][i].toString());
            }
        }*/

    }


    public static class Authenticate extends AsyncTask<Void, Void, Authentication>
    {
        Authentication authentication;

        @Override
        protected final Authentication doInBackground(Void... voids)
        {

            final Map<String, String> postParam = new HashMap<>();
            postParam.put("method", "authenticate");
            new PostHandler(TAG, 3, 3000).doPostRequest(ApiConstants.URL, postParam, new PostHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String response)
                {
                    if (status == HttpURLConnection.HTTP_OK)
                    {
                        try
                        {
                            final ObjectMapper objectMapper = new ObjectMapper();
                            authentication = objectMapper.readValue(response, Authentication.class);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });

            return authentication;
        }
    }

}
