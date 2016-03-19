package bhfragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import extras.Submit;
import extras.Validator;
import gcm.CommonUtilities;
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentAirTickets extends Fragment implements DatePickerDialog.OnDateSetListener {

    EditText etMobileNo, etDepartDate, etReturnDate;
    RadioButton rbIndia, rbWorldWild, rbReturn, rbOneWay, rbEconomy, rbBusiness;
    RadioGroup rgType, rgTrip, rgClass;
    Spinner spAdult, spChild, spInfant;
    FloatingActionButton fabDone;
    private AutoCompleteTextView actFrom, actTo;

    App app;

    Handler mHandler;

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
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_air_tickets, container, false);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        actFrom = (AutoCompleteTextView) rootView.findViewById(R.id.et_from);

        actTo = (AutoCompleteTextView) rootView.findViewById(R.id.et_to);

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
        etDepartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dateFlag = DEPART_DATE;
                    datePickerDialog.show(fragmentManager, "DepartDate");
                }
            }
        });

        etReturnDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dateFlag = RETURN_DATE;
                    datePickerDialog.show(fragmentManager, "ReturnDate");
                }
            }
        });

        actFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "TEXT CHENGED IN FROM: " + s.toString());
                if (!TextUtils.isEmpty(s.toString())) {
                    getFromAsync(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        actTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "TEXT CHENGED IN TO: " + s.toString());
                if (!TextUtils.isEmpty(s.toString())) {
                    getToAsync(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
                if (checkedId == R.id.rb_return) {
                    etReturnDate.setVisibility(View.VISIBLE);
                    trip = rbReturn.getText().toString();
                } else {
                    etReturnDate.setVisibility(View.GONE);
                    trip = rbIndia.getText().toString();
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
                boolean flag = false;
                flag = Validator.validateDates(etDepartDate.getText().toString(), text, new Validator.ValidationListener() {
                    @Override
                    public void validationFailed(String msg) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
                if (flag == true) {
                    etReturnDate.setText(text);
                }

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////

        etDepartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFlag = DEPART_DATE;
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setMinDate(calendar);
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
                Log.d(TAG, "press btn before....");
                submitForm();
            }
        });


        return rootView;
    }

    private void getToAsync(final String to) {

        new AsyncTask<Void, Void, Void>() {

            final String t = to;

            final Map<String, String> postParams = new HashMap<>();

            @Override
            protected Void doInBackground(Void... params) {
                Log.i(TAG, "Do in background in getFromAsync");
                postParams.put("term", t);
                new PostHandler(TAG, 2, 2000).doPostRequest("http://www.bhagwatiholidays.com/admin/webservice/airport_name.php",
                        postParams,
                        new PostHandler.ResponseCallback() {
                            @Override
                            public void response(int status, String response) {
                                Log.i(TAG, "GOT RESPONSE SUCCESSFULLY");
                                try {
                                    Log.i(TAG, "PARSING JSON");
                                    JSONArray array = new JSONArray(response);
                                    Log.i(TAG, "JSON ARRAY SIZE IN TO: " + array.length());
                                    final String[] tos = new String[array.length()];
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.i(TAG, "LABEL FOR TO: " + array.getJSONObject(i).getString("label"));
                                        tos[i] = array.getJSONObject(i).getString("label");
                                    }
                                    Log.i(TAG, "SETTING ADAPTER NOW IN TO");
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            FragmentAirTickets.this.actTo.setAdapter(new ArrayAdapter<String>(FragmentAirTickets.this.getActivity(),
                                                    android.R.layout.simple_list_item_1, tos));
                                        }
                                    });

                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                return null;
            }
        }.execute();
    }

    private void getFromAsync(final String from) {
        new AsyncTask<Void, Void, Void>() {

            final String f = from;

            final Map<String, String> postParams = new HashMap<>();

            @Override
            protected Void doInBackground(Void... params) {
                Log.i(TAG, "Do in background in getFromAsync");
                postParams.put("term", f);
                new PostHandler(TAG, 2, 2000).doPostRequest("http://www.bhagwatiholidays.com/admin/webservice/airport_name.php",
                        postParams,
                        new PostHandler.ResponseCallback() {
                            @Override
                            public void response(int status, String response) {
                                Log.i(TAG, "GOT RESPONSE SUCCESSFULLY");
                                try {
                                    Log.i(TAG, "PARSING JSON");
                                    JSONArray array = new JSONArray(response);
                                    Log.i(TAG, "JSON ARRAY SIZE IN FROM: " + array.length());
                                    final String[] froms = new String[array.length()];
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.i(TAG, "LABEL IN FROM: " + array.getJSONObject(i).getString("label"));
                                        froms[i] = array.getJSONObject(i).getString("label");
                                    }
                                    Log.i(TAG, "SETTING ADAPTER NOW FOR FROM");
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            FragmentAirTickets.this.actFrom.setAdapter(new ArrayAdapter<String>(FragmentAirTickets.this.getActivity(),
                                                    android.R.layout.simple_list_item_1, froms));
                                        }
                                    });

                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                return null;
            }
        }.execute();
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
            from = actFrom.getText().toString().trim();
            to = actTo.getText().toString().trim();

            RadioButton rbType = (RadioButton) rgType.findViewById(rgType.getCheckedRadioButtonId());
            type = rbType.getText().toString();

            RadioButton rbTrip = (RadioButton) rgTrip.findViewById(rgTrip.getCheckedRadioButtonId());
            trip = rbTrip.getText().toString();

            RadioButton rbClass = (RadioButton) rgClass.findViewById(rgClass.getCheckedRadioButtonId());
            mClass = rbClass.getText().toString();

            //Take Date from EditText
            departDate = etDepartDate.getText().toString();
            returnDate = etReturnDate.getText().toString();

            Map<String, String> formParams = new LinkedHashMap<>();
            formParams.put("Contact", mobileNo);
            formParams.put("Type", type);
            formParams.put("Trip", trip);
            formParams.put("Depart Date", departDate);
            formParams.put("Return Date", returnDate);
            formParams.put("From", from);
            formParams.put("To", to);
            formParams.put("Adult", adult);
            formParams.put("Child", child);
            formParams.put("Infant", infant);
            formParams.put("Class", mClass);

            Log.d(TAG, "Check check........!!!!");

            if (isFormParamValid(formParams)) {


                JSONArray array = new JSONArray();

                Iterator iterator = formParams.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry pair = (Map.Entry) iterator.next();
                    array.put(new JSONObject("{\"" + pair.getKey() + "\":" + "\"" + pair.getValue() + "\"}"));
                    iterator.remove();
                }

                Log.d(TAG, "JSON-DATA: " + array);

                final Map<String, String> postParam = new HashMap<String, String>();
                postParam.put("data", array.toString());
                postParam.put("email", "rakshit1993.rs@gmail.com");

                new AsyncTask<Void, Void, Void>() {
                    final Context c = getActivity();

                    @Override
                    protected Void doInBackground(Void... params) {
                        Submit.submitAirticketForm(postParam, new PostHandler.ResponseCallback() {
                            @Override
                            public void response(int status, String response) {
                                if (status == HttpURLConnection.HTTP_OK) {
                                    try {
                                        JSONObject mailResponse = new JSONObject(response).getJSONObject("mail_response");
                                        Log.i(TAG, "mailResponse.getString(\"status\"): " + mailResponse.getString("status"));
                                        Log.i(TAG, "mailResponse.getString(\"status\").equals(\"1\") " + mailResponse.getString("status").equals("1"));
                                        if (mailResponse.getString("status").equals("1")) {
                                            Log.i(TAG, "SHOWING ALERT DIALOG");
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    CommonUtilities
                                                            .showAlertDialog(c, "Air Ticket Booking",
                                                                    "",
                                                                    "Air Ticket Booking in Bhagwati Holidays");
                                                }
                                            });

                                        }
                                    } catch (JSONException e) {

                                    }
                                }
                            }
                        });
                        return null;
                    }

                }.execute();


            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    boolean isFormValid = true;

    public boolean isFormParamValid(Map<String, String> formParams) {
        Log.i(TAG, "inside is form param valid");

        Validator.validateContact(formParams.get("Contact"), new Validator.ValidationListener() {
            @Override
            public void validationFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                isFormValid = false;
            }
        });

        Validator.validateFrom(formParams.get("From"), new Validator.ValidationListener() {
            @Override
            public void validationFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                isFormValid = false;
            }
        });

        Validator.validTo(formParams.get("To"), new Validator.ValidationListener() {
            @Override
            public void validationFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                isFormValid = false;
            }
        });
        return isFormValid;
    }

}