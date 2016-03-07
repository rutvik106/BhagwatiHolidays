package com.rutvik.bhagwatiholidays;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.OffersAndPromotionsAdapter;
import jsonobj.PackageList;
import model.SimpleOffersAndPromotions;
import webservicehandler.PostHandler;

public class OffersActivity extends AppCompatActivity implements TextWatcher {

    private Toolbar mToolbar;

    private EditText etSearchBox;

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog progressDialog;

    private List<PackageList.Package> packages;

    private final List<SimpleOffersAndPromotions> modelList=new ArrayList<>();

    SharedPreferences sharedPreferences;

    private static final String TAG = App.APP_TAG + OffersActivity.class.getSimpleName();

    BroadcastReceiver offerNotificationReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setTitle("Offers & Promotions");

        offerNotificationReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadOffersAsync();
            }
        };



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etSearchBox = (EditText) findViewById(R.id.et_searchBox);

        etSearchBox.addTextChangedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_offers);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set Offers Adapter
        packages = new ArrayList<>();
        mAdapter = new OffersAndPromotionsAdapter(OffersActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        loadOffersAsync();

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(offerNotificationReceiver, new IntentFilter("bhagwatiholidays.OFFER_NOTIFICATION"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(offerNotificationReceiver);
    }

    void loadOffersAsync() {
        new AsyncTask<Void, Void, Void>() {

            String resp = "";
            boolean success = false;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(OffersActivity.this, "Please Wait...", "Getting latest offers...", true, true);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                new PostHandler(TAG, 2, 2000).doPostRequest("http://bhagwatiholidays.com/bwt-gcm-server/app/templates/GCM/get_all_offers.php", new HashMap<String, String>(), new PostHandler.ResponseCallback() {
                    @Override
                    public void response(int status, String response) {

                        Log.i(TAG, "HTTP STATUS: " + status);

                        if (status == HttpURLConnection.HTTP_OK) {

                            resp = response;

                            success = true;

                        }
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (success) {
                    Log.i(TAG, "REQUEST EXECUTED SUCCESSFULLY");
                    if (resp != "") {
                        Log.i(TAG, "RESPONSE IS NOT EMPTY");
                        try {
                            Log.i(TAG, "TRYING TO PARSE JSON RESPONSE: " + resp);

                            parseResponseAddToAdapter(resp);


                            sharedPreferences.edit().putString("SAVED_OFFERS_DATA", resp).apply();

                            Log.i(TAG, "OFFERS DATA SAVED FOR OFFLINE VIEWING ALSO");


                        } catch (JSONException e) {
                            Log.i(TAG, "ERROR PARSING JSON DATA: " + e.getMessage());
                        }

                    }
                } else {
                    Log.i(TAG, "FAILED TO CONNECT");
                    Log.i(TAG, "TRYING TO LOAD DATA FROM OFFLINE SAVED");
                    try {
                        parseResponseAddToAdapter(sharedPreferences.getString("SAVED_OFFERS_DATA", ""));
                    } catch (JSONException e) {
                        Log.i(TAG, "ERROR PARSING OFFLINE JSON DATA: " + e.getMessage());
                    } catch (NullPointerException e) {
                        Log.i(TAG, "OFFLINE DATA NULL: " + e.getMessage());
                    }
                }

                progressDialog.dismiss();

            }
        }.execute();
    }

    public void parseResponseAddToAdapter(String resp) throws JSONException {

        modelList.clear();

        ((OffersAndPromotionsAdapter) mAdapter)
                .getList().clear();

        JSONArray array = new JSONArray(resp);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            SimpleOffersAndPromotions model = new SimpleOffersAndPromotions();
            model.setImageUrl(obj.getString("image_url"));
            model.setTitle(obj.getString("title"));
            model.setDescription(obj.getString("description"));
            model.setOfferType(obj.getString("offer_type"));
            model.setValidity(obj.getString("validity"));
            ((OffersAndPromotionsAdapter) mAdapter)
                    .addOffersAndPromotionsItem(OffersAndPromotionsAdapter
                                    .OffersAndPromotionsItem.SIMPLE,
                            model);
            modelList.add(model);
        }
    }

    /*void loadOffersAsync() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {

                progressDialog = ProgressDialog.show(OffersActivity.this, "Please Wait...", "Getting offers...", true, true);

            }

            @Override
            protected Void doInBackground(Void... params) {

                HashMap<String, String> postParams = new HashMap<>();

                postParams.put("method", "get_package_list");

                new PostHandler("BWT", 4, 2000).doPostRequest("http://bhagwatiholidays.com/admin/webservice/index.php", postParams, new PostHandler.ResponseCallback() {
                    @Override
                    public void response(int status, String response) {
                        if (status == HttpURLConnection.HTTP_OK) {
                            try {
                                PackageList packageList = new PackageList(response, "package_list");
                                packages.clear();
                                for (PackageList.Package p : packageList.getPackageList()) {
                                    packages.add(p);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                progressDialog.dismiss();

                //mAdapter = new LazyAdapter(OffersActivity.this, packages);

                Log.i(TAG,"INSIDE ON POST EXECUTE");





               // mAdapter.notifyDataSetChanged();

            }
        }.execute();

    }*/

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {

        Log.i(TAG,"TEXT CHANGED...");

        Log.i(TAG,"Char Seq: "+s);

        final List<SimpleOffersAndPromotions> filteredModelList = filterOffers(modelList, s.toString());

        ((OffersAndPromotionsAdapter)mAdapter).animateTo(filteredModelList);

        mRecyclerView.scrollToPosition(0);


    }

    private List<SimpleOffersAndPromotions> filterOffers(List<SimpleOffersAndPromotions> modelList,String query){

        Log.i(TAG,"FILTERING OFFERS");

        query=query.toLowerCase();

        final List<SimpleOffersAndPromotions> filteredModelList=new ArrayList<>();

        for(SimpleOffersAndPromotions model:modelList){
            final String text=model.getTitle().toLowerCase();
            Log.i(TAG,"model.getTitle(): "+text);
            if(text.contains(query)){
                Log.i(TAG,"MATCH FOUND ADDING MODEL");
                filteredModelList.add(model);
            }
        }

        Log.i(TAG,"MODEL ARRAY SIZE: "+filteredModelList.size());

        return filteredModelList;
    }

    private List<PackageList.Package> filter(List<PackageList.Package> models, String query) {
        query = query.toLowerCase();

        final List<PackageList.Package> filteredModelList = new ArrayList<>();

        for (PackageList.Package model : models) {
            final String text = model.getPackage_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}


