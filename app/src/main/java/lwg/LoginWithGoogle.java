package lwg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.rutvik.bhagwatiholidays.App;


/**
 * Created by ACER on 01-Mar-16.
 */
public abstract class LoginWithGoogle extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final int RC_SIGN_IN = 0;

    private static final String TAG = App.APP_TAG + LoginWithGoogle.class.getSimpleName();

    protected GoogleApiClient mGoogleApiClient;

    ProgressDialog mProgressDialog;

    protected GoogleSignInOptions gso;

    public LoginWithGoogle(){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }



    @Override
    protected void onStart() {
        super.onStart();


        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (opr.isDone()) {
            Log.i(TAG, "GOT CACHED SIGN-IN(ALREADY LOGGED IN)");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            Log.i(TAG, "USER NOT LOGGED IN");
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "request code: " + requestCode);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.i(TAG,"Now Handling sign in result");
            Log.i(TAG, "intent Data: " + data.toString());
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            /*if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                Log.i(TAG,"display name: "+acct.getDisplayName());
                Log.i(TAG,"email: "+acct.getEmail());
            }
            Log.i(TAG,"account: "+result.getSignInAccount().getEmail());*/
            handleSignInResult(result);
        }

    }

    public LoginWithGoogle(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;


    }

    abstract protected void loggedIn(GoogleSignInAccount account);

    abstract protected void loggedOut();

    abstract protected void revokedAccess();

    @Override
    abstract public void onConnectionFailed(ConnectionResult connectionResult);


    // [START handleSignInResult]
    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            Log.i(TAG, "LOGIN SUCCESSFUL (GETTING USER INFO)");
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.i(TAG, "Display Name: " + acct.getDisplayName());
            Log.i(TAG, "Email: " + acct.getEmail());
            Log.i(TAG, "Photo Url: " + acct.getPhotoUrl());


            loggedIn(acct);


            ////Log.i(TAG, "DOING GCM STUFF...");

            /*Intent intent = new Intent(this, RegistrationIntentService.class);
            intent.putExtra("name", acct.getDisplayName());
            intent.putExtra("email", acct.getEmail());
            startService(intent);*/

        } else {
            // Signed out, show unauthenticated UI.
            loggedOut();
        }

        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }

    }
    // [END handleSignInResult]


    // [START signIn]
    protected void signIn() {
        Log.i(TAG, "SIGNING IN...");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]


    // [START signOut]
    protected void signOut() {
        Log.i(TAG, "SINGING OUT...");
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        loggedOut();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]


    // [START revokeAccess]
    protected void revokeAccess() {
        Log.i(TAG, "REVOKING ACCESS...");
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        Log.i(TAG, "ACCESS REVOKED.");
                        revokedAccess();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]


    private void showProgressDialog() {
        Log.i(TAG, "SHOWING PROGRESS DIALOG...");
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("Please Wait");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        Log.i(TAG, "HIDING PROGRESS DIALOG...");
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
