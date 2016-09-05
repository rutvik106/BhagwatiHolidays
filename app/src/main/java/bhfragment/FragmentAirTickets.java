package bhfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.FlightSearchResult;
import com.rutvik.bhagwatiholidays.LiveAPI;
import com.rutvik.bhagwatiholidays.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import adapter.CityListAutocompleteAdapter;
import extras.GetTermsAsync;
import extras.SendMail;
import extras.Validator;
import extras.CommonUtilities;
import liveapimodels.ApiConstants;
import liveapimodels.City;

/**
 * Created by Rakshit on 20-11-2015.
 */

public class FragmentAirTickets extends Fragment implements DatePickerDialog.OnDateSetListener
{

    private static final String TAG = App.APP_TAG + FragmentAirTickets.class.getSimpleName();

    EditText etMobileNo, etDepartDate, etReturnDate;
    //RadioButton rbIndia, rbWorldWild, rbReturn, rbOneWay, rbEconomy, rbBusiness;
    //RadioGroup rgType, rgTrip, rgClass;
    Spinner spAdult, spChild, spInfant;
    FloatingActionButton fabDone;
    AutoCompleteTextView actFrom, actTo;

    MultiStateToggleButton mstbClassType, mstbLocationType, mstbTripType;

    CoordinatorLayout clFragmentAirTicket;

    String[] classType;
    int selectedClassType = 0;

    String[] locationType;
    int selectedLocationType = 0;

    String[] tripType;
    int selectedTripType = 0;

    private GetTermsAsync getTermsAsync;

    App app;

    CityListAutocompleteAdapter fromAdapter, toAdapter;

    String fromCity, toCity;

    boolean isFormValid = true;

    DatePickerDialog datePickerDialog;
    android.app.FragmentManager fragmentManager;

    public static final int DEPART_DATE = 0;
    public static final int RETURN_DATE = 1;
    private int dateFlag;

    Map<Integer, OnGetDate> dateComponentMap = new HashMap<>();

    Calendar argCalendar = Calendar.getInstance();


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        classType = getResources().getStringArray(R.array.class_type);
        tripType = getResources().getStringArray(R.array.trip_type);
        locationType = getResources().getStringArray(R.array.location_type);

        app = (App) activity.getApplication();
        app.trackScreenView(FragmentAirTickets.class.getSimpleName());

    }

    public FragmentAirTickets()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_air_tickets, container, false);

        clFragmentAirTicket = (CoordinatorLayout) rootView.findViewById(R.id.cl_fragmentAirTickets);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etMobileNo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 10)
                {
                    CommonUtilities.hideKeyboard(getActivity(), getActivity().getCurrentFocus());
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        actFrom = (AutoCompleteTextView) rootView.findViewById(R.id.et_from);
        actTo = (AutoCompleteTextView) rootView.findViewById(R.id.et_to);
        actFrom.setThreshold(2);
        actFrom.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                fromCity = fromAdapter.getItem(i).toString();
                fromCity=fromCity.substring(0,fromCity.indexOf(" "));
                Log.i(TAG, "onItemSelected: CITY CODE: " + fromCity);
            }

        });
        actTo.setThreshold(2);
        actTo.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(toAdapter==null){
                    Log.i(TAG, "onItemClick: TO ADAPTER IS NULL!!!!!");
                }
                toCity = adapterView.getAdapter().getItem(i).toString();
                toCity=toCity.substring(0,toCity.indexOf(" "));
                Log.i(TAG, "onItemSelected: CITY CODE: " + toCity);
            }
        });
        fromAdapter = new CityListAutocompleteAdapter(getActivity(), actTo, toAdapter);

        actFrom.setAdapter(fromAdapter);

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

        /**     rgType = (RadioGroup) rootView.findViewById(R.id.rgType);
         rgTrip = (RadioGroup) rootView.findViewById(R.id.rgTrip);
         rgClass = (RadioGroup) rootView.findViewById(R.id.rgClass);

         rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

         rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWide);

         rbReturn = (RadioButton) rootView.findViewById(R.id.rb_return);

         rbOneWay = (RadioButton) rootView.findViewById(R.id.rb_oneWay);

         rbEconomy = (RadioButton) rootView.findViewById(R.id.rb_economy);

         rbBusiness = (RadioButton) rootView.findViewById(R.id.rb_business);*/

        mstbClassType = (MultiStateToggleButton) rootView.findViewById(R.id.mstb_classType);
        mstbClassType.setElements(R.array.class_type, 0);
        mstbClassType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener()
        {
            @Override
            public void onValueChanged(int position)
            {
                selectedClassType = position;
                CommonUtilities.hideKeyboard(getActivity(), getActivity().getCurrentFocus());
            }
        });

        mstbLocationType = (MultiStateToggleButton) rootView.findViewById(R.id.mstb_locationType);
        mstbLocationType.setElements(R.array.location_type, 0);
        mstbLocationType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener()
        {
            @Override
            public void onValueChanged(int position)
            {
                selectedLocationType = position;
                CommonUtilities.hideKeyboard(getActivity(), getActivity().getCurrentFocus());
            }
        });

        mstbTripType = (MultiStateToggleButton) rootView.findViewById(R.id.mstb_tripType);
        mstbTripType.setElements(R.array.trip_type, 0);
        mstbTripType.setOnValueChangedListener(new ToggleButton.OnValueChangedListener()
        {
            @Override
            public void onValueChanged(int position)
            {
                if (position == 1)
                {
                    etReturnDate.setVisibility(View.VISIBLE);

                } else
                {
                    etReturnDate.setVisibility(View.GONE);

                }
                selectedTripType = position;
            }
        });

        etDepartDate = (EditText) rootView.findViewById(R.id.et_departDate);
        etDepartDate.setTextIsSelectable(true);

        etReturnDate = (EditText) rootView.findViewById(R.id.et_returnDate);
        etReturnDate.setTextIsSelectable(false);
        etDepartDate.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    dateFlag = DEPART_DATE;
                    Calendar calendar = Calendar.getInstance();
                    datePickerDialog = DatePickerDialog.newInstance(FragmentAirTickets.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setMinDate(calendar);
                    datePickerDialog.show(fragmentManager, "DepartDate");
                }
            }
        });

        etReturnDate.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    if (!TextUtils.isEmpty(etDepartDate.getText()))
                    {
                        dateFlag = RETURN_DATE;
                        datePickerDialog = DatePickerDialog.newInstance(FragmentAirTickets.this, argCalendar.get(Calendar.YEAR), argCalendar.get(Calendar.MONTH),
                                argCalendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.setMinDate(argCalendar);
                        datePickerDialog.show(fragmentManager, "ReturnDate");
                    } else
                    {
                        etDepartDate.requestFocus();
                        Toast.makeText(getActivity(), "Set depart date first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**actFrom.addTextChangedListener(new TextWatcher()
         {
         @Override public void beforeTextChanged(CharSequence s, int start, int count, int after)
         {

         }

         @Override public void onTextChanged(CharSequence s, int start, int before, int count)
         {
         Log.i(TAG, "TEXT CHENGED IN FROM: " + s.toString());
         if (!TextUtils.isEmpty(s.toString()) && s.toString().length() > 3)
         {
         if (getTermsAsync != null)
         {
         getTermsAsync.cancel(true);
         getTermsAsync = null;
         }
         getTermsAsync = new GetTermsAsync(actFrom, getActivity(), CommonUtilities.URL_FROM_TO);
         getTermsAsync.execute(s.toString());
         actFrom.showDropDown();
         }
         }

         @Override public void afterTextChanged(Editable s)
         {

         }
         });

         actTo.addTextChangedListener(new TextWatcher()
         {
         @Override public void beforeTextChanged(CharSequence s, int start, int count, int after)
         {

         }

         @Override public void onTextChanged(CharSequence s, int start, int before, int count)
         {
         Log.i(TAG, "TEXT CHENGED IN FROM: " + s.toString());
         if (!TextUtils.isEmpty(s.toString()) && s.toString().length() > 1)
         {
         if (getTermsAsync != null)
         {
         getTermsAsync.cancel(true);
         getTermsAsync = null;
         }
         getTermsAsync = new GetTermsAsync(actTo, getActivity(), CommonUtilities.URL_FROM_TO);
         getTermsAsync.execute(s.toString());
         actTo.showDropDown();
         }
         }

         @Override public void afterTextChanged(Editable s)
         {

         }
         });*/


        //Take value from Radio


        /**        rgTrip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_return) {
        etReturnDate.setVisibility(View.VISIBLE);

        } else {
        etReturnDate.setVisibility(View.GONE);

        }
        }
        });*/


        dateComponentMap.put(DEPART_DATE, new OnGetDate()
        {
            @Override
            public void setText(String text)
            {
                etDepartDate.setText(text);
            }
        });

        dateComponentMap.put(RETURN_DATE, new OnGetDate()
        {
            @Override
            public void setText(String text)
            {
                final String t = text;
                Validator.validateDates(etDepartDate.getText().toString(), text, new Validator.ValidationListener()
                {
                    @Override
                    public void validationResult(boolean status, String msg)
                    {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        etReturnDate.setError(msg);
                        if (status == true)
                        {
                            etReturnDate.setText(t);
                        }
                    }
                });
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////

        etDepartDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dateFlag = DEPART_DATE;
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = DatePickerDialog.newInstance(FragmentAirTickets.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setMinDate(calendar);
                datePickerDialog.show(fragmentManager, "DepartDate");
            }
        });

        etReturnDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!TextUtils.isEmpty(etDepartDate.getText()))
                {
                    dateFlag = RETURN_DATE;
                    datePickerDialog = DatePickerDialog.newInstance(FragmentAirTickets.this, argCalendar.get(Calendar.YEAR), argCalendar.get(Calendar.MONTH),
                            argCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setMinDate(argCalendar);
                    datePickerDialog.show(fragmentManager, "ReturnDate");
                } else
                {
                    etDepartDate.requestFocus();
                    Toast.makeText(getActivity(), "Set depart date first", Toast.LENGTH_LONG).show();
                }
            }
        });

        fabDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //startActivity(new Intent(getActivity(), FlightSearchResult.class));

                app.trackEvent("FAB", "SUBMIT AIRTICKET INQUIRY", "AIRTICKET INQUIRY");

                Log.d(TAG, "press btn before....");

                submitForm();

            }
        });

        return rootView;
    }


    public static interface OnGetDate
    {
        void setText(String text);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {

        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        dateComponentMap.get(dateFlag).setText(date);
        if (dateFlag == DEPART_DATE)
        {
            argCalendar.set(Calendar.YEAR, year);
            argCalendar.set(Calendar.MONTH, monthOfYear);
            argCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }

    }

    private void submitForm()
    {

        final Map<String, String> formParams = new LinkedHashMap<>();
        /**formParams.put("Contact", etMobileNo.getText().toString());
         formParams.put("Email", app.getUser().getEmail());
         formParams.put("Type", locationType[selectedLocationType]);
         formParams.put("Trip", tripType[selectedTripType]);
         formParams.put("Depart Date", etDepartDate.getText().toString());
         if (!TextUtils.isEmpty(etReturnDate.getText().toString()))
         {
         formParams.put("Return Date", etReturnDate.getText().toString());
         }
         formParams.put("From", actFrom.getText().toString());
         formParams.put("To", actTo.getText().toString());
         formParams.put("Adult", spAdult.getSelectedItem().toString());
         formParams.put("Child", spChild.getSelectedItem().toString());
         formParams.put("Infant", spInfant.getSelectedItem().toString());
         formParams.put("Class", classType[selectedClassType]);*/

        formParams.put("origin", fromCity);
        formParams.put("destination", toCity);

        if (selectedClassType == 0)
        {
            formParams.put("flight_cabin_class", ApiConstants.CabinClass.ECONOMY);
        } else if (selectedClassType == 1)
        {
            formParams.put("flight_cabin_class", ApiConstants.CabinClass.BUSINESS);
        }

        formParams.put("departure_time", etDepartDate.getText().toString());

        if (!TextUtils.isEmpty(etReturnDate.getText().toString()))
        {
            formParams.put("arrival_time", etReturnDate.getText().toString());
            formParams.put("journey_type", ApiConstants.JourneyType.RETURN);
        } else
        {
            formParams.put("arrival_time", etDepartDate.getText().toString());
            formParams.put("journey_type", ApiConstants.JourneyType.ONE_WAY);
        }

        formParams.put("adult_count", spAdult.getSelectedItem().toString());
        formParams.put("child_count", spChild.getSelectedItem().toString());
        formParams.put("infant_count", spInfant.getSelectedItem().toString());

        Log.d(TAG, "Check check........!!!!");

        if (isFormParamValid(formParams))
        {
            final String authToken = app.getApiAuthentication().getTokenId();
            if (!authToken.isEmpty())
            {
                new LiveAPI.SearchFlights(authToken, formParams)
                {

                    @Override
                    protected void onPostExecute(liveapimodels.flightsearchresult.FlightSearchResult aVoid)
                    {

                    }
                }.execute();
            }

            /**CommonUtilities.showSimpleAlertDialog(getActivity(),
             "Alert",
             "Send inquiry to Bhagwati Holidays?",
             "Send",
             "Cancel",
             new CommonUtilities.SimpleAlertDialog.OnClickListener()
             {
             @Override public void positiveButtonClicked(DialogInterface dialog, int which)
             {

             final SendMail sendMail = new SendMail(app.getUser().getEmail(),
             SendMail.Type.AIRTICKET,
             getActivity(),
             new SendMail.MailCallbackListener()
             {
             @Override public void mailSentSuccessfully()
             {

             CommonUtilities.clearForm((ViewGroup) getActivity().findViewById(R.id.ll_formAirTicket));

             FragmentAirTickets.this.etMobileNo.requestFocus();

             CommonUtilities
             .showAlertDialog(getActivity(), clFragmentAirTicket, "Air Ticket Booking",
             "",
             "Air Ticket Booking in Bhagwati Holidays", argCalendar);


             }
             }, app);

             sendMail.execute(formParams);

             }

             @Override public void negativeButtonClicked(DialogInterface dialog, int which)
             {
             dialog.dismiss();
             }
             });*/


        }


    }

    public boolean isFormParamValid(Map<String, String> formParams)
    {
        Log.i(TAG, "inside is form param valid");

        isFormValid = true;

        /**        Validator.validateContact(formParams.get("Contact"), new Validator.ValidationListener()
         {
         @Override public void validationResult(boolean status, String msg)
         {
         //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
         etMobileNo.setError(msg);
         isFormValid = isFormValid & status;
         }
         });*/

        Validator.validateFrom(formParams.get("origin"), new Validator.ValidationListener()
        {
            @Override
            public void validationResult(boolean status, String msg)
            {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                actFrom.setError(msg);
                isFormValid = isFormValid & status;
            }
        });

        Validator.validTo(formParams.get("destination"), new Validator.ValidationListener()
        {
            @Override
            public void validationResult(boolean status, String msg)
            {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                actTo.setError(msg);
                isFormValid = isFormValid & status;
            }
        });

        Validator.validateDate(formParams.get("departure_time"), new Validator.ValidationListener()
        {
            @Override
            public void validationResult(boolean status, String msg)
            {
                //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                etDepartDate.setError(msg);
                isFormValid = isFormValid & status;
            }
        });

        if (formParams.get("arrival_time") != null)
        {
            Validator.validateDate(formParams.get("arrival_time"), new Validator.ValidationListener()
            {
                @Override
                public void validationResult(boolean status, String msg)
                {
                    //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    etReturnDate.setError(msg);
                    isFormValid = isFormValid & status;
                }
            });
        }

        return isFormValid;
    }


}