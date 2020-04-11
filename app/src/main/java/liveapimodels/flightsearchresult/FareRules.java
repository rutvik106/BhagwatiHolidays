package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:30 PM.
 */

public class FareRules
{

    @JsonProperty("FareBasisCode")
    private String FareBasisCode;

    @JsonProperty("Airline")
    private String Airline;

    @JsonProperty("FareRestriction")
    private String FareRestriction;

    @JsonProperty("FareRuleDetail")
    private String FareRuleDetail;

    @JsonProperty("Origin")
    private String Origin;

    @JsonProperty("Destination")
    private String Destination;

    public String getFareBasisCode ()
    {
        return FareBasisCode;
    }

    public void setFareBasisCode (String FareBasisCode)
    {
        this.FareBasisCode = FareBasisCode;
    }

    public String getAirline ()
    {
        return Airline;
    }

    public void setAirline (String Airline)
    {
        this.Airline = Airline;
    }

    public String getFareRestriction ()
    {
        return FareRestriction;
    }

    public void setFareRestriction (String FareRestriction)
    {
        this.FareRestriction = FareRestriction;
    }

    public String getFareRuleDetail ()
    {
        return FareRuleDetail;
    }

    public void setFareRuleDetail (String FareRuleDetail)
    {
        this.FareRuleDetail = FareRuleDetail;
    }

    public String getOrigin ()
    {
        return Origin;
    }

    public void setOrigin (String Origin)
    {
        this.Origin = Origin;
    }

    public String getDestination ()
    {
        return Destination;
    }

    public void setDestination (String Destination)
    {
        this.Destination = Destination;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [FareBasisCode = "+FareBasisCode+", Airline = "+Airline+", FareRestriction = "+FareRestriction+", FareRuleDetail = "+FareRuleDetail+", Origin = "+Origin+", Destination = "+Destination+"]";
    }

}
