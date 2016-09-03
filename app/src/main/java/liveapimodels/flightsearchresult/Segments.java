package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 02:02 PM.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class Segments
{

    @JsonProperty("StopPointArrivalTime")
    private String StopPointArrivalTime;

    @JsonProperty("Airline")
    private Airline Airline;

    @JsonProperty("StopOver")
    private String StopOver;

    @JsonProperty("Origin")
    private Origin Origin;

    @JsonProperty("StopPointDepartureTime")
    private String StopPointDepartureTime;

    @JsonProperty("Mile")
    private String Mile;

    @JsonProperty("IsETicketEligible")
    private String IsETicketEligible;

    @JsonProperty("Status")
    private String Status;

    @JsonProperty("SegmentIndicator")
    private String SegmentIndicator;

    @JsonProperty("TripIndicator")
    private String TripIndicator;

    @JsonProperty("StopPoint")
    private String StopPoint;

    @JsonProperty("Craft")
    private String Craft;

    @JsonProperty("Duration")
    private String Duration;

    @JsonProperty("GroundTime")
    private String GroundTime;

    //private null Remark;

    @JsonProperty("FlightStatus")
    private String FlightStatus;

    @JsonProperty("Destination")
    private Destination Destination;

    public String getStopPointArrivalTime ()
    {
        return StopPointArrivalTime;
    }

    public void setStopPointArrivalTime (String StopPointArrivalTime)
    {
        this.StopPointArrivalTime = StopPointArrivalTime;
    }

    public Airline getAirline ()
    {
        return Airline;
    }

    public void setAirline (Airline Airline)
    {
        this.Airline = Airline;
    }

    public String getStopOver ()
    {
        return StopOver;
    }

    public void setStopOver (String StopOver)
    {
        this.StopOver = StopOver;
    }

    public Origin getOrigin ()
    {
        return Origin;
    }

    public void setOrigin (Origin Origin)
    {
        this.Origin = Origin;
    }

    public String getStopPointDepartureTime ()
    {
        return StopPointDepartureTime;
    }

    public void setStopPointDepartureTime (String StopPointDepartureTime)
    {
        this.StopPointDepartureTime = StopPointDepartureTime;
    }

    public String getMile ()
    {
        return Mile;
    }

    public void setMile (String Mile)
    {
        this.Mile = Mile;
    }

    public String getIsETicketEligible ()
    {
        return IsETicketEligible;
    }

    public void setIsETicketEligible (String IsETicketEligible)
    {
        this.IsETicketEligible = IsETicketEligible;
    }

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public String getSegmentIndicator ()
    {
        return SegmentIndicator;
    }

    public void setSegmentIndicator (String SegmentIndicator)
    {
        this.SegmentIndicator = SegmentIndicator;
    }

    public String getTripIndicator ()
    {
        return TripIndicator;
    }

    public void setTripIndicator (String TripIndicator)
    {
        this.TripIndicator = TripIndicator;
    }

    public String getStopPoint ()
    {
        return StopPoint;
    }

    public void setStopPoint (String StopPoint)
    {
        this.StopPoint = StopPoint;
    }

    public String getCraft ()
    {
        return Craft;
    }

    public void setCraft (String Craft)
    {
        this.Craft = Craft;
    }

    public String getDuration ()
    {
        return Duration;
    }

    public void setDuration (String Duration)
    {
        this.Duration = Duration;
    }

    public String getGroundTime ()
    {
        return GroundTime;
    }

    public void setGroundTime (String GroundTime)
    {
        this.GroundTime = GroundTime;
    }

/**    public null getRemark ()
{
    return Remark;
}

    public void setRemark (null Remark)
    {
        this.Remark = Remark;
    }*/

    public String getFlightStatus ()
    {
        return FlightStatus;
    }

    public void setFlightStatus (String FlightStatus)
    {
        this.FlightStatus = FlightStatus;
    }

    public Destination getDestination ()
    {
        return Destination;
    }

    public void setDestination (Destination Destination)
    {
        this.Destination = Destination;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StopPointArrivalTime = "+StopPointArrivalTime+", Airline = "+Airline+", StopOver = "+StopOver+", Origin = "+Origin+", StopPointDepartureTime = "+StopPointDepartureTime+", Mile = "+Mile+", IsETicketEligible = "+IsETicketEligible+", Status = "+Status+", SegmentIndicator = "+SegmentIndicator+", TripIndicator = "+TripIndicator+", StopPoint = "+StopPoint+", Craft = "+Craft+", Duration = "+Duration+", GroundTime = "+GroundTime+", Remark = , FlightStatus = "+FlightStatus+", Destination = "+Destination+"]";
    }

}
