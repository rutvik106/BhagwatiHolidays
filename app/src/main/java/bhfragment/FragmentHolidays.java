package bhfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

import java.util.LinkedHashMap;
import java.util.Map;

import extras.GetTermsAsync;
import extras.SendMail;
import extras.Validator;
import extras.CommonUtilities;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentHolidays extends Fragment implements TextWatcher {

    private static final String TAG = App.APP_TAG + FragmentHolidays.class.getSimpleName();

    EditText etMobileNo, etBookingDate;
    AutoCompleteTextView actDestination;
    RadioGroup rgType;
    Spinner spAdult, spChild, spInfant, spNoOfNights;
    RatingBar rbPackageType;
    FloatingActionButton fabDone;

    boolean isFormValid = true;

    App app;

    private GetTermsAsync getTermsAsync;


    public FragmentHolidays() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app = (App) activity.getApplication();
        app.trackScreenView(FragmentHolidays.class.getSimpleName());
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

        rgType = (RadioGroup) rootView.findViewById(R.id.rg_type);

        rbPackageType = (RatingBar) rootView.findViewById(R.id.rb_packageType);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        actDestination = (AutoCompleteTextView) rootView.findViewById(R.id.et_destination);

        actDestination.addTextChangedListener(this);

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                app.trackEvent("FAB", "SUBMIT HOLIDAY INQUIRY", "HOLIDAY INQUIRY");

                submitForm();


            }
        });

        return rootView;
    }


    private void submitForm() {

        final Map<String, String> formParams = new LinkedHashMap<>();
        formParams.put("Contact", etMobileNo.getText().toString());
        formParams.put("Email", app.getUser().getEmail());
        formParams.put("Type", ((RadioButton) rgType.findViewById(rgType.getCheckedRadioButtonId())).getText().toString());
        formParams.put("Depart Date", etBookingDate.getText().toString());


        formParams.put("Adult", spAdult.getSelectedItem().toString());
        formParams.put("Child", spChild.getSelectedItem().toString());
        formParams.put("Infant", spInfant.getSelectedItem().toString());

        Log.d(TAG, "Check check........!!!!");

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
                                    SendMail.Type.HOLIDAY,
                                    getActivity(),
                                    new SendMail.MailCallbackListener() {
                                        @Override
                                        public void mailSentSuccessfully() {

                                            CommonUtilities.clearForm((ViewGroup) getActivity().findViewById(R.id.ll_formHoliday));

                                            FragmentHolidays.this.etMobileNo.requestFocus();

                                            CommonUtilities
                                                    .showAlertDialog(getActivity(), "Holiday Booking",
                                                            "",
                                                            "Holiday Booking in Bhagwati Holidays");


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


    public boolean isFormParamValid(Map<String, String> formParams) {
        Log.i(TAG, "inside is form param valid");

        isFormValid = true;

        Validator.validateContact(formParams.get("Contact"), new Validator.ValidationListener() {
            @Override
            public void validationFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                isFormValid = false;
            }
        });

        Validator.validateDate(formParams.get("Depart Date"), new Validator.ValidationListener() {
            @Override
            public void validationFailed(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                isFormValid = false;
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
