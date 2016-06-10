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
import extras.Log;
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
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentHolidays extends Fragment implements DatePickerDialog.OnDateSetListener,TextWatcher {

    private static final String TAG = App.APP_TAG + FragmentHolidays.class.getSimpleName();

    CoordinatorLayout clFragmentHolidays;

    EditText etMobileNo, etBookingDate;
    AutoCompleteTextView actDestination;
//  RadioGroup rgType;
    Spinner spAdult, spChild, spInfant, spNoOfNights;
    RatingBar rbPackageType;
    FloatingActionButton fabDone;

    DatePickerDialog datePickerDialog;

    boolean isFormValid = true;

    App app;

    private GetTermsAsync getTermsAsync;

    private String requestingActivity;

    private String packageId;

    private String packageDestination;

    private String packagePrice;

    private String stars;

    android.app.FragmentManager mFragmentManager;

    MultiStateToggleButton mstbLocationType;

    String[] locationType;
    int selectedLocationType=0;

    Calendar argCalendar=Calendar.getInstance();

    public FragmentHolidays() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        locationType=getResources().getStringArray(R.array.location_type);
        app = (App) activity.getApplication();
        app.trackScreenView(FragmentHolidays.class.getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            Log.i(App.APP_TAG,"GETTING EXTRAS.....");
            requestingActivity=getActivity().getIntent().getStringExtra("requesting_activity");

            if(requestingActivity.equals("single_package_view_activity")) {
                packageId=getActivity().getIntent().getStringExtra("package_id");
                packageDestination=getActivity().getIntent().getStringExtra("package_destination");
                packagePrice=getActivity().getIntent().getStringExtra("package_price");
                if(packagePrice.contains("star")){
                    rbPackageType.setRating(Float.parseFloat(String.valueOf(packagePrice.charAt(0))));
                }
                rbPackageType.setEnabled(false);
                Log.i(TAG,"package location type: "+getActivity().getIntent().getStringExtra("package_location_type"));
                if(getActivity().getIntent().getStringExtra("package_location_type").equals("2")){
                    Log.i(TAG,"setting location to india");
                    //((RadioButton)rgType.findViewById(R.id.rb_india)).setChecked(true);
                    mstbLocationType.setValue(0);
                }
                else{
                    Log.i(TAG,"setting location to worldwide");
                    //((RadioButton)rgType.findViewById(R.id.rb_worldWide)).setChecked(true);
                    mstbLocationType.setValue(1);
                }
                /*for (int i = 0; i < rgType.getChildCount(); i++) {
                    rgType.getChildAt(i).setEnabled(false);
                }*/
                mstbLocationType.setEnabled(false);
                actDestination.setEnabled(false);
                actDestination.setText(packageDestination);

            }
        }
        catch (Exception e){
            Log.i(App.APP_TAG,"cannot get intent extra requesting");
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_holidays, container, false);

        clFragmentHolidays=(CoordinatorLayout) rootView.findViewById(R.id.cl_fragmentHolidays);

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

        //rgType = (RadioGroup) rootView.findViewById(R.id.rg_type);

        mstbLocationType=(MultiStateToggleButton) rootView.findViewById(R.id.mstb_locationType);
        mstbLocationType.setElements(R.array.location_type, 0);
        mstbLocationType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                selectedLocationType=position;
            }
        });


        rbPackageType = (RatingBar) rootView.findViewById(R.id.rb_packageType);
        rbPackageType.setStepSize(1);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        mFragmentManager = getActivity().getFragmentManager();

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
        if(requestingActivity.equals("single_package_view_activity")) {
            formParams.put("Package", "http://bhagwatiholidays.com/package.php?id=" + packageId);
        }
        formParams.put("Contact", etMobileNo.getText().toString());
        formParams.put("Email", app.getUser().getEmail());
        formParams.put("Type", locationType[selectedLocationType]);
        formParams.put("Depart Date", etBookingDate.getText().toString());
        formParams.put("Destination",actDestination.getText().toString());
        formParams.put("Adult", spAdult.getSelectedItem().toString());
        formParams.put("Child", spChild.getSelectedItem().toString());
        formParams.put("Infant", spInfant.getSelectedItem().toString());
        formParams.put("Package Type",String.valueOf(rbPackageType.getRating()));

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
                                                    .showAlertDialog(getActivity(),clFragmentHolidays, "Holiday Booking",
                                                            "",
                                                            "Holiday Booking in Bhagwati Holidays",argCalendar);


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
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                etMobileNo.setError(msg);
                isFormValid = isFormValid&status;
            }
        });

        Validator.validateDate(formParams.get("Depart Date"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                etBookingDate.setError(msg);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        etBookingDate.setText(date);
        argCalendar.set(Calendar.YEAR,year);
        argCalendar.set(Calendar.MONTH,monthOfYear);
        argCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
    }
}
