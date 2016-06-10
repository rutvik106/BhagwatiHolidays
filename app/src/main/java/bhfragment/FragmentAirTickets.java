package bhfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import extras.GetTermsAsync;
import extras.SendMail;
import extras.Validator;
import extras.CommonUtilities;

/**
 * Created by Rakshit on 20-11-2015.
 */

public class FragmentAirTickets extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = App.APP_TAG + FragmentAirTickets.class.getSimpleName();

    EditText etMobileNo, etDepartDate, etReturnDate;
    //RadioButton rbIndia, rbWorldWild, rbReturn, rbOneWay, rbEconomy, rbBusiness;
    //RadioGroup rgType, rgTrip, rgClass;
    Spinner spAdult, spChild, spInfant;
    FloatingActionButton fabDone;
    AutoCompleteTextView actFrom, actTo;

    MultiStateToggleButton mstbClassType,mstbLocationType,mstbTripType;

    CoordinatorLayout clFragmentAirTicket;

    String[] classType;
    int selectedClassType=0;

    String[] locationType;
    int selectedLocationType=0;

    String[] tripType;
    int selectedTripType=0;

    private GetTermsAsync getTermsAsync;

    App app;

    boolean isFormValid = true;

    DatePickerDialog datePickerDialog;
    android.app.FragmentManager fragmentManager;

    public static final int DEPART_DATE = 0;
    public static final int RETURN_DATE = 1;
    private int dateFlag;

    Map<Integer, OnGetDate> dateComponentMap = new HashMap<>();

    Calendar argCalendar=Calendar.getInstance();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        classType=getResources().getStringArray(R.array.class_type);
        tripType=getResources().getStringArray(R.array.trip_type);
        locationType=getResources().getStringArray(R.array.location_type);

        app = (App) activity.getApplication();
        app.trackScreenView(FragmentAirTickets.class.getSimpleName());
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

        clFragmentAirTicket=(CoordinatorLayout) rootView.findViewById(R.id.cl_fragmentAirTickets);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        actFrom = (AutoCompleteTextView) rootView.findViewById(R.id.et_from);
        actFrom.setThreshold(3);
        actTo = (AutoCompleteTextView) rootView.findViewById(R.id.et_to);
        actTo.setThreshold(3);
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

/*        rgType = (RadioGroup) rootView.findViewById(R.id.rgType);
        rgTrip = (RadioGroup) rootView.findViewById(R.id.rgTrip);
        rgClass = (RadioGroup) rootView.findViewById(R.id.rgClass);

        rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

        rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWide);

        rbReturn = (RadioButton) rootView.findViewById(R.id.rb_return);

        rbOneWay = (RadioButton) rootView.findViewById(R.id.rb_oneWay);

        rbEconomy = (RadioButton) rootView.findViewById(R.id.rb_economy);

        rbBusiness = (RadioButton) rootView.findViewById(R.id.rb_business);*/

        mstbClassType=(MultiStateToggleButton) rootView.findViewById(R.id.mstb_classType);
        mstbClassType.setElements(R.array.class_type, 0);
        mstbClassType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                selectedClassType=position;
            }
        });

        mstbLocationType=(MultiStateToggleButton) rootView.findViewById(R.id.mstb_locationType);
        mstbLocationType.setElements(R.array.location_type, 0);
        mstbLocationType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                selectedLocationType=position;
            }
        });

        mstbTripType=(MultiStateToggleButton) rootView.findViewById(R.id.mstb_tripType);
        mstbTripType.setElements(R.array.trip_type, 0);
        mstbTripType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                if (position==1) {
                    etReturnDate.setVisibility(View.VISIBLE);

                } else {
                    etReturnDate.setVisibility(View.GONE);

                }
                selectedTripType=position;
            }
        });

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
                if (!TextUtils.isEmpty(s.toString()) && s.toString().length() > 3) {
                    if (getTermsAsync != null) {
                        getTermsAsync.cancel(true);
                        getTermsAsync = null;
                    }
                    getTermsAsync = new GetTermsAsync(actFrom, getActivity(), CommonUtilities.URL_FROM_TO);
                    getTermsAsync.execute(s.toString());
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
                Log.i(TAG, "TEXT CHENGED IN FROM: " + s.toString());
                if (!TextUtils.isEmpty(s.toString()) && s.toString().length() > 2) {
                    if (getTermsAsync != null) {
                        getTermsAsync.cancel(true);
                        getTermsAsync = null;
                    }
                    getTermsAsync = new GetTermsAsync(actTo, getActivity(), CommonUtilities.URL_FROM_TO);
                    getTermsAsync.execute(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Take value from Radio


/*        rgTrip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_return) {
                    etReturnDate.setVisibility(View.VISIBLE);

                } else {
                    etReturnDate.setVisibility(View.GONE);

                }
            }
        });*/


        dateComponentMap.put(DEPART_DATE, new OnGetDate() {
            @Override
            public void setText(String text) {
                etDepartDate.setText(text);
            }
        });

        dateComponentMap.put(RETURN_DATE, new OnGetDate() {
            @Override
            public void setText(String text) {
                final String t=text;
                Validator.validateDates(etDepartDate.getText().toString(), text, new Validator.ValidationListener() {
                    @Override
                    public void validationResult(boolean status,String msg) {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        etReturnDate.setError(msg);
                        if(status==true){
                            etReturnDate.setText(t);
                        }
                    }
                });
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////

        etDepartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFlag = DEPART_DATE;
                Calendar calendar = Calendar.getInstance();
                /*calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));*/

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


                app.trackEvent("FAB", "SUBMIT AIRTICKET INQUIRY", "AIRTICKET INQUIRY");

                Log.d(TAG, "press btn before....");

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
        if(dateFlag==DEPART_DATE){
            argCalendar.set(Calendar.YEAR,year);
            argCalendar.set(Calendar.MONTH,monthOfYear);
            argCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }

    }

    private void submitForm() {


        final Map<String, String> formParams = new LinkedHashMap<>();
        formParams.put("Contact", etMobileNo.getText().toString());
        formParams.put("Email", app.getUser().getEmail());
        formParams.put("Type", locationType[selectedLocationType]);
        formParams.put("Trip", tripType[selectedTripType]);
        formParams.put("Depart Date", etDepartDate.getText().toString());

        if (!TextUtils.isEmpty(etReturnDate.getText().toString())) {
            formParams.put("Return Date", etReturnDate.getText().toString());
        }

        formParams.put("From", actFrom.getText().toString());
        formParams.put("To", actTo.getText().toString());
        formParams.put("Adult", spAdult.getSelectedItem().toString());
        formParams.put("Child", spChild.getSelectedItem().toString());
        formParams.put("Infant", spInfant.getSelectedItem().toString());
        formParams.put("Class", classType[selectedClassType]);

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
                                    SendMail.Type.AIRTICKET,
                                    getActivity(),
                                    new SendMail.MailCallbackListener() {
                                        @Override
                                        public void mailSentSuccessfully() {

                                            CommonUtilities.clearForm((ViewGroup) getActivity().findViewById(R.id.ll_formAirTicket));

                                            FragmentAirTickets.this.etMobileNo.requestFocus();

                                            CommonUtilities
                                                    .showAlertDialog(getActivity(),clFragmentAirTicket, "Air Ticket Booking",
                                                            "",
                                                            "Air Ticket Booking in Bhagwati Holidays",argCalendar);


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

        Validator.validateFrom(formParams.get("From"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                actFrom.setError(msg);
                isFormValid = isFormValid&status;
            }
        });

        Validator.validTo(formParams.get("To"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                actTo.setError(msg);
                isFormValid = isFormValid&status;
            }
        });

        Validator.validateDate(formParams.get("Depart Date"), new Validator.ValidationListener() {
            @Override
            public void validationResult(boolean status,String msg) {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                etDepartDate.setError(msg);
                isFormValid = isFormValid&status;
            }
        });

        if (formParams.get("Return Date") != null) {
            Validator.validateDate(formParams.get("Return Date"), new Validator.ValidationListener() {
                @Override
                public void validationResult(boolean status,String msg) {
                    //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    etReturnDate.setError(msg);
                    isFormValid = isFormValid&status;
                }
            });
        }

        return isFormValid;
    }

}