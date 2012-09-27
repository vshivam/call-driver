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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class prefs extends Activity {
	EditText num ;
	Button upd;
	SharedPreferences app_preferences;
	Intent main_intent;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefs);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String number = app_preferences.getString("number", "Please Add a Number !");
        
        num =(EditText)(findViewById(R.id.EditText01));
        num.setText(number);
        upd = (Button)(findViewById(R.id.Button01));
        
        upd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	try
            	{
		            	String edittext = String.valueOf(num.getText());
		            	HttpClient client = new DefaultHttpClient();  
				        String getURL = "http://www.calldriver.in/site/check_num.php"+"?num="+edittext;
				        HttpGet get = new HttpGet(getURL);
				        HttpResponse responseGet = client.execute(get);  
				        HttpEntity entity = responseGet.getEntity();
				        String res = EntityUtils.toString(entity);
    					Log.d("http",res );
    					Toast.makeText(getApplicationContext(),res , Toast.LENGTH_SHORT).show();
    					if(res.compareTo("1")==0)
    					{
    						SharedPreferences.Editor editor = app_preferences.edit();
    		                editor.putString("number",edittext );
    		                editor.commit(); // Very important
    		                Toast.makeText(getApplicationContext(), "Thanks, We'll now start sending updates", Toast.LENGTH_LONG).show();
    		                main_intent = new Intent(getApplicationContext(), main.class);
    		                startActivity(main_intent);
    					}
    					else
    					{
    						Toast.makeText(getApplicationContext(), "Sorry! This Number is not Registered. Please Register First", Toast.LENGTH_LONG).show();
    					}
    					 		        
    		        	}
    		        
    					catch(Exception e)
    					{
    						Log.d("httperror","Error in http connection "+e.toString());
    					}
    					finally
    					{
    						
    					}
    			        
            	
            }
        });
        
        
	}

}
