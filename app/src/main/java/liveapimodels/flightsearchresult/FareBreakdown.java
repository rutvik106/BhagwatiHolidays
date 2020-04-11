package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:36 PM.
 */

public class FareBreakdown
{

    @JsonProperty("PassengerType")
    private String PassengerType;

    @JsonProperty("YQTax")
    private String YQTax;

    @JsonProperty("Tax")
    private String Tax;

    @JsonProperty("PassengerCount")
    private String PassengerCount;

    @JsonProperty("BaseFare")
    private String BaseFare;

    @JsonProperty("Currency")
    private String Currency;

    @JsonProperty("AdditionalTxnFeePub")
    private String AdditionalTxnFeePub;

    @JsonProperty("AdditionalTxnFeeOfrd")
    private String AdditionalTxnFeeOfrd;

    public String getPassengerType ()
    {
        return PassengerType;
    }

    public void setPassengerType (String PassengerType)
    {
        this.PassengerType = PassengerType;
    }

    public String getYQTax ()
    {
        return YQTax;
    }

    public void setYQTax (String YQTax)
    {
        this.YQTax = YQTax;
    }

    public String getTax ()
    {
        return Tax;
    }

    public void setTax (String Tax)
    {
        this.Tax = Tax;
    }

    public String getPassengerCount ()
    {
        return PassengerCount;
    }

    public void setPassengerCount (String PassengerCount)
    {
        this.PassengerCount = PassengerCount;
    }

    public String getBaseFare ()
    {
        return BaseFare;
    }

    public void setBaseFare (String BaseFare)
    {
        this.BaseFare = BaseFare;
    }

    public String getCurrency ()
    {
        return Currency;
    }

    public void setCurrency (String Currency)
    {
        this.Currency = Currency;
    }

    public String getAdditionalTxnFeePub ()
    {
        return AdditionalTxnFeePub;
    }

    public void setAdditionalTxnFeePub (String AdditionalTxnFeePub)
    {
        this.AdditionalTxnFeePub = AdditionalTxnFeePub;
    }

    public String getAdditionalTxnFeeOfrd ()
    {
        return AdditionalTxnFeeOfrd;
    }

    public void setAdditionalTxnFeeOfrd (String AdditionalTxnFeeOfrd)
    {
        this.AdditionalTxnFeeOfrd = AdditionalTxnFeeOfrd;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PassengerType = "+PassengerType+", YQTax = "+YQTax+", Tax = "+Tax+", PassengerCount = "+PassengerCount+", BaseFare = "+BaseFare+", Currency = "+Currency+", AdditionalTxnFeePub = "+AdditionalTxnFeePub+", AdditionalTxnFeeOfrd = "+AdditionalTxnFeeOfrd+"]";
    }

}
