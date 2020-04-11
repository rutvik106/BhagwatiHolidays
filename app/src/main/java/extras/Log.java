package extras;

/**
 * Created by rutvik on 04-04-2016 at 10:36 AM.
 */
public class Log {


    static boolean showLog=true;

    public static void i(String tag, String message){
        if(showLog){
            android.util.Log.i(tag,message);
        }
    }

    public static void d(String tag, String message){
        if(showLog){
            android.util.Log.d(tag,message);
        }
    }


}
