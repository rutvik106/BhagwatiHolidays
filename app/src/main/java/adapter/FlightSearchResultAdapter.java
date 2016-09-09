package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.ActivityFlightSearchResult;
import com.rutvik.bhagwatiholidays.App;

import java.util.LinkedList;
import java.util.List;

import component.FlightSearchResultComponent;
import liveapimodels.flightsearchresult.FlightSearchResult;
import model.MultiFlightResult;
import model.SingleFlightResult;
import viewholders.MultiFlightResultVH;
import viewholders.SingleFlightResultVH;

/**
 * Created by rutvik on 31-08-2016 at 09:50 PM.
 */

public class FlightSearchResultAdapter extends RecyclerView.Adapter
{

    private static final String TAG = App.APP_TAG + FlightSearchResultAdapter.class.getSimpleName();

    public List<FlightSearchResultComponent> flightSearchResultComponentList;

    private final Context context;

    public FlightSearchResultAdapter(final Context context)
    {
        this.context = context;
        flightSearchResultComponentList = new LinkedList<>();
    }

    public void addFlightSearchResult(SingleFlightResult flightDetails)
    {

        flightSearchResultComponentList
                .add(new FlightSearchResultComponent(FlightSearchResultComponent.FLIGHT_INFO, flightDetails));

        Log.i(TAG, "addFlightSearchResult: ADDING FLIGHT DETAILS: " + flightSearchResultComponentList.size());

        notifyItemInserted(flightSearchResultComponentList.size());
    }

    public void addMultiFlightSearchResult(MultiFlightResult resultList)
    {
        flightSearchResultComponentList
                .add(new FlightSearchResultComponent(FlightSearchResultComponent.MULTI_FLIGHT_INFO, resultList));

        Log.i(TAG, "addMultiFlightSearchResult: ADDING FLIGHT DETAILS: " + flightSearchResultComponentList.size());

        notifyItemInserted(flightSearchResultComponentList.size());
    }

    @Override
    public int getItemViewType(int position)
    {
        return flightSearchResultComponentList.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case FlightSearchResultComponent.FLIGHT_INFO:
                return SingleFlightResultVH.create(context, parent);

            case FlightSearchResultComponent.MULTI_FLIGHT_INFO:
                return MultiFlightResultVH.create(context, parent);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (getItemViewType(position))
        {
            case FlightSearchResultComponent.FLIGHT_INFO:
                SingleFlightResultVH.bind((SingleFlightResultVH) holder,
                        (SingleFlightResult) flightSearchResultComponentList.get(position).getObject());
                break;

            case FlightSearchResultComponent.MULTI_FLIGHT_INFO:
                MultiFlightResultVH.bind((MultiFlightResultVH) holder,
                        (MultiFlightResult) flightSearchResultComponentList.get(position).getObject());
                break;

        }
    }

    @Override
    public int getItemCount()
    {
        return flightSearchResultComponentList.size();
    }
}
