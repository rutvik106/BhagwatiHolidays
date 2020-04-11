package com.rutvik.bhagwatiholidays;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import liveapimodels.City;

/**
 * Created by rutvik on 04-09-2016 at 02:53 PM.
 */

public class ParseCityList
{

    private static final String TAG = App.APP_TAG + ParseCityList.class.getSimpleName();

    public static List<City> parseXML(final Context context)
    {

        final List<City> cityList = new ArrayList<>();

        final List<String> cityName = new LinkedList<>();
        final List<String> cityCode = new LinkedList<>();

        AssetManager assetManager = context.getAssets();
        try
        {
            final InputStream is = assetManager.open("CityList.xml");

            final XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            final XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(is, null);

            int event;
            String text = null;

            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT)
            {
                String name = myParser.getName();


                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("CityCode"))
                        {
                            cityCode.add(text);
                            //Log.i(TAG, "city code: " + text);
                        } else if (name.equals("CityName"))
                        {
                            cityName.add(text);
                            //Log.i(TAG, "city name: " + text);
                        }

                        /**else if (name.equals("pressure"))
                         {
                         pressure = myParser.getAttributeValue(null, "value");
                         } else if (name.equals("temperature"))
                         {
                         temperature = myParser.getAttributeValue(null, "value");
                         } else
                         {
                         }*/
                        break;
                }


                event = myParser.next();
            }

            for (int i = 0; i < cityCode.size(); i++)
            {
                City c = new City();
                c.setCityCode(cityCode.get(i));
                c.setCityFullName(cityName.get(i));
                cityList.add(c);
            }

            Log.i(TAG, "parseXML: city list size:" + cityList.size());

        } catch (IOException | XmlPullParserException e)
        {
            e.printStackTrace();
            Log.i(TAG, e.getMessage());
        }

        return cityList;

    }

}
