package com.example.kittipong.alarmtestt;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowEvent extends Activity implements OnClickListener {



    MediaPlayer mpBgm;
    PowerManager pm;
    WakeLock wl;
    KeyguardManager km;
    KeyguardLock kl;
    Ringtone r;
    Button btnStop;
    Button content;


   // public static final String editextt = "edtKey";

   // SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sharedPreferences = getSharedPreferences("MyPreFs", Context.MODE_PRIVATE);

        mpBgm = MediaPlayer.create(ShowEvent.this, R.raw.lolgg);
        mpBgm.setLooping(true);
        mpBgm.start();

        Log.i("ShowEvent", "onCreate() in DismissLock");
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("ShowEvent");
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "ShowEvent");
        wl.acquire(); //wake up the screen
        kl.disableKeyguard();
        setContentView(R.layout.sec);


        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

        TextView _textView = (TextView)findViewById(R.id.textView11);
        _textView.setText(sharedPreferences.getString("content",null));


    }


        @Override
        public void onClick (View v){


           if (v.getId() == R.id.btnStop) {
                MainActivity.listValue.remove(0);
                this.finish();
            }
        }


    @Override
    protected void onResume() {

        super.onResume();
        wl.acquire();//must call this!

       /* Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if(notif==null){
            notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if(notif==null){
                notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }*/
        //r = RingtoneManager.getRingtone(getApplicationContext(), notif);


      //  r.play();
        mpBgm.start();



    }

    @Override
    public void onPause(){
        super.onPause();
        wl.release();

        if(mpBgm.isPlaying()){
         //   r.stop();
            mpBgm.stop();
        }
    }

}
