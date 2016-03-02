package gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.rutvik.bhagwatiholidays.App;


/**
 * Created by ACER on 01-Mar-16.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = App.APP_TAG + MyInstanceIDListenerService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        Intent intent=new Intent(this,RegistrationIntentService.class);
        startService(intent);
    }
}
