package bhfragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import webservicehandler.PostHandler;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentVisa extends Fragment implements DatePickerDialog.OnDateSetListener {

    FloatingActionButton fabDone;
    EditText etMobileNo, etDateOfTravel, etDestination;
    RadioButton rbBusiness, rbStudent;
    RadioGroup radioGroup;
    private String visaType;
    private String mobileNO, dateOfTravel, destination;

    private DatePickerDialog datePickerDialog;
    private FragmentManager fragmentManager;

    private static final String TAG = App.APP_TAG + FragmentVisa.class.getSimpleName();

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

        etDestination = (EditText) rootView.findViewById(R.id.et_destination);

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
            destination = etDestination.getText().toString().trim();

            RadioButton rbType = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            visaType = rbType.getText().toString();

            Map<String, String> formParams = new LinkedHashMap<>();
            formParams.put("Contact", mobileNO);
            formParams.put("Date Of Travel", dateOfTravel);
            formParams.put("Destination", destination);
            formParams.put("Visa Type", visaType);

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

            Submit.submitVisaForm(postParam, new PostHandler.ResponseCallback() {
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
                    }
            );
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
}
