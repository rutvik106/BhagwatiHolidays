package liveapimodels.flightsearchresult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rutvik on 03-09-2016 at 01:44 PM.
 */

public class Fare
{

    @JsonProperty("ChargeBU")
    private ChargeBU[] ChargeBU;

    @JsonProperty("CommissionEarned")
    private String CommissionEarned;

    @JsonProperty("OfferedFare")
    private String OfferedFare;

    @JsonProperty("Tax")
    private String Tax;

    @JsonProperty("TdsOnCommission")
    private String TdsOnCommission;

    @JsonProperty("BaseFare")
    private String BaseFare;

    @JsonProperty("PLBEarned")
    private String PLBEarned;

    @JsonProperty("AdditionalTxnFeePub")
    private String AdditionalTxnFeePub;

    @JsonProperty("ServiceFee")
    private String ServiceFee;

    @JsonProperty("TdsOnIncentive")
    private String TdsOnIncentive;

    @JsonProperty("OtherCharges")
    private String OtherCharges;

    @JsonProperty("YQTax")
    private String YQTax;

    @JsonProperty("PublishedFare")
    private String PublishedFare;

    @JsonProperty("IncentiveEarned")
    private String IncentiveEarned;

    @JsonProperty("TdsOnPLB")
    private String TdsOnPLB;

    @JsonProperty("Discount")
    private String Discount;

    @JsonProperty("Currency")
    private String Currency;

    @JsonProperty("AdditionalTxnFeeOfrd")
    private String AdditionalTxnFeeOfrd;

    public ChargeBU[] getChargeBU ()
    {
        return ChargeBU;
    }

    public void setChargeBU (ChargeBU[] ChargeBU)
    {
        this.ChargeBU = ChargeBU;
    }

    public String getCommissionEarned ()
    {
        return CommissionEarned;
    }

    public void setCommissionEarned (String CommissionEarned)
    {
        this.CommissionEarned = CommissionEarned;
    }

    public String getOfferedFare ()
    {
        return OfferedFare;
    }

    public void setOfferedFare (String OfferedFare)
    {
        this.OfferedFare = OfferedFare;
    }

    public String getTax ()
    {
        return Tax;
    }

    public void setTax (String Tax)
    {
        this.Tax = Tax;
    }

    public String getTdsOnCommission ()
    {
        return TdsOnCommission;
    }

    public void setTdsOnCommission (String TdsOnCommission)
    {
        this.TdsOnCommission = TdsOnCommission;
    }

    public String getBaseFare ()
    {
        return BaseFare;
    }

    public void setBaseFare (String BaseFare)
    {
        this.BaseFare = BaseFare;
    }

    public String getPLBEarned ()
    {
        return PLBEarned;
    }

    public void setPLBEarned (String PLBEarned)
    {
        this.PLBEarned = PLBEarned;
    }

    public String getAdditionalTxnFeePub ()
    {
        return AdditionalTxnFeePub;
    }

    public void setAdditionalTxnFeePub (String AdditionalTxnFeePub)
    {
        this.AdditionalTxnFeePub = AdditionalTxnFeePub;
    }

    public String getServiceFee ()
    {
        return ServiceFee;
    }

    public void setServiceFee (String ServiceFee)
    {
        this.ServiceFee = ServiceFee;
    }

    public String getTdsOnIncentive ()
    {
        return TdsOnIncentive;
    }

    public void setTdsOnIncentive (String TdsOnIncentive)
    {
        this.TdsOnIncentive = TdsOnIncentive;
    }

    public String getOtherCharges ()
    {
        return OtherCharges;
    }

    public void setOtherCharges (String OtherCharges)
    {
        this.OtherCharges = OtherCharges;
    }

    public String getYQTax ()
    {
        return YQTax;
    }

    public void setYQTax (String YQTax)
    {
        this.YQTax = YQTax;
    }

    public String getPublishedFare ()
    {
        return PublishedFare;
    }

    public void setPublishedFare (String PublishedFare)
    {
        this.PublishedFare = PublishedFare;
    }

    public String getIncentiveEarned ()
    {
        return IncentiveEarned;
    }

    public void setIncentiveEarned (String IncentiveEarned)
    {
        this.IncentiveEarned = IncentiveEarned;
    }

    public String getTdsOnPLB ()
    {
        return TdsOnPLB;
    }

    public void setTdsOnPLB (String TdsOnPLB)
    {
        this.TdsOnPLB = TdsOnPLB;
    }

    public String getDiscount ()
    {
        return Discount;
    }

    public void setDiscount (String Discount)
    {
        this.Discount = Discount;
    }

    public String getCurrency ()
    {
        return Currency;
    }

    public void setCurrency (String Currency)
    {
        this.Currency = Currency;
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
        return "ClassPojo [ChargeBU = "+ChargeBU+", CommissionEarned = "+CommissionEarned+", OfferedFare = "+OfferedFare+", Tax = "+Tax+", TdsOnCommission = "+TdsOnCommission+", BaseFare = "+BaseFare+", PLBEarned = "+PLBEarned+", AdditionalTxnFeePub = "+AdditionalTxnFeePub+", ServiceFee = "+ServiceFee+", TdsOnIncentive = "+TdsOnIncentive+", OtherCharges = "+OtherCharges+", YQTax = "+YQTax+", PublishedFare = "+PublishedFare+", IncentiveEarned = "+IncentiveEarned+", TdsOnPLB = "+TdsOnPLB+", Discount = "+Discount+", Currency = "+Currency+", AdditionalTxnFeeOfrd = "+AdditionalTxnFeeOfrd+"]";
    }

}
