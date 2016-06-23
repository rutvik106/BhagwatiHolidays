package bhfragment;

import android.app.Fragment;
import android.os.Bundle;

import com.rutvik.bhagwatiholidays.App;

import org.json.JSONObject;

/**
 * Created by rutvik on 17-06-2016 at 10:02 AM.
 */
public class FragmentBookingDetails extends Fragment {

    public static final String TAG = App.APP_TAG + FragmentBookingDetails.class.getSimpleName();

    JSONObject obj;

    public static FragmentBookingDetails newInstance(int index) {
        FragmentBookingDetails fragment = new FragmentBookingDetails();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

}
