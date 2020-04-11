package liveapimodels;

import android.util.Log;

import com.rutvik.bhagwatiholidays.App;

import adapter.CityListAutocompleteAdapter;

/**
 * Created by rutvik on 03-09-2016 at 04:10 PM.
 */

public class City implements CityListAutocompleteAdapter.AutoCompleteDropDownItem
{

    private static final String TAG = App.APP_TAG + City.class.getSimpleName();

    private String cityFullName, cityCode;

    public String getCityFullName()
    {
        //Log.i(TAG, "getCityName: " + cityFullName);
        return cityFullName;
    }

    public String getCityCode()
    {
        return cityCode;
    }

    public void setCityFullName(String cityFullName)
    {
        //Log.i(TAG, "setCityName: "+cityFullName);
        this.cityFullName = cityFullName;
    }

    public void setCityCode(String cityCode)
    {
        this.cityCode = cityCode;
    }

    @Override
    public String getValue()
    {
        //Log.i(TAG, "getValue: CITY NAME" + getCityFullName());
        return getCityCode() + " - " + getCityFullName();
    }

    @Override
    public String getKey()
    {
        return getCityCode();
    }
}
