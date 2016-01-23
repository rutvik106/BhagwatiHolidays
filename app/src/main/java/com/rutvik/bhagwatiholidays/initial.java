package com.rutvik.bhagwatiholidays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;


import lwg.MyGoogleApiClient;
import lwg.MyGoogleApiClientListener;
import lwg.MyLoginWithGoogle;

public class initial extends Activity implements MyGoogleApiClientListener{

    public final static String TAG="BWT "+initial.class.getSimpleName();

    SignInButton btnSignIn;

    MyGoogleApiClient myGoogleApiClient;
    MyLoginWithGoogle myLoginWithGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_initial);

        btnSignIn = (SignInButton) findViewById(R.id.btn_signIn);

        myGoogleApiClient = new MyGoogleApiClient(this,this);

        myLoginWithGoogle = new MyLoginWithGoogle(myGoogleApiClient,this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLoginWithGoogle.signInWithGplus();
            }
        });

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {



    }

    @Override
    public void onConnected(Bundle bundle) {

        //User has logged in successfully (do something)

        App app=(App) getApplication();

        app.setUser(myLoginWithGoogle.getProfileInformation());

        Intent i=new Intent(this,SwipeTabActivity.class);

        startActivity(i);


    }

    @Override
    public void onConnectionSuspended(int status) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG,"request code: ("+requestCode+") result code: ("+resultCode+")");

    }
}
