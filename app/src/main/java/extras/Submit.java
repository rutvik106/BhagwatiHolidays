package extras;

import com.rutvik.bhagwatiholidays.App;

import java.util.Map;

import webservicehandler.PostHandler;

/**
 * Created by ACER on 08-Mar-16 at 12:03 AM.
 */
public class Submit {

    private static final String TAG= App.APP_TAG+Submit.class.getSimpleName();

    public static void submitHolidayForm(Map<String,String> params,PostHandler.ResponseCallback responseCallback){
        if(params==null){
            throw new IllegalArgumentException("cannot pass null paramater");
        }
        params.put("type","holiday");
        new PostHandler(TAG,1,2000).doPostRequest("http://bhagwatiholidays.com/send-mail/send_mail.php",params,responseCallback);
    }


}
