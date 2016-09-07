package com.rutvik.bhagwatiholidays;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;

import adapter.FlightSearchResultAdapter;
import extras.Log;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import liveapimodels.flightsearchresult.FlightSearchResult;
import liveapimodels.flightsearchresult.Results;
import liveapimodels.flightsearchresult.Segments;
import model.SingleFlightResult;

public class ActivityFlightSearchResult extends AppCompatActivity
{

    private static final String TAG = App.APP_TAG + ActivityFlightSearchResult.class.getSimpleName();

    private RecyclerView rvFlightSearchResult;

    private FlightSearchResultAdapter adapter;

    private HashMap<String, String> postParams;

    private FrameLayout flLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search_result);

        flLoading = (FrameLayout) findViewById(R.id.fl_loading);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Available Flights");

        rvFlightSearchResult = (RecyclerView) findViewById(R.id.rv_flightSearchResult);
        rvFlightSearchResult.setHasFixedSize(true);
        rvFlightSearchResult.setLayoutManager(new LinearLayoutManager(this));

        rvFlightSearchResult.setItemAnimator(new SlideInUpAnimator());

        adapter = new FlightSearchResultAdapter(this);

        rvFlightSearchResult.setAdapter(adapter);

        if (getIntent().getSerializableExtra("post_param") != null)
        {
            postParams = (HashMap<String, String>) getIntent().getSerializableExtra("post_param");

            getSupportActionBar()
                    .setSubtitle(postParams.get("origin").toString()+" - "+postParams.get("destination").toString());

            if (postParams != null)
            {
                if(((App) getApplication()).getApiAuthentication()==null){
                    return;
                }
                final String authToken = ((App) getApplication()).getApiAuthentication().getTokenId();
                if (!authToken.isEmpty())
                {
                    new LiveAPI.SearchFlights(authToken, postParams)
                    {
                        @Override
                        protected void onPostExecute(FlightSearchResult flightSearchResult)
                        {
                            Results[][] r = flightSearchResult.getResponse().getResults();
                            android.util.Log.i(TAG, "RESULT[][] length: " + r[0].length);
                            for (int i = 0; i < r[0].length; i++)
                            {
                                final Results result = r[0][i];
                                final Segments segment = r[0][i].getSegments()[0][0];
                                Log.i(TAG, result.toString());

                                SingleFlightResult singleFlightResult = new SingleFlightResult();

                                singleFlightResult.setAirPrice(result.getFare().getBaseFare());
                                singleFlightResult.setAirCode(segment.getAirline().getAirlineCode());
                                singleFlightResult.setFlightNumber(segment.getAirline().getFlightNumber());
                                singleFlightResult.setFlightName(segment.getAirline().getAirlineCode());


                                singleFlightResult.setStartTime(segment.getOrigin().getDepTime());


                                singleFlightResult.setEndTime(segment.getDestination().getArrTime());

                                if (segment.getStopPoint().isEmpty())
                                {
                                    singleFlightResult.setIsNonStop("Non Stop");
                                } else
                                {
                                    singleFlightResult.setIsNonStop(segment.getStopPoint());
                                }

                                adapter.addFlightSearchResult(singleFlightResult);
                            }

                            flLoading.setVisibility(View.GONE);

                        }
                    }.execute();
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
