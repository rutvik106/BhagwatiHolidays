package liveapimodels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 09:52 AM.
 */

public class Member
{

    @JsonProperty("Email")
    private String Email;

    @JsonProperty("LoginName")
    private String LoginName;

    @JsonProperty("FirstName")
    private String FirstName;

    @JsonProperty("LastName")
    private String LastName;

    @JsonProperty("AgencyId")
    private String AgencyId;

    @JsonProperty("LoginDetails")
    private String LoginDetails;

    @JsonProperty("isPrimaryAgent")
    private String isPrimaryAgent;

    @JsonProperty("MemberId")
    private String MemberId;

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String Email)
    {
        this.Email = Email;
    }

    public String getLoginName()
    {
        return LoginName;
    }

    public void setLoginName(String LoginName)
    {
        this.LoginName = LoginName;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public String getAgencyId()
    {
        return AgencyId;
    }

    public void setAgencyId(String AgencyId)
    {
        this.AgencyId = AgencyId;
    }

    public String getLoginDetails()
    {
        return LoginDetails;
    }

    public void setLoginDetails(String LoginDetails)
    {
        this.LoginDetails = LoginDetails;
    }

    public String getIsPrimaryAgent()
    {
        return isPrimaryAgent;
    }

    public void setIsPrimaryAgent(String isPrimaryAgent)
    {
        this.isPrimaryAgent = isPrimaryAgent;
    }

    public String getMemberId()
    {
        return MemberId;
    }

    public void setMemberId(String MemberId)
    {
        this.MemberId = MemberId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Email = " + Email + ", LoginName = " + LoginName + ", FirstName = " + FirstName + ", LastName = " + LastName + ", AgencyId = " + AgencyId + ", LoginDetails = " + LoginDetails + ", isPrimaryAgent = " + isPrimaryAgent + ", MemberId = " + MemberId + "]";
    }

}
