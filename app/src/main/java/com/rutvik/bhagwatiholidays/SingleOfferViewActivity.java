package com.rutvik.bhagwatiholidays;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Rakshit on 07-03-2016.
 */
public class SingleOfferViewActivity extends AppCompatActivity {

    ImageView offerImage;

    TextView offerType, offerValidity, offerTitle, offerDescription;

    public com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    public DisplayImageOptions options;

    private Toolbar mToolbar;

    private Button btnBookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.itinerary_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Offer Details");

        offerImage = (ImageView) findViewById(R.id.iv_offerImage);

        offerTitle = (TextView) findViewById(R.id.tv_offerTitle);

        offerType = (TextView) findViewById(R.id.tv_applicableOn);

        offerValidity = (TextView) findViewById(R.id.tv_offerValidity);

        offerDescription = (TextView) findViewById(R.id.tv_offerDescription);

        btnBookNow=(Button) findViewById(R.id.btn_bookNow);

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SingleOfferViewActivity.this,HolidayFormActivity.class));

            }
        });

        try {

            offerTitle.setText(getIntent().getStringExtra("offer_title"));

            offerDescription.setText(Html.fromHtml(getIntent().getStringExtra("offer_description")));

            offerType.setText(getIntent().getStringExtra("offer_type"));

            offerValidity.setText(getIntent().getStringExtra("offer_validity"));

            imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();

            options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.loading_image)
                    .cacheInMemory()
                    .cacheOnDisc()
                    .displayer(new RoundedBitmapDisplayer(1))
                    .build();

            imageLoader.displayImage(getIntent().getStringExtra("offer_image"),
                    offerImage,
                    options);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
