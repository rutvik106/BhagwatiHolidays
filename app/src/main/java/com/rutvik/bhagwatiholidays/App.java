package com.rutvik.bhagwatiholidays;

import android.app.Application;

import lwg.MyLoginWithGoogle;

/**
 * Created by ACER on 11-Nov-15.
 */
public class App extends Application {

    MyLoginWithGoogle.GooglePlusUser user;

    public void setUser(MyLoginWithGoogle.GooglePlusUser user)
    {
        this.user=user;
    }

    public MyLoginWithGoogle.GooglePlusUser getUser()
    {
        return user;
    }

}
