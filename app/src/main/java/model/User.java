package model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ACER on 02-Mar-16.
 */
public class User {

    final SharedPreferences sp;


    public User(Context context, String name, String email, String profilePic) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("USER_NAME", name).apply();
        sp.edit().putString("USER_EMAIL", email).apply();
        sp.edit().putString("USER_PROFILE_PIC", profilePic).apply();
    }

    public String getEmail() {
        return sp.getString("USER_EMAIL", "");

    }

    public String getProfilePic() {
        return sp.getString("USER_PROFILE_PIC", "https://lh3.googleusercontent.com/-Wlkp-_tMv-Y/AAAAAAAAAAI/AAAAAAAAAAA/NbvcGT31kjM/s60-p-rw-no/photo.jpg");


    }

    public String getName() {
        return sp.getString("USER_NAME", "");
    }
}
