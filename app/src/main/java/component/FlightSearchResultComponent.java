package component;

import model.FlightDetails;

/**
 * Created by rutvik on 31-08-2016 at 09:51 PM.
 */

public class FlightSearchResultComponent<T> implements FlightDetails
{

    public static final int FLIGHT_INFO = 0;

    public static final int MULTI_FLIGHT_INFO = 1;

    final T object;

    final int viewType;

    public FlightSearchResultComponent(final int viewType, final T object)
    {
        this.object = object;
        this.viewType = viewType;
    }

    public T getObject()
    {
        return object;
    }

    public int getViewType()
    {
        return viewType;
    }

    @Override
    public double getPrice()
    {
        return ((FlightDetails)object).getPrice();
    }
}
