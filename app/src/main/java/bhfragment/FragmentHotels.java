package bhfragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.rutvik.bhagwatiholidays.R;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentHotels extends Fragment {

    EditText etMobileNo,etBookingDate,etDestination;
    RadioButton rbIndia, rbWorldWild;
    Spinner spAdult, spChild, spInfant,spNoOfNights;
    RatingBar rbHotelType;
    FloatingActionButton fabDone;

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

        spChild = (Spinner) rootView.findViewById(R.id.sp_child);

        spInfant = (Spinner) rootView.findViewById(R.id.sp_infant);

        spNoOfNights = (Spinner) rootView.findViewById(R.id.sp_noOfNights);

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        rbIndia = (RadioButton) rootView.findViewById(R.id.rb_india);

        rbWorldWild = (RadioButton) rootView.findViewById(R.id.rb_worldWild);

        rbHotelType = (RatingBar) rootView.findViewById(R.id.rb_hotelType);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etBookingDate = (EditText) rootView.findViewById(R.id.et_bookingDate);

        etDestination = (EditText) rootView.findViewById(R.id.et_destination);

        return rootView;
    }
}
