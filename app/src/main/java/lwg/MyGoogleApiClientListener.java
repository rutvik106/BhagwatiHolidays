package lwg;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

public interface MyGoogleApiClientListener
{
	public void onConnectionFailed(ConnectionResult result);

	public void onConnected(Bundle bundle);
	
	public void onConnectionSuspended(int status);
}
