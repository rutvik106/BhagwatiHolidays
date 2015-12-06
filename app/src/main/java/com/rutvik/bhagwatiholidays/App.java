package com.rutvik.bhagwatiholidays;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

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

    MyLoginWithGoogle.GooglePlusUser user;

    public void setUser(MyLoginWithGoogle.GooglePlusUser user)
    {
        this.user=user;
    }

    public MyLoginWithGoogle.GooglePlusUser getUser()
    {
        return user;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        new test().execute();
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