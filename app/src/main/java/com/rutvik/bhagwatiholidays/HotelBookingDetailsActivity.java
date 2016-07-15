package com.rutvik.bhagwatiholidays;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rutvik on 23-06-2016 at 01:19 PM.
 */

public class HotelBookingDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    JSONObject obj;

    TextView
            tvBdEmail,tvBdContact,
            tvBdDestination,
            tvBdLocationType,tvBdBookingDate,tvBdNights,
            tvBdAdult,tvBdChild,tvBdInfant,
            tvBdInquiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_booking_details);

        mToolbar=(Toolbar) findViewById(R.id.toolbar_bookingDetails);

        if(mToolbar!=null){
            mToolbar.setTitle("Booking Details");
            setSupportActionBar(mToolbar);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvBdEmail=(TextView) findViewById(R.id.tv_bdEmail);
        tvBdContact=(TextView) findViewById(R.id.tv_bdContact);
        tvBdLocationType=(TextView) findViewById(R.id.tv_bdLocationType);
        tvBdDestination=(TextView) findViewById(R.id.tv_bdDestination);
        tvBdBookingDate=(TextView) findViewById(R.id.tv_bdBookingDate);
        tvBdAdult=(TextView) findViewById(R.id.tv_bdAdult);
        tvBdChild=(TextView) findViewById(R.id.tv_bdChild);
        tvBdInfant=(TextView) findViewById(R.id.tv_bdInfant);
        tvBdNights=(TextView) findViewById(R.id.tv_bdNights);
        tvBdInquiryDate=(TextView) findViewById(R.id.tv_bdInquiryDate);


        String JSON = getIntent().getStringExtra("JSON");
        if (!JSON.isEmpty()) {
            try {
                obj = new JSONObject(JSON);

                tvBdEmail.setText(obj.getString("email"));
                tvBdContact.setText(obj.getString("contact"));
                tvBdLocationType.setText(obj.getString("location_type"));
                tvBdAdult.setText(obj.getString("no_adult"));
                tvBdChild.setText(obj.getString("no_child"));
                tvBdInfant.setText(obj.getString("no_infant"));
                tvBdInquiryDate.setText(obj.getString("inquiry_date"));
                tvBdBookingDate.setText(obj.getString("booking_date"));
                tvBdDestination.setText(obj.getString("destination"));
                tvBdNights.setText(obj.getString("no_of_nights"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
