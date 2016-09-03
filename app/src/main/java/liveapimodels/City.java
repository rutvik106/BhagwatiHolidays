package liveapimodels;

/**
 * Created by rutvik on 03-09-2016 at 04:10 PM.
 */

public class City
{
    final String cityName, cityCode;

    public City(final String cityName, final String cityCode)
    {
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    public String getCityName()
    {
        return cityName;
    }

    public String getCityCode()
    {
        return cityCode;
    }


}
