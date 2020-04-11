package com.rutvik.bhagwatiholidays;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;

import java.util.ArrayList;

import bhfragment.AirticketBottomFragment;
import bhfragment.HolidayBottomFragment;
import bhfragment.HotelBottomFragment;

public class MyBookingActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private FragmentManager fragmentManager;
    private AHBottomNavigation bottomNavigation;

    private AirticketBottomFragment airticketBottomFragment;
    private HolidayBottomFragment holidayBottomFragment;
    private HotelBottomFragment hotelBottomFragment;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        fragmentManager = getFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();

        //fragmentManager.beginTransaction().setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit).commit();

        initUI();

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_my_booking);
        mToolbar.setTitle("My Bookings");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(null);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        //floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
        //final AHBottomNavigationItem item0 = new AHBottomNavigationItem(R.string.list, R.drawable.ic_list_black_24dp, R.color.color_tab_1);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.flight, R.drawable.ic_airticket4, R.color.color_tab);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.hotel, R.drawable.ic_hotel2, R.color.color_tab);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.holiday, R.drawable.ic_holiday2, R.color.color_tab);

        //bottomNavigationItems.add(item0);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.setForceTitlesDisplay(true);
//      bottomNavigation.setColored(true);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(Color.parseColor("#91221b"));
        bottomNavigation.setInactiveColor(Color.parseColor("#999999"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#91221b"));

        bottomNavigation.setCurrentItem(0);
//        allListFragment = AllListFragment.newInstance(0);
        airticketBottomFragment = AirticketBottomFragment.newInstance(0);
        holidayBottomFragment = HolidayBottomFragment.newInstance(1);
        hotelBottomFragment = HotelBottomFragment.newInstance(2);


        fragmentTransaction
                .add(R.id.fragment_container, airticketBottomFragment)
                .add(R.id.fragment_container, holidayBottomFragment)
                .add(R.id.fragment_container, hotelBottomFragment)

                .show(airticketBottomFragment)
                .hide(holidayBottomFragment)
                .hide(hotelBottomFragment)
                .commit();

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {

                //Toast.makeText(MyBookingActivity.this, " " + position + " " + wasSelected, Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    airticketBottomFragment.getFlightBookingAsync();
                    fragmentManager.beginTransaction()
                            //.setCustomAnimations(R.animator.pop_enter, R.animator.pop_exit)
                            .setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)
                            .show(airticketBottomFragment)
                            .hide(holidayBottomFragment)
                            .hide(hotelBottomFragment)
                            .commit();
                } else if (position == 1) {
                    hotelBottomFragment.getHotelBookingsAsync();
                    fragmentManager.beginTransaction()
                            //.setCustomAnimations(R.animator.pop_enter, R.animator.pop_exit)
                            .setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)
                            .hide(airticketBottomFragment)
                            .hide(holidayBottomFragment)
                            .show(hotelBottomFragment)
                            .commit();
                } else if (position == 2) {
                    holidayBottomFragment.getHolidayBookingsAsync();
                    fragmentManager.beginTransaction()
                            //.setCustomAnimations(R.animator.pop_enter, R.animator.pop_exit)
                            .setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)
                            .hide(airticketBottomFragment)
                            .show(holidayBottomFragment)
                            .hide(hotelBottomFragment)
                            .commit();
                } else if (!wasSelected) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)

                            .show(airticketBottomFragment)
                            .hide(holidayBottomFragment)
                            .hide(hotelBottomFragment)
                            .commit();
                }
            }
        });
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
