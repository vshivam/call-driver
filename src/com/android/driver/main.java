package com.android.driver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class main extends Activity {
	Button free;
	Button busy;
	String number;
	Button update;
	Button reg;
	Intent service_intent,update_intent,reg_intent;
	SharedPreferences app_preferences;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        service_intent = new Intent(getApplicationContext(), gps_service.class);
        startService(service_intent);
              
        busy = (Button)findViewById(R.id.Button01);
        free = (Button)findViewById(R.id.Button02);
        update = (Button)findViewById(R.id.Button03);
        reg = (Button) findViewById(R.id.Button04);
        free.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	
            	
    			   
    			try {
    				app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    		        String number = app_preferences.getString("number", "Please Add a Number !");
    		        if(number.equalsIgnoreCase("Please Add a Number !"))
    		        {
    		        	Toast.makeText(getApplicationContext(), "Please Update Your Number", Toast.LENGTH_LONG).show();
    		        }
    		        else
    		        {	
    		        HttpClient client = new DefaultHttpClient();  
    		        String getURL = "http://www.calldriver.in/site/update_busy.php"+"?num="+number+"&busy=0";
    		        HttpGet get = new HttpGet(getURL);
    		        HttpResponse responseGet = client.execute(get);  
    		        HttpEntity resEntityGet = responseGet.getEntity();  
    		        if (resEntityGet != null) {  
    		                    //do something with the response
    		                    Log.i("GET RESPONSE",EntityUtils.toString(resEntityGet));
    		                }
    		        Toast.makeText(getApplicationContext(), "You've been marked Free !", Toast.LENGTH_LONG).show();
    		        }
    		} catch (Exception e) {
    		    e.printStackTrace();
    		}
    			

    			free.setEnabled(false);
    			busy.setEnabled(true);
                startService(service_intent);
            }
        });
        
        busy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub  		    
    			  
    			try {
    				app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    		        String number = app_preferences.getString("number", "Please Add a Number !");
    		        if(number.equalsIgnoreCase("Please Add a Number !"))
    		        {
    		        	Toast.makeText(getApplicationContext(), "Please Update Your Number", Toast.LENGTH_LONG).show();
    		        }
    		        else
    		        {
    		        HttpClient client = new DefaultHttpClient();  
    		        String getURL = "http://www.calldriver.in/site/update_busy.php"+"?num="+number+"&busy=1";
                    Log.i("GET RESPONSE",getURL);
    		        HttpGet get = new HttpGet(getURL);
    		        HttpResponse responseGet = client.execute(get);  
    		        HttpEntity resEntityGet = responseGet.getEntity();  
    		        if (resEntityGet != null) {  
    		                    //do something with the response
    		                    Log.i("GET RESPONSE",EntityUtils.toString(resEntityGet));
    		                }
    		        Toast.makeText(getApplicationContext(), "You've been marked Busy !", Toast.LENGTH_LONG).show();
    		        }
    		} catch (Exception e) {
    		    e.printStackTrace();
    		}
        		
    		
    			free.setEnabled(true);
    			busy.setEnabled(false);
                stopService(service_intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                update_intent = new Intent(getApplicationContext(), prefs.class);
                startActivity(update_intent);
            }
        });
        
        reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	reg_intent = new  Intent(getApplicationContext(),reg.class);
                startActivity(reg_intent);
            }
        });
    }
}