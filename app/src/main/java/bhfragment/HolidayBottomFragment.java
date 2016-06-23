package bhfragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import adapter.MyBookingsAdapter;
import extras.CommonUtilities;
import model.FlightBooking;
import model.HolidayBooking;
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 11-06-2016 at 11:23.
 */
public class HolidayBottomFragment extends Fragment {

    public static final String TAG= App.APP_TAG+HolidayBottomFragment.class.getSimpleName();

    RecyclerView rvHolidayBookingList;

    RecyclerView.LayoutManager layoutManager;

    MyBookingsAdapter adapter;

    App app;

    public static HolidayBottomFragment newInstance(int index) {
        HolidayBottomFragment fragment = new HolidayBottomFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_holiday_bottom, container, false);
        rvHolidayBookingList =(RecyclerView) rootView.findViewById(R.id.rv_holidayBookingList);
        if(rvHolidayBookingList !=null){
            app=(App) getActivity().getApplication();
            layoutManager=new LinearLayoutManager(getActivity());
            rvHolidayBookingList.setHasFixedSize(true);
            rvHolidayBookingList.setLayoutManager(layoutManager);
            adapter=new MyBookingsAdapter(getActivity());
            rvHolidayBookingList.setAdapter(adapter);
            //new GetHolidayBookingsAsync().execute();
        }
        return rootView;
    }


    public void getHolidayBookingsAsync(){
        if(adapter.getItemCount()==0){
            new GetHolidayBookingsAsync().execute();
        }
    }

    class GetHolidayBookingsAsync extends AsyncTask<Void,Void,Void> {

        ProgressDialog p;

        String result="";

        @Override
        protected void onPreExecute() {
            p=ProgressDialog.show(getActivity(), "Please Wait", "Getting holiday bookings...", true, true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    GetHolidayBookingsAsync.this.cancel(true);
                    p.dismiss();
                }
            });
            p.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String,String> postParams=new HashMap<>();
            postParams.put("method","get_holiday_bookings");
            postParams.put("email",app.getUser().getEmail());

            new PostHandler(TAG,2,4000).doPostRequest(CommonUtilities.URL_WEBSERVICE, postParams, new PostHandler.ResponseCallback() {
                @Override
                public void response(int status, String response) {
                    if(status== HttpURLConnection.HTTP_OK){
                        result=response;
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                Log.i(TAG,"RESULT: "+result);
                JSONArray arr = new JSONObject(result).getJSONArray("holiday_bookings");
                for (int i=0;i<arr.length();i++){
                    adapter.addMyBooking(new HolidayBooking(arr.getJSONObject(i)));
                }
                adapter.notifyDataSetChanged();
            }catch (JSONException e){
                e.printStackTrace();
            }finally {
                if(p!=null) {
                    p.dismiss();
                }
            }
        }
    }

}
