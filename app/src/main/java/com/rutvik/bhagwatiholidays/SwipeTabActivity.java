package com.rutvik.bhagwatiholidays;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;

import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.LikeView;
import com.google.android.gms.plus.PlusOneButton;
import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


import java.util.ArrayList;
import java.util.List;

import bhfragment.FragmentAirTickets;
import bhfragment.FragmentDrawer;
import bhfragment.FragmentHolidayPackages;
import bhfragment.FragmentHotels;
import bhfragment.FragmentVisa;
import extras.CommonUtilities;

public class SwipeTabActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentDrawer drawerFragment;

    private static final String TAG = App.APP_TAG + SwipeTabActivity.class.getSimpleName();

    final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

    private App app;

    MenuItem search;

    public com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    public DisplayImageOptions options;
    PlusOneButton mPlusOneButton;
    private static final int PLUS_ONE_REQUEST_CODE = 0;

    boolean isOnPackagesPage = false;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_tab);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.BOTTOM);

        likeView.setObjectIdAndType(
                CommonUtilities.URL_FB,
                LikeView.ObjectType.OPEN_GRAPH);

        mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);

        app = (App) getApplication();

        app.trackScreenView(SwipeTabActivity.class.getName());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setLogo(R.drawable.bh_icon);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setBackgroundColor(Color.parseColor("#2973bd"));
        //tabLayout.setTabTextColors(Color.parseColor("#a9c7e5"), Color.BLACK);
        tabLayout.setupWithViewPager(viewPager);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading_image)
                .cacheInMemory()
                .cacheOnDisc()
                .displayer(new RoundedBitmapDisplayer(7))
                .build();

        try {
            imageLoader.displayImage(app.getUser().getProfilePic(), (android.widget.ImageView) drawerFragment.getView().findViewById(R.id.iv_userImage), options);
            ((TextView) drawerFragment.getView().findViewById(R.id.tv_userName)).setText(app.getUser().getName());
            ((TextView) drawerFragment.getView().findViewById(R.id.tv_userEmail)).setText(app.getUser().getEmail());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        if (drawerFragment.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
            drawerFragment.getDrawerLayout().closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupViewPager(ViewPager viewPager) {


        adapter.addFragment(new FragmentAirTickets(), "AIR TICKETS");
        adapter.addFragment(new FragmentHotels(), "HOTELS");
        adapter.addFragment(new FragmentHolidayPackages(), "HOLIDAYS");
        adapter.addFragment(new FragmentVisa(), "VISA");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    isOnPackagesPage = true;
                    search.setVisible(isOnPackagesPage);
                    ((FragmentHolidayPackages) adapter.getItem(position)).loadOffersAsync();
                } else {
                    isOnPackagesPage = false;
                    search.setVisible(isOnPackagesPage);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bh_menu, menu);

        // Associate searchable configuration with the SearchView
        // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        search = menu.findItem(R.id.action_search);
        search.setVisible(isOnPackagesPage);
        //searchView.setVisibility(View.GONE);
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ((FragmentHolidayPackages) SwipeTabActivity.this.adapter.getItem(2)).searchForPackage(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlusOneButton.initialize(CommonUtilities.GPLUS_LINK, PLUS_ONE_REQUEST_CODE);
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.activateApp(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bh_menu, menu);
        return true;
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       *//* switch (item.getItemId()) {
            case R.id.menu_offers:
                startActivity(new Intent(SwipeTabActivity.this, OffersActivity.class));
                break;

            case R.id.menu_bus:
                startActivity(new Intent(SwipeTabActivity.this, BusActivity.class));
                break;

            case R.id.menu_settings:
                startActivity(new Intent(SwipeTabActivity.this, SettingsActivity.class));
                break;

            default:
                return super.onOptionsItemSelected(item);
        }*//*
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onDrawerItemSelected(View view, int position) {

        if (position == 1) {
            startActivity(new Intent(this, OffersActivity.class));
        } else if (position == 2) {
            Intent supportIntent = new Intent(Intent.ACTION_DIAL);
            supportIntent.setData(Uri.parse("tel:07940223333"));
            startActivity(supportIntent);
        } else if (position == 5) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"info@bhagwatiholidays.com"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } else if (position == 7) {
            startActivity(new Intent(SwipeTabActivity.this, LocateUsActivity.class));
        } else if (position == 6) {
            Log.i(TAG, "IS_NOTIFICATION_DISABLED: " + sp.getBoolean("IS_NOTIFICATION_DISABLED", false));
            if (sp.getBoolean("IS_NOTIFICATION_DISABLED", false)) {
                CommonUtilities.enableNotification(this, sp,drawerFragment.getAdapter());
            } else {
                CommonUtilities.disableNotification(this, sp,drawerFragment.getAdapter());
            }
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
