package com.rutvik.bhagwatiholidays;

import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import adapter.FilterDrawerAdapter;
import adapter.ViewPagerAdapter;
import bhfragment.FlightResultFragment;
import bhfragment.FragmentDrawer;
import bhfragment.FragmentFilterDrawer;
import component.FlightSearchResultComponent;
import extras.Log;
import liveapimodels.ApiConstants;
import liveapimodels.flightsearchresult.FlightSearchResult;
import liveapimodels.flightsearchresult.Results;
import liveapimodels.flightsearchresult.Segments;
import model.FilterCheckBox;
import model.MultiFlightResult;
import model.SingleFlightResult;
import model.SingleMultiFlightResult;
import viewholders.FilterCheckBoxVH;

public class ActivityFlightSearchResult extends AppCompatActivity
        implements FragmentDrawer.FragmentDrawerListener, FilterCheckBoxVH.FilterCheckBoxListener
{

    private static final String TAG = App.APP_TAG + ActivityFlightSearchResult.class.getSimpleName();

    LiveAPI.SearchFlights searchFlights;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

    FlightResultFragment fragmentOneWayFlight, fragmentReturnFlight;

    private HashMap<String, String> postParams;

    private TextView tvOrigin, tvDestination;

    private ImageView ivOneWay, ivReturn;

    private FragmentFilterDrawer fragmentFilterDrawer;

    private Map<String, String> FlightNameSet = new HashMap<>();

    private List<String> filteredFlightCodeList = new ArrayList<>();

    public FilterDrawerAdapter adapter;

    public List<FlightSearchResultComponent> oneWayFlightSearchResultComponentList = new ArrayList<>();
    public List<FlightSearchResultComponent> tempOneWayFlightSearchResultComponentList = new ArrayList<>();
    public List<FlightSearchResultComponent> returnWayFlightSearchResultComponentList = new ArrayList<>();
    public List<FlightSearchResultComponent> tempReturnWayFlightSearchResultComponentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search_result);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        ((TextView) findViewById(R.id.tv_toolbarTitle)).setText("Available Flights");

        ivOneWay = (ImageView) findViewById(R.id.iv_oneWay);
        ivReturn = (ImageView) findViewById(R.id.iv_return);

        tvDestination = (TextView) findViewById(R.id.tv_destination);
        tvOrigin = (TextView) findViewById(R.id.tv_origin);

        if (getIntent().getSerializableExtra("post_param") != null)
        {
            postParams = (HashMap<String, String>) getIntent().getSerializableExtra("post_param");
            tvOrigin.setText(postParams.get("origin").toString());
            tvDestination.setText(postParams.get("destination").toString());
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);


        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.ll_sortByPrice).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (fragmentOneWayFlight != null)
                {
                    if (fragmentOneWayFlight.adapter != null)
                    {
                        if (fragmentOneWayFlight.adapter.getItemCount() > 0)
                        {
                            Log.i(TAG, "SORTING NOW !!!!!!!!");
                            Collections.sort(oneWayFlightSearchResultComponentList, new MultiFlightResult.FairComparator());
                            fragmentOneWayFlight.adapter.animateTo(oneWayFlightSearchResultComponentList);
                        }
                    }
                }

                if (fragmentReturnFlight != null)
                {
                    if (fragmentReturnFlight.adapter != null)
                    {
                        if (fragmentReturnFlight.adapter.getItemCount() > 0)
                        {

                        }
                    }
                }

            }
        });

        findViewById(R.id.ll_filterFlights).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fragmentFilterDrawer.getDrawerLayout().openDrawer(GravityCompat.END);
            }
        });


        fragmentFilterDrawer = (FragmentFilterDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_filter_drawer);
        adapter = new FilterDrawerAdapter(this, this);
        fragmentFilterDrawer
                .setUp((DrawerLayout) findViewById(R.id.activity_flight_search_result), adapter);
        fragmentFilterDrawer.setDrawerListener(this);


    }

    private void setupTabIcons()
    {

        tabLayout.getTabAt(0).setIcon(R.drawable.logo);

    }

    private void setupViewPager(ViewPager viewPager)
    {

        fragmentOneWayFlight = FlightResultFragment.getInstance(this);
        fragmentReturnFlight = FlightResultFragment.getInstance(this);

        if (postParams.get("journey_type").equals(ApiConstants.JourneyType.ONE_WAY))
        {
            ivOneWay.setVisibility(View.VISIBLE);
            viewPagerAdapter.addFragment(fragmentOneWayFlight, "ONE-WAY");
            tabLayout.setVisibility(View.GONE);
        }
        if (postParams.get("journey_type").equals(ApiConstants.JourneyType.RETURN))
        {
            ivReturn.setVisibility(View.VISIBLE);
            viewPagerAdapter.addFragment(fragmentOneWayFlight, "ONE-WAY");
            viewPagerAdapter.addFragment(fragmentReturnFlight, "RETURN");
        }

        viewPager.setAdapter(viewPagerAdapter);


    }

    @Override
    protected void onStart()
    {
        super.onStart();


        if (postParams != null)
        {
            if (((App) getApplication()).getApiAuthentication() == null)
            {
                return;
            }
            final String authToken = ((App) getApplication()).getApiAuthentication().getTokenId();
            if (!authToken.isEmpty())
            {
                try
                {
                    Log.i(TAG, "SEARCHING FOR FLIGHTS TOKEN IS: " + authToken);
                    searchFlightsAsync(authToken, postParams.get("journey_type"));
                } catch (Exception e)
                {
                    e.printStackTrace();
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


    private void searchFlightsAsync(final String authToken, final String journeyType)
    {

        Log.i(TAG,"SEARCHING FOR FLIGHTS");

        searchFlights = new LiveAPI.SearchFlights(authToken, postParams)
        {

            @Override
            protected void onPostExecute(FlightSearchResult flightSearchResult)
            {
                if (flightSearchResult != null)
                {

                    if (fragmentOneWayFlight != null)
                    {
                        fragmentOneWayFlight.clearAdapter();
                    }
                    if (fragmentReturnFlight != null)
                    {
                        fragmentReturnFlight.clearAdapter();
                    }

                    Results[][] r = flightSearchResult.getResponse().getResults();
                    android.util.Log.i(TAG, "RESULT[][] length: " + r[0].length);
                    for (int i = 0; i < r[0].length; i++)
                    {
                        final Results result = r[0][i];
                        final Segments[][] s = r[0][i].getSegments();

                        //will be true for INTERNATIONAL FLIGHTS as they have multi stops
                        if (s[0].length > 1)
                        {
                            final MultiFlightResult multiFlightResult = new MultiFlightResult();
                            for (int j = 0; j < s[0].length; j++)
                            {
                                final Segments segment = s[0][j];
                                Log.i(TAG, result.toString());

                                SingleMultiFlightResult singleFlightResult = new SingleMultiFlightResult();

                                singleFlightResult.setOriginDestination(segment.getOrigin().getAirport().getCityCode()
                                        + " - "
                                        + segment.getDestination().getAirport().getCityCode());

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

                                FlightNameSet.put(segment.getAirline().getAirlineCode(),
                                        segment.getAirline().getAirlineName());
                                multiFlightResult.addSingleMultiFlightResult(singleFlightResult);

                            }

                            multiFlightResult.setPublishedFair(result.getFare().getPublishedFare());

                            oneWayFlightSearchResultComponentList.add(fragmentOneWayFlight.adapter
                                    .addMultiFlightSearchResult(multiFlightResult));


                        } else
                        {
                            //flights having no multi stops
                            final Segments segment = s[0][0];
                            Log.i(TAG, result.toString());
                            SingleFlightResult singleFlightResult = new SingleFlightResult();
                            singleFlightResult.setAirPrice(result.getFare().getPublishedFare());
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

                            FlightNameSet.put(segment.getAirline().getAirlineCode(),
                                    segment.getAirline().getAirlineName());
                            oneWayFlightSearchResultComponentList.add(fragmentOneWayFlight.adapter.addFlightSearchResult(singleFlightResult));

                        }

                    }


                    fragmentOneWayFlight.hideProgressBar();
                    fragmentOneWayFlight.loading = false;

                    //will be true for journey type RETURN
                    if (journeyType.equals(ApiConstants.JourneyType.RETURN))
                    {
                        if (fragmentReturnFlight != null)
                        {
                            for (int i = 0; i < r[1].length; i++)
                            {
                                final Results result = r[1][i];
                                final Segments segment = r[1][i].getSegments()[0][0];
                                Log.i(TAG, result.toString());
                                SingleFlightResult singleFlightResult = new SingleFlightResult();
                                singleFlightResult.setAirPrice(result.getFare().getPublishedFare());
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

                                returnWayFlightSearchResultComponentList.add(fragmentReturnFlight.adapter.addFlightSearchResult(singleFlightResult));
                                //
                            }

                            fragmentReturnFlight.hideProgressBar();
                            fragmentReturnFlight.loading = false;
                        }
                    }


                    final Iterator<Map.Entry<String, String>> flightNameIterator = FlightNameSet.entrySet().iterator();
                    while (flightNameIterator.hasNext())
                    {
                        final Map.Entry<String, String> entry = flightNameIterator.next();
                        adapter
                                .addFilterCheckBox(new FilterCheckBox(entry.getValue(), entry.getKey()));
                        filteredFlightCodeList.add(entry.getKey());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        };

        searchFlights.execute();

    }

    public void filterFlightByType()
    {
        if (fragmentOneWayFlight != null)
        {
            if (fragmentOneWayFlight.adapter.getItemCount() > 0)
            {
                try
                {
                    tempOneWayFlightSearchResultComponentList.clear();
                    for (FlightSearchResultComponent component : oneWayFlightSearchResultComponentList)
                    {
                        for (String s : filteredFlightCodeList)
                        {
                            if (component.getFlightType(s))
                            {
                                tempOneWayFlightSearchResultComponentList.add(component);
                            }
                        }
                    }

                    fragmentOneWayFlight.adapter.animateTo(tempOneWayFlightSearchResultComponentList);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if (fragmentFilterDrawer.getDrawerLayout().isDrawerOpen(GravityCompat.END))
        {
            fragmentFilterDrawer.getDrawerLayout().closeDrawer(GravityCompat.END);
        } else
        {
            searchFlights.cancel(true);
            super.onBackPressed();
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position)
    {

    }

    @Override
    public void onCheckChanged(FilterCheckBox model, boolean b)
    {
        if (model.isChecked())
        {
            filteredFlightCodeList.add(model.getValue());
        } else
        {
            filteredFlightCodeList.remove(model.getValue());
        }
        filterFlightByType();
    }
}
