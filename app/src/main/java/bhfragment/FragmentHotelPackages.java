package bhfragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.LazyAdapter;
import com.rutvik.bhagwatiholidays.R;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jsonobj.PackageList;
import webservicehandler.PostHandler;

/**
 * Created by ACER on 10-Mar-16 at 11:03 AM.
 */
public class FragmentHotelPackages extends Fragment {

    private static final String TAG = App.APP_TAG + FragmentHotelPackages.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog progressDialog;

    private List<PackageList.Package> packages;

    App app;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        packages = new ArrayList<>();
        app = (App) activity.getApplication();
    }

    public FragmentHotelPackages() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_holiday_packages, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rcv_hotelPackages);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadOffersAsync();

        return rootView;
    }


    private void loadOffersAsync() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {

                progressDialog = ProgressDialog.show(FragmentHotelPackages.this.getActivity(), "Please Wait...", "Getting packages...", true, true);

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

                mAdapter = new LazyAdapter(FragmentHotelPackages.this.getActivity(), packages);

                Log.i(TAG, "INSIDE ON POST EXECUTE");

                mRecyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

                try {
                    if ((progressDialog != null) && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                } catch (final Exception e) {
                    // Handle or log or ignore
                } finally {
                    progressDialog = null;
                }

            }
        }.execute();

    }


}
