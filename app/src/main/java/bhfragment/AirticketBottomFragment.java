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
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import adapter.MyBookingsAdapter;
import extras.CommonUtilities;
import model.FlightBooking;
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 11-06-2016 at 11:23.
 */
public class AirticketBottomFragment extends Fragment {

    public static final String TAG= App.APP_TAG+AirticketBottomFragment.class.getSimpleName();

    RecyclerView rvFlightBookingList;

    RecyclerView.LayoutManager layoutManager;

    MyBookingsAdapter adapter;

    App app;

    public static AirticketBottomFragment newInstance(int index) {
        AirticketBottomFragment fragment = new AirticketBottomFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_air_bottom, container, false);
        rvFlightBookingList=(RecyclerView) rootView.findViewById(R.id.rv_flightBookingList);
        if(rvFlightBookingList!=null){
            app=(App) getActivity().getApplication();
            layoutManager=new LinearLayoutManager(getActivity());
            rvFlightBookingList.setHasFixedSize(true);
            rvFlightBookingList.setLayoutManager(layoutManager);
            adapter=new MyBookingsAdapter(getActivity());
            rvFlightBookingList.setAdapter(adapter);
            new GetFlightBookingsAsync().execute();
        }
        return rootView;
    }

    public void getFlightBookingAsync(){
        if(adapter.getItemCount()==0) {
            new GetFlightBookingsAsync().execute();
        }
    }

    class GetFlightBookingsAsync extends AsyncTask<Void,Void,Void>{

        ProgressDialog p;

        String result="";

        @Override
        protected void onPreExecute() {
            p=ProgressDialog.show(getActivity(), "Please Wait", "Getting flight bookings...", true, true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    GetFlightBookingsAsync.this.cancel(true);
                    p.dismiss();
                }
            });
            p.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String,String> postParams=new HashMap<>();
            postParams.put("method","get_flight_bookings");
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
                JSONArray arr = new JSONObject(result).getJSONArray("flight_bookings");
                for (int i=0;i<arr.length();i++){
                    adapter.addMyBooking(new FlightBooking(arr.getJSONObject(i)));
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
