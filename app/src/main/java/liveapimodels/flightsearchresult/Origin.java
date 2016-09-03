package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:33 PM.
 */

public class Origin
{

    @JsonProperty("Airport")
    private Airport Airport;

    @JsonProperty("DepTime")
    private String DepTime;

    public Airport getAirport ()
    {
        return Airport;
    }

    public void setAirport (Airport Airport)
    {
        this.Airport = Airport;
    }

    public String getDepTime ()
    {
        return DepTime;
    }

    public void setDepTime (String DepTime)
    {
        this.DepTime = DepTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Airport = "+Airport+", DepTime = "+DepTime+"]";
    }

}
