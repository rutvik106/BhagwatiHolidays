package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rutvik on 13-06-2016 at 11:48 PM.
 */
public class FlightBooking implements Booking {

    JSONObject obj;

    public FlightBooking(JSONObject obj){
        this.obj=obj;
    }

    @Override
    public String getTitle(){
        String title="";
        try {
             title = obj.getString("from_place") + " - " + obj.getString("to_place");
        }catch (JSONException e){
            e.printStackTrace();
            title="Location not available";
        }
        return title;
    }

    @Override
    public String getDate(){
        String date="";
        try{
            date=obj.getString("depart_date");
            try {
                Date d=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(date);
                date=new SimpleDateFormat("EE, dd MMM",Locale.getDefault()).format(d);
            }catch (ParseException e){
                e.printStackTrace();
            }


        }catch (JSONException e){
            e.printStackTrace();
            date="Date not available";
        }
        return date;
    }

    @Override
    public String getJSON() {
        return obj.toString();
    }

    @Override
    public int getViewType() {
        return FLIGHT_BOOKING;
    }



}
