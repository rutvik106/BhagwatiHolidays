package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:17 PM.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class Results
{

    @JsonProperty("FareBreakdown")
    private FareBreakdown[] FareBreakdown;

    @JsonProperty("Source")
    private String Source;

    @JsonProperty("FareRules")
    private FareRules[] FareRules;

    @JsonProperty("Fare")
    private Fare Fare;

    @JsonProperty("ResultIndex")
    private String ResultIndex;

/**    @JsonProperty("FareBreakdown")
    private null LastTicketDate;*/

    @JsonProperty("AirlineRemark")
    private String AirlineRemark;

    //private null TicketAdvisory;

    @JsonProperty("Segments")
    private Segments[][] Segments;

    @JsonProperty("IsLCC")
    private String IsLCC;

    @JsonProperty("IsRefundable")
    private String IsRefundable;

    @JsonProperty("ValidatingAirline")
    private String ValidatingAirline;

    @JsonProperty("AirlineCode")
    private String AirlineCode;

    public FareBreakdown[] getFareBreakdown ()
    {
        return FareBreakdown;
    }

    public void setFareBreakdown (FareBreakdown[] FareBreakdown)
    {
        this.FareBreakdown = FareBreakdown;
    }

    public String getSource ()
    {
        return Source;
    }

    public void setSource (String Source)
    {
        this.Source = Source;
    }

    public FareRules[] getFareRules ()
    {
        return FareRules;
    }

    public void setFareRules (FareRules[] FareRules)
    {
        this.FareRules = FareRules;
    }

    public Fare getFare ()
    {
        return Fare;
    }

    public void setFare (Fare Fare)
    {
        this.Fare = Fare;
    }

    public String getResultIndex ()
    {
        return ResultIndex;
    }

    public void setResultIndex (String ResultIndex)
    {
        this.ResultIndex = ResultIndex;
    }

/**    public null getLastTicketDate ()
{
    return LastTicketDate;
}

    public void setLastTicketDate (null LastTicketDate)
    {
        this.LastTicketDate = LastTicketDate;
    }*/

    public String getAirlineRemark ()
    {
        return AirlineRemark;
    }

    public void setAirlineRemark (String AirlineRemark)
    {
        this.AirlineRemark = AirlineRemark;
    }

/**    public null getTicketAdvisory ()
{
    return TicketAdvisory;
}

    public void setTicketAdvisory (null TicketAdvisory)
    {
        this.TicketAdvisory = TicketAdvisory;
    }*/

    public Segments[][] getSegments ()
    {
        return Segments;
    }

    public void setSegments (Segments[][] Segments)
    {
        this.Segments = Segments;
    }

    public String getIsLCC ()
    {
        return IsLCC;
    }

    public void setIsLCC (String IsLCC)
    {
        this.IsLCC = IsLCC;
    }

    public String getIsRefundable ()
    {
        return IsRefundable;
    }

    public void setIsRefundable (String IsRefundable)
    {
        this.IsRefundable = IsRefundable;
    }

    public String getValidatingAirline ()
    {
        return ValidatingAirline;
    }

    public void setValidatingAirline (String ValidatingAirline)
    {
        this.ValidatingAirline = ValidatingAirline;
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
        return "ClassPojo [FareBreakdown = "+FareBreakdown+", Source = "+Source+", FareRules = "+FareRules+", Fare = "+Fare+", ResultIndex = "+ResultIndex+", LastTicketDate = , AirlineRemark = "+AirlineRemark+", TicketAdvisory = , Segments = "+Segments+", IsLCC = "+IsLCC+", IsRefundable = "+IsRefundable+", ValidatingAirline = "+ValidatingAirline+", AirlineCode = "+AirlineCode+"]";
    }

}
