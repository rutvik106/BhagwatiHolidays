package bhfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.Spinner;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import extras.GetTermsAsync;
import extras.SendMail;
import extras.Validator;
import extras.CommonUtilities;

/**
 * Created by Rakshit on 20-11-2015 at 14:23.
 */
public class FragmentHotels extends Fragment implements DatePickerDialog.OnDateSetListener, TextWatcher {

    private static final String TAG = App.APP_TAG + FragmentHotels.class.getSimpleName();

    CoordinatorLayout clFragmentHotels;

    EditText etMobileNo, etBookingDate;
    AutoCompleteTextView actDestination;
    //RadioGroup radioGroupType;

    MultiStateToggleButton mstbLocationType;

    String[] locationType;
    int selectedLocationType=0;

    Spinner spAdult, spChild, spInfant, spNoOfNights;
    FloatingActionButton fabDone;
    android.app.FragmentManager mFragmentManager;
    DatePickerDialog datePickerDialog;
    App app;

    private GetTermsAsync getTermsAsync;

    boolean isFormValid = true;

    Calendar argCalendar=Calendar.getInstance();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        locationType=getResources().getStringArray(R.array.location_type);
        app = (App) activity.getApplication();
        app.trackScreenView(FragmentHotels.class.getSimpleName());
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

        clFragmentHotels=(CoordinatorLayout) rootView.findViewById(R.id.cl_fragmentHotels);

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

        //radioGroupType = (RadioGroup) rootView.findViewById(R.id.rg_type);

        mstbLocationType=(MultiStateToggleButton) rootView.findViewById(R.id.mstb_locationType);
        mstbLocationType.setElements(R.array.location_type, 0);
        mstbLocationType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                selectedLocationType=position;
                CommonUtilities.hideKeyboard(getActivity(),getActivity().getCurrentFocus());
            }
        });

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==10){
                    CommonUtilities.hideKeyboard(getActivity(),getActivity().getCurrentFocus());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        actDestination = (AutoCompleteTextView) rootView.findViewById(R.id.et_destination);
        actDestination.addTextChangedListener(this);

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
        datePickerDialog.setMinDate(calendar);

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                app.trackEvent("FAB", "SUBMIT HOTEL INQUIRY", "HOTEL INQUIRY");

                submitForm();


            }
        });

        return rootView;
    }

    private void submitForm() {

        final Map<String, String> formParams = new LinkedHashMap<>();
        formParams.put("Contact", etMobileNo.getText().toString());
        formParams.put("Email",app.getUser().getEmail());
        formParams.put("Booking Date", etBookingDate.getText().toString());
        formParams.put("Type", locationType[selectedLocationType]);
        formParams.put("Adult", spAdult.getSelectedItem().toString());
        formParams.put("Child", spChild.getSelectedItem().toString());
        formParams.put("Infant", spInfant.getSelectedItem().toString());
        formParams.put("Destination", actDestination.getText().toString());
        formParams.put("No. of Nights", spNoOfNights.getSelectedItem().toString());

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
                                    SendMail.Type.HOTEL,
                                    getActivity(),
                                    new SendMail.MailCallbackListener() {
                                        @Override
                                        public void mailSentSuccessfully() {
                                            CommonUtilities.clearForm((ViewGroup) getActivity().findViewById(R.id.ll_formHotels));

                                            FragmentHotels.this.etMobileNo.requestFocus();

                                            CommonUtilities
                                                    .showAlertDialog(getActivity(),clFragmentHotels, "Hotel Booking",
                                                            "",
                                                            "Hotel Booking in Bhagwati Holidays",argCalendar);
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
        etBookingDate.setText(date);
        argCalendar.set(Calendar.YEAR,year);
        argCalendar.set(Calendar.MONTH,monthOfYear);
        argCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "TEXT CHANGED TO: " + s.toString());
        if (!TextUtils.isEmpty(s.toString())) {
            Log.i(TAG, "TEXT CHENGED IN FROM: " + s.toString());
            if (!TextUtils.isEmpty(s.toString()) && s.toString().length() > 1) {
                if (getTermsAsync != null) {
                    getTermsAsync.cancel(true);
                    getTermsAsync = null;
                }
                actDestination.showDropDown();
                getTermsAsync = new GetTermsAsync(actDestination, getActivity(), CommonUtilities.URL_DESTINATIONS);
                getTermsAsync.execute(s.toString());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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

        Validator.validateDate(formParams.get("Booking Date"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                etBookingDate.setError(msg);
                isFormValid=isFormValid&status;
            }
        });

        return isFormValid;
    }
}
