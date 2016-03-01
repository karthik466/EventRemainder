package com.karthik.example.calender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ShowAlarm extends Activity {

    public final String TAG = this.getClass().getSimpleName();
    Context context=ShowAlarm.this;
    String tone;
    private MediaPlayer mPlayer;
    Vibrator vibrator;
    int mHours,mMintues;
    TextView  txtTitle,txtMsg;
    SharedPreferences pref;
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         pref=getSharedPreferences("myCalender", MODE_PRIVATE);
        //Setup layout
        this.setContentView(R.layout.show_alarm);
       
        Intent timeIntent=getIntent();
        mHours=timeIntent.getExtras().getInt("hours");
        mMintues=timeIntent.getExtras().getInt("min");
        
        TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
        tvTime.setText(String.format("%02d : %02d", mHours, mMintues));
        
        txtTitle= (TextView) findViewById(R.id.alarm_screen_name);
        txtTitle.setText(timeIntent.getExtras().getString("title"));
        
        txtMsg= (TextView) findViewById(R.id.alarm_screen_msg);
        txtMsg.setText(timeIntent.getExtras().getString("msg"));
        
       // Log.d("Alarm Show", ""Utilsce.mHours+"::"+Utils.mMintues);

        vibrator= (Vibrator)getSystemService(VIBRATOR_SERVICE) ;

        Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              if(mPlayer.isPlaying()) {
                  mPlayer.stop();
              }
                vibrator.cancel();
                finish();
            }
        });
 
        tone = pref.getString("ringTone", "");
        	
        
        
        //Play alarm tone
        mPlayer = new MediaPlayer();
        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    mPlayer.setDataSource(this, toneUri);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM | AudioManager.VIBRATE_SETTING_ON);
                    //mPlayer.setLooping(true);
                    mPlayer.prepare();
                    mPlayer.start();
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            finish();
                        }
                    });
                }
            }
            else
            {
                vibrator.vibrate(10*1000);
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

  
    @Override
    protected void onResume() {
        super.onResume();

        // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);     

    }

    @Override
    protected void onPause() {
        super.onPause();
        vibrator.cancel();
        if(mPlayer.isPlaying()){
        mPlayer.stop();
        mPlayer.release();
        }
    }
}

