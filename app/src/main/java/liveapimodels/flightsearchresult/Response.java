package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

import liveapimodels.Error;

/**
 * Created by rutvik on 03-09-2016 at 01:15 PM.
 */

public class Response
{

    @JsonProperty("ResponseStatus")
    private String ResponseStatus;

    @JsonProperty("TraceId")
    private String TraceId;

    @JsonProperty("Origin")
    private String Origin;

    @JsonProperty("Results")
    private Results[][] Results;

    @JsonProperty("Destination")
    private String Destination;

    @JsonProperty("Error")
    private liveapimodels.Error Error;

    public String getResponseStatus ()
    {
        return ResponseStatus;
    }

    public void setResponseStatus (String ResponseStatus)
    {
        this.ResponseStatus = ResponseStatus;
    }

    public String getTraceId ()
    {
        return TraceId;
    }

    public void setTraceId (String TraceId)
    {
        this.TraceId = TraceId;
    }

    public String getOrigin ()
    {
        return Origin;
    }

    public void setOrigin (String Origin)
    {
        this.Origin = Origin;
    }

    public Results[][] getResults ()
    {
        return Results;
    }

    public void setResults (Results[][] Results)
    {
        this.Results = Results;
    }

    public String getDestination ()
    {
        return Destination;
    }

    public void setDestination (String Destination)
    {
        this.Destination = Destination;
    }

    public Error getError ()
    {
        return Error;
    }

    public void setError (Error Error)
    {
        this.Error = Error;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ResponseStatus = "+ResponseStatus+", TraceId = "+TraceId+", Origin = "+Origin+", Results = "+Results[0]+", Destination = "+Destination+", Error = "+Error+"]";
    }

}
