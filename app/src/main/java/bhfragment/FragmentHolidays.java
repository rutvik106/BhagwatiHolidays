package bhfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import extras.Submit;
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentHolidays extends Fragment {

    EditText etMobileNo, etBookingDate, etDestination;
    RadioButton rbIndia, rbWorldWild;
    Spinner spAdult, spChild, spInfant, spNoOfNights;
    RatingBar rbPackageType;
    FloatingActionButton fabDone;

    App app;
    private static final String TAG = App.APP_TAG + FragmentHolidays.class.getSimpleName();


    public FragmentHolidays() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app = (App) activity.getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_holidays, container, false);

        spAdult = (Spinner) rootView.findViewById(R.id.sp_adult);
        spAdult.setAdapter(app.getHotelAdultAdapter());
        spAdult.setSelection(0);

        spChild = (Spinner) rootView.findViewById(R.id.sp_child);
        spChild.setAdapter(app.getHotelChildAdapter());
        spChild.setSelection(0);

        spInfant = (Spinner) rootView.findViewById(R.id.sp_infant);
        spInfant.setAdapter(app.getHotelInfantAdapter());
        spInfant.setSelection(0);

        spNoOfNights = (Spinner) rootView.findViewById(R.id.sp_noOfNights);
        spNoOfNights.setAdapter(app.getNoOfNightsAdapter());
        spNoOfNights.setSelection(0);

        spNoOfNights = (Spinner) rootView.findViewById(R.id.sp_noOfNights);

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

        rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWide);

        rbPackageType = (RatingBar) rootView.findViewById(R.id.rb_packageType);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        etDestination = (EditText) rootView.findViewById(R.id.et_destination);

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();

            }
        });

        return rootView;
    }


    private void submitForm(){
        try {

            Map<String, String> formParams = new HashMap<String, String>();
            formParams.put("Email-ID", "rakshit106@outlook.com");
            formParams.put("Name", "Rakshit");

            JSONArray array = new JSONArray();

            Iterator iterator = formParams.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                array.put(new JSONObject("{\""+pair.getKey()+"\":" + "\"" + pair.getValue() + "\"}"));
                iterator.remove();
            }

            Log.d(TAG, "JSON-DATA: " + array);

            Map<String,String> postParam=new HashMap<String, String>();
            postParam.put("data",array.toString());
            postParam.put("email",FragmentHolidays.this.app.getUser().getEmail());

            Submit.submitHolidayForm(postParam, new PostHandler.ResponseCallback() {
                @Override
                public void response(int status, String response) {
                    if(status== HttpURLConnection.HTTP_OK){
                        try{
                            JSONObject mailResponse=new JSONObject(response).getJSONObject("mail_response");
                            if(mailResponse.getString("status").equals(1)){
                                //Notify User For successful mail sent
                            }
                        }catch (JSONException e){

                        }
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }


}
