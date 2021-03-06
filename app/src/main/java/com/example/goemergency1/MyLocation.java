package com.example.goemergency1;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

public class MyLocation extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		
		Double newlat=newlat();
		Double newlon=newlong();//?z=11&t=k&q=loc:58 41.881N 152 31.324W 
			String uri = "http://maps.google.com/maps?z=11&t=k&q=loc:"+newlat+" "+newlon;
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
		intent.setComponent((new ComponentName("com.google.android.apps.maps",  
	    "com.google.android.maps.MapsActivity")));
		startActivity(intent);
	}
	public double newlat() {
		Location location = null;
		try{
		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(context);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		
		 location = locationManager.getLastKnownLocation(provider);
		
		 //seconds and meter
		locationManager.requestLocationUpdates(provider, 1000, 10,
				locationListener);
		}catch (Exception e){
			
		}
		return updateWithNewLatitude(location);
	}

	public double newlong() {
		Location location = null;
		try{
	
		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(context);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		
		locationManager.requestLocationUpdates(provider, 1000, 10,
				locationListener);
		location = locationManager.getLastKnownLocation(provider);
		
		}catch(Exception e){
			
		}
	
		return updateWithNewLongitude(location);
	}

	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			updateWithNewLatitude(location);
			updateWithNewLongitude(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			updateWithNewLatitude(null);
			updateWithNewLongitude(null);
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	private double updateWithNewLatitude(Location location) {

		// myLocationText = (TextView) findViewById(R.id.myLocationText);
		if (location != null) {
			double lat = location.getLatitude();
			return lat;
			// double lng = location.getLongitude();
			// latLongString = "Lat:" + lat + "\nLong:" + lng;
		} else {
			return 0.0;
			// latLongString = "No location found";
		}
		// myLocationText.setText("Your Current Position is:\n" +
		// latLongString);
	}

	private double updateWithNewLongitude(Location location) {

		// myLocationText = (TextView) findViewById(R.id.myLocationText);
		if (location != null) {
			double lat = location.getLongitude();
			return lat;
			// double lng = location.getLongitude();
			// latLongString = "Lat:" + lat + "\nLong:" + lng;
		} else {
			return 0.0;
			// latLongString = "No location found";
		}
		// myLocationText.setText("Your Current Position is:\n" +
		// latLongString);
	}
}
