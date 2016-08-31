package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.FlightSearchResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import component.FlightSearchResultComponent;
import model.SingleFlightResult;
import viewholders.SingleFlightResultVH;

/**
 * Created by rutvik on 31-08-2016 at 09:50 PM.
 */

public class FlightSearchResultAdapter extends RecyclerView.Adapter
{

    private List<FlightSearchResultComponent> flightSearchResultComponentList;

    private final Context context;

    public FlightSearchResultAdapter(final Context context)
    {
        this.context = context;
        flightSearchResultComponentList = new LinkedList<>();
    }

    public void addFlightSearchResult(FlightSearchResult flightDetails)
    {
        flightSearchResultComponentList
                .add(new FlightSearchResultComponent(FlightSearchResultComponent.FLIGHT_INFO, flightDetails));
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
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (position)
        {
            case FlightSearchResultComponent.FLIGHT_INFO:
                SingleFlightResultVH.bind((SingleFlightResultVH) holder,
                        (SingleFlightResult) flightSearchResultComponentList.get(position).getObject());
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return flightSearchResultComponentList.size();
    }
}
