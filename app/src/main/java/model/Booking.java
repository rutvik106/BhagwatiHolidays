package model;

/**
 * Created by rutvik on 15-06-2016 at 12:32 PM.
 */
public interface Booking {

    public static final int FLIGHT_BOOKING=0;
    public static final int HOTEL_BOOKING=1;
    public static final int HOLIDAY_BOOKING=2;

    String getTitle();

    String getDate();

    int getViewType();

    String getJSON();

}
