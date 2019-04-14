package com.example.goemergency1;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class FloatingFaceBubbleService extends Service {
    private WindowManager windowManager;
    private ImageView floatingFaceBubble;
    private static int count=0;
    public void onCreate() {
        super.onCreate();
        floatingFaceBubble = new ImageView(this);
        //a face floating bubble as imageView
        floatingFaceBubble.setImageResource(R.drawable.ic_launcher);

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        //here is all the science of params
        final LayoutParams myParams = new LayoutParams(
           100,
            100,
            LayoutParams.TYPE_PHONE,
            LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);

        myParams.gravity = Gravity.TOP | Gravity.LEFT;
        myParams.x=0;
        myParams.y=100;
        // add a floatingfacebubble icon in window
        windowManager.addView(floatingFaceBubble, myParams);


        try{
        	//for moving the picture on touch and slide
        	floatingFaceBubble.setOnTouchListener(new View.OnTouchListener() {
                LayoutParams paramsT = myParams;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private long touchStartTime = 0;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //remove face bubble on long press
                	if(System.currentTimeMillis()-touchStartTime>ViewConfiguration.getLongPressTimeout() && initialTouchX== event.getX()){
                		windowManager.removeView(floatingFaceBubble);
                		stopSelf();
                		return false;
                		
                	}
                	switch(event.getAction()){
                    
                    
                    case MotionEvent.ACTION_DOWN:
                    	touchStartTime = System.currentTimeMillis();
                        initialX = myParams.x;
                        initialY = myParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
count++;
                        if(count==2){
                            SMS();
                            count=0;
                        }


                        break;
                        case MotionEvent.ACTION_OUTSIDE:

                            break;
                    case MotionEvent.ACTION_MOVE:
                    	
                        myParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        myParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(v, myParams);
                        break;
                    }
                    return false;
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

    private String etone, ettwo, etthree, etfour, etfive;
    private String etMessage1;
    private SharedPreferences preferences;

    public void SMS() {
        preferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        etone = preferences.getString("no1", null);
        ettwo = preferences.getString("no2", null);
        etthree = preferences.getString("no3", null);
        etfour = preferences.getString("no4", null);
        etfive = preferences.getString("no5", null);
        etMessage1 = preferences.getString("msg", null);

        final String mylocation;
        // to change the location
        mylocation = preferences.getString("location", "NA");
        String arr[] = new String[1];
        arr[0] = mylocation;

        final String etMessage = etMessage1 + mylocation;
        System.out.println(etMessage);

        try {

            SmsManager smsManager = null;
            int count = 0;

            if (etone != null) {
                if (etone.length() > 5) {
                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(etone.trim(), null, etMessage,
                            null, null);
                    Log.d("sms1 ", "sent" + etone.trim());
                    count++;
                }
            }

            if (ettwo != null) {
                if (ettwo.length() > 5) {
                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(ettwo.trim(), null, etMessage,
                            null, null);
                    Log.d("sms2 ", "sent" + ettwo.trim());
                    count++;
                }

            }

            if (etthree != null) {
                if (etthree.length() > 5) {
                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(etthree.trim(), null, etMessage,
                            null, null);
                    Log.d("sms3 ", "sent");
                    count++;
                }

            }
            if (etfour != null) {
                if (etfour.length() > 5) {
                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(etfour.trim(), null, etMessage,
                            null, null);
                    Log.d("sms4 ", "sent");
                    count++;
                }
            }
            if (etfive != null) {
                if (etfive.length() > 5) {
                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(etfive.trim(), null, etMessage,
                            null, null);
                    Log.d("sms5 ", "sent");
                    count++;
                }
            }
            if (count > 0) {
                Toast.makeText(getApplicationContext(),
                        "SMS sent on " + count + " Contacts", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please set contact first", Toast.LENGTH_LONG).show();
            }
          PostTweetAsync tweet = new PostTweetAsync();
            tweet.execute(arr);
        } catch (final Exception e) {

            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();

        }

    }

    Status status;

    public class PostTweetAsync extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("X8NdwVZnwYCu55iC7VRLQ1HJs")
                    .setOAuthConsumerSecret(
                            "zeKVk1MExIoOZ4B714AXVpQXf6y3s9QXVHPQ42eoHIe490aRHC")
                    .setOAuthAccessToken(
                            "4538148973-UE49HezlFRZKodUEZUYXtxN9yxhMLsGz1zmFNqe")
                    .setOAuthAccessTokenSecret(
                            "2XhwAP24k40foLYnMOPwgTa4OrBHc2Geo1YV221DhPkCr");
            TwitterFactory factory = new TwitterFactory(cb.build());
            Twitter twitter = factory.getInstance();

            try {
                System.out.println(twitter.getScreenName());
                status = twitter.updateStatus(params[0]);

            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }
    }

}