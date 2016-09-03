package com.rutvik.bhagwatiholidays;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import adapter.FlightSearchResultAdapter;
import extras.Log;
import liveapimodels.ApiConstants;
import liveapimodels.Authentication;
import liveapimodels.flightsearchresult.Results;
import webservicehandler.PostHandler;

public class FlightSearchResult extends AppCompatActivity
{

    private static final String TAG = App.APP_TAG + FlightSearchResult.class.getSimpleName();

    private RecyclerView rvFlightSearchResult;

    private FlightSearchResultAdapter adapter;

    liveapimodels.flightsearchresult.FlightSearchResult flightSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search_result);

        rvFlightSearchResult = (RecyclerView) findViewById(R.id.rv_flightSearchResult);
        rvFlightSearchResult.setHasFixedSize(true);
        rvFlightSearchResult.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FlightSearchResultAdapter(this);

        rvFlightSearchResult.setAdapter(adapter);

        new AsyncTask<Void, Void, Void>()
        {
            Authentication authentication;

            @Override
            protected Void doInBackground(Void... voids)
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

                                if (authentication.getTokenId() != null)
                                {
                                    if (!authentication.getTokenId().isEmpty())
                                    {
                                        final Map<String, String> postParam2 = new HashMap<>();
                                        postParam2.put("method", "search_flights");

                                        postParam2.put("token_id", authentication.getTokenId());
                                        postParam2.put("origin", "AMD");
                                        postParam2.put("destination", "DEL");
                                        postParam2.put("flight_cabin_class", ApiConstants.CabinClass.ECONOMY);
                                        postParam2.put("departure_time", "2016-09-04");
                                        postParam2.put("arrival_time", "2016-09-04");
                                        postParam2.put("adult_count", "1");
                                        postParam2.put("child_count", "0");
                                        postParam2.put("infant_count", "0");
                                        postParam2.put("journey_type", ApiConstants.JourneyType.ONE_WAY);

                                        new PostHandler(TAG, 3, 3000).doPostRequest(ApiConstants.URL, postParam2, new PostHandler.ResponseCallback()
                                        {
                                            @Override
                                            public void response(int status2, String response2)
                                            {
                                                if (status2 == HttpURLConnection.HTTP_OK)
                                                {

                                                    try
                                                    {
                                                        flightSearchResult = objectMapper.readValue(response2, liveapimodels.flightsearchresult.FlightSearchResult.class);

                                                    } catch (IOException e)
                                                    {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }

                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                Log.i(TAG, authentication.toString());
                Log.i(TAG, flightSearchResult.toString());
                Results[][] r = flightSearchResult.getResponse().getResults();
                android.util.Log.i(TAG, "RESULT[][] length: " + r.length);
                for (int i = 0; i < r[0].length; i++)
                {
                    Log.i(TAG, r[0][i].toString());
                }
            }
        }.execute();

    }


}
