package liveapimodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rutvik on 02-09-2016 at 11:09 PM.
 */

public class Authentication
{

    @JsonProperty("TokenId")
    private String TokenId;

    @JsonProperty("Status")
    private String Status;

    @JsonProperty("Member")
    private Member Member;

    @JsonProperty("Error")
    private Error Error;

    public String getTokenId()
    {
        return TokenId;
    }

    public void setTokenId(String TokenId)
    {
        this.TokenId = TokenId;
    }

    public String getStatus()
    {
        return Status;
    }

    public void setStatus(String Status)
    {
        this.Status = Status;
    }

    public Member getMember()
    {
        return Member;
    }

    public void setMember(Member Member)
    {
        this.Member = Member;
    }

    public Error getError()
    {
        return Error;
    }

    public void setError(Error Error)
    {
        this.Error = Error;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TokenId = " + TokenId + ", Status = " + Status + ", Member = " + Member + ", Error = " + Error + "]";
    }

}
