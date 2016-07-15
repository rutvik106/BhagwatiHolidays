package com.rutvik.bhagwatiholidays;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class BookingDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    JSONObject obj;

    LinearLayout llBookingDetails;

    TextView
            tvBdEmail,tvBdContact,
            tvBdLocationType,tvBdTripType,
            tvBdDepartDate,tvBdReturnDate,
            tvBdFrom,tvBdTo,
            tvBdAdult,tvBdChild,tvBdInfant,
            tvBdFlightClass,tvBdInquiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_booking_details);

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
        tvBdTripType=(TextView) findViewById(R.id.tv_bdTripType);
        tvBdFrom=(TextView) findViewById(R.id.tv_bdFrom);
        tvBdTo=(TextView) findViewById(R.id.tv_bdTo);
        tvBdDepartDate=(TextView) findViewById(R.id.tv_bdDepartDate);
        tvBdReturnDate=(TextView) findViewById(R.id.tv_bdReturnDate);
        tvBdAdult=(TextView) findViewById(R.id.tv_bdAdult);
        tvBdChild=(TextView) findViewById(R.id.tv_bdChild);
        tvBdInfant=(TextView) findViewById(R.id.tv_bdInfant);
        tvBdFlightClass=(TextView) findViewById(R.id.tv_bdFlightClass);
        tvBdInquiryDate=(TextView) findViewById(R.id.tv_bdInquiryDate);


        String JSON = getIntent().getStringExtra("JSON");
        if (!JSON.isEmpty()) {
            try {
                obj = new JSONObject(JSON);

                tvBdEmail.setText(obj.getString("email"));
                tvBdContact.setText(obj.getString("contact"));
                tvBdLocationType.setText(obj.getString("location_type"));
                tvBdTripType.setText(obj.getString("trip_type"));
                tvBdFrom.setText(obj.getString("from_place"));
                tvBdTo.setText(obj.getString("to_place"));
                tvBdDepartDate.setText(obj.getString("depart_date"));
                tvBdReturnDate.setText(obj.getString("return_date"));
                tvBdAdult.setText(obj.getString("no_adult"));
                tvBdChild.setText(obj.getString("no_child"));
                tvBdInfant.setText(obj.getString("no_infant"));
                tvBdFlightClass.setText(obj.getString("flight_class"));
                tvBdInquiryDate.setText(obj.getString("inquiry_date"));

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
