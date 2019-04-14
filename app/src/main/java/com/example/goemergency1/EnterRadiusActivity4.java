package com.example.goemergency1;





import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterRadiusActivity4 extends Activity {
String latitude,longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_radius_activity2);
		latitude=getIntent().getStringExtra("LAT");
		longitude=getIntent().getStringExtra("LON");
		Button btnFire=(Button) findViewById(R.id.btnFireRadius);
		btnFire.setOnClickListener(new View.OnClickListener() {
			
			private String radius;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText et = (EditText) findViewById(R.id.etFire);
				radius = et.getText().toString();
				Intent intent = new Intent(getBaseContext(),GetPlace.class);
				System.out.println(radius);
				intent.putExtra("radius", radius);
				intent.putExtra("LAT", latitude);
				intent.putExtra("LON", longitude);
			//	intent.putExtra("type", "cafe");
				intent.putExtra("type", "restaurant");
				startActivity(intent);
			}
		});
		// Show the Up button in the action bar.
	
	}



}
