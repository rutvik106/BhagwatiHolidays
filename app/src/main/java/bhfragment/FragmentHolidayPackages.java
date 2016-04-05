package bhfragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rutvik.bhagwatiholidays.App;

import adapter.LazyAdapter;

import com.rutvik.bhagwatiholidays.R;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jsonobj.PackageList;
import webservicehandler.PostHandler;

/**
 * Created by ACER on 10-Mar-16 at 11:03 AM.
 */
public class FragmentHolidayPackages extends Fragment {

    private static final String TAG = App.APP_TAG + FragmentHolidayPackages.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private final List<PackageList.Package> packages = new ArrayList<>();

    private final List<PackageList.Package> searchedPackages = new ArrayList<>();

    App app;

    ProgressBar pb;

    SwipeRefreshLayout srlPackagesList;

    LoadOffersAsync mLoadOffersAsync;

    //private int range = 20;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app = (App) activity.getApplication();
        app.trackScreenView(FragmentHolidayPackages.class.getSimpleName());

    }

    public FragmentHolidayPackages() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new LazyAdapter(getActivity());
    }

    private void filterOffers(List<PackageList.Package> modelList, String query) {

        searchedPackages.clear();

        Log.i(TAG, "FILTERING OFFERS");

        query = query.toLowerCase(Locale.getDefault());

        for (PackageList.Package model : modelList) {
            final String name = model.getPackage_name().toLowerCase(Locale.getDefault());
            final String places = model.getPlaces().toLowerCase(Locale.getDefault());
            final String days = model.getDays().toLowerCase(Locale.getDefault());
            final String nights = model.getNights().toLowerCase(Locale.getDefault());
            //Log.i(TAG, "model.getTitle(): " + );
            if (name.contains(query) || places.contains(query) || days.contains(query) || nights.contains(query)) {
                Log.i(TAG, "MATCH FOUND ADDING MODEL");
                searchedPackages.add(model);
            }
        }

        Log.i(TAG, "MODEL ARRAY SIZE: " + searchedPackages.size());

    }

    public void searchForPackage(String inputText) {

        filterOffers(packages, inputText);


        ((LazyAdapter) mAdapter).animateTo(searchedPackages);


        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_holiday_packages, container, false);

        pb = (ProgressBar) rootView.findViewById(R.id.pb);

        srlPackagesList = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_packagesList);

        srlPackagesList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mLoadOffersAsync != null) {
                    mLoadOffersAsync.cancel(true);
                }
                mLoadOffersAsync = new LoadOffersAsync();
                mLoadOffersAsync.execute();

            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rcv_holidayPackages);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);


        //loadOffersAsync();

        return rootView;
    }

    private class LoadOffersAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            if (!srlPackagesList.isRefreshing()) {
                pb.setVisibility(View.VISIBLE);
            }
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

            ((LazyAdapter) mAdapter).getPackages().clear();

            for (PackageList.Package p : packages) {
                ((LazyAdapter) mAdapter).addPackage(p);
            }

            mAdapter.notifyDataSetChanged();

            Log.i(TAG, "INSIDE ON POST EXECUTE");


            if (srlPackagesList.isRefreshing()) {
                srlPackagesList.setRefreshing(false);
            }

            pb.setVisibility(View.GONE);


        }
    }

    public void loadOffersAsync() {
        if (packages.size() == 0) {
            Log.i(TAG, "packages not found getting from internet");


            mLoadOffersAsync = new LoadOffersAsync();
            mLoadOffersAsync.execute();


        } else {
            Log.i(TAG, "packages are already loaded");
            if (mAdapter.getItemCount() == 0) {
                Log.i(TAG, "adapter item count is 0");
                for (PackageList.Package p : packages) {
                    ((LazyAdapter) mAdapter).addPackage(p);
                }
            } else {
                Log.i(TAG, "adapter item count is OK");
                Log.i(TAG, "just setting adapter and notifying data set changed");
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }

    }


/*    public boolean isLastItemDisplaying() {
        if (mRecyclerView.getAdapter().getItemCount() != 0) {
            int lastItem = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastItem != RecyclerView.NO_POSITION && lastItem == mRecyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }
        }
        return false;
    }*/


}
