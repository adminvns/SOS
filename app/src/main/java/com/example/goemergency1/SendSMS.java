package com.example.goemergency1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendSMS extends Activity {
	private String etone,ettwo,etthree,etfour,etfive;
	private String etMessage1;
	String lat="",lon="";
	String address="";
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent i=getIntent();
		lat=i.getStringExtra("LAT");
		lon=i.getStringExtra("LON");
		address=i.getStringExtra("address");
		dialog=ProgressDialog.show(SendSMS.this, "Please wait..", "Sending sms");
		SendSms1 send=new SendSms1();
		send.execute();
	}
	public class SendSms1 extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SMS();
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
						Toast.makeText(getApplicationContext(), "SMS Sent! on"+etone+","+ettwo+","+etthree+","+etfour+","+etfive,
				Toast.LENGTH_LONG).show();

				}
			});
			finish();
		}
		
	}
public void SMS(){
	try {
		BufferedReader inputReader;
		inputReader = new BufferedReader(new InputStreamReader(
				openFileInput("emergency1.txt")));
		String inputString;
		StringBuffer stringBuffer = new StringBuffer();
		while ((inputString = inputReader.readLine()) != null) {
			stringBuffer.append(inputString + "\n");
		}
		etone=stringBuffer.toString();
		stringBuffer = new StringBuffer();

		inputReader = new BufferedReader(new InputStreamReader(
				openFileInput("emergency2.txt")));

		while ((inputString = inputReader.readLine()) != null) {
			stringBuffer.append(inputString + "\n");
		}

		ettwo=stringBuffer.toString();
		stringBuffer = new StringBuffer();
		inputReader = new BufferedReader(new InputStreamReader(
				openFileInput("emergency3.txt")));

		while ((inputString = inputReader.readLine()) != null) {
			stringBuffer.append(inputString + "\n");
		}

		etthree=stringBuffer.toString();
		stringBuffer = new StringBuffer();
		inputReader = new BufferedReader(new InputStreamReader(
				openFileInput("emergency4.txt")));

		while ((inputString = inputReader.readLine()) != null) {
			stringBuffer.append(inputString + "\n");
		}
		etfour=stringBuffer.toString();
		stringBuffer = new StringBuffer();
		inputReader = new BufferedReader(new InputStreamReader(
				openFileInput("emergency5.txt")));

		while ((inputString = inputReader.readLine()) != null) {
			stringBuffer.append(inputString + "\n");
		}
		etfive=stringBuffer.toString();
		stringBuffer = new StringBuffer();
		inputReader = new BufferedReader(new InputStreamReader(
				openFileInput("message.txt")));

		while ((inputString = inputReader.readLine()) != null) {
			stringBuffer.append(inputString + "\n");
		}
		etMessage1=stringBuffer.toString();
		stringBuffer = new StringBuffer();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	final String mylocation;
	//to change the location 
	mylocation=" Location ("+lat+","+lon+") && "+address;
	final String etMessage=etMessage1+mylocation;
	System.out.println(etMessage);
	try {
		
		SmsManager smsManager=SmsManager.getDefault();
		
		if (!etone.equalsIgnoreCase("")) {
		smsManager.sendTextMessage(etone.trim(),
					null, etMessage , null,
					null);
			Log.d("sms1 ", "sent");
		}
		
		if (!ettwo.equalsIgnoreCase("")) {
			smsManager.sendTextMessage(ettwo.trim(),
					null, etMessage,
					null,null);
			Log.d("sms2 ", "sent");
		}
		if (!etthree.equalsIgnoreCase("")) {
			smsManager.sendTextMessage(etthree.trim(),
					null, etMessage, null,
					null);
			Log.d("sms3 ", "sent");
		}
		if (!etfour.equalsIgnoreCase("")) {
			smsManager.sendTextMessage(etfour.trim(),
					null, etMessage, null,
					null);
			Log.d("sms4 ", "sent");
		}
		if (!etfive.equalsIgnoreCase("")) {
			smsManager.sendTextMessage(etfive.trim(),
					null, etMessage, null,
					null);
			Log.d("sms5 ", "sent");
		}
		
		
		
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(),
							"SMS failed, please try again later!",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			});
		
	}
	
}
}
