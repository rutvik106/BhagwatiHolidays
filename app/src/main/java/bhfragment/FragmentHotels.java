package bhfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

/**
 * Created by Rakshit on 20-11-2015 at 14:23.
 */
public class FragmentHotels extends Fragment {

    EditText etMobileNo,etBookingDate,etDestination;
    RadioButton rbIndia, rbWorldWild;
    Spinner spAdult, spChild, spInfant,spNoOfNights;
    RatingBar rbHotelType;
    FloatingActionButton fabDone;

    String no;

    App app;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        app=(App)activity.getApplication();
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

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

        rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWide);

        rbHotelType = (RatingBar) rootView.findViewById(R.id.rb_hotelType);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        etDestination = (EditText) rootView.findViewById(R.id.et_destination);

        no = etMobileNo.getText().toString().trim();

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DONE",no);
            }
        });

        return rootView;
    }
}
