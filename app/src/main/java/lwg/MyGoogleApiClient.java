package lwg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.Plus.PlusOptions;

public class MyGoogleApiClient implements ConnectionCallbacks,OnConnectionFailedListener {

	public final static String TAG="LWG " + MyGoogleApiClient.class.getSimpleName();

	private GoogleApiClient mGoogleApiClient;

	private final Context context;

	private ConnectionResult mConnectionResult;
	
	private MyGoogleApiClientListener apiClientListener;

	public MyGoogleApiClient(Context context, MyGoogleApiClientListener apiClientListener) {

		Log.i(TAG, "initializing...");

		this.context=context;
		this.apiClientListener=apiClientListener;

		buildGoogleApiClient();
		
		connect();

	}
	
	public Context getContext()
	{
		return context;
	}

	public ConnectionResult getConnectionResult()
	{
		return mConnectionResult;
	}

	public boolean isConnecting()
	{
		return mGoogleApiClient.isConnecting();
	}

	public boolean isConnected()
	{
		return mGoogleApiClient.isConnected();
	}

	public synchronized void buildGoogleApiClient() {

		Log.i(TAG, "building google api client...");

		mGoogleApiClient = new GoogleApiClient.Builder(context)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API,PlusOptions.builder().build())
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();

	}

	public void connect()
	{
		if(!isConnected() && !isConnecting())
		{
			Log.i(TAG, "connecting...");
			mGoogleApiClient.connect();
		}
	}

	public GoogleApiClient getGoogleApiClient()
	{
		return mGoogleApiClient;
	}

	public void disconnect()
	{
		if(isConnected() && !isConnecting())
		{
			Log.i(TAG, "disconnecting...");
			mGoogleApiClient.disconnect();
		}
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		
		Log.i(TAG, "connection failed (result saved)");
		
		mConnectionResult=result;
		
		apiClientListener.onConnectionFailed(result);
		
	}

	@Override
	public void onConnected(Bundle bundle) {
		
		Log.i(TAG, "connected");
		
		apiClientListener.onConnected(bundle);
	}

	@Override
	public void onConnectionSuspended(int status) {
		
		Log.i(TAG, "connection suspended");
		
		apiClientListener.onConnectionSuspended(status);
		
		
	}
	
	

}



