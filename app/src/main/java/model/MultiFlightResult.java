package model;

import android.util.Log;

import com.rutvik.bhagwatiholidays.App;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by rutvik on 09-09-2016 at 01:44 PM.
 */

public class MultiFlightResult implements FlightDetails
{

    private String publishedFair = "";

    private final List<SingleMultiFlightResult> singleMultiFlightResultList;


    private boolean isVisible=true;

    public MultiFlightResult()
    {
        singleMultiFlightResultList = new ArrayList<>();
    }

    public void addSingleMultiFlightResult(SingleMultiFlightResult singleMultiFlightResult)
    {
        singleMultiFlightResultList.add(singleMultiFlightResult);
    }

    public List<SingleMultiFlightResult> getSingleMultiFlightResultList()
    {
        return singleMultiFlightResultList;
    }

    public String getPublishedFair()
    {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.valueOf(publishedFair));
    }

    public void setPublishedFair(String publishedFair)
    {
        this.publishedFair = publishedFair;
    }

    @Override
    public double getPrice()
    {
        return Double.valueOf(publishedFair);
    }

    @Override
    public boolean getFlightType(String name)
    {
        for (SingleMultiFlightResult singleMultiFlightResult : singleMultiFlightResultList)
        {
            return singleMultiFlightResult.getFlightName().equals(name);
        }
        return false;
    }

/**    @Override
    public boolean isVisible()
    {
        return isVisible;
    }

    @Override
    public void setVisibility(boolean isVisible)
    {
        this.isVisible=isVisible;
    }*/

    public static class FairComparator implements Comparator<FlightDetails>
    {

        private static final String TAG = App.APP_TAG + FairComparator.class;

        @Override
        public int compare(FlightDetails multiFlightResult, FlightDetails multiFlightResult2)
        {
            double p1 = multiFlightResult.getPrice();
            Log.i(TAG, "p1: " + multiFlightResult.getPrice());
            double p2 = multiFlightResult2.getPrice();
            Log.i(TAG, "p2: " + multiFlightResult2.getPrice());

            if (p1 > p2)
            {
                return 1;
            } else if (p1 < p2)
            {
                return -1;
            } else
            {
                return 0;
            }
        }
    }

}
