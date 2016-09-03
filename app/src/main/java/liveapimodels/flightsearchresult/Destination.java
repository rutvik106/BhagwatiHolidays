package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:32 PM.
 */

public class Destination
{

    @JsonProperty("Airport")
    private Airport Airport;

    @JsonProperty("ArrTime")
    private String ArrTime;

    public Airport getAirport ()
    {
        return Airport;
    }

    public void setAirport (Airport Airport)
    {
        this.Airport = Airport;
    }

    public String getArrTime ()
    {
        return ArrTime;
    }

    public void setArrTime (String ArrTime)
    {
        this.ArrTime = ArrTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Airport = "+Airport+", ArrTime = "+ArrTime+"]";
    }

}
