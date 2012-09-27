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
import android.widget.Spinner;
import android.widget.Toast;

public class reg extends Activity{
	String name, dl_num, dl_valid, city, mob_num, mon, dd;
	EditText e_name,e_dl_num,e_dl_valid,e_city,e_mob_num,e_dl_valid_dd;
	Spinner s_mon;
	Button reg;
	Intent main_intent;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        reg = (Button)findViewById(R.id.Button01);
        reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	
            	
    			   
    			try {
    				 
    				SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    		        String number = app_preferences.getString("number", "Please Add a Number !");
    		        
    		        s_mon = (Spinner) findViewById(R.id.spinner1);
    		        e_name = (EditText) findViewById(R.id.EditText01);
    		        e_dl_num = (EditText) findViewById(R.id.EditText02);
    		        e_dl_valid = (EditText) findViewById(R.id.EditText03);
    		        e_city = (EditText) findViewById(R.id.EditText04);
    		        e_mob_num = (EditText) findViewById(R.id.EditText05);
    		        e_dl_valid_dd = (EditText) findViewById(R.id.EditText06);

    		        mon = String.valueOf(s_mon.getSelectedItem());
    		        name = String.valueOf(e_name.getText());
    		        dl_num = String.valueOf(e_dl_num.getText());
    		        dl_valid = String.valueOf(e_dl_valid.getText());
    		        city = String.valueOf(e_city.getText());
    		        mob_num = String.valueOf(e_mob_num.getText());
    		        dd = String.valueOf(e_dl_valid_dd.getText());
    		        
		        	Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

    		        if(name.length()==0 ||dd.length()==0|| dl_num.length() ==0 || dl_valid.length()==0 || city.length() ==0)
    		        {
    		        	Toast.makeText(getApplicationContext(), "All Fields are Compulsory !", Toast.LENGTH_LONG).show();
    		        }
    		        else if(mob_num.length()<10)
    		        {
    		        	Toast.makeText(getApplicationContext(), "Please Check Your Phone Number !", Toast.LENGTH_LONG).show();

    		        }
    		        else    		        
    		        {	
    		        	SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("number",mob_num );
                        editor.commit(); // Very important
    		        	name = name.trim();
    		        	dl_num = dl_num.trim();
    		           	dl_valid = dl_valid.trim();
    		        	
    		           	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    					nameValuePairs.add(new BasicNameValuePair("mon", mon));
    					nameValuePairs.add(new BasicNameValuePair("name", name));
    					nameValuePairs.add(new BasicNameValuePair("city", city));
    					nameValuePairs.add(new BasicNameValuePair("dl_num", dl_num));
    					nameValuePairs.add(new BasicNameValuePair("dl_valid", dl_valid));
    					nameValuePairs.add(new BasicNameValuePair("mob_num", mob_num));
    					nameValuePairs.add(new BasicNameValuePair("dd", dd));

    					Log.d("mon", mon);


    					HttpClient httpclient = new DefaultHttpClient();
    					HttpPost httppost = new HttpPost("http://www.calldriver.in/site/reg.php");
    					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    					HttpResponse response = httpclient.execute(httppost);
    					HttpEntity entity = response.getEntity();
    					Log.d("http", EntityUtils.toString(entity));
    					Toast.makeText(getApplicationContext(), "Location Update Sent !", Toast.LENGTH_LONG).show();
    		        	main_intent = new Intent(getApplicationContext(), main.class);
    		        
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
