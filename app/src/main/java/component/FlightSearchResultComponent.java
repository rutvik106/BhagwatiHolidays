package component;

/**
 * Created by rutvik on 31-08-2016 at 09:51 PM.
 */

public class FlightSearchResultComponent<T>
{

    public static final int FLIGHT_INFO = 0;

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
}
