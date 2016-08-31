package com.rutvik.bhagwatiholidays;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import adapter.FlightSearchResultAdapter;
import extras.Log;
import liveapi.LiveAPI;

public class FlightSearchResult extends AppCompatActivity
{

    private static final String TAG = App.APP_TAG + FlightSearchResult.class.getSimpleName();

    private RecyclerView rvFlightSearchResult;

    private FlightSearchResultAdapter adapter;

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
            String response = "";

            @Override
            protected Void doInBackground(Void... voids)
            {
                response = LiveAPI.authenticate();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                Log.i(TAG, response);
            }
        }.execute();

    }


}
