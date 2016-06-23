package com.rutvik.bhagwatiholidays;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        fragmentManager = getSupportFragmentManager();

        initUI();

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_my_booking);
        mToolbar.setTitle("My Bookings");
        setSupportActionBar(mToolbar);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
//        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
        //final AHBottomNavigationItem item0 = new AHBottomNavigationItem(R.string.list, R.drawable.ic_list_black_24dp, R.color.color_tab_1);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.flight, R.drawable.ic_airticket, R.color.color_tab);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.holiday, R.drawable.ic_holiday, R.color.color_tab);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.hotel, R.drawable.ic_hotel, R.color.color_tab);

        //bottomNavigationItems.add(item0);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.setForceTitlesDisplay(true);
//        bottomNavigation.setColored(true);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(Color.parseColor("#000000"));
        bottomNavigation.setInactiveColor(Color.parseColor("#91221b"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#91221b"));

        bottomNavigation.setCurrentItem(0);
//        allListFragment = AllListFragment.newInstance(0);
        airticketBottomFragment = AirticketBottomFragment.newInstance(0);
        holidayBottomFragment = HolidayBottomFragment.newInstance(1);
        hotelBottomFragment = HotelBottomFragment.newInstance(2);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, airticketBottomFragment)
                .commit();

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                Toast.makeText(MyBookingActivity.this, " " + position + " " + wasSelected, Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, airticketBottomFragment)
                            .commit();
                } else if (position == 1) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, holidayBottomFragment)
                            .commit();

                } else if (position == 2) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, hotelBottomFragment)
                            .commit();
                } else if (!wasSelected) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, airticketBottomFragment)
                            .commit();
                }
            }
        });
    }
}
