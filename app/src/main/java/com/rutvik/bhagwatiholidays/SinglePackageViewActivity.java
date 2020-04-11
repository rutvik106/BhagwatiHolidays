package com.rutvik.bhagwatiholidays;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import extras.CommonUtilities;
import jsonobj.PackageItenary;
import webservicehandler.PostHandler;

public class SinglePackageViewActivity extends AppCompatActivity {

    private static final String TAG = App.APP_TAG + SinglePackageViewActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    private TextView tvPackageDescription, tvSuggestedLength, tvPlaces; //tvPriceFrom;//, tvPlaces;

    private Button btnBookNow;

    private ImageView ivPackageImage;

    private Toolbar mToolbar;

    private ArrayList<String> packagePrices = new ArrayList<String>();

    private String inclusions, exclusions, packageDestination, packageLocationType;

    private String packagePrice = "";

    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    private DisplayImageOptions options;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_package_view_new);

        app = (App) getApplication();

        app.trackScreenView(SinglePackageViewActivity.class.getSimpleName());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra("package_name"));

        btnBookNow = (Button) findViewById(R.id.btn_bookNow);

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                app.trackEvent(SingleOfferViewActivity.class.getSimpleName(), "BOOK NOW CLICKED", "BUTTON");
                final Intent i = new Intent(SinglePackageViewActivity.this, HolidayFormActivity.class);
                i.putExtra("requesting_activity", "single_package_view_activity");

                i.putExtra("package_id", getIntent().getStringExtra("package_id"));
                i.putExtra("package_destination", packageDestination);

                i.putExtra("package_location_type", packageLocationType);

                if (packagePrices.size() > 0) {
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(SinglePackageViewActivity.this);

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SinglePackageViewActivity.this,
                            android.R.layout.simple_list_item_1, packagePrices);

                    // 3. Get the AlertDialog from create()
                    AlertDialog.Builder dialog = builder.setTitle("Pick a package")
                            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    i.putExtra("package_price", packagePrices.get(which));

                                    startActivity(i);
                                }
                            });

                    dialog.show();

                } else {

                    i.putExtra("package_price", "On Request");

                    startActivity(i);

                }

            }
        });

        ivPackageImage = (ImageView) findViewById(R.id.iv_packageImage);

        tvSuggestedLength = (TextView) findViewById(R.id.tv_suggestedLength);

        tvSuggestedLength.setText(getIntent().getStringExtra("package_days") + " Days / " + getIntent().getStringExtra("package_nights") + " Nights");

        tvPlaces = (TextView) findViewById(R.id.tv_places);

        tvPlaces.setText(getIntent().getStringExtra("package_place"));
        tvPlaces.setSelected(true);

        //tvPriceFrom = (TextView) findViewById(R.id.tv_priceFrom2);
        //tvPriceFrom.setSelected(true);

        inclusions = getIntent().getStringExtra("inclusions");

        exclusions = getIntent().getStringExtra("exclusions");

        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading_image)
                .cacheInMemory()
                .cacheOnDisc()
                .displayer(new RoundedBitmapDisplayer(5))
                .build();

        try {
            imageLoader.displayImage(getIntent().getStringExtra("package_image"), ivPackageImage, options);
        } catch (Exception e) {
            Log.i(TAG, "ERROR LOADING IMAGE: " + e.getMessage());
        }


        tvPackageDescription = (TextView) findViewById(R.id.tv_packageDescription);

        getPackageDetailsAsync(getIntent().getStringExtra("package_id"));


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                //Toast.makeText(SinglePackageViewActivity.this, "Content shared Successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SinglePackageViewActivity.this, "Sharing failed.", Toast.LENGTH_SHORT).show();
            }
        });

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

            case R.id.action_share_fb:
                shareContentOnFacebook();
                return true;

            case R.id.action_share:
                app.trackEvent(SinglePackageViewActivity.class.getSimpleName(), "SIMPLE SHARING", "SHARE");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey, Watch out this new exciting holiday package from Bhagwati Holidays " +
                                CommonUtilities.URL_WEBSITE_PACKAGE + getIntent().getStringExtra("package_id"));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getPackageDetailsAsync(final String packageId) {
        new AsyncTask<Void, Void, Void>() {

            String htmlContent = "";

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(SinglePackageViewActivity.this, "Please Wait...", "Getting package details...", true, true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        cancel(true);
                        SinglePackageViewActivity.this.finish();
                    }
                });
            }

            @Override
            protected Void doInBackground(Void... params) {


                HashMap<String, String> postParams = new HashMap<>();

                postParams.put("method", "get_single_package");
                postParams.put("package_id", packageId);

                new PostHandler("BWT", 4, 2000).doPostRequest("http://bhagwatiholidays.com/admin/webservice/index.php",
                        postParams,
                        new PostHandler.ResponseCallback() {
                            @Override
                            public void response(int status, String response) {
                                if (status == HttpURLConnection.HTTP_OK) {
                                    try {

                                        JSONObject responseObj = new JSONObject(response);

                                        try {
                                            JSONArray arr = responseObj.getJSONArray("package_price");
                                            for (int i = 0; i < arr.length(); i++) {
                                                packagePrices.add(arr.getString(i));
                                                packagePrice += arr.getString(i) + " | ";
                                            }
                                        } catch (JSONException j) {
                                            packagePrice = responseObj.getString("package_price");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        packageLocationType = responseObj.getString("location_type");

                                        packageDestination = responseObj.getString("destination");

                                        PackageItenary pkg = new PackageItenary(response, "package");

                                        ArrayList<String> headings = new ArrayList<String>();
                                        ArrayList<String> descriptions = new ArrayList<String>();


                                        for (String heading : pkg.getItenaryHeading()) {
                                            headings.add(heading);
                                        }

                                        for (String desc : pkg.getItenaryDescription()) {
                                            descriptions.add(desc);
                                        }

                                        for (int i = 0; i < headings.size(); i++) {
                                            htmlContent = htmlContent + headings.get(i) + descriptions.get(i);
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
                        "<pre>" + inclusions + "</pre><br/>" +
                        "<h4><font color='#e2bb3d'>EXCLUSIONS</font></h4><br/>" +
                        "<pre>" + exclusions + "</pre><br/>"));

                //tvPriceFrom.setText(packagePrice);

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        }.execute();
    }


    private void shareContentOnFacebook() {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            app.trackEvent(SinglePackageViewActivity.class.getSimpleName(), "FB SHARING", "SHARE");
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(getIntent().getStringExtra("package_name"))
                    .setContentDescription(CommonUtilities.URL_WEBSITE_PACKAGE +
                            getIntent().getStringExtra("package_id"))
                    .setImageUrl(Uri.parse(getIntent().getStringExtra("package_image")))
                    .setContentUrl(Uri.parse(CommonUtilities.URL_WEBSITE_PACKAGE +
                            getIntent().getStringExtra("package_id")))
                    .build();
            shareDialog.show(linkContent);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
