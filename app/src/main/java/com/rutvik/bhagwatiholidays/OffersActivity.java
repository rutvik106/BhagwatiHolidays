package com.rutvik.bhagwatiholidays;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jsonobj.PackageList;
import webservicehandler.PostHandler;

public class OffersActivity extends AppCompatActivity implements TextWatcher {

    private Toolbar mToolbar;

    private EditText etSearchBox;

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog progressDialog;

    private List<PackageList.Package> packages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setTitle("Offers & Promotions");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etSearchBox=(EditText) findViewById(R.id.et_searchBox);

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



        loadOffersAsync();

    }


    void loadOffersAsync() {

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

                mAdapter = new LazyAdapter(OffersActivity.this, packages);

                mRecyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

            }
        }.execute();

    }

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

        final List<PackageList.Package> filteredModelList = filter(packages, s.toString());
        ((LazyAdapter)mAdapter).animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);


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


