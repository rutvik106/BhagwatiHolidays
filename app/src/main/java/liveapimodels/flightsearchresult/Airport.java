package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:35 PM.
 */

public class Airport
{

    @JsonProperty("CountryName")
    private String CountryName;

    @JsonProperty("Terminal")
    private String Terminal;

    @JsonProperty("CityName")
    private String CityName;

    @JsonProperty("CityCode")
    private String CityCode;

    @JsonProperty("CountryCode")
    private String CountryCode;

    @JsonProperty("AirportCode")
    private String AirportCode;

    @JsonProperty("AirportName")
    private String AirportName;

    public String getCountryName ()
    {
        return CountryName;
    }

    public void setCountryName (String CountryName)
    {
        this.CountryName = CountryName;
    }

    public String getTerminal ()
    {
        return Terminal;
    }

    public void setTerminal (String Terminal)
    {
        this.Terminal = Terminal;
    }

    public String getCityName ()
    {
        return CityName;
    }

    public void setCityName (String CityName)
    {
        this.CityName = CityName;
    }

    public String getCityCode ()
    {
        return CityCode;
    }

    public void setCityCode (String CityCode)
    {
        this.CityCode = CityCode;
    }

    public String getCountryCode ()
    {
        return CountryCode;
    }

    public void setCountryCode (String CountryCode)
    {
        this.CountryCode = CountryCode;
    }

    public String getAirportCode ()
    {
        return AirportCode;
    }

    public void setAirportCode (String AirportCode)
    {
        this.AirportCode = AirportCode;
    }

    public String getAirportName ()
    {
        return AirportName;
    }

    public void setAirportName (String AirportName)
    {
        this.AirportName = AirportName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CountryName = "+CountryName+", Terminal = "+Terminal+", CityName = "+CityName+", CityCode = "+CityCode+", CountryCode = "+CountryCode+", AirportCode = "+AirportCode+", AirportName = "+AirportName+"]";
    }

}
