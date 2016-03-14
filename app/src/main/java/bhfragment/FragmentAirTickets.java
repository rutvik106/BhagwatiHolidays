package bhfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentAirTickets extends Fragment implements DatePickerDialog.OnDateSetListener {

    EditText etMobileNo, etFrom, etTo, etDepartDate, etReturnDate;
    RadioButton rbIndia, rbWorldWild, rbReturn, rbOneWay, rbEconomy, rbBusiness;
    RadioGroup rgType, rgTrip, rgClass;
    Spinner spAdult, spChild, spInfant;
    FloatingActionButton fabDone;

    App app;


    DatePickerDialog datePickerDialog;
    android.app.FragmentManager fragmentManager;
    private String mobileNo, from, to;
    private String adult, child, infant, departDate, returnDate;
    private String type, trip, mClass;
    public static final int DEPART_DATE = 0;
    public static final int RETURN_DATE = 1;
    private int dateFlag;

    Map<Integer, OnGetDate> dateComponentMap = new HashMap<>();

    private static final String TAG = App.APP_TAG + FragmentAirTickets.class.getSimpleName();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app = (App) activity.getApplication();
    }

    public FragmentAirTickets() {
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
        View rootView = inflater.inflate(R.layout.fragment_air_tickets, container, false);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etFrom = (EditText) rootView.findViewById(R.id.et_from);

        etTo = (EditText) rootView.findViewById(R.id.et_to);
        
        fragmentManager = getActivity().getFragmentManager();

        spAdult = (Spinner) rootView.findViewById(R.id.sp_adult);
        spAdult.setAdapter(app.getFlightAdultAdapter());
        spAdult.setSelection(0);

        spChild = (Spinner) rootView.findViewById(R.id.sp_child);
        spChild.setAdapter(app.getFlightChildAdapter());
        spChild.setSelection(0);

        spInfant = (Spinner) rootView.findViewById(R.id.sp_infant);
        spInfant.setAdapter(app.getFlightInfantAdapter());
        spInfant.setSelection(0);

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        rgType = (RadioGroup) rootView.findViewById(R.id.rgType);
        rgTrip = (RadioGroup) rootView.findViewById(R.id.rgTrip);
        rgClass = (RadioGroup) rootView.findViewById(R.id.rgClass);

        rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

        rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWide);

        rbReturn = (RadioButton) rootView.findViewById(R.id.rb_return);

        rbOneWay = (RadioButton) rootView.findViewById(R.id.rb_oneWay);

        rbEconomy = (RadioButton) rootView.findViewById(R.id.rb_economy);

        rbBusiness = (RadioButton) rootView.findViewById(R.id.rb_business);

        etDepartDate = (EditText) rootView.findViewById(R.id.et_departDate);
        etDepartDate.setTextIsSelectable(true);

        etReturnDate = (EditText) rootView.findViewById(R.id.et_returnDate);
        etReturnDate.setTextIsSelectable(false);


        //Take value from Spinner
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

        //Take value from Radio

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) rgType.findViewById(checkedId);
                if (null != radioButton && checkedId > -1) {
                    type = radioButton.getText().toString();
                    Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
                }
            }
        });

        rgTrip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_india) {
                    etReturnDate.setVisibility(View.GONE);
                    trip = rbIndia.getText().toString();
                } else if (checkedId == R.id.rb_return) {
                    etReturnDate.setVisibility(View.VISIBLE);
                    trip = rbReturn.getText().toString();
                }
            }
        });

        rgClass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbClass = (RadioButton) rgClass.findViewById(checkedId);
                if (null != rbClass && checkedId > -1) {
                    mClass = rbClass.getText().toString();
                }
            }
        });


        dateComponentMap.put(DEPART_DATE, new OnGetDate() {
            @Override
            public void setText(String text) {
                etDepartDate.setText(text);
            }
        });

        dateComponentMap.put(RETURN_DATE, new OnGetDate() {
            @Override
            public void setText(String text) {
                etReturnDate.setText(text);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        etDepartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFlag = DEPART_DATE;
                datePickerDialog.show(fragmentManager, "DepartDate");
            }
        });

        etReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFlag = RETURN_DATE;
                datePickerDialog.show(fragmentManager, "ReturnDate");
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

    public static interface OnGetDate {
        void setText(String text);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

        dateComponentMap.get(dateFlag).setText(date);

        /*if(dateFlag == DEPART_DATE){
            etDepartDate.setText(date);
        }
        if()
        {
            etReturnDate.setText(date);
        }*/
    }

    private void submitForm() {
        try {

            //Take Value from EditText
            mobileNo = etMobileNo.getText().toString().trim();
            from = etFrom.getText().toString().trim();
            to = etTo.getText().toString().trim();

            RadioButton rbType = (RadioButton) rgType.findViewById(rgType.getCheckedRadioButtonId());
            type = rbType.getText().toString();

            RadioButton rbTrip = (RadioButton) rgTrip.findViewById(rgTrip.getCheckedRadioButtonId());
            trip = rbTrip.getText().toString();

            RadioButton rbClass = (RadioButton) rgClass.findViewById(rgClass.getCheckedRadioButtonId());
            mClass = rbClass.getText().toString();

            //Take Date from EditText
            departDate = etDepartDate.getText().toString();
            returnDate = etReturnDate.getText().toString();

            Map<String, String> formParams = new HashMap<String, String>();
            formParams.put("Mobile NO. ", mobileNo);
            formParams.put("Type ", type);
            formParams.put("Trip ", trip);
            formParams.put("Depart Date ", departDate);
            formParams.put("Return Date ", returnDate);
            formParams.put("From ", from);
            formParams.put("To ", to);
            formParams.put("Adult ", adult);
            formParams.put("Child ", child);
            formParams.put("Infant ", infant);
            formParams.put("Class ", mClass);

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
            postParam.put("email", FragmentAirTickets.this.app.getUser().getEmail());

            Submit.submitAirticketForm(postParam, new PostHandler.ResponseCallback() {
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
}