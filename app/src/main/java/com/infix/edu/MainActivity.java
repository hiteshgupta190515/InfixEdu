package com.infix.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.infix.edu.R;
import com.infix.edu.activity.HomeActivity;
import com.infix.edu.activity.LoginActivity;
import com.infix.edu.myconfig.MyReceiver;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver myReceiver = null;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private boolean loggedIn = false;
    ProgressBar bar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar =  findViewById(R.id.splash_progress);

        myReceiver = new MyReceiver();

        broadcastIntent();

        new Thread(new Runnable() {

            int i = 0;
            int progressStatus = 0;

            public void run() {
                while (progressStatus < 100) {
                    progressStatus += doWork();
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            bar.setProgress(progressStatus);
                            i++;
                        }
                    });
                }
            }
            private int doWork() {

                return i * 3;
            }

        }).start();


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                //If we will get true
                if(loggedIn){
                    //We will start the Profile Activity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean("isLoged", false);

    }

    public void broadcastIntent() {
        registerReceiver(myReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

}

