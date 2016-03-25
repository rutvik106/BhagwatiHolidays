package com.rutvik.bhagwatiholidays;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import extras.AnalyticsTrackers;
import io.branch.referral.Branch;
import jsonobj.PackageItenary;
import jsonobj.PackageList;
import model.User;
import webservicehandler.PostHandler;

/**
 * Created by ACER on 11-Nov-15.
 */
public class App extends Application {

    public static final String APP_TAG = "BWT ";

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    static final String[] hotelAdult = new String[]{"1", "2", "3", "4"};
    static final String[] hotelChild = new String[]{"0", "1", "2"};
    static final String[] hotelInfant = new String[]{"0", "1", "2"};

    static final String[] flightAdult = new String[]{"1", "2", "3", "4", "5", "6"};
    static final String[] flightChild = new String[]{"0", "1", "2", "3", "4", "5", "6"};
    static final String[] flightInfant = new String[]{"0", "1", "2", "3", "4", "5", "6"};

    static final String[] noOfNights = new String[]{"1", "2", "3", "4", "5", "6", "7"};


    ArrayAdapter<String> hotelAdultAdapter;
    ArrayAdapter<String> hotelChildAdapter;
    ArrayAdapter<String> hotelInfantAdapter;

    ArrayAdapter<String> flightAdultAdapter;
    ArrayAdapter<String> flightChildAdapter;
    ArrayAdapter<String> flightInfantAdapter;
    ArrayAdapter<String> noOfNightsAdapter;


    private ImageLoaderConfiguration imageLoaderConfiguration;


    @Override
    public void onCreate() {
        super.onCreate();

        Branch.getInstance(this, "key_live_jobYwEC4RDj7qiGLV32VjhfiuuomI8ua");

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache()).build();

        ImageLoader.getInstance().init(imageLoaderConfiguration);

        // END - UNIVERSAL IMAGE LOADER SETUP


        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);





        hotelAdultAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner_item, hotelAdult);
        hotelChildAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner_item, hotelChild);
        hotelInfantAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner_item, hotelInfant);

        flightAdultAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner_item, flightAdult);
        flightChildAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner_item, flightChild);
        flightInfantAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner_item, flightInfant);

        noOfNightsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner_item, noOfNights);


        //new test().execute();
    }

    public ArrayAdapter<String> getHotelAdultAdapter() {
        return hotelAdultAdapter;
    }

    public ArrayAdapter<String> getHotelChildAdapter() {
        return hotelChildAdapter;
    }

    public ArrayAdapter<String> getHotelInfantAdapter() {
        return hotelInfantAdapter;
    }


    public ArrayAdapter<String> getFlightAdultAdapter() {
        return flightAdultAdapter;
    }

    public ArrayAdapter<String> getFlightChildAdapter() {
        return flightChildAdapter;
    }

    public ArrayAdapter<String> getFlightInfantAdapter() {
        return flightInfantAdapter;
    }

    public ArrayAdapter<String> getNoOfNightsAdapter() {
        return noOfNightsAdapter;
    }


    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /**
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /**
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                            .setDescription(
                                    new StandardExceptionParser(this, null)
                                            .getDescription(Thread.currentThread().getName(), e))
                            .setFatal(false)
                            .build()
            );
        }
    }

    /**
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }


}


/*
class test extends AsyncTask<Void, Void, Void>
{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        HashMap<String,String> postParams = new HashMap<>();

        postParams.put("method","get_package_list");

        String response = new PostHandler("BWT",4,2000).doPostRequest("http://bhagwatiholidays.com/admin/webservice/index.php",postParams);

        try {
            PackageList packageList = new PackageList(response,"package_list");

            for(PackageList.Package p:packageList.getPackageList())
            {
                Log.d("BWT", p.getThumb_href());

                HashMap<String,String> postParams2 = new HashMap<>();

                postParams2.put("method","get_single_package");
                postParams2.put("package_id", p.getPackage_id());

                String response2 = new PostHandler("BWT",4,2000).doPostRequest("http://bhagwatiholidays.com/admin/webservice/index.php",postParams2);

                PackageItenary i=new PackageItenary(response2,"package");

                for(String heading:i.getItenaryHeading())
                {
                    Log.i("BWT",heading);
                }
            }
            //Log.i("BWT", packageList.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}*/
