package com.example.cam;

//import com.example.demo3.GPSService;
//import com.example.demo3.R;

//import com.example.sms2.MainActivity;
//import com.example.sms2.SmsListener;
//import com.example.sms2.SmsReceiver;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.Activity;
import android.view.Menu;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	Button buttonsend ;
	EditText textPhoneNo;
	EditText textSMS;
	final double latitude=0;
	 final double longitude=0;
	 Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonsend=(Button)findViewById(R.id.button1);
		
		textPhoneNo = (EditText) findViewById(R.id.editText1);
		textSMS = (EditText) findViewById(R.id.editText2);
		 String address = "";
			GPSService mGPSService = new GPSService(getApplicationContext());
			mGPSService.getLocation();

	if (mGPSService.isLocationAvailable == false) {

				// Here you can ask the user to try again, using return; for that
	Toast.makeText(getApplicationContext(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
		//		return;

				// Or you can continue without getting the location, remove the return; above and uncomment the line given below
				// address = "Location not available";
	} else {

				// Getting location co-ordinates
				double latitude = mGPSService.getLatitude();
				double longitude = mGPSService.getLongitude();
				Toast.makeText(getApplicationContext(), "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();

				address = mGPSService.getLocationAddress();

				//tvLocation.setText("Latitude: " + latitude + " \nLongitude: " + longitude);
				//tvAddress.setText("Address: " + address);
		}

			Toast.makeText(getApplicationContext(), "Your address is: " + address, Toast.LENGTH_SHORT).show();
			
			// make sure you close the gps after using it. Save user's battery power
			mGPSService.closeGPS();
			
			SmsReceiver.bindListener(new SmsListener() {
				
	            public void messageReceived(String messageText) {
	                Log.d("Text",messageText);
	                 Toast.makeText(MainActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();
	            }
	        });
			buttonsend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				  String phoneNo = textPhoneNo.getText().toString();
				  String sms = textSMS.getText().toString();

				  try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNo, null, sms, null, null);
					Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();
				  } catch (Exception e) {
					Toast.makeText(getApplicationContext(),
						"SMS faild, please try again later!",
						Toast.LENGTH_LONG).show();
					e.printStackTrace();
				  }

				}
			});
			


			

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
