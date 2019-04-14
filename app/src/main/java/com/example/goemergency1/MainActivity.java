package com.example.goemergency1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Context context = null;
	//private ProgressDialog dialog = null;
	String latitude = "0";
	String longitude = "0";
	TextView tvLocation = null;
	private String etone,ettwo,etthree,etfour,etfive;
	private String etMessage1;
	private SharedPreferences preferences;
	
	//String address="";
	MediaPlayer objPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		
		Button btnMylocation = (Button) findViewById(R.id.btnMyLocation);
		tvLocation = (TextView) findViewById(R.id.tvLocation);

		btnMylocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), MyLocation.class);
				
				intent.putExtra("LAT", latitude);
				intent.putExtra("LON", longitude);
				startActivity(intent);
			}
		});
		
//		Intent intent = new Intent(this, MyService.class);
//		//Start Service
//		startService(intent);
	}
	public void scream(View view) {

	}
	@Override
	public void onPause(){
		super.onPause();


	}
@Override
	public void onDestroy(){
		super.onDestroy();
	try{
		objPlayer.stop();
		objPlayer.release();
	}catch (Exception e){

	}
	}
	public void stopScream(View view) {

	}
	public void bubble(View v){
		Intent intent = new Intent(getApplicationContext(), FloatingFaceBubbleService.class);
		startService(intent);
		/*View view=findViewById(R.id.clayout);
		Snackbar.make(view, "Click on row to know more details", Snackbar.LENGTH_LONG)
				.setAction("OK", new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}).show();*/

	}
	public void SMS() {

		etone = preferences.getString("no1", null);
		ettwo = preferences.getString("no2", null);
		etthree = preferences.getString("no3", null);
		etfour = preferences.getString("no4", null);
		etfive = preferences.getString("no5", null);
		etMessage1 = preferences.getString("msg", null);

		final String mylocation;
		// to change the location
		mylocation = " Location (" + latitude + "," + longitude + ") && "
				+ address1;
		final String etMessage =etMessage1+"\n"+ preferences.getString("address",null) + "http://maps.google.com/maps?z=11&t=k&q=loc:"+latitude+","+longitude;
		System.out.println(etMessage);
		ArrayList<String> strings = new ArrayList<>();
		int index = 0;
		while (index < etMessage.length()) {
			String value=etMessage.substring(index, Math.min(index + 150,etMessage.length()));
			Log.i("message parts", "SMS: "+value);
			strings.add(value);
			index += 150;

		}
		try {

			SmsManager smsManager = null;
			int count = 0;
		
				if (etone != null) {
					if(etone.length()>5){
					smsManager = SmsManager.getDefault();

					smsManager.sendMultipartTextMessage(etone.trim(),null,strings,null,null);

					//					smsManager.sendTextMessage(etone.trim(), null, etMessage, null,
//							null);
					Log.d("sms1 ", "sent" + etone.trim());
					count++;
					}
				}
			
	
			if (ettwo != null) {
				if(ettwo.length()>5){
					smsManager = SmsManager.getDefault();
					smsManager.sendMultipartTextMessage(ettwo.trim(),null,strings,null,null);

					Log.d("sms2 ", "sent" + ettwo.trim());
					count++;
				}
			
			}
		

			
			if (etthree != null) {
				if(etthree.length()>5){
					smsManager = SmsManager.getDefault();
					smsManager.sendMultipartTextMessage(etthree.trim(),null,strings,null,null);
					Log.d("sms3 ", "sent");
					count++;
				}
				
			}
			if (etfour != null) {
				if(etfour.length()>5){
				smsManager = SmsManager.getDefault();
					smsManager.sendMultipartTextMessage(etfour.trim(),null,strings,null,null);

					Log.d("sms4 ", "sent");
				count++;
				}
			}
			if (etfive != null) {
				if(etfive.length()>5){
				smsManager = SmsManager.getDefault();
					smsManager.sendMultipartTextMessage(etfive.trim(),null,strings,null,null);

					Log.d("sms5 ", "sent");
				count++;
				}
			}
			if (count > 0) {
				Toast.makeText(getApplicationContext(), "SMS sent on "+count+" Contacts",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Please set contact first", Toast.LENGTH_LONG).show();
			}

		} catch (final Exception e) {

			Toast.makeText(getApplicationContext(),
					"SMS failed, please try again later!", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();

		}

	}
	public void refresh(View v) {
		FetchLocation fd=new FetchLocation();
		fd.execute();

	

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		FetchLocation fd=new FetchLocation();
		fd.execute();

		GetCurrentAddress currentadd = new GetCurrentAddress();
		currentadd.execute();

	}

	public void sendPanicAlert(View view) {
		/*Intent intent1 = new Intent(this, SendSMS.class);
		intent1.putExtra("LAT", latitude);
		intent1.putExtra("LON", longitude);
		intent1.putExtra("address", tvLocation.getText().toString());
		startActivity(intent1);*/
		SMS();
	}

	public void enterRadiusPolice(View view) {
		Intent intent = new Intent(this, EnterRadiusActivity.class);
		intent.putExtra("LAT", latitude);
		intent.putExtra("LON", longitude);
		startActivity(intent);
	}

	public void enterRadiusHospital(View view) {
		Intent intent = new Intent(this, EnterRadiusActivity3.class);
		intent.putExtra("LAT", latitude);
		intent.putExtra("LON", longitude);
		startActivity(intent);
	}

	public void enterRadiusFire(View view) {
		Intent intent = new Intent(this, EnterRadiusActivity2.class);
		intent.putExtra("LAT", latitude);
		intent.putExtra("LON", longitude);
		startActivity(intent);
	}

	public void aboutus(View view) {
		Intent intent = new Intent(this, Aboutus.class);
		startActivity(intent);
	}

	public void enterRadiusCafe(View view) {

		Intent intent = new Intent(this, EnterRadiusActivity4.class);
		intent.putExtra("LAT", latitude);
		intent.putExtra("LON", longitude);
		startActivity(intent);
	}

	public void enterEmergencyContact(View view) {

		Intent intent = new Intent(getBaseContext(), AddEmergencyNo.class);
		startActivity(intent);
	}

	public String getAddress(Context ctx, double latitude, double longitude) {
		StringBuilder result = new StringBuilder();
		try {
			Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(latitude,
					longitude, 1);
			if (addresses.size() > 0) {
				Address address = addresses.get(0);

				String locality = address.getLocality();
				String region_code = address.getCountryCode();
				result.append(""
						+ address.getAddressLine(0) + " "
					//	+ address.getAddressLine(1) + " "
						+ address.getPostalCode() + " ");
				result.append(locality + " ");
				result.append(region_code);

			}
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}

		return result.toString();
	}
String address1="Current Address: ";
	private class GetCurrentAddress extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			// this lat and log we can get from current location but here we
			// given hard coded
			address1="Current Address: ";
			System.out.println("lati=" + latitude + " longi=" + longitude);

			String address = getAddress(context,Double.parseDouble(latitude), Double.parseDouble(longitude));
			System.out.println("address=" + address);
			
			return address;
		}

		@Override
		protected void onPostExecute(String resultString) {
			// dialog.dismiss();
			address1=address1+resultString;
			tvLocation.setText(address1+latlon);
			preferences.edit().putString("address",address1+latlon ).commit();
			preferences.edit().putString("location",latlon ).commit();
		}
	}

	String latlon;
	
	LocationGetter location;
	class FetchLocation extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 location=new LocationGetter();
			runOnUiThread(new Runnable() {
				public void run() {
					try{
						latitude= Double.toString(location.getLocation(MainActivity.this).getLatitude());
						longitude= Double.toString(location.getLocation(MainActivity.this).getLongitude());
						GetCurrentAddress currentadd = new GetCurrentAddress();
						currentadd.execute();
					}catch(Exception e){}
				
				}
			});
			 
			/*latitude= Double.toString(newlat());
			longitude= Double.toString(newlong());*/
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			runOnUiThread(new Runnable() {
				public void run() {
					if(latitude==null||longitude==null){
						
					}else{
						Toast.makeText(getBaseContext(), "Latitude,Longitude "+latitude+","+longitude, Toast.LENGTH_SHORT).show();
						latlon= "\nCurrent Location: "+latitude+","+longitude;
						tvLocation.setText(address1+latlon);
					}
				}
			});
			
		}
	}
	
}
