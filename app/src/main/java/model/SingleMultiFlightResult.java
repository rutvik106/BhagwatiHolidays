package model;

import com.rutvik.bhagwatiholidays.App;


/**
 * Created by rutvik on 09-09-2016 at 01:44 PM.
 */

public class SingleMultiFlightResult
{

    private static final String TAG = App.APP_TAG + SingleMultiFlightResult.class.getSimpleName();

    private String flightName = "", airCode = "", startTime = "", endTime = "", originDestination = "", airPriceExtra = "",
            totalTime = "", isNonStop = "", flightNumber = "";

    public SingleMultiFlightResult()
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

    public String getOriginDestination()
    {
        return originDestination;
    }

    public void setOriginDestination(String originDestination)
    {
        this.originDestination = originDestination;
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

}
