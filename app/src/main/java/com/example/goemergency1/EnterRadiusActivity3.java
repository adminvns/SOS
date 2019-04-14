package com.example.goemergency1;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterRadiusActivity3 extends Activity {

	private String latitude;
	private String longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		latitude=getIntent().getStringExtra("LAT");
		longitude=getIntent().getStringExtra("LON");
		setContentView(R.layout.activity_enter_radius_activity3);
		// Show the Up button in the action bar.
		Button btnFire=(Button) findViewById(R.id.btnHospitalRadius);
		btnFire.setOnClickListener(new View.OnClickListener() {
			
			private String radius;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText et = (EditText) findViewById(R.id.etHospital);
				radius = et.getText().toString();
				Intent intent = new Intent(getBaseContext(), GetPlace.class);
				//System.out.println(radius);
				intent.putExtra("LAT", latitude);
				intent.putExtra("LON", longitude);
				intent.putExtra("radius", radius);
				intent.putExtra("type", "hospital");
				startActivity(intent);	
			}
		});
	}

	

}
