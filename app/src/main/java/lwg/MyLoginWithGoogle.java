package lwg;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MyLoginWithGoogle {

	public final static String TAG="LWG "+MyLoginWithGoogle.class.getSimpleName();

	public static final int RC_SIGN_IN = 0;

	private MyGoogleApiClient myGoogleApiClient;

	private Activity activity;

	public MyLoginWithGoogle(MyGoogleApiClient myGoogleApiClient, Activity activity) {

		Log.i(TAG, "initializing...");
		
		this.myGoogleApiClient=myGoogleApiClient;
		this.activity=activity;

	}



	public void signInWithGplus() {
		
		Log.i(TAG, "signInWithGplus");
		
		if (!myGoogleApiClient.isConnecting()) {
			resolveSignInError();
		}
	}


	private void resolveSignInError() {
		Log.i(TAG, "resolveSignInError");
		if (myGoogleApiClient.getConnectionResult().hasResolution()) {
			
			Log.i(TAG, "yes has resolution");
			
			Log.i(TAG, "opening OAuth Window...");
			
			try {
				myGoogleApiClient.getConnectionResult().startResolutionForResult(activity, RC_SIGN_IN);
			} catch (SendIntentException e) {
				myGoogleApiClient.connect();
			}
		}
	}

	public void signOutFromGplus() {
		Log.i(TAG, "signing out");
		if (myGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(myGoogleApiClient.getGoogleApiClient());
			myGoogleApiClient.disconnect();
			myGoogleApiClient.connect();
		}
	}


	public void revokeGplusAccess() {
		
		Log.i(TAG, "revoking access");
		
		if (myGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(myGoogleApiClient.getGoogleApiClient());
			Plus.AccountApi.revokeAccessAndDisconnect(myGoogleApiClient.getGoogleApiClient())
			.setResultCallback(new ResultCallback<Status>() {

				@Override
				public void onResult(Status arg0) {

					Log.e(TAG, "User access revoked!");
					myGoogleApiClient.connect();

				}
			});

		}
	}
	

	public GooglePlusUser getProfileInformation() {
		
		Log.i(TAG, "getting profile info");
		
		try {
			if (Plus.PeopleApi.getCurrentPerson(myGoogleApiClient.getGoogleApiClient()) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(myGoogleApiClient.getGoogleApiClient());

				GooglePlusUser user=new GooglePlusUser(currentPerson,myGoogleApiClient);


				return user;


			} else {
				Log.i(TAG,"Person information is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

    public static class GooglePlusUser
    {
        //public final static String TAG="LWG "+MyLoginWithGoogle.class.getSimpleName();

        String userName,userPhotoUrl,userGooglePlusProfileUrl,userEmail;

        private Person person;

        private MyGoogleApiClient myGoogleApiClient;

        public GooglePlusUser(Person currentPerson, MyGoogleApiClient myGoogleApiClient) {
            this.person=currentPerson;

            this.myGoogleApiClient=myGoogleApiClient;

            userName=person.getDisplayName();
            userPhotoUrl=person.getImage().getUrl();
            userGooglePlusProfileUrl=person.getUrl();



            userEmail=Plus.AccountApi.getAccountName(this.myGoogleApiClient.getGoogleApiClient());

            printLog();


        }

        private void printLog()
        {
            Log.i(TAG,"display name: "+userName);
            Log.i(TAG,"image url: "+userPhotoUrl);
            Log.i(TAG,"profile url "+userGooglePlusProfileUrl);
            Log.i(TAG,"email: "+userEmail);
        }

        public String getUserGooglePlusProfileUrl() {
            return userGooglePlusProfileUrl;
        }

        public void setUserGooglePlusProfileUrl(String userGooglePlusProfileUrl) {
            this.userGooglePlusProfileUrl = userGooglePlusProfileUrl;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }
    }




}


