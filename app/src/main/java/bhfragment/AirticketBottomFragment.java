package bhfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.R;

/**
 * Created by Rakshit on 11-06-2016 at 11:23.
 */
public class AirticketBottomFragment extends Fragment {

    public static AirticketBottomFragment newInstance(int index) {
        AirticketBottomFragment fragment = new AirticketBottomFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_air_bottom, container, false);

        return rootView;
    }
}
