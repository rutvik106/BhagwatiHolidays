package bhfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentAirTickets extends Fragment {

    EditText etMobileNo, etFrom, etTo, etDepartureDate;
    RadioButton rbIndia, rbWorldWild, rbReturn, rbOneWay, rbEconomy, rbBusiness;
    Spinner spAdult, spChild, spInfant;
    FloatingActionButton fabDone;

    App app;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app=(App)activity.getApplication();
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

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etFrom = (EditText) rootView.findViewById(R.id.et_from);

        etTo = (EditText) rootView.findViewById(R.id.et_to);

        etDepartureDate = (EditText) rootView.findViewById(R.id.et_departureDate);

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

        rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

        rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWild);

        rbReturn = (RadioButton) rootView.findViewById(R.id.rb_return);

        rbOneWay = (RadioButton) rootView.findViewById(R.id.rb_oneWay);

        rbEconomy = (RadioButton) rootView.findViewById(R.id.rb_economy);

        rbBusiness = (RadioButton) rootView.findViewById(R.id.rb_business);


        return rootView;
    }
}

