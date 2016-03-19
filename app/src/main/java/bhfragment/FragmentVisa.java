package bhfragment;

import android.app.FragmentManager;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import extras.Submit;
import extras.Validator;
import gcm.CommonUtilities;
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentVisa extends Fragment implements DatePickerDialog.OnDateSetListener, TextWatcher {

    FloatingActionButton fabDone;
    EditText etMobileNo, etDateOfTravel;
    AutoCompleteTextView actDestination;
    RadioButton rbBusiness, rbStudent;
    RadioGroup radioGroup;
    private String visaType;
    private String mobileNO, dateOfTravel, destination;

    private DatePickerDialog datePickerDialog;
    private FragmentManager fragmentManager;

    private static final String TAG = App.APP_TAG + FragmentVisa.class.getSimpleName();
    Handler mHandler;

    private App app;

    @Override
    public void onStart() {
        super.onStart();
        app = (App) getActivity().getApplication();
    }

    public FragmentVisa() {
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
        View rootView = inflater.inflate(R.layout.fragment_visa, container, false);

        fragmentManager = getActivity().getFragmentManager();

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etDateOfTravel = (EditText) rootView.findViewById(R.id.et_dateOfTravel);
        etDateOfTravel.setTextIsSelectable(false);
        etDateOfTravel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePickerDialog.show(fragmentManager, "FragmentVisa");
                }
            }
        });

        actDestination = (AutoCompleteTextView) rootView.findViewById(R.id.et_destination);

        rbBusiness = (RadioButton) rootView.findViewById(R.id.rb_business);

        rbStudent = (RadioButton) rootView.findViewById(R.id.rb_student);

        radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_visaType);

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        etDateOfTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(fragmentManager, "FragmentVisa");
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != radioButton && checkedId > -1) {
                    visaType = radioButton.getText().toString();
                    //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
            mobileNO = etMobileNo.getText().toString().trim();
            dateOfTravel = etDateOfTravel.getText().toString();
            destination = actDestination.getText().toString().trim();

            RadioButton rbType = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            visaType = rbType.getText().toString();

            Map<String, String> formParams = new LinkedHashMap<>();
            formParams.put("Contact", mobileNO);
            formParams.put("Date Of Travel", dateOfTravel);
            formParams.put("Destination", destination);
            formParams.put("Visa Type", visaType);

            if (isFormParamValid(formParams)) {
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
                postParam.put("email", FragmentVisa.this.app.getUser().getEmail());

                final Context mContext = getActivity();

                Submit.submitVisaForm(postParam, new PostHandler.ResponseCallback() {
                            @Override
                            public void response(int status, String response) {
                                if (status == HttpURLConnection.HTTP_OK) {
                                    try {
                                        JSONObject mailResponse = new JSONObject(response).getJSONObject("mail_response");
                                        if (mailResponse.getString("status").equals("1")) {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    CommonUtilities.showAlertDialog(mContext,
                                                            "Visa Booking", "", "Visa booking in Bhagwati Holiday.");
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {

                                    }
                                }
                            }
                        }
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        etDateOfTravel.setText(date);
    }

    boolean isFormValid = true;

    public boolean isFormParamValid(Map<String, String> formParams) {

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
            }
        });

        return isFormValid;
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
                                            FragmentVisa.this.actDestination.setAdapter(new ArrayAdapter<String>(FragmentVisa.this.getActivity(),
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
}
