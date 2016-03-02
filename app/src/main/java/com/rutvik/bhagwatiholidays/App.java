package com.rutvik.bhagwatiholidays;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;

import java.util.HashMap;

import jsonobj.PackageItenary;
import jsonobj.PackageList;
import lwg.MyLoginWithGoogle;
import webservicehandler.PostHandler;

/**
 * Created by ACER on 11-Nov-15.
 */
public class App extends Application {

    public static final String APP_TAG="BWT ";

    MyLoginWithGoogle.GooglePlusUser user;

    public void setUser(MyLoginWithGoogle.GooglePlusUser user)
    {
        this.user=user;
    }

    public MyLoginWithGoogle.GooglePlusUser getUser()
    {
        return user;
    }

    static final String[] hotelAdult = new String[] { "1","2","3","4" };
    static final String[] hotelChild = new String[] { "0","1","2" };
    static final String[] hotelInfant = new String[] { "0","1","2" };

    static final String[] flightAdult = new String[] { "1","2","3","4","5","6" };
    static final String[] flightChild = new String[] { "0","1","2","3","4","5","6" };
    static final String[] flightInfant = new String[] { "0","1","2","3","4","5","6" };



    ArrayAdapter<String> hotelAdultAdapter;
    ArrayAdapter<String> hotelChildAdapter;
    ArrayAdapter<String> hotelInfantAdapter;

    ArrayAdapter<String> flightAdultAdapter;
    ArrayAdapter<String> flightChildAdapter;
    ArrayAdapter<String> flightInfantAdapter;





    @Override
    public void onCreate() {
        super.onCreate();

        hotelAdultAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_item, hotelAdult);
        hotelChildAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_item, hotelChild);
        hotelInfantAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_item, hotelInfant);

        flightAdultAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_item, flightAdult);
        flightChildAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_item, flightChild);
        flightInfantAdapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_item, flightInfant);



        //new test().execute();
    }

    public ArrayAdapter<String> getHotelAdultAdapter()
    {
        return hotelAdultAdapter;
    }

    public ArrayAdapter<String> getHotelChildAdapter()
    {
        return hotelChildAdapter;
    }

    public ArrayAdapter<String> getHotelInfantAdapter()
    {
        return hotelInfantAdapter;
    }



    public ArrayAdapter<String> getFlightAdultAdapter()
    {
        return flightAdultAdapter;
    }

    public ArrayAdapter<String> getFlightChildAdapter()
    {
        return flightChildAdapter;
    }

    public ArrayAdapter<String> getFlightInfantAdapter()
    {
        return flightInfantAdapter;
    }

}


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
}