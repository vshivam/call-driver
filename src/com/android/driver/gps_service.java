package com.android.driver;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class gps_service extends Service
{
private static final String TAG = "BOOMBOOMTESTGPS";
private LocationManager mLocationManager = null;
private static final int LOCATION_INTERVAL = 30*1000*60;
private static final float LOCATION_DISTANCE = 25;
SharedPreferences app_preferences;


private class LocationListener implements android.location.LocationListener{
    Location mLastLocation;
    public LocationListener(String provider)
    {
        Log.e(TAG, "LocationListener " + provider);
        mLastLocation = new Location(provider);
    }
    @Override
    public void onLocationChanged(Location location)
    {
    	app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String number = app_preferences.getString("number", "Please Add a Number !");
        if(number.equalsIgnoreCase("Please Add a Number !"))
        {
        	Toast.makeText(getApplicationContext(), "Please Update Your Number", Toast.LENGTH_LONG).show();
        }
        else
        {
	        Log.e(TAG, "onLocationChanged: " + location);
	        String lat = String.valueOf(location.getLatitude());
			String lng = String.valueOf(location.getLongitude());
			Log.e(TAG, lat);
			Log.e(TAG, lng);
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("num", number));
			nameValuePairs.add(new BasicNameValuePair("lat", String.valueOf(lat)));
			nameValuePairs.add(new BasicNameValuePair("long", String.valueOf(lng)));
			
	    
	    
		    try
			{
		    	Log.e(TAG, "try");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://calldriver.in/site/update_location.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
	
	
			
			}
			catch(Exception e)
			{
				Toast.makeText(getBaseContext(),"Error in http connection "+e.toString(), Toast.LENGTH_LONG).show();
			}
			finally{
			}
	        mLastLocation.set(location);
        	Toast.makeText(getApplicationContext(), "Location Update Sent !", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void onProviderDisabled(String provider)
    {
        Log.e(TAG, "onProviderDisabled: " + provider);            
    }
    @Override
    public void onProviderEnabled(String provider)
    {
        Log.e(TAG, "onProviderEnabled: " + provider);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.e(TAG, "onStatusChanged: " + provider);
    }
} 
LocationListener[] mLocationListeners = new LocationListener[] {
        new LocationListener(LocationManager.GPS_PROVIDER),
        new LocationListener(LocationManager.NETWORK_PROVIDER)
};
@Override
public IBinder onBind(Intent arg0)
{
    return null;
}
@Override
public int onStartCommand(Intent intent, int flags, int startId)
{
    Log.e(TAG, "onStartCommand");
    super.onStartCommand(intent, flags, startId);       
    return START_STICKY;
}
@Override
public void onCreate()
{
    Log.e(TAG, "onCreate");
    initializeLocationManager();
    try {
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[1]);
    } catch (java.lang.SecurityException ex) {
        Log.i(TAG, "fail to request location update, ignore", ex);
    } catch (IllegalArgumentException ex) {
        Log.d(TAG, "network provider does not exist, " + ex.getMessage());
    }
    try {
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[0]);
    } catch (java.lang.SecurityException ex) {
        Log.i(TAG, "fail to request location update, ignore", ex);
    } catch (IllegalArgumentException ex) {
        Log.d(TAG, "gps provider does not exist " + ex.getMessage());
    }
}
@Override
public void onDestroy()
{
    Log.e(TAG, "onDestroy");
    super.onDestroy();
    if (mLocationManager != null) {
        for (int i = 0; i < mLocationListeners.length; i++) {
            try {
                mLocationManager.removeUpdates(mLocationListeners[i]);
            } catch (Exception ex) {
                Log.i(TAG, "fail to remove location listners, ignore", ex);
            }
        }
    }
} 
private void initializeLocationManager() {
    Log.e(TAG, "initializeLocationManager");
    if (mLocationManager == null) {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }
}
}
   