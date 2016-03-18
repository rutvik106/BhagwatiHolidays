package com.rutvik.bhagwatiholidays;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import jsonobj.PackageItenary;
import webservicehandler.PostHandler;

public class ItineraryListActivity extends AppCompatActivity {

    private static final String TAG=App.APP_TAG+ItineraryListActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    private TextView tvPackageDescription,tvSuggestedLength,tvPriceFrom,tvPlaces;

    private ImageView ivPackageImage;

    private Toolbar mToolbar;

    private String inclusions,exclusions,packagePrice;

    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_list);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra("package_name"));

        ivPackageImage=(ImageView) findViewById(R.id.iv_packageImage);

        tvSuggestedLength=(TextView) findViewById(R.id.tv_suggestedLength);

        tvSuggestedLength.setText(getIntent().getStringExtra("package_days") + " Days / " + getIntent().getStringExtra("package_nights")+" Nights");

        tvPlaces=(TextView) findViewById(R.id.tv_places);

        tvPlaces.setText(getIntent().getStringExtra("package_place"));

        tvPlaces.setSelected(true);

        tvPriceFrom=(TextView) findViewById(R.id.tv_priceFrom);

        inclusions=getIntent().getStringExtra("inclusions");

        exclusions=getIntent().getStringExtra("exclusions");

        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading_image)
                .cacheInMemory()
                .cacheOnDisc()
                .displayer(new RoundedBitmapDisplayer(5))
                .build();

        try {
            imageLoader.displayImage(getIntent().getStringExtra("package_image"), ivPackageImage, options);
        }catch (Exception e){
            Log.i(TAG,"ERROR LOADING IMAGE: "+e.getMessage());
        }


        tvPackageDescription=(TextView) findViewById(R.id.tv_packageDescription);

        getPackageDetailsAsync(getIntent().getStringExtra("package_id"));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.package_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_share:
                shareContent();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getPackageDetailsAsync(final String packageId){
        new AsyncTask<Void,Void,Void>(){

            String htmlContent="";

            @Override
            protected void onPreExecute() {
                progressDialog=ProgressDialog.show(ItineraryListActivity.this,"Please Wait...","Getting package details...",true,false);
            }

            @Override
            protected Void doInBackground(Void... params) {


                HashMap<String,String> postParams = new HashMap<>();

                postParams.put("method", "get_single_package");
                postParams.put("package_id", packageId);

                new PostHandler("BWT",4,2000).doPostRequest("http://bhagwatiholidays.com/admin/webservice/index.php", postParams, new PostHandler.ResponseCallback() {
                    @Override
                    public void response(int status, String response) {
                        if (status == HttpURLConnection.HTTP_OK) {
                            try {

                                packagePrice=new JSONObject(response).getString("package_price");


                                PackageItenary pkg = new PackageItenary(response, "package");

                                ArrayList<String> headings=new ArrayList<String>();
                                ArrayList<String> descriptions=new ArrayList<String>();




                                for (String heading : pkg.getItenaryHeading()) {
                                    headings.add(heading);
                                }

                                for(String desc:pkg.getItenaryDescription()){
                                    descriptions.add(desc);
                                }

                                for(int i=0;i<headings.size();i++){
                                    htmlContent=htmlContent+headings.get(i)+descriptions.get(i);
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

                tvPackageDescription.setText(Html.fromHtml(htmlContent +
                        "<h4><font color='#e2bb3d'>INCLUSIONS</font></h4><br/>" +
                        "<pre>"+inclusions+"</pre><br/>" +
                        "<h4><font color='#e2bb3d'>EXCLUSIONS</font></h4><br/>" +
                        "<pre>"+exclusions+"</pre><br/>"));

                tvPriceFrom.setText(packagePrice);

                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
            }
        }.execute();
    }




    private void shareContent(){

    }




}
