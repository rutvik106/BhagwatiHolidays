package bhfragment;

import android.app.FragmentManager;
import android.content.DialogInterface;
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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import extras.GetTermsAsync;
import extras.SendMail;
import extras.Validator;
import extras.CommonUtilities;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentVisa extends Fragment implements DatePickerDialog.OnDateSetListener, TextWatcher {

    private static final String TAG = App.APP_TAG + FragmentVisa.class.getSimpleName();

    FloatingActionButton fabDone;
    EditText etMobileNo, etDateOfTravel;
    AutoCompleteTextView actDestination;
    RadioButton rbBusiness, rbStudent;
    RadioGroup radioGroup;

    DatePickerDialog datePickerDialog;
    FragmentManager fragmentManager;

    Handler mHandler;

    private App app;

    boolean isFormValid = true;

    private GetTermsAsync getTermsAsync;

    @Override
    public void onStart() {
        super.onStart();
        app = (App) getActivity().getApplication();
        app.trackScreenView(FragmentVisa.class.getSimpleName());
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

        actDestination.addTextChangedListener(this);

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

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                app.trackEvent("FAB", "SUBMIT VISA INQUIRY", "VISA INQUIRY");

                submitForm();


            }
        });

        return rootView;
    }

    private void submitForm() {

        final Map<String, String> formParams = new LinkedHashMap<>();
        formParams.put("Contact", etMobileNo.getText().toString());
        formParams.put("Email", app.getUser().getEmail());
        formParams.put("Date Of Travel", etDateOfTravel.getText().toString());
        formParams.put("Destination", actDestination.getText().toString());
        formParams.put("Visa Type", ((RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());

        if (isFormParamValid(formParams)) {

            CommonUtilities.showSimpleAlertDialog(getActivity(),
                    "Alert",
                    "Send inquiry to Bhagwati Holidays?",
                    "Send",
                    "Cancel",
                    new CommonUtilities.SimpleAlertDialog.OnClickListener() {
                        @Override
                        public void positiveButtonClicked(DialogInterface dialog, int which) {

                            final SendMail sendMail = new SendMail(app.getUser().getEmail(),
                                    SendMail.Type.VISA,
                                    getActivity(),
                                    new SendMail.MailCallbackListener() {
                                        @Override
                                        public void mailSentSuccessfully() {
                                            CommonUtilities.clearForm((ViewGroup) getActivity().findViewById(R.id.ll_formVisa));

                                            FragmentVisa.this.etMobileNo.requestFocus();

                                            CommonUtilities
                                                    .showAlertDialog(getActivity(), "Visa Booking",
                                                            "",
                                                            "Visa booking in Bhagwati Holidays",etDateOfTravel.getText().toString());
                                        }
                                    }, app);

                            sendMail.execute(formParams);

                        }

                        @Override
                        public void negativeButtonClicked(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });


        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        etDateOfTravel.setText(date);
    }

    public boolean isFormParamValid(Map<String, String> formParams) {

        isFormValid = true;

        Validator.validateContact(formParams.get("Contact"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                etMobileNo.setError(msg);
                isFormValid = isFormValid&status;
            }
        });

        Validator.validDestination(formParams.get("Destination"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                actDestination.setError(msg);
                isFormValid = isFormValid&status;
            }
        });

        Validator.validateDate(formParams.get("Date Of Travel"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                etDateOfTravel.setError(msg);
                isFormValid = isFormValid&status;
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
        if (!TextUtils.isEmpty(s.toString()) && s.toString().length() > 2) {
            if (getTermsAsync != null) {
                getTermsAsync.cancel(true);
                getTermsAsync = null;
            }
            getTermsAsync = new GetTermsAsync(actDestination, getActivity(), CommonUtilities.URL_DESTINATIONS);
            getTermsAsync.execute(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
