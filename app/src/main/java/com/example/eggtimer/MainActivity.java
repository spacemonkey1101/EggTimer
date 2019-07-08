 package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


 public class MainActivity extends AppCompatActivity {
     SeekBar seekBar;
      TextView textView;
      Boolean counterIsActive= false;//we must not be able to change
                              // the slider when the timer is running
     Button button ;
     CountDownTimer countDownTimer;

     public  void resetTimer()
     {
         textView.setText("0:30");
         seekBar.setProgress(30);
         countDownTimer.cancel();//stopping the running timer and starting the new
         button.setText("Go");
         counterIsActive =false;
         seekBar.setEnabled(true);

     }
     public void updateTimer(int secLeft){
         int min = (int)secLeft/60;//(#secs/60) = mins
         int sec = (int)secLeft%60;//#sec remaining

         //To solve the problem of leading zeroes in sec <10
         String seconds;
         if(sec<10)
             seconds = "0" + sec;
         else
             seconds = Integer.toString(sec);


         textView.setText(min + ":" + seconds);
     }

    public  void  timerControl(View view) {
        if (counterIsActive == false) {
            counterIsActive = true;
            seekBar.setEnabled(false);//user cant change the slider
            button.setText("Stop");


           countDownTimer= new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {//time in miliseconds
                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }
            }.start();

        }
        else {
            resetTimer();
        }
    }
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

          seekBar = findViewById(R.id.seekBar);
         textView = findViewById(R.id.textView);
         button =findViewById(R.id.button);

         seekBar.setMax(10*60);//10 mins in secs
         seekBar.setProgress(30); //current progress is set to 30 secs
         seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            updateTimer(progress);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });
         }



 }



