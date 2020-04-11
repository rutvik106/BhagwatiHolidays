package extras;

import android.text.TextUtils;
import android.util.Log;

import com.rutvik.bhagwatiholidays.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rakshit on 16-03-2016 at 14:44.
 */
public class Validator {

    private static final String TAG = App.APP_TAG + Validator.class.getSimpleName();

    public static interface ValidationListener {
        public void validationResult(boolean status,String msg);
    }

    public static void validateContact(String value, ValidationListener listener) {

        Log.i(TAG, "validating contact VALUE: " + value);

        if (TextUtils.isEmpty(value)) {
            listener.validationResult(false,"Contact Required.");
        }
        else if (value.length() > 10) {
            listener.validationResult(false,"Must be 10 Digits");
        }
        else{
            listener.validationResult(true,null);
        }

    }

    public static void validateDate(String value,ValidationListener listener){
        if(TextUtils.isEmpty(value)){
            listener.validationResult(false,"Date required.");
        }
        else{
            listener.validationResult(true,null);
        }
    }

    public static void validateDates(String departDate, String returnDate, ValidationListener listener) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date dd = formatter.parse(departDate);
            Date rd = formatter.parse(returnDate);
            int result = rd.compareTo(dd);
            if (result < 0) {
                listener.validationResult(false,"invalid date.");
            }
            else{
                listener.validationResult(true,null);
            }
        } catch (ParseException p) {
            listener.validationResult(false,"error parsing date");
        }
    }

    public static void validateFrom(String from, ValidationListener listener) {
        if (TextUtils.isEmpty(from)) {
            listener.validationResult(false,"From Required.");
        }else {
            listener.validationResult(true,null);
        }
    }

    public static void validTo(String to, ValidationListener listener) {
        if (TextUtils.isEmpty(to)) {
            listener.validationResult(false,"To Required.");
        }else{
            listener.validationResult(true,null);
        }
    }

    public static void validDestination(String destination, ValidationListener listener) {
        if (TextUtils.isEmpty(destination)) {
            listener.validationResult(false,"Destination Required.");
        }else{
            listener.validationResult(true,null);
        }
    }
}
