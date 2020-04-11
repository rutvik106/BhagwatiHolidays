package bhfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.LiveAPI;
import com.rutvik.bhagwatiholidays.R;

import java.util.HashMap;
import java.util.List;

import adapter.FlightSearchResultAdapter;
import extras.Log;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import liveapimodels.flightsearchresult.FlightSearchResult;
import liveapimodels.flightsearchresult.Results;
import liveapimodels.flightsearchresult.Segments;
import model.MultiFlightResult;
import model.SingleFlightResult;

/**
 * Created by rutvik on 08-09-2016 at 04:05 PM.
 */

public class FlightResultFragment extends Fragment
{


    private static final String TAG = App.APP_TAG + FlightResultFragment.class.getSimpleName();

    private RecyclerView rvFlightSearchResult;

    private FrameLayout flLoading;

    Context context;

    public FlightSearchResultAdapter adapter;

    private LinearLayoutManager llm;

    public boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;


    public FlightResultFragment()
    {

    }

    public static FlightResultFragment getInstance(Context context)
    {
        FlightResultFragment fragment = new FlightResultFragment();
        fragment.context = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.flight_result_fragment, container, false);

        flLoading = (FrameLayout) rootView.findViewById(R.id.fl_loading);
        flLoading.setVisibility(View.GONE);

        rvFlightSearchResult = (RecyclerView) rootView.findViewById(R.id.rv_flightSearchResult);
        rvFlightSearchResult.setHasFixedSize(true);

        llm=new LinearLayoutManager(getActivity());

        rvFlightSearchResult.setLayoutManager(llm);

        adapter = new FlightSearchResultAdapter(getActivity());

        rvFlightSearchResult.setAdapter(adapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        rvFlightSearchResult.setItemAnimator(itemAnimator);

        if(loading){
            for (int i=0;i<10;i++){
                adapter.addEmptyView();
            }
        }

        rvFlightSearchResult.setOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisibleItems = llm.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
                            adapter.addEmptyView();
                        }
                    }
                }
            }
        });

        return rootView;

    }

    public void hideProgressBar()
    {
        flLoading.setVisibility(View.GONE);
    }

    public void clearAdapter(){
        if(llm!=null)
        {
            llm.removeAllViews();
        }
        if(adapter!=null)
        {
            adapter.clear();
        }
    }

}
