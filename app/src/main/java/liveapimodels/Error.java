package liveapimodels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 09:52 AM.
 */

public class Error
{

    @JsonProperty("ErrorMessage")
    private String ErrorMessage;

    @JsonProperty("ErrorCode")
    private String ErrorCode;

    public String getErrorMessage()
    {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage)
    {
        this.ErrorMessage = ErrorMessage;
    }

    public String getErrorCode()
    {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode)
    {
        this.ErrorCode = ErrorCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ErrorMessage = " + ErrorMessage + ", ErrorCode = " + ErrorCode + "]";
    }
}
