package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.rutvik.bhagwatiholidays.ActivityFlightSearchResult;
import com.rutvik.bhagwatiholidays.App;

import java.util.LinkedList;
import java.util.List;

import component.FlightSearchResultComponent;
import liveapimodels.flightsearchresult.FlightSearchResult;
import model.MultiFlightResult;
import model.SingleFlightResult;
import viewholders.EmptyVH;
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

    private int lastPosition = -1;

    public FlightSearchResultAdapter(final Context context)
    {
        this.context = context;
        flightSearchResultComponentList = new LinkedList<>();
    }

    public FlightSearchResultComponent addFlightSearchResult(SingleFlightResult flightDetails)
    {
        final FlightSearchResultComponent component=new FlightSearchResultComponent(FlightSearchResultComponent.FLIGHT_INFO, flightDetails);

        flightSearchResultComponentList
                .add(component);

        Log.i(TAG, "addFlightSearchResult: ADDING FLIGHT DETAILS: " + flightSearchResultComponentList.size());

        notifyItemInserted(flightSearchResultComponentList.size());

        return component;

    }

    public FlightSearchResultComponent addMultiFlightSearchResult(MultiFlightResult resultList)
    {
        final FlightSearchResultComponent component=new FlightSearchResultComponent(FlightSearchResultComponent.MULTI_FLIGHT_INFO, resultList);
        flightSearchResultComponentList
                .add(component);

        Log.i(TAG, "addMultiFlightSearchResult: ADDING FLIGHT DETAILS: " + flightSearchResultComponentList.size());

        notifyItemInserted(flightSearchResultComponentList.size());

        return component;
    }


    @Override
    public int getItemViewType(int position)
    {
        if (flightSearchResultComponentList.size() == 0)
        {
            return FlightSearchResultComponent.EMPTY_VIEW;
        }
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

            case FlightSearchResultComponent.EMPTY_VIEW:
                return EmptyVH.create(context, parent);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        setAnimation(holder.itemView, position);

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

            case FlightSearchResultComponent.EMPTY_VIEW:
                break;

        }
    }

    @Override
    public int getItemCount()
    {
        if (flightSearchResultComponentList.size() == 0)
        {
            return 1;
        }
        return flightSearchResultComponentList.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder holder)
    {
        holder.itemView.clearAnimation();
    }


    public void removeItem(int position)
    {
        Log.i(TAG, "remove item at: " + position);
        flightSearchResultComponentList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, FlightSearchResultComponent model)
    {
        Log.i(TAG, "add item at: " + position);
        flightSearchResultComponentList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition)
    {
        Log.i(TAG, "move ite from: " + fromPosition + " to: " + toPosition);
        final FlightSearchResultComponent model = flightSearchResultComponentList.remove(fromPosition);
        flightSearchResultComponentList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }


    public void animateTo(List<FlightSearchResultComponent> models) throws ArrayIndexOutOfBoundsException
    {
        Log.i(TAG, "animate to model list size: " + models.size());
        Log.i(TAG, "packages size: " + flightSearchResultComponentList.size());
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
        Log.i(TAG, "packages size: " + flightSearchResultComponentList.size());

    }


    private void applyAndAnimateRemovals(List<FlightSearchResultComponent> newModels)
    {
        for (int i = flightSearchResultComponentList.size() - 1; i >= 0; i--)
        {
            final FlightSearchResultComponent model = flightSearchResultComponentList.get(i);
            if (!newModels.contains(model))
            {
                removeItem(i);
            }
        }
    }


    private void applyAndAnimateAdditions(List<FlightSearchResultComponent> newModels)
    {
        for (int i = 0, count = newModels.size(); i < count; i++)
        {
            final FlightSearchResultComponent model = newModels.get(i);
            if (!flightSearchResultComponentList.contains(model))
            {
                addItem(i, model);
            }
        }
    }


    private void applyAndAnimateMovedItems(List<FlightSearchResultComponent> newModels)
    {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--)
        {
            final FlightSearchResultComponent model = newModels.get(toPosition);
            final int fromPosition = flightSearchResultComponentList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition)
            {
                moveItem(fromPosition, toPosition);
            }
        }
    }


}
