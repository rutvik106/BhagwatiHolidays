package bhfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import extras.Submit;
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 20-11-2015 at 14:23.
 */
public class FragmentHotels extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = App.APP_TAG + FragmentHotels.class.getSimpleName();

    EditText etMobileNo, etBookingDate, etDestination;
    RadioGroup radioGroupType;
    RadioButton rbIndia, rbWorldWild;
    Spinner spAdult, spChild, spInfant, spNoOfNights;
    RatingBar rbHotelType;
    FloatingActionButton fabDone;

    String mobileNo, destination, bookingDate;
    private String adult, child, infant;
    private String type;
    private String noOfNights;
    DatePickerDialog datePickerDialog;
    android.app.FragmentManager mFragmentManager;

    App app;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app = (App) activity.getApplication();
    }

    public FragmentHotels() {
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
        View rootView = inflater.inflate(R.layout.fragment_hotels, container, false);

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

        mFragmentManager = getActivity().getFragmentManager();

        spNoOfNights = (Spinner) rootView.findViewById(R.id.sp_noOfNights);

        radioGroupType = (RadioGroup) rootView.findViewById(R.id.rg_type);

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

        rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWide);

        //rbHotelType = (RatingBar) rootView.findViewById(R.id.rb_hotelType);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        etDestination = (EditText) rootView.findViewById(R.id.et_destination);

        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroupType.findViewById(checkedId);
                if (null != radioButton && checkedId > -1) {
                    type = radioButton.getText().toString();
                    //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
                }
            }
        });

        spAdult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adult = spAdult.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                child = spChild.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spInfant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                infant = spInfant.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNoOfNights.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noOfNights = spNoOfNights.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etBookingDate.setTextIsSelectable(true);

        etBookingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(mFragmentManager, "DepartDate");
            }
        });

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        return rootView;
    }

    private void submitForm() {
        try {

            //Take Value from EditText
            mobileNo = etMobileNo.getText().toString().trim();
            destination = etDestination.getText().toString().trim();

            RadioButton rbType = (RadioButton) radioGroupType.findViewById(radioGroupType.getCheckedRadioButtonId());
            type = rbType.getText().toString();

            bookingDate = etBookingDate.getText().toString();

            Map<String, String> formParams = new HashMap<String, String>();
            formParams.put("Mobile NO. ", mobileNo);
            formParams.put("Booking Date  ", bookingDate);
            formParams.put("Type ", type);
            formParams.put("Adult ", adult);
            formParams.put("Child ", child);
            formParams.put("Infant ", infant);
            formParams.put("Destination ", destination);
            formParams.put("No. of Nights ", noOfNights);

            JSONArray array = new JSONArray();

            Iterator iterator = formParams.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                array.put(new JSONObject("{\"" + pair.getKey() + "\":" + "\"" + pair.getValue() + "\"}"));
                iterator.remove();
            }

            Log.d(TAG, "JSON-DATA: " + array);

            Map<String, String> postParam = new HashMap<String, String>();
            postParam.put("data", array.toString());
            postParam.put("email", FragmentHotels.this.app.getUser().getEmail());

            Submit.submitHolidayForm(postParam, new PostHandler.ResponseCallback() {
                @Override
                public void response(int status, String response) {
                    if (status == HttpURLConnection.HTTP_OK) {
                        try {
                            JSONObject mailResponse = new JSONObject(response).getJSONObject("mail_response");
                            if (mailResponse.getString("status").equals(1)) {
                                //Notify User For successful mail sent
                            }
                        } catch (JSONException e) {

                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        etBookingDate.setText(date);
    }
}
