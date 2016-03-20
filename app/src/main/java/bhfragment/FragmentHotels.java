package bhfragment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.LinkedHashMap;
import java.util.Map;

import extras.SendMail;
import extras.Submit;
import extras.Validator;
import gcm.CommonUtilities;
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 20-11-2015 at 14:23.
 */
public class FragmentHotels extends Fragment implements DatePickerDialog.OnDateSetListener, TextWatcher {

    private static final String TAG = App.APP_TAG + FragmentHotels.class.getSimpleName();

    private EditText etMobileNo, etBookingDate;
    private AutoCompleteTextView etDestination;
    private RadioGroup radioGroupType;
    private RadioButton rbIndia, rbWorldWild;
    private Spinner spAdult, spChild, spInfant, spNoOfNights;
    private FloatingActionButton fabDone;
    private android.app.FragmentManager mFragmentManager;
    private DatePickerDialog datePickerDialog;
    App app;
    final Handler mHandler = new Handler();


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

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        etDestination = (AutoCompleteTextView) rootView.findViewById(R.id.et_destination);
        etDestination.addTextChangedListener(this);

        etBookingDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePickerDialog.show(mFragmentManager, "DepartDate");
                }
            }
        });

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

        Map<String, String> formParams = new LinkedHashMap<>();
        formParams.put("Contact", etMobileNo.getText().toString());
        formParams.put("Booking Date", etBookingDate.getText().toString());
        formParams.put("Type", ((RadioButton) radioGroupType.findViewById(radioGroupType.getCheckedRadioButtonId())).getText().toString());
        formParams.put("Adult", spAdult.getSelectedItem().toString());
        formParams.put("Child", spChild.getSelectedItem().toString());
        formParams.put("Infant", spInfant.getSelectedItem().toString());
        formParams.put("Destination", etDestination.getText().toString());
        formParams.put("No. of Nights", spNoOfNights.getSelectedItem().toString());

        if (isFormParamValid(formParams)) {

            final SendMail sendMail = new SendMail(app.getUser().getEmail(),
                    SendMail.Type.HOTEL,
                    getActivity(),
                    new SendMail.MailCallbackListener() {
                        @Override
                        public void mailSentSuccessfully() {
                            CommonUtilities.clearForm((ViewGroup) getActivity().findViewById(R.id.ll_formHotels));

                            FragmentHotels.this.etMobileNo.requestFocus();

                            CommonUtilities
                                    .showAlertDialog(getActivity(), "Hotel Booking",
                                            "",
                                            "Hotel Booking in Bhagwati Holidays");
                        }
                    });
            sendMail.execute();
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        etBookingDate.setText(date);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "TEXT CHANGED TO: " + s.toString());
        if (!TextUtils.isEmpty(s.toString())) {
            getDestinationsAsync(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void getDestinationsAsync(final String term) {
        new AsyncTask<Void, Void, Void>() {

            final String t = term;

            final Map<String, String> postParams = new HashMap<>();

            @Override
            protected Void doInBackground(Void... params) {
                Log.i(TAG, "do in background in getDestinationAsync");
                postParams.put("term", t);
                new PostHandler(TAG, 2, 2000).doPostRequest("http://www.bhagwatiholidays.com/admin/webservice/destination_name.php",
                        postParams,
                        new PostHandler.ResponseCallback() {
                            @Override
                            public void response(int status, String response) {
                                Log.i(TAG, "GOT RESPONSE SUCCESSFULLY");
                                try {
                                    Log.i(TAG, "PARSING JSON");
                                    JSONArray array = new JSONArray(response);
                                    Log.i(TAG, "JSON ARRAY SIZE: " + array.length());
                                    final String[] destinations = new String[array.length()];
                                    for (int i = 0; i < array.length(); i++) {
                                        Log.i(TAG, "LABEL: " + array.getJSONObject(i).getString("label"));
                                        destinations[i] = array.getJSONObject(i).getString("label");
                                    }
                                    Log.i(TAG, "SETTING ADAPTER NOW");
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            FragmentHotels.this.etDestination.setAdapter(new ArrayAdapter<String>(FragmentHotels.this.getActivity(),
                                                    android.R.layout.simple_list_item_1, destinations));
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                return null;
            }
        }.execute();
    }

    boolean isFormValid = true;

    public boolean isFormParamValid(Map<String, String> formParams) {

        isFormValid = true;

        Validator.validateContact(formParams.get("Contact"), new Validator.ValidationListener() {
            @Override
            public void validationFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                isFormValid = false;
            }
        });

        Validator.validDestination(formParams.get("Destination"), new Validator.ValidationListener() {
            @Override
            public void validationFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                isFormValid = false;
            }
        });
        return isFormValid;
    }
}
