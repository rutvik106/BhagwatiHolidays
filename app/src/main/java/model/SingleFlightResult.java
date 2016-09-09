package model;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rutvik.bhagwatiholidays.App;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by rutvik on 31-08-2016 at 09:59 PM.
 */

public class SingleFlightResult implements FlightDetails
{

    private static final String TAG = App.APP_TAG + SingleFlightResult.class.getSimpleName();

    private String flightName = "", airCode = "", startTime = "", endTime = "", airPrice = "", airPriceExtra = "",
            totalTime = "", isNonStop = "", flightNumber = "";

    public SingleFlightResult()
    {

    }

    public String getFlightName()
    {
        return flightName;
    }

    public void setFlightName(String flightName)
    {
        this.flightName = flightName;
    }

    public String getAirCode()
    {
        return airCode;
    }

    public void setAirCode(String airCode)
    {
        this.airCode = airCode;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getAirPrice()
    {
        return airPrice;
    }

    public void setAirPrice(String airPrice)
    {
        this.airPrice = airPrice;
    }

    public String getAirPriceExtra()
    {
        return airPriceExtra;
    }

    public void setAirPriceExtra(String airPriceExtra)
    {
        this.airPriceExtra = airPriceExtra;
    }

    public String getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(String totalTime)
    {
        this.totalTime = totalTime;
    }

    public String getIsNonStop()
    {
        return isNonStop;
    }

    public void setIsNonStop(String isNonStop)
    {
        this.isNonStop = isNonStop;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    @Override
    public double getPrice()
    {
        return Double.valueOf(airPrice);
    }
}
