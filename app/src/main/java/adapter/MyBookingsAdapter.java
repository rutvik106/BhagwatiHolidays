package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.App;

import java.util.LinkedList;

import model.Booking;
import model.HolidayBooking;
import model.HotelBooking;
import viewholders.FlightBookingVH;
import viewholders.HolidayBookingVH;
import viewholders.HotelBookingVH;

/**
 * Created by rutvik on 13-06-2016 at 11:44 PM.
 */
public class MyBookingsAdapter extends RecyclerView.Adapter {

    public static final String TAG = App.APP_TAG + MyBookingsAdapter.class.getSimpleName();

    LinkedList<Booking> bookingList;

    Context context;

    public MyBookingsAdapter(Context context) {
        bookingList = new LinkedList<>();
        this.context = context;
    }

    public void addMyBooking(Booking flightBooking) {
        bookingList.add(flightBooking);
    }

    @Override
    public int getItemViewType(int position) {
        return bookingList.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case Booking.FLIGHT_BOOKING:
                return FlightBookingVH.create(context, parent);

            case Booking.HOLIDAY_BOOKING:
                return HolidayBookingVH.create(context, parent);

            case Booking.HOTEL_BOOKING:
                return HotelBookingVH.create(context, parent);

        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case Booking.FLIGHT_BOOKING:
                FlightBookingVH.bind((FlightBookingVH) holder, bookingList.get(position));
                break;

            case Booking.HOLIDAY_BOOKING:
                HolidayBookingVH.bind((HolidayBookingVH) holder, bookingList.get(position));
                break;

            case Booking.HOTEL_BOOKING:
                HotelBookingVH.bind((HotelBookingVH) holder, bookingList.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
