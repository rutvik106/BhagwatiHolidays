package extras;

import android.text.TextUtils;
import android.util.Log;

import com.rutvik.bhagwatiholidays.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rakshit on 16-03-2016 at 14:44.
 */
public class Validator {

    private static final String TAG = App.APP_TAG + Validator.class.getSimpleName();

    public static interface ValidationListener {
        public void validationFailed(String msg);
    }

    public static boolean validateContact(String value, ValidationListener listener) {

        Log.i(TAG, "validating contact VALUE: " + value);

        if (TextUtils.isEmpty(value)) {
            listener.validationFailed("Contact Required.");
            return false;
        }
        if (value.length() > 10) {
            listener.validationFailed("Invalid contact.");
            return false;
        }
        return true;
    }

    public static boolean validateDates(String departDate, String returnDate, ValidationListener listener) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date dd = formatter.parse(departDate);
            Date rd = formatter.parse(returnDate);
            int result = rd.compareTo(dd);
            if (result < 0) {
                listener.validationFailed("invalid date.");
                return false;
            }
        } catch (ParseException p) {
            listener.validationFailed("error parsing date");
            return false;
        }
        return true;
    }

    public static boolean validateFrom(String from, ValidationListener listener) {
        if (TextUtils.isEmpty(from)) {
            listener.validationFailed("From Required.");
            return false;
        }
        return true;
    }

    public static boolean validTo(String to, ValidationListener listener) {
        if (TextUtils.isEmpty(to)) {
            listener.validationFailed("To Required.");
            return false;
        }
        return true;
    }

    public static boolean validDestination(String destination, ValidationListener listener) {
        if (TextUtils.isEmpty(destination)) {
            listener.validationFailed("Destination Required.");
            return false;
        }
        return true;
    }
}
