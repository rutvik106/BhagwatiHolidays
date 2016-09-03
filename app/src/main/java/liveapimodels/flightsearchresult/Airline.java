package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:54 PM.
 */

public class Airline
{

    @JsonProperty("AirlineName")
    private String AirlineName;

    @JsonProperty("FlightNumber")
    private String FlightNumber;

    @JsonProperty("OperatingCarrier")
    private String OperatingCarrier;

    @JsonProperty("FareClass")
    private String FareClass;

    @JsonProperty("AirlineCode")
    private String AirlineCode;

    public String getAirlineName ()
    {
        return AirlineName;
    }

    public void setAirlineName (String AirlineName)
    {
        this.AirlineName = AirlineName;
    }

    public String getFlightNumber ()
    {
        return FlightNumber;
    }

    public void setFlightNumber (String FlightNumber)
    {
        this.FlightNumber = FlightNumber;
    }

    public String getOperatingCarrier ()
    {
        return OperatingCarrier;
    }

    public void setOperatingCarrier (String OperatingCarrier)
    {
        this.OperatingCarrier = OperatingCarrier;
    }

    public String getFareClass ()
    {
        return FareClass;
    }

    public void setFareClass (String FareClass)
    {
        this.FareClass = FareClass;
    }

    public String getAirlineCode ()
    {
        return AirlineCode;
    }

    public void setAirlineCode (String AirlineCode)
    {
        this.AirlineCode = AirlineCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [AirlineName = "+AirlineName+", FlightNumber = "+FlightNumber+", OperatingCarrier = "+OperatingCarrier+", FareClass = "+FareClass+", AirlineCode = "+AirlineCode+"]";
    }

}
