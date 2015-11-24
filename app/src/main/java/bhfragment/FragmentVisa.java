package bhfragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.rutvik.bhagwatiholidays.R;

/**
 * Created by Rakshit on 20-11-2015.
 */
public class FragmentVisa extends Fragment {

    FloatingActionButton fabDone;
    EditText etMobileNo, etDateOfTravel, etDestination;
    RadioButton rbBusiness, rbStudent;

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

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.done);

        etMobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);

        etDateOfTravel = (EditText) rootView.findViewById(R.id.et_dateOfTravel);

        etDestination = (EditText) rootView.findViewById(R.id.et_destination);

        rbBusiness = (RadioButton) rootView.findViewById(R.id.rb_business);

        rbStudent = (RadioButton) rootView.findViewById(R.id.rb_student);

        return rootView;
    }
}
